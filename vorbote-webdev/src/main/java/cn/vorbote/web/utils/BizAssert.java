package cn.vorbote.web.utils;

import cn.vorbote.core.utils.CollectionUtil;
import cn.vorbote.core.utils.ObjectUtil;
import cn.vorbote.core.utils.StringUtil;
import cn.vorbote.web.exceptions.BizException;
import com.sun.net.httpserver.HttpPrincipal;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * This class is to do some business assertions.
 * <p>
 * If the assertion is not passed, the util will throw a {@link BizException}.
 * Created at 2022/2/22 22:22
 *
 * @author vorbote theodore0126@outlook.com
 * @author zihluwang really@zihlu.wang
 */
public final class BizAssert {

    /**
     * Private constructor, to hide the constructor to prevent some build a instance of this util class.
     */
    private BizAssert() {
    }

    /**
     * Get value from a supplier.
     *
     * @param valueSupplier a supplier for the value
     * @param <T>           the type of the value
     * @return value contained in the supplier
     */
    private static <T> T getValueFromSupplier(Supplier<T> valueSupplier) {
        return Optional.ofNullable(valueSupplier)
                .map(Supplier::get)
                .orElse(null);
    }

    /**
     * Assert a boolean expression, throwing an {@code BizException} if the expression evaluates to {@code false}.
     *
     * @param expression a boolean expression
     * @param code       the code returned to the front-end
     * @param message    the exception message to use if the assertion fails
     * @throws BizException if {@code expression} is {@code false}
     */
    public static void state(boolean expression, int code, String message) {
        if (!expression) {
            throw new BizException(code, message);
        }
    }

    /**
     * Assert a boolean expression, throwing an {@code BizException} if the expression evaluates to {@code false}.
     * <p>
     * The {@code code} will be set to {@code HttpServletResponse.SC_BAD_REQUEST} as default.
     *
     * @param expression a boolean expression
     * @param message    the exception message to use if the assertion fails
     * @throws BizException if {@code expression} is {@code false}
     */
    public static void state(boolean expression, String message) {
        state(expression, HttpServletResponse.SC_BAD_REQUEST, message);
    }

    /**
     * Assert a boolean expression, throwing an {@code BizException} if the expression evaluates to {@code false}.
     *
     * @param expression      a boolean expression
     * @param code            the code returned to the front-end
     * @param messageSupplier a supplier for the exception message to use if the assertion fails
     * @throws BizException if {@code expression} is {@code false}
     */
    public static void state(boolean expression, int code, Supplier<String> messageSupplier) {
        state(expression, code, getValueFromSupplier(messageSupplier));
    }

    /**
     * Assert a boolean expression, throwing an {@code BizException} if the expression evaluates to {@code false}.
     * <p>
     * The {@code code} will be set to {@code HttpServletResponse.SC_BAD_REQUEST} as default.
     *
     * @param expression      a boolean expression
     * @param messageSupplier a supplier for the exception message to use if the assertion fails
     * @throws BizException if {@code expression} is {@code false}
     */
    public static void state(boolean expression, Supplier<String> messageSupplier) {
        state(expression, HttpServletResponse.SC_BAD_REQUEST, getValueFromSupplier(messageSupplier));
    }

    /**
     * Assert a boolean expression, throwing an {@link BizException} if the expression evaluates to {@code false}.
     *
     * @param expression a boolean expression
     * @param code       the code returned to the front-end
     * @param message    the exception message to use if the assertion fails
     * @throws BizException if {@code expression} is {@code false}
     */
    public static void isTrue(boolean expression, int code, String message) {
        if (!expression) {
            throw new BizException(code, message);
        }
    }

    /**
     * Assert a boolean expression, throwing an {@link BizException} if the expression evaluates to {@code false}.
     * <p>
     * The code will be set to {@code HttpServletResponse.SC_BAD_REQUEST} as default.
     *
     * @param expression a boolean expression
     * @param message    the exception message to use if the assertion fails
     * @throws BizException if {@code expression} is {@code false}
     * @see #isTrue(boolean, int, String)
     */
    public static void isTrue(boolean expression, String message) {
        isTrue(expression, HttpServletResponse.SC_BAD_REQUEST, message);
    }

