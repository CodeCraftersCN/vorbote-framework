package cn.vorbote.message.sender.tencent;

import cn.vorbote.core.exceptions.NotImplementedException;
import cn.vorbote.core.time.DateTime;
import cn.vorbote.message.auth.UserProfile;
import cn.vorbote.message.model.BatchMessageRequest;
import cn.vorbote.message.model.MessageRequest;
import cn.vorbote.message.model.MessageResponse;
import cn.vorbote.message.sender.IMessageSender;
import cn.vorbote.message.sender.tencent.config.TencentConfig;
import cn.vorbote.message.sender.tencent.config.TencentRegion;
import cn.vorbote.message.sender.tencent.models.SendMessageRequest;
import cn.vorbote.message.sender.tencent.models.SendMessageResponse;
import cn.vorbote.message.sender.tencent.models.SendStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * TencentSender<br>
 * Created at 02/09/2022 16:42
 *
 * @author theod
 */
@Slf4j
public final class TencentSender implements IMessageSender<List<String>> {

    private final UserProfile userProfile;

    private final String appId;

    private final String sign;

    private final ObjectMapper objectMapper;

    private final OkHttpClient okHttpClient;

    // ********************************
    // region constructors

    /**
     * Generate an SMS Sender which is using Tencent Cloud as the service provider.
     *
     * @param sign         the sign of messages
     * @param appId        the app id
     * @param objectMapper jackson ObjectMapper
     */
    public TencentSender(String sign, String appId, UserProfile userProfile, ObjectMapper objectMapper, OkHttpClient okHttpClient) {
        this.sign = sign;
        this.appId = appId;
        this.objectMapper = objectMapper;
        this.userProfile = userProfile;
        this.okHttpClient = okHttpClient;
    }

    public TencentSender(String sign, String appId, UserProfile userProfile, ObjectMapper objectMapper) {
        this(sign, appId, userProfile, objectMapper, new OkHttpClient());
    }

    public TencentSender(String sign, String appId, UserProfile userProfile, OkHttpClient okHttpClient) {
        this(sign, appId, userProfile, new ObjectMapper(), okHttpClient);
    }

    public TencentSender(String sign, String appId, UserProfile userProfile) {
        this(sign, appId, userProfile, new ObjectMapper(), new OkHttpClient());
    }
    // endregion
    // ********************************

    // ********************************
    // region private methods

    private byte[] hmac256(byte[] key, String message) {
        try {
            final Mac mac = Mac.getInstance("HmacSHA256");
            final SecretKeySpec secretKeySpec = new SecretKeySpec(key, mac.getAlgorithm());
            mac.init(secretKeySpec);
            return mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException | InvalidKeyException exception) {
            log.error(exception.getMessage());
            return null;
        }
    }

