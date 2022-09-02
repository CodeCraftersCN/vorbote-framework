package cn.vorbote.web.model;

import cn.vorbote.core.time.DateTime;
import cn.vorbote.core.utils.StringUtil;
import cn.vorbote.web.constants.WebStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.function.Supplier;

/**
 * Response model for response body.
 *
 * @param <T> The type of the result.
 * @author vorbote thills@vorbote.cn
 */
@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public final class ResponseResult<T> {

    private int code;
    private T data;
    private long timestamp;
    private String message;

    /**
     * Get the data of status.
     *
     * @return The status.
     */
    public int code() {
        return code;
    }

    /**
     * Set the data of status.
     *
     * @param code The status.
     * @return The instance itself.
     */
    public ResponseResult<T> code(int code) {
        this.code = code;
        return this;
    }

    /**
     * Get the data of result.
     *
     * @return The result.
     */
    public T data() {
        return data;
    }

    /**
     * Set the data of result.
     *
     * @param data The result.
     * @return The instance itself.
     */
    public ResponseResult<T> data(T data) {
        this.data = data;
        return this;
    }

    /**
     * Get the data of timestamp.
     *
     * @return The timestamp.
     */
    public long timestamp() {
        return timestamp;
    }

    /**
     * Set the data of timestamp.
     *
     * @param timestamp The timestamp.
     * @return The instance itself.
     */
    public ResponseResult<T> timestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    /**
     * Get the data of message.
     *
     * @return The message.
     */
    public String message() {
        return message;
    }

    /**
     * Set the data of message.
     *
     * @param message The message.
     * @return The instance itself.
     */
    public ResponseResult<T> message(String message) {
        this.message = message;
        return this;
    }

    /**
     * Set the data of message.
     *
     * @param format The format message.
     * @param args   The args to be put into the message.
     * @return The instance itself.
     * @see StringUtil#format(String, Object...)
     */
    public ResponseResult<T> message(String format, Object... args) {
        this.message = StringUtil.format(format, args);
        return this;
    }

    /**
     * Generate a new Response Result instance.
     */
    public ResponseResult() {
        this.timestamp = DateTime.now().unix();
    }

    /**
     * Generate a new Response Result instance with a success status.
     *
     * @param data The data.
     * @param <T>  The type of the result.
     * @return The instance itself.
     */
    public static <T> ResponseResult<T> success(T data) {
        ResponseResult<T> result = new ResponseResult<>();
        result.code = WebStatus.OK.getCode();
        result.data = data;
        return result;
    }

    /**
     * Generate a new Response Result instance with a success status.
     *
     * @param message The message.
     * @param <T>     The type of the result.
     * @return The instance itself.
     */
    public static <T> ResponseResult<T> success(String message) {
        ResponseResult<T> result = new ResponseResult<>();
        result.code = WebStatus.OK.getCode();
        result.message = message;
        return result;
    }

    /**
     * Generate a new Response Result instance with a success status.
     *
     * @param messageSupplier The message.
     * @param <T>             The type of the result.
     * @return The instance itself.
     */
    public static <T> ResponseResult<T> success(Supplier<String> messageSupplier) {
        ResponseResult<T> result = new ResponseResult<>();
        result.code = WebStatus.OK.getCode();
        result.message = messageSupplier.get();
        return result;
    }

    /**
     * Generate a new Response Result instance with a success status.
     *
     * @param data    The data.
     * @param message The message.
     * @param <T>     The type of the result.
     * @return The instance itself.
     */
    public static <T> ResponseResult<T> success(T data, String message) {
        ResponseResult<T> result = new ResponseResult<>();
        result.code = WebStatus.OK.getCode();
        result.data = data;
        result.message = message;
        return result;
    }

    /**
     * Generate a new Response Result instance with a success status.
     *
     * @param data            The data.
     * @param messageSupplier The message supplier.
     * @param <T>             The type of the result.
     * @return The instance itself.
     */
    public static <T> ResponseResult<T> success(T data, Supplier<String> messageSupplier) {
        ResponseResult<T> result = new ResponseResult<>();
        result.code = WebStatus.OK.getCode();
        result.data = data;
        result.message = messageSupplier.get();
        return result;
    }

    /**
     * Generate a new Response Result instance with a timeout status.
     *
     * @param messageSupplier The message supplier.
     * @param <T>             The type of the result.
     * @return The instance itself.
     */
    public static <T> ResponseResult<T> timeout(Supplier<String> messageSupplier) {
        ResponseResult<T> result = new ResponseResult<>();
        result.code = WebStatus.REQUEST_TIMEOUT.getCode();
        result.message = messageSupplier.get();
        return result;
    }

    /**
     * Generate a new Response Result instance with a timeout status.
     *
     * @param message The message.
     * @param <T>     The type of the result.
     * @return The instance itself.
     */
    public static <T> ResponseResult<T> timeout(String message) {
        ResponseResult<T> result = new ResponseResult<>();
        result.code = WebStatus.REQUEST_TIMEOUT.getCode();
        result.message = message;
        return result;
    }

    /**
     * Generate a new Response Result instance with an error status.
     *
     * @param messageSupplier The messageSupplier.
     * @param <T>             The type of the result.
     * @return The instance itself.
     */
    public static <T> ResponseResult<T> error(Supplier<String> messageSupplier) {
        ResponseResult<T> result = new ResponseResult<>();
        result.code = WebStatus.INTERNAL_SERVER_ERROR.getCode();
        result.message = messageSupplier.get();
        return result;
    }

    /**
     * Generate a new Response Result instance with an error status.
     *
     * @param message The message.
     * @param <T>     The type of the result.
     * @return The instance itself.
     */
    public static <T> ResponseResult<T> error(String message) {
        ResponseResult<T> result = new ResponseResult<>();
        result.code = WebStatus.INTERNAL_SERVER_ERROR.getCode();
        result.message = message;
        return result;
    }

    /**
     * Generate a new Response Result instance with an unauthorized status.
     *
     * @param message The message.
     * @param <T>     The type of the result.
     * @return The instance itself.
     */
    public static <T> ResponseResult<T> unauthorized(String message) {
        ResponseResult<T> result = new ResponseResult<>();
        result.code = WebStatus.UNAUTHORIZED.getCode();
        result.message = message;
        return result;
    }

    /**
     * Generate a new Response Result instance with an unauthorized status.
     *
     * @param messageSupplier The message supplier.
     * @param <T>             The type of the result.
     * @return The instance itself.
     */
    public static <T> ResponseResult<T> unauthorized(Supplier<String> messageSupplier) {
        ResponseResult<T> result = new ResponseResult<>();
        result.code = WebStatus.UNAUTHORIZED.getCode();
        result.message = messageSupplier.get();
        return result;
    }

    /**
     * Generate a new Response Result instance with a forbidden status.
     *
     * @param message The message.
     * @param <T>     The type of the result.
     * @return The instance itself.
     */
    public static <T> ResponseResult<T> forbidden(String message) {
        ResponseResult<T> result = new ResponseResult<>();
        result.code = WebStatus.FORBIDDEN.getCode();
        result.message = message;
        return result;
    }

    /**
     * Generate a new Response Result instance with a forbidden status.
     *
     * @param messageSupplier The message supplier.
     * @param <T>             The type of the result.
     * @return The instance itself.
     */
    public static <T> ResponseResult<T> forbidden(Supplier<String> messageSupplier) {
        ResponseResult<T> result = new ResponseResult<>();
        result.code = WebStatus.FORBIDDEN.getCode();
        result.message = messageSupplier.get();
        return result;
    }
}