    /**
     * Assert a boolean expression, throwing an {@link BizException} if the expression evaluates to {@code false}.
     *
     * @param expression      a boolean expression
     * @param code            the code returned to the front-end
     * @param messageSupplier a supplier for the exception message to use if the assertion fails
     * @throws BizException if {@code expression} is {@code false}
     * @see #isTrue(boolean, int, String)
     */
    public static void isTrue(boolean expression, int code, Supplier<String> messageSupplier) {
        isTrue(expression, code, getValueFromSupplier(messageSupplier));
    }

    /**
     * Assert a boolean expression, throwing an {@link BizException} if the expression evaluates to {@code false}.
     * <p>
     * The code will be set to {@code HttpServletResponse.SC_BAD_REQUEST} as default.
     *
     * @param expression      a boolean expression
     * @param messageSupplier a supplier for the exception message to use if the assertion fails
     * @throws BizException if {@code expression} is {@code false}
     * @see #isTrue(boolean, int, String)
     */
    public static void isTrue(boolean expression, Supplier<String> messageSupplier) {
        isTrue(expression, HttpServletResponse.SC_BAD_REQUEST, getValueFromSupplier(messageSupplier));
    }

    /**
     * Assert that an object is {@code null}.
     *
     * @param object  the object to check
     * @param code    the code returned to the front-end
     * @param message the exception message to use if the assertion fails
     * @throws BizException if the object is not {@code null}
     */
    public static void isNull(Object object, int code, String message) {
        if (object != null) {
            throw new BizException(code, message);
        }
    }

    /**
     * Assert that an object is {@code null}.
     * <p>
     * The code will be set to {@code HttpServletResponse.SC_BAD_REQUEST} as default.
     *
     * @param object  the object to check
     * @param message the exception message to use if the assertion fails
     * @throws BizException if the object is not {@code null}
     * @see #isNull(Object, int, String)
     */
    public static void isNull(Object object, String message) {
        isNull(object, HttpServletResponse.SC_BAD_REQUEST, message);
    }

    /**
     * Assert that an object is {@code null}.
     *
     * @param object          the object to check
     * @param code            the code returned to the front-end
     * @param messageSupplier a supplier for the exception message to use if the assertion fails
     * @throws BizException if the object is not {@code null}
     * @see #isNull(Object, int, String)
     */
    public static void isNull(Object object, int code, Supplier<String> messageSupplier) {
        isNull(object, code, getValueFromSupplier(messageSupplier));
    }

    /**
     * Assert that an object is {@code null}.
     * <p>
     * The code will be set to {@code HttpServletResponse.SC_BAD_REQUEST} as default.
     *
     * @param object          the object to check
     * @param messageSupplier a supplier for the exception message to use if the assertion fails
     * @throws BizException if the object is not {@code null}
     * @see #isNull(Object, int, String)
     */
    public static void isNull(Object object, Supplier<String> messageSupplier) {
        isNull(object, HttpServletResponse.SC_BAD_REQUEST, getValueFromSupplier(messageSupplier));
    }

    /**
     * Assert that an object is not {@code null}.
     *
     * @param object  the object to check
     * @param code    the code returned to the front-end
     * @param message the exception message to use if the assertion fails
     * @throws BizException if the object is not {@code null}
     */
    public static void notNull(Object object, int code, String message) {
        if (object == null) {
            throw new BizException(code, message);
        }
    }

    /**
     * Assert that an object is not {@code null}.
     * <p>
     * The code will be set to {@code HttpServletResponse.SC_BAD_REQUEST} as default.
     *
     * @param object  the object to check
     * @param message the exception message to use if the assertion fails
     * @throws BizException if the object is not {@code null}
     * @see #notNull(Object, int, String)
     */
    public static void notNull(Object object, String message) {
        notNull(object, HttpServletResponse.SC_BAD_REQUEST, message);
    }

