package cn.vorbote.message.sender.aliyun;

import cn.vorbote.core.exceptions.NotImplementedException;
import cn.vorbote.message.config.AliyunRegion;
import cn.vorbote.message.model.MessageRequest;
import cn.vorbote.message.model.MessageResponse;
import cn.vorbote.message.sender.BasicSender;
import cn.vorbote.message.util.JacksonSerializer;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * SMS Sender implemented in Aliyun Platform.<br>
 * Created at 02/09/2022 14:05
 *
 * @author theod
 */
@Slf4j
public final class AliyunSender extends BasicSender {

    private final IAcsClient client;

    private final JacksonSerializer jacksonSerializer;

    /**
     * Create a new Message Sender using Aliyun Platform.
     *
     * @param region       The region to send from aliyun.
     * @param keyId        The auth key id.
     * @param keySecret    The auth key secret.
     * @see AliyunSender#AliyunSender(AliyunRegion, String, String, ObjectMapper)
     */
    public AliyunSender(AliyunRegion region, String keyId, String keySecret) {
        this(region, keyId, keySecret, new ObjectMapper());
    }

    /**
     * Create a new Message Sender using Aliyun Platform.
     *
     * @param region       The region to send from aliyun.
     * @param keyId        The auth key id.
     * @param keySecret    The auth key secret.
     * @param objectMapper Jackson JSON Utility.
     */
    public AliyunSender(AliyunRegion region, String keyId, String keySecret, ObjectMapper objectMapper) {
        this.client = new DefaultAcsClient(
                DefaultProfile.getProfile(region.getRegionId(), keyId, keySecret));
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
    public MessageResponse send(MessageRequest request) throws JsonProcessingException {
        var platformRequest = new SendSmsRequest();
        platformRequest.setSignName(request.sign());
        platformRequest.setTemplateCode(request.templateCode());
        platformRequest.setPhoneNumbers(request.getReceivers());
        platformRequest.setTemplateParam(request.getParams());

        MessageResponse response = null;

        try {
            var platformResponse = client.getAcsResponse(platformRequest);
            response = new MessageResponse(platformResponse.getMessage(), platformResponse.getCode());
            log.info(jacksonSerializer.serialize(response));
        } catch (ClientException e) {
            log.error(e.getErrMsg());
        }
        return response;
    }

    /**
     * Send several messages to multiple recipients.<br>
     *
     * <p><b>NOTE:<br>
     * This feature will not be implemented as the AliCloud Platform Send SMS interface
     * supports the transmission of single or multiple SMS recipients.
     * </b></p>
     *
     * @param request The data to send a sms.
     * @return The response data from sent message.
     */
    @Deprecated
    @Override
    public MessageResponse batchSend(MessageRequest request) {
        throw new NotImplementedException("""
                This feature will not be implemented as the AliCloud Platform \
                Send SMS interface supports the transmission of single or multiple SMS recipients.""");
    }
}
