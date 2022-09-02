package cn.vorbote.web.exceptions;

import cn.vorbote.web.model.ResponseResult;

/**
 * BizException stands for Business Exception, and meant to be thrown while Business Assertion fails. This Exception is
 * used to solve that Spring Framework throws <code>java.lang.IllegalArgumentException</code> while its assertion fails.<br>
 * Created at 2022/2/22 22:22
 *
 * @author vorbote
 */
public class BizException extends RuntimeException {

    /**
     * The web status code.
     */
    private final int code;

    /**
     * Get the exception message.
     *
     * @return The message of this exception.
     */
    @Override
    public String getMessage() {
        return super.getMessage();
    }

    /**
     * Get the web status code.
     *
     * @return The status code of this request.
     */
    public int getCode() {
        return code;
    }

    /**
     * Constructor to build a BizException.
     *
     * @param code    Web status code.
     * @param message Exception message.
     */
    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * Convert this exception to a response result.
     *
     * @return A converted response result entity.
     */
    public ResponseResult<?> respond() {
        return ResponseResult.error(this.getMessage()).code(this.getCode());
    }

}