    /**
     * Assert that an object is not {@code null}.
     *
     * @param object          the object to check
     * @param code            the code returned to the front-end
     * @param messageSupplier a supplier for the exception message to use if the assertion fails
     * @throws BizException if the object is not {@code null}
     * @see #notNull(Object, int, String)
     */
    public static void notNull(Object object, int code, Supplier<String> messageSupplier) {
        notNull(object, code, getValueFromSupplier(messageSupplier));
    }

    /**
     * Assert that an object is not {@code null}.
     * <p>
     * The code will be set to {@code HttpServletResponse.SC_BAD_REQUEST} as default.
     *
     * @param object          the object to check
     * @param messageSupplier a supplier for the exception message to use if the assertion fails
     * @throws BizException if the object is not {@code null}
     * @see #notNull(Object, int, String)
     */
    public static void notNull(Object object, Supplier<String> messageSupplier) {
        notNull(object, HttpServletResponse.SC_BAD_REQUEST, getValueFromSupplier(messageSupplier));
    }

    /**
     * Assert that the given String is not empty; that is, it must not be {@code null} and not the empty String.
     *
     * @param text    the String to check
     * @param code    the code returned to the front-end
     * @param message the exception message to use if the assertion fails
     * @throws BizException if the text is empty
     * @see StringUtil#hasLength
     */
    public static void hasLength(String text, int code, String message) {
        if (!StringUtil.hasLength(text)) {
            throw new BizException(code, message);
        }
    }

    /**
     * Assert that the given String is not empty; that is, it must not be {@code null} and not the empty String.
     * <p>
     * The code will be set to {@code HttpServletResponse.SC_BAD_REQUEST} as default.
     *
     * @param text    the String to check
     * @param message the exception message to use if the assertion fails
     * @throws BizException if the text is empty
     * @see StringUtil#hasLength
     * @see #hasLength(String, int, String)
     */
    public static void hasLength(String text, String message) {
        hasLength(text, HttpServletResponse.SC_BAD_REQUEST, message);
    }

    /**
     * Assert that the given String is not empty; that is, it must not be {@code null} and not the empty String.
     *
     * @param text            the String to check
     * @param code            the code returned to the front-end
     * @param messageSupplier a supplier for the exception message to use if the assertion fails
     * @throws BizException if the text is empty
     * @see StringUtil#hasLength
     * @see #hasLength(String, int, String)
     */
    public static void hasLength(String text, int code, Supplier<String> messageSupplier) {
        hasLength(text, code, getValueFromSupplier(messageSupplier));
    }

    /**
     * Assert that the given String is not empty; that is, it must not be {@code null} and not the empty String.
     * <p>
     * The code will be set to {@code HttpServletResponse.SC_BAD_REQUEST} as default.
     *
     * @param text            the String to check
     * @param messageSupplier a supplier for the exception message to use if the assertion fails
     * @throws BizException if the text is empty
     * @see StringUtil#hasLength
     * @see #hasLength(String, int, String)
     */
    public static void hasLength(String text, Supplier<String> messageSupplier) {
        hasLength(text, HttpServletResponse.SC_BAD_REQUEST, getValueFromSupplier(messageSupplier));
    }

    /**
     * Assert that the given String contains valid text content; that is, it must not be {@code null} and must contain
     * at least one non-whitespace character.
     *
     * @param text    the String to check
     * @param code    the code returned to the front-end
     * @param message the exception message to use if the assertion fails
     * @throws BizException if the text does not contain valid text content
     * @see StringUtil#hasText
     */
    public static void hasText(String text, int code, String message) {
        if (!StringUtil.hasText(text)) {
            throw new BizException(code, message);
        }
    }

