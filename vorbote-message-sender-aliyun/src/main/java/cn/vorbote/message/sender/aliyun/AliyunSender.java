package cn.vorbote.message.sender.aliyun;

import cn.vorbote.core.exceptions.NotImplementedException;
import cn.vorbote.message.auth.UserProfile;
import cn.vorbote.message.model.BatchMessageRequest;
import cn.vorbote.message.model.MessageRequest;
import cn.vorbote.message.model.MessageResponse;
import cn.vorbote.message.sender.IMessageSender;
import cn.vorbote.message.sender.aliyun.config.AliyunConfig;
import cn.vorbote.message.sender.aliyun.config.AliyunRegion;
import cn.vorbote.message.sender.aliyun.models.SendMessageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.crypto.Mac;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Text Message sender implemented on aliyun service.<br>
 * Created at 04/12/2022 19:41
 *
 * @author theod
 * @since 3.x
 */
@Slf4j
public final class AliyunSender implements IMessageSender<Map<String, Object>> {

    private final UserProfile userProfile;

    private final String sign;

    private final ObjectMapper objectMapper;

    private final OkHttpClient okHttpClient;

    /**
     * Create a aliyun sender instance.
     *
     * @param userProfile  user's access key id and access key secret
     * @param sign         the sign to the sms
     * @param objectMapper jackson json serializer
     * @param okHttpClient http client to send some web requests
     */
    public AliyunSender(UserProfile userProfile, String sign, ObjectMapper objectMapper, OkHttpClient okHttpClient) {
        this.userProfile = userProfile;
        this.sign = sign;
        this.objectMapper = objectMapper;
        this.okHttpClient = okHttpClient;
    }

    /**
     * Create a aliyun sender instance.
     * <p>
     * If you use this constructor, a new instance of {@code OkHttpClient} will be generated.
     *
     * @param userProfile  user's access key id and access key secret
     * @param sign         the sign to the sms
     * @param objectMapper jackson json serializer
     */
    public AliyunSender(UserProfile userProfile, String sign, ObjectMapper objectMapper) {
        this(userProfile, sign, objectMapper, new OkHttpClient());
    }

    /**
     * Create a aliyun sender instance.
     * <p>
     * If you use this constructor, a new instance of {@code ObjectMapper} will be generated.
     *
     * @param userProfile  user's access key id and access key secret
     * @param sign         the sign to the sms
     * @param okHttpClient http client to send some web requests
     */
    public AliyunSender(UserProfile userProfile, String sign, OkHttpClient okHttpClient) {
        this(userProfile, sign, new ObjectMapper(), okHttpClient);
    }

    /**
     * Create a aliyun sender instance.
     * <p>
     * If you use this constructor, a new instance of {@code ObjectMapper} and {@code OkHttpClient} will be generated.
     *
     * @param userProfile user's access key id and access key secret
     * @param sign        the sign to the sms
     */
    public AliyunSender(UserProfile userProfile, String sign) {
        this(userProfile, sign, new ObjectMapper(), new OkHttpClient());
    }

