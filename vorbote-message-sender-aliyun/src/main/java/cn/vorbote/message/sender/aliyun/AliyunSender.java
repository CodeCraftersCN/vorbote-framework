package cn.vorbote.message.sender.aliyun;

import cn.vorbote.core.exceptions.NotImplementedException;
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
import lombok.extern.slf4j.Slf4j;

/**
 * AliyunSender<br>
 * Created at 02/09/2022 14:05
 *
 * @author theod
 */
@Slf4j
public final class AliyunSender extends BasicSender {

    private final IAcsClient client;

    public AliyunSender(AliyunRegion aliyunRegion, String keyId, String keySecret) {
        this.client = new DefaultAcsClient(
                DefaultProfile.getProfile(aliyunRegion.getRegionId(), keyId, keySecret));
    }


    @Override
    public MessageResponse send(MessageRequest request) throws JsonProcessingException {
        var aliRequest = new SendSmsRequest();
        aliRequest.setSignName(request.sign());
        aliRequest.setTemplateCode(request.templateCode());
        aliRequest.setPhoneNumbers(request.getReceivers());
        aliRequest.setTemplateParam(request.getParams());

        MessageResponse resp = null;

        try {
            var response = client.getAcsResponse(aliRequest);
            resp = new MessageResponse(response.getMessage(), response.getRequestId(),
                    response.getCode(), response.getBizId());
            log.info(JacksonSerializer.getInstance().serialize(resp));
        } catch (ClientException e) {
            log.error(e.getErrMsg());
        }
        return resp;
    }

    @Override
    public MessageResponse batchSend(MessageRequest request) {
        throw new NotImplementedException("This feature will not be implemented as the AliCloud Platform " +
                "Send SMS interface supports the transmission of single or multiple SMS recipients.");
    }
}