    /**
     * Assert that the given String contains valid text content; that is, it must not be {@code null} and must contain
     * at least one non-whitespace character.
     * <p>
     * The code will be set to {@code HttpServletResponse.SC_BAD_REQUEST} as default.
     *
     * @param text    the String to check
     * @param message the exception message to use if the assertion fails
     * @throws BizException if the text does not contain valid text content
     * @see StringUtil#hasText
     * @see #hasText(String, int, String)
     */
    public static void hasText(String text, String message) {
        hasText(text, HttpServletResponse.SC_BAD_REQUEST, message);
    }

    /**
     * Assert that the given String contains valid text content; that is, it must not be {@code null} and must contain
     * at least one non-whitespace character.
     *
     * @param text            the String to check
     * @param code            the code returned to the front-end
     * @param messageSupplier a supplier for the exception message to use if the assertion fails
     * @throws BizException if the text does not contain valid text content
     * @see StringUtil#hasText
     * @see #hasText(String, int, String)
     */
    public static void hasText(String text, int code, Supplier<String> messageSupplier) {
        hasText(text, code, getValueFromSupplier(messageSupplier));
    }

    /**
     * Assert that the given String contains valid text content; that is, it must not be {@code null} and must contain
     * at least one non-whitespace character.
     * <p>
     * The code will be set to {@code HttpServletResponse.SC_BAD_REQUEST} as default.
     *
     * @param text            the String to check
     * @param messageSupplier a supplier for the exception message to use if the assertion fails
     * @throws BizException if the text does not contain valid text content
     * @see StringUtil#hasText
     * @see #hasText(String, int, String)
     */
    public static void hasText(String text, Supplier<String> messageSupplier) {
        hasText(text, HttpServletResponse.SC_BAD_REQUEST, getValueFromSupplier(messageSupplier));
    }

    /**
     * Assert that the given text does not contain the given substring.
     *
     * @param textToSearch the text to search
     * @param substring    the substring to find within the text
     * @param code         the code returned to the front-end
     * @param message      the exception message to use if the assertion fails
     * @throws BizException if the text does not contain valid text content
     * @see StringUtil#hasLength(String)
     */
    public static void doesNotContain(String textToSearch, String substring, int code, String message) {
        if (StringUtil.hasLength(textToSearch) && StringUtil.hasLength(substring) &&
                textToSearch.contains(substring)) {
            throw new BizException(code, message);
        }
    }

    /**
     * Assert that the given text does not contain the given substring.
     * <p>
     * The code will be set to {@code HttpServletResponse.SC_BAD_REQUEST} as default.
     *
     * @param textToSearch the text to search
     * @param substring    the substring to find within the text
     * @param message      the exception message to use if the assertion fails
     * @throws BizException if the text does not contain valid text content
     * @see StringUtil#hasLength(String)
     * @see #doesNotContain(String, String, int, String)
     */
    public static void doesNotContain(String textToSearch, String substring, String message) {
        doesNotContain(textToSearch, substring, HttpServletResponse.SC_BAD_REQUEST, message);
    }

    /**
     * Assert that the given text does not contain the given substring.
     *
     * @param textToSearch    the text to search
     * @param code            the code returned to the front-end
     * @param substring       the substring to find within the text
     * @param messageSupplier a supplier for the exception message to use if the assertion fails
     * @throws BizException if the text does not contain valid text content
     * @see StringUtil#hasLength(String)
     * @see #doesNotContain(String, String, int, String)
     */
    public static void doesNotContain(String textToSearch, String substring, int code, Supplier<String> messageSupplier) {
        doesNotContain(textToSearch, substring, code, getValueFromSupplier(messageSupplier));
    }

    /**
     * Assert that the given text does not contain the given substring.
     * <p>
     * The code will be set to {@code HttpServletResponse.SC_BAD_REQUEST} as default.
     *
     * @param textToSearch    the text to search
     * @param substring       the substring to find within the text
     * @param messageSupplier a supplier for the exception message to use if the assertion fails
     * @throws BizException if the text does not contain valid text content
     * @see StringUtil#hasLength(String)
     * @see #doesNotContain(String, String, int, String)
     */
    public static void doesNotContain(String textToSearch, String substring, Supplier<String> messageSupplier) {
        doesNotContain(textToSearch, substring, HttpServletResponse.SC_BAD_REQUEST, getValueFromSupplier(messageSupplier));
    }