    /**
     * Encode the url to match standards.
     *
     * @param value the url to encode
     * @return encoded string that matches url format.
     */
    private static String specialUrlEncode(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, "UTF-8")
                .replace("+", "%20")
                .replace("*", "%2A")
                .replace("%7E", "~");
    }

    /**
     * Sign the request to string.
     *
     * @param accessSecret the AccessKeySecret from the Aliyun to access the resource on your account
     * @param stringToSign the string you want to sign
     * @return a signed string, or null when any exception occurred
     */
    private static String sign(String accessSecret, String stringToSign) {
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(new javax.crypto.spec.SecretKeySpec(accessSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA1"));
            byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(signData);
        } catch (NoSuchAlgorithmException | InvalidKeyException exception) {
            log.error(exception.getMessage());
            return null;
        }
    }

    /**
     * Ask Aliyun to send a text message to the specified receiver.
     * <p>
     * The aliyun server kept reporting <b>Signature Mismatch</b> when sending via POST method, so now uses GET method
     * to prevent a mismatched signature.
     *
     * @param region  the region which can offer sms service on aliyun you want to use
     * @param request a request including info to send a text message to specified receiver
     * @return a response including info about how was the sms you just sent.
     * @throws IOException {@code OkHttpClient} may occur IOException, or jackson {@code ObjectMapper} may produce a
     *                     JsonParseException
     * @see ObjectMapper#writeValueAsString(Object)
     * @see Call#execute()
     */
    public MessageResponse send(AliyunRegion region, MessageRequest<Map<String, Object>> request) throws IOException {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        simpleDateFormat.setTimeZone(new SimpleTimeZone(0, "GMT"));// 这里一定要设置GMT时区

        Map<String, String> params = new TreeMap<String, String>() {{
            // following codes are system parameters
            put("SignatureMethod", AliyunConfig.SIGNATURE_METHOD);
            put("SignatureNonce", UUID.randomUUID().toString());
            put("AccessKeyId", userProfile.secretId());
            put("SignatureVersion", AliyunConfig.SIGNATURE_VERSION);
            put("Timestamp", simpleDateFormat.format(new Date()));
            put("Format", AliyunConfig.FORMAT);
            put("Action", AliyunConfig.ACTION);
            put("Version", AliyunConfig.VERSION);

            // following codes are service parameters
            put("RegionId", region.getRegion());
            put("PhoneNumbers", request.getReceiver());
            put("SignName", sign);
            put("TemplateParam", objectMapper.writeValueAsString(request.getParams()));
            put("TemplateCode", request.getTemplateId());
        }};

        // build the string to be signed
        Iterator<String> iterator = params.keySet().iterator();
        StringBuilder sortedQueryStringBuilder = new StringBuilder();
        while (iterator.hasNext()) {
            String key = iterator.next();
            sortedQueryStringBuilder.append("&").append(specialUrlEncode(key)).append("=").append(specialUrlEncode(params.get(key)));
        }

        // remove the fist and(&) sign at the lead of the string
        String stringToSign = "GET" + "&" +
                specialUrlEncode("/") + "&" +
                specialUrlEncode(sortedQueryStringBuilder.substring(1));
        log.trace("Building the string to sign, value is [{}].", stringToSign);

        String signature = specialUrlEncode(sign(userProfile.secretKey() + "&", stringToSign));
        String url = "https://" + region.getEndpoint() + // host
                "/?Signature=" + signature + // signature
                sortedQueryStringBuilder; // request params

        log.trace("Sending request to {}", url);
        Request webCall = new Request.Builder()
                .url(url)
                .get()
                .build();
        MessageResponse messageResponse;
        try (Response webResp = okHttpClient.newCall(webCall).execute()) {
            String jsonResp = Optional.of(webResp)
                    .map(Response::body)
                    .map((item) -> {
                        try {
                            return item.string();
                        } catch (IOException e) {
                            log.error(e.getMessage());
                            return null;
                        }
                    })
                    .orElse("{}");
            log.trace(jsonResp);

            SendMessageResponse resp = objectMapper.readValue(jsonResp, SendMessageResponse.class);
            log.trace("Response entity: {}", resp);
            messageResponse = MessageResponse.initResponse(resp.getCode(), resp.getMessage());
        }

        return messageResponse;
    }

    /**
     * Ask Aliyun to send a text message to the specified receiver.
     * <p>
     * The region will be set to <b>Hangzhou</b> by default.
     *
     * @param request a request including info to send a text message to specified receiver
     * @return a response including info about how was the sms you just sent.
     * @throws IOException {@code OkHttpClient} may occur IOException, or jackson {@code ObjectMapper} may produce a
     *                     JsonParseException
     * @see #send(AliyunRegion, MessageRequest)
     * @see ObjectMapper#writeValueAsString(Object)
     * @see Call#execute()
     */
    @Override
    public MessageResponse send(MessageRequest<Map<String, Object>> request) throws IOException {
        return send(AliyunRegion.HANGZHOU, request);
    }

    /**
     * Send several messages to multiple recipients.
     *
     * @param request The data to send a sms.
     * @return The response data from sent message.
     */
    @Override
    public MessageResponse batchSend(BatchMessageRequest<Map<String, Object>> request) {
        throw new NotImplementedException("This method is not implemented yet. Please hold on for several versions.");
    }
}
