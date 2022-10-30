package cn.vorbote.message.sender.tencent;

import cn.vorbote.core.exceptions.NotImplementedException;
import cn.vorbote.message.config.TencentRegion;
import cn.vorbote.message.model.BatchMessageRequest;
import cn.vorbote.message.model.MessageRequest;
import cn.vorbote.message.model.MessageResponse;
import cn.vorbote.message.sender.IMessageSender;
import cn.vorbote.message.util.JacksonSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * TencentSender<br>
 * Created at 02/09/2022 16:42
 *
 * @author theod
 */
@Slf4j
public final class TencentSender implements IMessageSender<List<String>> {

    private final SmsClient client;

    private final String appId;

    private final JacksonSerializer jacksonSerializer;

    /**
     * Generate an SMS Sender which is using Tencent Cloud as the service provider.
     *
     * @param appId     The app id.
     * @param keyId     The key id.
     * @param keySecret The key secret.
     * @see TencentSender#TencentSender(TencentRegion, String, String, String, ObjectMapper)
     */
    public TencentSender(TencentRegion region, String appId, String keyId, String keySecret) {
        this(region, appId, keyId, keySecret, new ObjectMapper());
    }

    /**
     * Generate an SMS Sender which is using Tencent Cloud as the service provider.
     *
     * @param appId        The app id.
     * @param keyId        The key id.
     * @param keySecret    The key secret.
     * @param objectMapper Jackson ObjectMapper Utility.
     */
    public TencentSender(TencentRegion region, String appId, String keyId, String keySecret, ObjectMapper objectMapper) {
        var cred = new Credential(keyId, keySecret);

        var httpProfile = new HttpProfile();
        httpProfile.setReqMethod("POST");
        httpProfile.setConnTimeout(60);
        httpProfile.setEndpoint("sms.tencentcloudapi.com");

        var clientProfile = new ClientProfile();
        clientProfile.setSignMethod("HmacSHA256");
        clientProfile.setHttpProfile(httpProfile);

        this.client = new SmsClient(cred, region.getRegionId(), clientProfile);
        this.appId = appId;
        this.jacksonSerializer = JacksonSerializer.getJacksonSerializer(objectMapper);
    }

    /**
     * Send a SMS.
     *
     * @param request The data to send a sms.
     * @return The response data from sent message.
     * @throws JsonProcessingException ObjectMapper could make this exception because
     *                                 of the data is not serializable.
     */
    @Override
    public MessageResponse send(MessageRequest<List<String>> request) throws JsonProcessingException {
        var req = new SendSmsRequest();
        req.setSmsSdkAppId(appId);
        req.setSignName(request.sign());
        req.setTemplateId(request.templateId());
        req.setTemplateParamSet(request.params().toArray(String[]::new));
        req.setPhoneNumberSet(resolve(request.receiver()));

        SendSmsResponse resp = null;
        try {
            resp = client.SendSms(req);
            var response = MessageResponse.initResponse(resp.getSendStatusSet()[0].getMessage(),
                    resp.getSendStatusSet()[0].getCode());
            log.debug("Sent sms via tencent cloud platform, response message is: {}", jacksonSerializer.serialize(response));
            return response;
        } catch (TencentCloudSDKException e) {
            throw new RuntimeException(e);
        }
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
        throw new NotImplementedException("""
                This feature will not be implemented as the AliCloud Platform \
                Send SMS interface supports the transmission of single or multiple SMS recipients.""");
    }

    private String[] resolve(String receiver) {
        return new String[]{receiver};
    }
}