    /**
     * Assert that an array contains elements; that is, it must not be {@code null} and must contain at least one
     * element.
     *
     * @param array   the array to check
     * @param code    the code returned to the front-end
     * @param message the exception message to use if the assertion fails
     * @throws BizException if the object array is {@code null} or contains no elements
     * @see ObjectUtil#isEmpty(Object[])
     */
    public static void notEmpty(Object[] array, int code, String message) {
        if (ObjectUtil.isEmpty(array)) {
            throw new BizException(code, message);
        }
    }

    /**
     * Assert that an array contains elements; that is, it must not be {@code null} and must contain at least one
     * element.
     * <p>
     * The code will be set to {@code HttpServletResponse.SC_BAD_REQUEST} as default.
     *
     * @param array   the array to check
     * @param message the exception message to use if the assertion fails
     * @throws BizException if the object array is {@code null} or contains no elements
     * @see ObjectUtil#isEmpty(Object[])
     * @see #notEmpty(Object[], int, String)
     */
    public static void notEmpty(Object[] array, String message) {
        notEmpty(array, HttpServletResponse.SC_BAD_REQUEST, message);
    }

    /**
     * Assert that an array contains elements; that is, it must not be {@code null} and must contain at least one
     * element.
     *
     * @param array           the array to check
     * @param code            the code returned to the front-end
     * @param messageSupplier a supplier for the exception message to use if the assertion fails
     * @throws BizException if the object array is {@code null} or contains no elements
     * @see ObjectUtil#isEmpty(Object[])
     * @see #notEmpty(Object[], int, String)
     */
    public static void notEmpty(Object[] array, int code, Supplier<String> messageSupplier) {
        notEmpty(array, code, getValueFromSupplier(messageSupplier));
    }

    /**
     * Assert that an array contains elements; that is, it must not be {@code null} and must contain at least one
     * element.
     * <p>
     * The code will be set to {@code HttpServletResponse.SC_BAD_REQUEST} as default.
     *
     * @param array           the array to check
     * @param messageSupplier a supplier for the exception message to use if the assertion fails
     * @throws BizException if the object array is {@code null} or contains no elements
     * @see ObjectUtil#isEmpty(Object[])
     * @see #notEmpty(Object[], int, String)
     */
    public static void notEmpty(Object[] array, Supplier<String> messageSupplier) {
        notEmpty(array, HttpServletResponse.SC_BAD_REQUEST, getValueFromSupplier(messageSupplier));
    }

    /**
     * Assert that an array contains no {@code null} elements.
     *
     * @param array   the array to check
     * @param code    the code returned to the front-end
     * @param message the exception message to use if the assertion fails
     * @throws BizException if the object array is {@code null} or contains no elements
     */
    public static void noNullElements(Object[] array, int code, String message) {
        if (array != null) {
            for (var element : array) {
                if (element == null) {
                    throw new BizException(code, message);
                }
            }
        }
    }

    /**
     * Assert that an array contains no {@code null} elements.
     * <p>
     * The code will be set to {@code HttpServletResponse.SC_BAD_REQUEST} as default.
     *
     * @param array   the array to check
     * @param message the exception message to use if the assertion fails
     * @throws BizException if the object array is {@code null} or contains no elements
     * @see #noNullElements(Object[], int, String)
     */
    public static void noNullElements(Object[] array, String message) {
        noNullElements(array, HttpServletResponse.SC_BAD_REQUEST, message);
    }

