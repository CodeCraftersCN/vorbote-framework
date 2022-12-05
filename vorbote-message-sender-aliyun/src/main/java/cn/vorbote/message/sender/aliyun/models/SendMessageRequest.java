package cn.vorbote.message.sender.aliyun.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Map;
import java.util.Objects;

/**
 * SendMessageRequest<br>
 * Created at 04/12/2022 20:07
 *
 * @author theod
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public final class SendMessageRequest {

    @JsonProperty("PhoneNumbers")
    private String receiver;

    @JsonProperty("SignName")
    private String sign;

    @JsonProperty("TemplateCode")
    private String templateId;

    @JsonProperty("TemplateParam")
    private Map<String, Object> params;

}
