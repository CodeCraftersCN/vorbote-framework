package cn.vorbote.core.utils;

import cn.vorbote.core.constants.Hash;
import cn.vorbote.core.exceptions.UnsupportedHashAlgorithmException;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;

/**
 * HashUtil can help you hash arbitrary strings.
 *
 * @author vorbote
 */
@Slf4j
public final class HashUtil {

    /**
     * Private constructor will prevent other coder create an instance of this class.
     */
    private HashUtil() {
    }

    /**
     * Encrypt the string via specified encrypt method. All supported method:
     * <ul>
     *     <li>{@code MD2}</li>
     *     <li>{@code MD5}</li>
     *     <li>{@code SHA-1}</li>
     *     <li>{@code SHA-224}</li>
     *     <li>{@code SHA-384}</li>
     *     <li>{@code SHA-512}</li>
     * </ul>
     *
     * @param method Encrypt method.
     * @param value  The string will be encrypted
     * @return The encrypted String
     * @throws UnsupportedHashAlgorithmException If the param method used an item which is
     *                                           not listed on the list above, the exception
     *                                           will be thrown.
     * @see MessageDigest#getInstance(String)
     */
    public static String encrypt(Hash method, String value) {
        if (method == Hash.RC4 || method == Hash.AES || method == Hash.DES) {
            throw new UnsupportedHashAlgorithmException(method.getValue());
        }

        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance(method.toString());
            md.update(value.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest();
            StringBuilder builder = new StringBuilder();
            for (byte b : bytes) {
                String str = Integer.toHexString(b & 0xff);
                if (str.length() == 1) {
                    builder.append("0");
                }
                builder.append(str);
            }
            result = builder.toString();
        } catch (NoSuchAlgorithmException e) {
            // It will get no exception, so ignore it
        }
        return result;
    }

    /**
     * Encrypt the string via Base64.
     *
     * @param value The string will be encrypted.
     * @return The encrypted String.
     */
    public static String base64Encode(String value) {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedString = encoder.encode(value.getBytes(StandardCharsets.UTF_8));

        return new String(encodedString);
    }

    /**
     * Decode the string via Base64.
     *
     * @param value The string will be encrypted.
     * @return The encrypted String.
     */
    public static String base64Decode(String value) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedString = decoder.decode(value.getBytes(StandardCharsets.UTF_8));

        return new String(decodedString);
    }

    /**
     * This method can decrypt a encrypted String(in byte mode).
     *
     * @param method The decrypt method type.
     * @param key    The key to decrypt the String.
     * @param data   The encrypted {@code String}.
     * @return The original string.
     */
    public static String decrypt(Hash method, String key, byte[] data) {
        if (method != Hash.RC4)
            throw new UnsupportedHashAlgorithmException("This algorithm is unsupported yet.");
        if (data == null || key == null) {
            return null;
        }
        return asString(rc4Base(data, key));
    }

    /**
     * This method can decrypt an encrypted String(in byte mode).
     *
     * @param method The decrypt method type.
     * @param key    The key to decrypt the String.
     * @param data   The encrypted {@code String}.
     * @return The original string.
     */
    public static String decrypt(Hash method, String key, String data) {
        if (method != Hash.RC4)
            throw new UnsupportedHashAlgorithmException("This algorithm is unsupported yet.");
        if (data == null || key == null) {
            return null;
        }
        return new String(rc4Base(castHexStringToBytes(data), key));
    }

    /**
     * Encrypt the data through a key.
     *
     * @param method The method to encrypt the data.
     * @param data   The data will be encrypt to rc4 string.
     * @param key    The key to encrypt the string.
     * @return The encrypted String by stream.
     */
    public static byte[] encryptToByteStream(Hash method, String key, String data) {
        if (method != Hash.RC4)
            throw new UnsupportedHashAlgorithmException("This algorithm is unsupported yet.");
        if (data == null || key == null) {
            return null;
        }
        byte[] b_data = data.getBytes();
        return rc4Base(b_data, key);
    }

    /**
     * Encrypt a string to a HexString
     *
     * @param method The specified method.
     * @param data   The origin data.
     * @param key    The key.
     * @return The encrypted string.
     */
    public static String encrypt(Hash method, String key, String data) {
        if (data == null || key == null) {
            return null;
        }
        return castToHexString(asString(Objects.requireNonNull(encryptToByteStream(Hash.RC4, key, data))));

    }

    /**
     * Transfer a byte array to string.
     *
     * @param buf The byte array.
     * @return The string.
     */
    private static String asString(byte[] buf) {
        StringBuilder sb = new StringBuilder(buf.length);
        for (byte b : buf) {
            sb.append((char) b);
        }
        return sb.toString();
    }

    /**
     * Load key and transfer to byte array.
     *
     * @param key The key.
     * @return The byte key.
     */
    private static byte[] initKey(String key) {
        byte[] b_key = key.getBytes(StandardCharsets.UTF_8);
        byte[] state = new byte[256];
        for (int i = 0; i < 256; i++) {
            state[i] = (byte) i;
        }

        int index1 = 0;
        int index2 = 0;
        if (b_key.length == 0) {
            return null;
        }

        for (int i = 0; i < 256; i++) {
            index2 = ((b_key[index1] & 0xff) + (state[i] & 0xff) + index2) & 0xff;
            byte tmp = state[i];
            state[i] = state[index2];
            state[index2] = tmp;
            index1 = (index1 + 1) % b_key.length;
        }
        return state;
    }

    /**
     * Transfer a string to HexString.
     *
     * @param s The source string.
     * @return The hex string of the origin.
     */
    private static String castToHexString(String s) {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            int ch = s.charAt(i);
            String s4 = Integer.toHexString(ch & 0xFF);
            if (s4.length() == 1) {
                s4 = '0' + s4;
            }
            str.append(s4);

        }

        return str.toString();// 0x表示十六进制

    }

    /**
     * Transfer a <b>hex</b> string to byte array.
     *
     * @param src The hex string.
     * @return The byte stream of this string.
     */
    private static byte[] castHexStringToBytes(String src) {
        int size = src.length();
        byte[] ret = new byte[size / 2];
        byte[] tmp = src.getBytes();
        for (int i = 0; i < size / 2; i++) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }
        return ret;
    }

    /**
     * Calc the unite result.
     *
     * @param src0 The first op num.
     * @param src1 The second op num.
     * @return The result.
     */
    private static byte uniteBytes(byte src0, byte src1) {
        char _b0 = (char) Byte.decode("0x" + new String(new byte[]{src0})).byteValue();

        _b0 = (char) (_b0 << 4);

        char _b1 = (char) Byte.decode("0x" + new String(new byte[]{src1})).byteValue();

        return (byte) (_b0 ^ _b1);

    }

    /**
     * Generate a RC4 base.
     *
     * @param input The input string bytes.
     * @param m_key The key.
     * @return The byte stream.
     */
    private static byte[] rc4Base(byte[] input, String m_key) {
        int x = 0;
        int y = 0;
        byte[] key = initKey(m_key);
        int xorIndex;
        byte[] result = new byte[input.length];

        for (int i = 0; i < input.length; i++) {
            x = (x + 1) & 0xff;
            assert key != null;
            y = ((key[x] & 0xff) + y) & 0xff;
            byte tmp = key[x];
            key[x] = key[y];
            key[y] = tmp;
            xorIndex = ((key[x] & 0xff) + (key[y] & 0xff)) & 0xff;
            result[i] = (byte) (input[i] ^ key[xorIndex]);
        }

        return result;
    }

}
