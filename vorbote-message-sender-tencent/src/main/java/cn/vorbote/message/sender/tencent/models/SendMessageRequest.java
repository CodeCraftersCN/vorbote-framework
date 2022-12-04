package cn.vorbote.message.sender.tencent.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * SendMessageRequest<br>
 * Created at 03/12/2022 18:54
 *
 * @author vorbote
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public final class SendMessageRequest {

    @JsonProperty("SmsSdkAppId")
    private String appId;

    @JsonProperty("SignName")
    private String sign;

    @JsonProperty("TemplateId")
    private String templateId;

    @JsonProperty("TemplateParamSet")
    private List<String> params;

    @JsonProperty("PhoneNumberSet")
    private List<String> receivers;

}
