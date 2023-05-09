package cn.vorbote.core.constants;

import cn.vorbote.core.utils.StringUtil;
import lombok.Getter;

/**
 * This is an enum class for {@code HashUtil} and supplied  all supported methods by the {@code HashUtil}.
 *
 * @author vorbote
 * @since 3.0.0
 */
@Getter
public enum Hash {

    /**
     * Hash algorithm - MD2
     */
    MD2("MD2"),

    /**
     * Hash algorithm - MD5
     */
    MD5("MD5"),

    /**
     * Hash algorithm - SHA1
     */
    SHA_1("sha-1"),

    /**
     * Hash algorithm - SHA224
     */
    SHA_224("sha-224"),

    /**
     * Hash algorithm - SHA256
     */
    SHA_256("sha-256"),

    /**
     * Hash algorithm - SHA384
     */
    SHA_384("sha-384"),

    /**
     * Hash algorithm - SHA512
     */
    SHA_512("sha-512"),

    /**
     * Encryption algorithm - RC4
     */
    RC4("rc4"),

    /**
     * Encryption algorithm - AES
     */
    AES("aes"),

    /**
     * Encryption algorithm - DES
     */
    DES("des");

    private final String value;

    Hash(String value) {
        this.value = value;
    }

    /**
     * This method helps you get the value of the enum. Deprecated
     * because of its name.
     *
     * @return The value included in this enum.
     */
    @Override
    public String toString() {
        return value;
    }
}
