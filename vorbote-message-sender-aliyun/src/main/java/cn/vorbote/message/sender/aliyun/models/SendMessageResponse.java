package cn.vorbote.message.sender.aliyun.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * SendMessageResponse<br>
 * Created at 05/12/2022 16:33
 *
 * @author theod
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SendMessageResponse {

    @JsonProperty("Message")
    private String message;

    @JsonProperty("RequestId")
    private String requestId;

    @JsonProperty("Code")
    private String code;

    @JsonProperty("BizId")
    private String businessId;

}