    private String sha256Hex(String s) {
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] d = md.digest(s.getBytes(StandardCharsets.UTF_8));
            return DatatypeConverter.printHexBinary(d).toLowerCase();
        } catch (NoSuchAlgorithmException exception) {
            log.error(exception.getMessage());
            return null;
        }
    }

    // endregion
    // ********************************

    /**
     * Send a SMS.
     *
     * @param request The data to send a sms.
     * @return The response data from sent message.
     * @throws JsonProcessingException ObjectMapper could make this exception because
     *                                 of the data is not serializable.
     */
    public MessageResponse send(TencentRegion region, MessageRequest<List<String>> request) throws IOException {
        final DateTime now = DateTime.now();
        final String date = now.pattern("yyyy-MM-dd").toString();

        // build the payload that will be uploaded to tencent servers
        SendMessageRequest payload = new SendMessageRequest()
                .setAppId(appId)
                .setSign(sign)
                .setTemplateId(request.templateId())
                .setParams(request.getParams())
                .setReceivers(Collections.singletonList(request.receiver()));

        // joint the canonical request string
        final String canonicalRequest = TencentConfig.SEND_METHOD + "\n" +
                TencentConfig.CANONICAL_URI + "\n" +
                TencentConfig.CANONICAL_QUERY_STRING + "\n" +
                TencentConfig.CANONICAL_HEADERS + "\n" +
                TencentConfig.SIGNED_HEADERS + "\n" +
                sha256Hex(objectMapper.writeValueAsString(payload));

        // joint the request string that need to be signed
        String credentialScope = date + "/" + TencentConfig.SERVICE + "/" + "tc3_service";
        String stringToSign = TencentConfig.ALGORITHM + "\n" +
                now.unix() + "\n" +
                credentialScope + "\n" +
                sha256Hex(canonicalRequest); // hashed canonical request

        // calculate signature
        byte[] secretDate = hmac256(("TC3" + userProfile.secretKey()).getBytes(StandardCharsets.UTF_8), date);
        byte[] secretService = hmac256(secretDate, TencentConfig.SERVICE);
        byte[] secretSigning = hmac256(secretService, "tc3_request");
        String signature = DatatypeConverter.printHexBinary(hmac256(secretSigning, stringToSign)).toLowerCase();

        // joint the header - authorization
        final String authorization = TencentConfig.ALGORITHM + " " +
                "Credential=" + userProfile.secretId() + "/" + credentialScope + ", " +
                "SignedHeaders=" + TencentConfig.SIGNED_HEADERS + ", " + "Signature=" + signature;

        // use okhttp send a request
        Request requestEntity = new Request.Builder()
                .url("https://" + TencentConfig.HOST)
                .addHeader("Authorization", authorization)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("X-TC-Action", TencentConfig.ACTION)
                .addHeader("X-TC-Timestamp", String.valueOf(now.unix()))
                .addHeader("X-TC-Version", TencentConfig.VERSION)
                .addHeader("X-TC-Region", region.getRegion())
                .post(RequestBody.create(objectMapper.writeValueAsString(payload),
                        MediaType.parse("application/json; charset=utf-8")))
                .build();

        MessageResponse messageResponse = null;

        try (Response response = okHttpClient.newCall(requestEntity).execute()) {
            JsonNode json = objectMapper.readTree(Optional.of(response).map(Response::body).map(item -> {
                try {
                    return item.string();
                } catch (IOException e) {
                    log.error(e.getMessage());
                    return null;
                }
            }).orElse("{}"));

            if (json.has("Response")) {
                // objectMapper.readValue(json.get("Response").toString(), SendMessageResponse.class);
                SendStatus resp = Optional.ofNullable(json.get("Response"))
                        .map(JsonNode::toString)
                        .map((item) -> {
                            try {
                                return objectMapper.readValue(item, SendMessageResponse.class);
                            } catch (JsonProcessingException e) {
                                log.error(e.getMessage());
                                return null;
                            }
                        })
                        .map(SendMessageResponse::getSendStatusSet)
                        .map((item) -> item.get(0))
                        .orElse(null);

                if (resp != null) {
                    log.trace("SMS Request has been sent, response message from server is {}, and code is {}",
                            resp.getMessage(), resp.getCode());
                    messageResponse = MessageResponse.initResponse(resp.getCode(), resp.getMessage());
                }

            }
        }

        return messageResponse;
    }

    @Override
    public MessageResponse send(MessageRequest<List<String>> request) throws IOException {
        return send(TencentRegion.GUANGZHOU, request);
    }

    /**
     * Send several messages to multiple recipients.
     *
     * @param request The data to send a sms.
     * @return The response data from sent message.
     */
    @Override
    @Deprecated
    public MessageResponse batchSend(BatchMessageRequest<List<String>> request) {
        throw new NotImplementedException("This feature will not be implemented as the Tencent Cloud Platform Send SMS " +
                "interface supports the transmission of single or multiple SMS recipients.");
    }

    private String[] resolve(String receiver) {
        return new String[]{receiver};
    }
}
