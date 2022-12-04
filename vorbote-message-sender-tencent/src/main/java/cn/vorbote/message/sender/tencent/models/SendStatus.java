package cn.vorbote.message.sender.tencent.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * SendStatus<br>
 * Created at 03/12/2022 23:43
 *
 * @author vorbote
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public final class SendStatus {

    @JsonProperty("SerialNo")
    private String serialNo;

    @JsonProperty("PhoneNumber")
    private String phoneNumber;

    @JsonProperty("Fee")
    private Integer fee;

    @JsonProperty("SessionContext")
    private String sessionContext;

    @JsonProperty("Code")
    private String code;

    @JsonProperty("Message")
    private String message;

    @JsonProperty("IsoCode")
    private String isoCode;

}
