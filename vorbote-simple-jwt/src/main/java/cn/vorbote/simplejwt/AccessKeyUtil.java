package cn.vorbote.simplejwt;

import cn.vorbote.core.time.DateTime;
import cn.vorbote.core.time.TimeSpan;
import cn.vorbote.core.utils.MapUtil;
import cn.vorbote.simplejwt.annotations.JwtIgnore;
import cn.vorbote.simplejwt.choices.JwtAlgorithm;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.*;

/**
 * JWT Token implementation, easy to use.
 *
 * @author vorbote thills@vorbote.cn
 */
@Slf4j
public class AccessKeyUtil {
    /*
        iss: jwt签发者
        sub: jwt所面向的用户
        aud: 接收jwt的一方
        exp: jwt的过期时间，这个过期时间必须要大于签发时间
        nbf: 定义在什么时间之前，该jwt都是不可用的.
        iat: jwt的签发时间
        jti: jwt的唯一身份标识，主要用来作为一次性token，从而回避重放攻击。
    */

    private final JwtAlgorithm algorithm;
    private String secret;
    private String issuer;

    protected String getSecret() {
        return secret;
    }

    protected void setSecret(String secret) {
        this.secret = secret;
    }

    protected String getIssuer() {
        return issuer;
    }

    protected void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    protected JwtAlgorithm getAlgorithm() {
        return algorithm;
    }

    public AccessKeyUtil(@NonNull JwtAlgorithm algorithm, @NonNull String secret, @NonNull String issuer) {
        this.algorithm = algorithm;
        this.secret = secret;
        this.issuer = issuer;
    }

    /**
     * Build basic info such as subject, audience, and so on.
     *
     * @param subject  The subject of this jwt.
     * @param audience The audiences of this jwt.
     * @param expire   The jwt will expire after this time period.
     * @param builder  The builder of this jwt.
     */
    private void buildBasicInfo(String subject, String[] audience, TimeSpan expire, JWTCreator.Builder builder) {
        DateTime now = DateTime.now();

        builder.withIssuer(issuer);
        builder.withIssuedAt(now.toDate());
        builder.withNotBefore(now.toDate());
        builder.withAudience(audience);
        builder.withSubject(subject);
        now.add(expire);
        builder.withExpiresAt(now.toDate());
        builder.withJWTId(UUID.randomUUID().toString());
    }

    /**
     * Build claims into the builder.
     *
     * @param claims  The claims.
     * @param builder The builder.
     */
    private void buildClaims(Map<String, Object> claims, JWTCreator.Builder builder) {
        if (claims != null) {
            for (Map.Entry<String, Object> e : claims.entrySet()) {
                builder.withClaim(e.getKey(), e.getValue().toString());
            }
        }
    }

    /**
     * Build this token.
     *
     * @param builder The JWT's builder.
     * @return The built token.
     */
    private String buildToken(JWTCreator.Builder builder) {
        String token;

        switch (algorithm) {
            case HS256 -> token = builder.sign(Algorithm.HMAC256(secret));
            case HS384 -> token = builder.sign(Algorithm.HMAC384(secret));
            case HS512 -> token = builder.sign(Algorithm.HMAC512(secret));
            default -> {
                token = builder.sign(Algorithm.HMAC256(secret));
                log.error("This algorithm is not supported yet, will use HMAC256 by default.");
            }
        }

        return token;
    }

    /**
     * Create a new Token. All the items in claims will be set as a String into this JSON Web
     * Token.
     *
     * @param expire   Specify when will the token be expired. (Unit: Second)
     * @param subject  Specify the users will be faced.
     * @param audience Specify who will receive this token.
     * @param claims   Give some info need to be transformed by token, can be null when
     *                 you don't need to pass any information.
     * @return A token string.
     */
    public String createToken(TimeSpan expire, String subject, String[] audience, Map<String, Object> claims) {
        final JWTCreator.Builder builder = JWT.create();
        buildBasicInfo(subject, audience, expire, builder);
        buildClaims(claims, builder);
        return buildToken(builder);
    }

