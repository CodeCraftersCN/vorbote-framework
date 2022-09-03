package cn.vorbote.message.sender.tencent;

import cn.vorbote.message.model.MessageRequest;
import cn.vorbote.message.model.MessageResponse;
import cn.vorbote.message.sender.BasicSender;
import cn.vorbote.message.util.JacksonSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20210111.models.SendStatus;
import lombok.extern.slf4j.Slf4j;

/**
 * TencentSender<br>
 * Created at 02/09/2022 16:42
 *
 * @author theod
 */
@Slf4j
public final class TencentSender extends BasicSender {

    private final SmsClient client;

    public TencentSender(TencentRegion region, String keyId, String keySecret) {
        Credential cred = new Credential(keyId, keySecret);

        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setReqMethod("POST");
        httpProfile.setConnTimeout(60);
        httpProfile.setEndpoint("sms.tencentcloudapi.com");

        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setSignMethod("HmacSHA256");
        clientProfile.setHttpProfile(httpProfile);

        this.client = new SmsClient(cred, "ap-guangzhou",clientProfile);
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
    public MessageResponse send(MessageRequest request) throws JsonProcessingException {
        var req = new SendSmsRequest();
        req.setSmsSdkAppId(request.appId());
        req.setSignName(request.sign());
        req.setTemplateId(request.templateCode());
        if (request.params() instanceof String[] value)
            req.setTemplateParamSet(value);
        req.setPhoneNumberSet(request.receivers());

        MessageResponse response = null;

        try {
            var resp = client.SendSms(req);
            response = new MessageResponse(resp.getSendStatusSet()[0].getMessage(),
                    resp.getSendStatusSet()[0].getCode());
            log.info(JacksonSerializer.getInstance().serialize(response));
        } catch (TencentCloudSDKException e) {
            log.error(e.toString());
        }

        return response;
    }

    /**
     * Send several messages to multiple recipients.
     *
     * @param request The data to send a sms.
     * @return The response data from sent message.
     */
    @Override
    public MessageResponse batchSend(MessageRequest request) {
        return null;
    }
}
