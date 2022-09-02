package cn.vorbote.webdev.encoding;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * EncodingConfigurationInfo<br>
 * Created at 2022/3/28 15:36
 *
 * @author vorbote
 */
@Data
@Builder
public class EncodingConfigurationInfo implements Serializable {

    /**
     * Specify the encoding for request.
     */
    private final String requestEncoding;

    /**
     * Specify the encoding for response.
     */
    private final String responseEncoding;

}