    /**
     * Create a new token with all data(except null data) in the
     * bean.
     *
     * @param expire    After this time span, this jwt will be
     *                  expired and is not be able to use again.
     * @param subject   The subject of this jwt.
     * @param audiences The audiences of this jwt.
     * @param bean      The bean contains any possible users' data.
     * @return A token generated by the data of the bean.
     * @throws IllegalAccessException if one {@code Field} object
     *                                is enforcing Java language
     *                                access control and the underlying
     *                                field is inaccessible.
     * @see Class#getDeclaredFields()
     * @see Field#get(Object)
     * @see Field#setAccessible(boolean)
     * @see JWTCreator.Builder#withClaim(String, Boolean)
     * @see JWTCreator.Builder#withClaim(String, Date)
     * @see JWTCreator.Builder#withClaim(String, Double)
     * @see JWTCreator.Builder#withClaim(String, Integer)
     * @see JWTCreator.Builder#withClaim(String, List)
     * @see JWTCreator.Builder#withClaim(String, Long)
     * @see JWTCreator.Builder#withClaim(String, Map)
     * @see JWTCreator.Builder#withClaim(String, String)
     */
    public String createTokenWithBean(TimeSpan expire, String subject, String[] audiences, Object bean) throws IllegalAccessException {
        final JWTCreator.Builder builder = JWT.create();
        buildBasicInfo(subject, audiences, expire, builder);

        Class<?> beanClass = bean.getClass();
        Field[] fields = beanClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(JwtIgnore.class))
                continue;
            field.setAccessible(true);
            var fieldName = field.getName();
            var fieldValue = field.get(bean);
            // 跳过空数据
            if (fieldValue != null) {
                // 经过实验，无法通过动态转换进行 withClaim 运算，因此只能一个一个进行 instanceof 运算
                // 并进行强制转换
                // region 强制转换
                if (fieldValue instanceof Boolean v) {
                    builder.withClaim(fieldName, v);
                } else if (fieldValue instanceof Double v) {
                    builder.withClaim(fieldName, v);
                } else if (fieldValue instanceof Float v) {
                    builder.withClaim(fieldName, v.doubleValue());
                } else if (fieldValue instanceof Integer v) {
                    builder.withClaim(fieldName, v);
                } else if (fieldValue instanceof Long v) {
                    builder.withClaim(fieldName, v);
                } else if (fieldValue instanceof String v) {
                    builder.withClaim(fieldName, v);
                } else if (fieldValue instanceof Date v) {
                    builder.withClaim(fieldName, v);
                } else if (fieldValue instanceof DateTime v) {
                    builder.withClaim(fieldName, v.toDate());
                } else if (fieldValue instanceof List<?> v) {
                    builder.withClaim(fieldName, v);
                } else {
                    log.error("The property [{}] is of an unsupported type and will be converted to String, please " +
                            "ensure that the toString() method is overridden correctly! ", fieldName);
                    builder.withClaim(fieldName, fieldValue.toString());
                }
                // endregion
            }
        }

        return buildToken(builder);
    }

    /**
     * Check whether the token is valid. This method will happen
     * nothing when the token is valid, or throw some exception
     * when token is invalid.
     *
     * @param token The token.
     */
    public void verify(String token) {
        info(token);
    }

    /**
     * Decode the token, and you can easily get some info from
     * this token.
     *
     * @param token The token.
     * @return The decoded jwt token.
     * @throws com.auth0.jwt.exceptions.AlgorithmMismatchException     If the algorithm stated in the token's
     *                                                                 header it's not equal to the one
     *                                                                 defined in the JWTVerifier.
     * @throws com.auth0.jwt.exceptions.SignatureVerificationException If the signature is invalid.
     * @throws com.auth0.jwt.exceptions.TokenExpiredException          If the token has expired.
     * @throws com.auth0.jwt.exceptions.InvalidClaimException          If a claim contained a different value
     *                                                                 than the expected one.
     * @throws com.auth0.jwt.exceptions.JWTVerificationException       If any of the verification steps fail
     * @see JWTVerifier#verify(String)
     */
    public DecodedJWT info(String token) {
        JWTVerifier verifier;
        switch (algorithm) {
            case HS256 -> verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            case HS384 -> verifier = JWT.require(Algorithm.HMAC384(secret)).build();
            case HS512 -> verifier = JWT.require(Algorithm.HMAC512(secret)).build();
            default -> {
                // 这里理论上应该抛出异常的，但是实在是懒得做了，就先这样吧。
                // 至于其他的算法，后续再考虑加上。
                verifier = JWT.require(Algorithm.HMAC256(secret)).build();
                log.error("This algorithm is not supported yet, will use HMAC256 by default.");
            }
        }
        return verifier.verify(token);
    }

    /**
     * Renew the token.
     *
     * @param token       The original token.
     * @param expireAfter The time period (seconds) when the new token expired.
     * @return The renewed token.
     */
    public String renew(String token, TimeSpan expireAfter) {
        final DecodedJWT info = this.info(token);
        final HashMap<String, Object> map = new HashMap<>();
        // 排除一些JWT已经定义好用处的字段
        List<String> keys = Arrays.asList("aud", "sub", "nbf", "iss", "exp", "iat", "jti");
        for (Map.Entry<String, Claim> e : info.getClaims().entrySet()) {
            if (!keys.contains(e.getKey())) {
                map.put(e.getKey(), e.getValue().asString());
            }
        }

        String[] audiences = info.getAudience().toArray(new String[0]);
        return createToken(expireAfter, info.getSubject(), audiences, map);
    }

    /**
     * Renew this token with the data in the bean. This method will auto distract
     * the data needed by required type.
     *
     * @param token        The original token.
     * @param expireAfter  Expire after this time.
     * @param requiredType The required type's class.
     * @return A new token.
     * @throws Exception This method is using lots of methods could cause some exception, please see its
     *                   upriver methods.
     * @see #createTokenWithBean(TimeSpan, String, String[], Object)
     * @see #getBean(String, Class)
     * @see List#toArray(Object[])
     */
    public String renewWithBean(String token, TimeSpan expireAfter, Class<?> requiredType)
            throws Exception {
        final DecodedJWT info = this.info(token);
        Object bean = getBean(token, requiredType);
        String[] audiences = info.getAudience().toArray(new String[0]);

        return createTokenWithBean(expireAfter, info.getSubject(), audiences, bean);
    }

    /**
     * Get the bean in the token. In this method, you have to
     * make sure that your stored info is in the format of
     * key-value pair and which is stored in the claims. And
     * the key must be the same with that field in the required
     * type (such as the field name in required is declared as
     * {@code private String name;}, then your key must be
     * {@code name}). Meanwhile, the setter for this field is
     * required either. Be advised, the field log and logger
     * is thought as a helper field, thus, it will not be put
     * into the token.
     *
     * @param token        The user token.
     * @param requiredType The class of user.
     * @param <T>          The type of the bean.
     * @return The user bean.
     * @throws Exception This method is using lots of methods
     *                   could cause some exception, please
     *                   see its upriver methods.
     * @see Class#getConstructor(Class...)
     * @see Class#getDeclaredFields()
     * @see java.lang.reflect.Constructor#newInstance(Object...)
     * @see Map#get(Object)
     * @see MapUtil#setFieldValue(Object, String, Object)
     */
    public <T> T getBean(String token, Class<T> requiredType)
            throws Exception {
        // 创建token的解析对象
        Map<String, Claim> tokenInfo = info(token).getClaims();

        // 获取默认无参构造并创建对象
        T bean = requiredType.getConstructor().newInstance();

        Field[] fields = requiredType.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(JwtIgnore.class))
                continue;
            String fieldName = field.getName();
            if (fieldName.equalsIgnoreCase("log") || fieldName.equalsIgnoreCase("logger"))
                continue;
            // 根据名字创建属性并设置值
            Object fieldValue = Optional.ofNullable(tokenInfo.get(fieldName))
                    .map(claim -> claim.as(field.getType()))
                    .orElse(null);
            if (fieldValue != null) {
                log.debug("为{}注入数据：{}", fieldName, fieldValue);
                log.debug("数据值：{}，数据类型：{}", fieldValue, fieldValue.getClass());
                MapUtil.setFieldValue(bean, fieldName, fieldValue);
            }
        }

        return bean;
    }
}