    /**
     * Assert that an array contains no {@code null} elements.
     *
     * @param array           the array to check
     * @param code            the code returned to the front-end
     * @param messageSupplier a supplier for the exception message to use if the assertion fails
     * @throws BizException if the object array is {@code null} or contains no elements
     * @see #noNullElements(Object[], int, String)
     */
    public static void noNullElements(Object[] array, int code, Supplier<String> messageSupplier) {
        noNullElements(array, code, getValueFromSupplier(messageSupplier));
    }

    /**
     * Assert that an array contains no {@code null} elements.
     * <p>
     * The code will be set to {@code HttpServletResponse.SC_BAD_REQUEST} as default.
     *
     * @param array           the array to check
     * @param messageSupplier a supplier for the exception message to use if the assertion fails
     * @throws BizException if the object array is {@code null} or contains no elements
     * @see #noNullElements(Object[], int, String)
     */
    public static void noNullElements(Object[] array, Supplier<String> messageSupplier) {
        noNullElements(array, HttpServletResponse.SC_BAD_REQUEST, getValueFromSupplier(messageSupplier));
    }

    /**
     * Assert that a collection contains elements; that is, it must not be {@code null} and must contain at lease one
     * element.
     *
     * @param collection the collection to check
     * @param code       the code returned to the front-end
     * @param message    the exception message to use if the assertion fails
     * @throws BizException if the collection is {@code null} or contains no elements
     * @see CollectionUtil#isEmpty(Collection)
     */
    public static void notEmpty(Collection<?> collection, int code, String message) {
        if (CollectionUtil.isEmpty(collection)) {
            throw new BizException(code, message);
        }
    }

    /**
     * Assert that a collection contains elements; that is, it must not be {@code null} and must contain at lease one
     * element.
     * <p>
     * The code will be set to {@code HttpServletResponse.SC_BAD_REQUEST} as default.
     *
     * @param collection the collection to check
     * @param message    the exception message to use if the assertion fails
     * @throws BizException if the collection is {@code null} or contains no elements
     * @see CollectionUtil#isEmpty(Collection)
     * @see #notEmpty(Collection, int, String)
     */
    public static void notEmpty(Collection<?> collection, String message) {
        notEmpty(collection, HttpServletResponse.SC_BAD_REQUEST, message);
    }

    /**
     * Assert that a collection contains elements; that is, it must not be {@code null} and must contain at lease one
     * element.
     * <p>
     * The code will be set to {@code HttpServletResponse.SC_BAD_REQUEST} as default.
     *
     * @param collection      the collection to check
     * @param code            the code returned to the front-end
     * @param messageSupplier a supplier for the exception message to use if the assertion fails
     * @throws BizException if the collection is {@code null} or contains no elements
     * @see CollectionUtil#isEmpty(Collection)
     * @see #notEmpty(Collection, int, String)
     */
    public static void notEmpty(Collection<?> collection, int code, Supplier<String> messageSupplier) {
        notEmpty(collection, code, getValueFromSupplier(messageSupplier));
    }

    /**
     * Assert that a collection contains elements; that is, it must not be {@code null} and must contain at lease one
     * element.
     * <p>
     * The code will be set to {@code HttpServletResponse.SC_BAD_REQUEST} as default.
     *
     * @param collection      the collection to check
     * @param messageSupplier a supplier for the exception message to use if the assertion fails
     * @throws BizException if the collection is {@code null} or contains no elements
     * @see CollectionUtil#isEmpty(Collection)
     * @see #notEmpty(Collection, int, String)
     */
    public static void notEmpty(Collection<?> collection, Supplier<String> messageSupplier) {
        notEmpty(collection, HttpServletResponse.SC_BAD_REQUEST, getValueFromSupplier(messageSupplier));
    }

    /**
     * Assert that a collection contains no {@code null} elements.
     *
     * @param collection the collection to check
     * @param code       the code returned to the front-end
     * @param message    the exception message to use if the assertion fails
     * @throws BizException if the collection contains a {@code null} element
     */
    public static void noNullElements(Collection<?> collection, int code, String message) {
        if (collection != null) {
            for (var element : collection) {
                if (element == null) {
                    throw new BizException(code, message);
                }
            }
        }
    }

