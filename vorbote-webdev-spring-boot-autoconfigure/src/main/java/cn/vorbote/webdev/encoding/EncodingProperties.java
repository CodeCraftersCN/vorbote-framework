package cn.vorbote.webdev.encoding;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * EncodingProperties<br>
 * Created at 2022/3/28 15:36
 *
 * @author vorbote
 */
@Data
@ConfigurationProperties(prefix = "vorbote.web-dev.encoding")
public class EncodingProperties {

    /**
     * Open the <code>EncodingFilter</code> by set this to <code>true</code>.
     */
    private Boolean enabled;

    /**
     * Specify the encoding for request.<br>
     * If you have no idea what character sets can be used, you can see <a
     * href="https://www.iana.org/assignments/character-sets/character-sets.xhtml">IANA Character Sets Page</a> for more
     * detail.
     */
    private String requestEncoding;

    /**
     * Specify the encoding for response.<br>
     * If you have no idea what character sets can be used, you can see <a
     * href="https://www.iana.org/assignments/character-sets/character-sets.xhtml">IANA Character Sets Page</a> for more
     * detail.
     */
    private String responseEncoding;

}
