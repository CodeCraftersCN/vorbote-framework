package cn.vorbote.message.sender.tencent.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * SendMessageResponse<br>
 * Created at 03/12/2022 23:42
 *
 * @author vorbote
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public final class SendMessageResponse {

    @JsonProperty("RequestId")
    private String requestId;

    @JsonProperty("SendStatusSet")
    private List<SendStatus> sendStatusSet;

}