    /**
     * Assert that a collection contains no {@code null} elements.
     * <p>
     * The code will be set to {@code HttpServletResponse.SC_BAD_REQUEST} as default.
     *
     * @param collection the collection to check
     * @param message    the exception message to use if the assertion fails
     * @throws BizException if the collection contains a {@code null} element
     * @see #noNullElements(Collection, int, String)
     */
    public static void noNullElements(Collection<?> collection, String message) {
        noNullElements(collection, HttpServletResponse.SC_BAD_REQUEST, message);
    }

    /**
     * Assert that a collection contains no {@code null} elements.
     * <p>
     * The code will be set to {@code HttpServletResponse.SC_BAD_REQUEST} as default.
     *
     * @param collection      the collection to check
     * @param code            the code returned to the front-end
     * @param messageSupplier a supplier for the exception message to use if the assertion fails
     * @throws BizException if the collection contains a {@code null} element
     * @see #noNullElements(Collection, int, String)
     */
    public static void noNullElements(Collection<?> collection, int code, Supplier<String> messageSupplier) {
        noNullElements(collection, code, getValueFromSupplier(messageSupplier));
    }

    /**
     * Assert that a collection contains no {@code null} elements.
     * <p>
     * The code will be set to {@code HttpServletResponse.SC_BAD_REQUEST} as default.
     *
     * @param collection      the collection to check
     * @param messageSupplier a supplier for the exception message to use if the assertion fails
     * @throws BizException if the collection contains a {@code null} element
     * @see #noNullElements(Collection, int, String)
     */
    public static void noNullElements(Collection<?> collection, Supplier<String> messageSupplier) {
        noNullElements(collection, HttpServletResponse.SC_BAD_REQUEST, getValueFromSupplier(messageSupplier));
    }

    /**
     * Assert that a Map contains entries; that is, it must not be {@code null} and must contain at least one entry.
     *
     * @param map     the map to check
     * @param code    the code returned to the front-end
     * @param message the exception message to use if the assertion fails
     * @throws BizException if the map is {@code null} or contains no entries
     */
    public static void notEmpty(Map<?, ?> map, int code, String message) {
        if (CollectionUtil.isEmpty(map)) {
            throw new BizException(code, message);
        }
    }

    /**
     * Assert that a Map contains entries; that is, it must not be {@code null} and must contain at least one entry.
     *
     * @param map             the map to check
     * @param code            the code returned to the front-end
     * @param messageSupplier a supplier for the exception message to use if the assertion fails
     * @throws BizException if the map is {@code null} or contains no entries
     */
    public static void notEmpty(Map<?, ?> map, int code, Supplier<String> messageSupplier) {
        notEmpty(map, code, getValueFromSupplier(messageSupplier));
    }

    /**
     * Assert that a Map contains entries; that is, it must not be {@code null} and must contain at least one entry.
     * <p>
     * The code will be set to {@code HttpServletResponse.SC_BAD_REQUEST} as default.
     *
     * @param map     the map to check
     * @param message the exception message to use if the assertion fails
     * @throws BizException if the map is {@code null} or contains no entries
     */
    public static void notEmpty(Map<?, ?> map, String message) {
        notEmpty(map, HttpServletResponse.SC_BAD_REQUEST, message);
    }

    /**
     * Assert that a Map contains entries; that is, it must not be {@code null} and must contain at least one entry.
     * <p>
     * The code will be set to {@code HttpServletResponse.SC_BAD_REQUEST} as default.
     *
     * @param map             the map to check
     * @param messageSupplier a supplier for the exception message to use if the assertion fails
     * @throws BizException if the map is {@code null} or contains no entries
     */
    public static void notEmpty(Map<?, ?> map, Supplier<String> messageSupplier) {
        notEmpty(map, HttpServletResponse.SC_BAD_REQUEST, getValueFromSupplier(messageSupplier));
    }

}
