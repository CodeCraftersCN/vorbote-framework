package cn.vorbote.web.utils;

import cn.vorbote.web.exceptions.BizException;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

/**
 * This class is to do some business assertions.
 * <p>
 * If the assertion is not passed, the util will throw a {@link BizException}
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
     * Assert that the given text does not contain the given substring.
     * <pre class="code">BizAssert.doesNotContain(name, "rod", "Name must not contain \"rod\".");</pre>
     *
     * @param textToSearch the text to search
     * @param substring    the substring to find within the text
     * @param code         the code when assertion fails
     * @param message      the exception message to use if the assertion fails
     * @throws BizException if the text contains the substring
     */
    public static void doesNotContain(String textToSearch, String substring, int code, String message) {
        if (textToSearch != null && !textToSearch.isBlank() && textToSearch.contains(substring)) {
            throw new BizException(code, message);
        }
    }


    public static void doesNotContain(String textToSearch, String substring, String message) {
        doesNotContain(textToSearch, substring, HttpServletResponse.SC_BAD_REQUEST, message);
    }


    public static void doesNotContain(String textToSearch, String substring, int code, Supplier<String> messageSupplier) {
        if (textToSearch != null && !textToSearch.isBlank() && textToSearch.contains(substring)) {
            throw new BizException(code, getMessageFromSupplier(messageSupplier));
        }
    }


    public static void doesNotContain(String textToSearch, String substring, Supplier<String> messageSupplier) {
        doesNotContain(textToSearch, substring, HttpServletResponse.SC_BAD_REQUEST, messageSupplier);
    }


    public static void hasLength(String text, int code, String message) {
        if (text != null && text.length() > 0) {
            throw new BizException(code, message);
        }
    }


    public static void hasLength(String text, String message) {
        hasLength(text, HttpServletResponse.SC_BAD_REQUEST, message);
    }


    public static void hasLength(String text, int code, Supplier<String> messageSupplier) {
        if (text != null && text.length() > 0) {
            throw new BizException(code, getMessageFromSupplier(messageSupplier));
        }
    }


    public static void hasLength(String text, Supplier<String> messageSupplier) {
        hasLength(text, HttpServletResponse.SC_BAD_REQUEST, messageSupplier);
    }


    public static void hasText(String text, int code, String message) {
        if (!StringUtil.hasText(text)) {
            throw new BizException(, message);
        }
    }


    public static void hasText(String text, String message) {
        if (!StringUtil.hasText(text)) {
            throw new BizException(WebStatus.BAD_REQUEST.getCode(), message);
        }
    }

    /**
     * Assert that the given {@code String} contains valid text content; that is, it must not be {@code null} and must
     * contain at lease one non-whitespace character.
     *
     * @param text            The {@code String} to check.
     * @param messageSupplier A supplier for the exception message to use if the assertion fails.
     * @throws BizException If the text does not contain valid text content.
     */
    public static void hasText(String text, Supplier<String> messageSupplier) {
        if (!StringUtil.hasText(text)) {
            throw new BizException(WebStatus.BAD_REQUEST.getCode(), getMessageFromSupplier(messageSupplier));
        }
    }

    /**
     * Assert that {@code superType.isAssignableFrom(subType)} is {@code true}.
     *
     * @param superType The super type to check against.
     * @param subType   The subtype to check.
     * @param message   A message which will be prepended to provide further context. If it is empty or end in
     *                  {@code ":"} or {@code ";"} or {@code ","} or {@code "."}, a full exception message will be
     *                  appended. If it ends in a space, the name of the offending subtype will be appended.
     * @throws BizException If the classes are not assignable.
     */
    public static void isAssignable(Class<?> superType, Class<?> subType, String message) {
        notNull(superType, "Super type to check against must not be null.");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            assignableCheckFailed(superType, subType, message);
        }
    }

    /**
     * Assert that {@code superType.isAssignableFrom(subType)} is {@code true}.
     *
     * @param superType       The super type to check against.
     * @param subType         The subtype to check.
     * @param messageSupplier A supplier for the exception message to use if the assertion fails. See
     *                        {@link #isAssignable(Class, Class, String)} for details.
     * @throws BizException If the classes are not assignable.
     */
    public static void isAssignable(Class<?> superType, Class<?> subType, Supplier<String> messageSupplier) {
        notNull(superType, "Super type to check against must not be null.");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            assignableCheckFailed(superType, subType, getMessageFromSupplier(messageSupplier));
        }
    }

    /**
     * Assert that the provided object is an instance of the provided class.
     *
     * @param type    The type to check against.
     * @param object  The object to check.
     * @param message A message which will be prepended to provide further context. If it is empty or ends in
     *                {@code ":"} or {@code ";"} or {@code ","} or {@code "."}, a full exception message will be
     *                appended. In any other case, a {@code ":"} with a space and the name of the offending object's
     *                type will be appended.
     * @throws BizException If the object is not an instance of type.
     */
    public static void isInstanceOf(Class<?> type, Object object, String message) {
        notNull(type, "Type to check against must not be null.");
        if (!type.isInstance(object)) {
            instanceCheckFailed(type, object, message);
        }
    }

    /**
     * Assert that the provided object is an instance of the provided class.
     *
     * @param type            The type to check against.
     * @param object          The object to check.
     * @param messageSupplier A supplier for the exception message to use if the assertion fails. See
     *                        {@link #isInstanceOf(Class, Object, String)} for details.
     * @throws BizException If the object is not an instance of type.
     */
    public static void isInstanceOf(Class<?> type, Object object, Supplier<String> messageSupplier) {
        notNull(type, "Type to check against must not be null.");
        if (!type.isInstance(object)) {
            instanceCheckFailed(type, object, getMessageFromSupplier(messageSupplier));
        }
    }

    /**
     * Assert that an object is null.
     *
     * @param object  The object to check.
     * @param message The exception message to use if assertion fails.
     * @throws BizException If the object is not null.
     */
    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new BizException(WebStatus.BAD_REQUEST.getCode(), message);
        }
    }

    /**
     * Assert that an object is null.
     *
     * @param object          The object to check.
     * @param messageSupplier A supplier for the exception message to use if assertion fails.
     * @throws BizException If the object is not null.
     */
    public static void isNull(Object object, Supplier<String> messageSupplier) {
        if (object != null) {
            throw new BizException(WebStatus.BAD_REQUEST.getCode(), getMessageFromSupplier(messageSupplier));
        }
    }

    /**
     * Assert a boolean expression, throwing an {@code BizException} if the expression evaluates to {@code false}.
     *
     * @param expression A boolean expression.
     * @param message    The exception message to use if assertion fails.
     * @throws BizException If expression is {@code false}
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new BizException(WebStatus.BAD_REQUEST.getCode(), message);
        }
    }

    /**
     * Assert a boolean expression, throwing an {@code BizException} if the expression evaluates to {@code false}.
     *
     * @param expression      A boolean expression.
     * @param messageSupplier A supplier for the exception message to use if assertion fails.
     * @throws BizException If expression is {@code false}
     */
    public static void isTrue(boolean expression, Supplier<String> messageSupplier) {
        if (!expression) {
            throw new BizException(WebStatus.BAD_REQUEST.getCode(), getMessageFromSupplier(messageSupplier));
        }
    }

    /**
     * Assert that a collection contains no null elements. <br>
     * Note: Does not complain if the collection is empty.
     *
     * @param collection The collection to check.
     * @param message    The exception message to use if assertion fails.
     * @throws BizException If the collection contains a null element.
     */
    public static void noNullElements(Collection<?> collection, String message) {
        if (collection != null) {
            collection.forEach((item) -> {
                if (item == null) {
                    throw new BizException(WebStatus.BAD_REQUEST.getCode(), message);
                }
            });
        }
    }

    /**
     * Assert that a collection contains no null elements. <br>
     * Note: Does not complain if the collection is empty.
     *
     * @param collection      The collection to check.
     * @param messageSupplier A supplier for the exception message to use if assertion fails.
     * @throws BizException If the collection contains a null element.
     */
    public static void noNullElements(Collection<?> collection, Supplier<String> messageSupplier) {
        if (collection != null) {
            collection.forEach((item) -> {
                if (item == null) {
                    throw new BizException(WebStatus.BAD_REQUEST.getCode(), getMessageFromSupplier(messageSupplier));
                }
            });
        }
    }

    /**
     * Assert that an array contains no null elements.<br>
     * Note: Does not complain if the array is empty.
     *
     * @param array   The array to check.
     * @param message The exception message to use if assertion fails.
     * @throws BizException If the object array contains a null element.
     */
    public static void noNullElements(Object[] array, String message) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new BizException(WebStatus.BAD_REQUEST.getCode(), message);
                }
            }
        }
    }

    /**
     * Assert that an array contains no null elements.<br>
     * Note: Does not complain if the array is empty.
     *
     * @param array           The array to check.
     * @param messageSupplier A supplier for the exception message to use if assertion fails.
     * @throws BizException If the object array contains a null element.
     */
    public static void noNullElements(Object[] array, Supplier<String> messageSupplier) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new BizException(WebStatus.BAD_REQUEST.getCode(), getMessageFromSupplier(messageSupplier));
                }
            }
        }
    }

    /**
     * Assert that a collection contains elements; that is, it must not be {@code null} and must contain at least one
     * element.
     *
     * @param collection The collection to check.
     * @param message    The exception message to use if the assertion fails.
     * @throws BizException If the collection is null or contains no elements.
     */
    public static void notEmpty(Collection<?> collection, String message) {
        if (collection == null || collection.size() == 0) {
            throw new BizException(WebStatus.BAD_REQUEST.getCode(), message);
        }
    }

    /**
     * Assert that a collection contains elements; that is, it must not be {@code null} and must contain at least one
     * element.
     *
     * @param collection      The collection to check.
     * @param messageSupplier A supplier for the exception message to use if the assertion fails.
     * @throws BizException If the collection is null or contains no elements.
     */
    public static void notEmpty(Collection<?> collection, Supplier<String> messageSupplier) {
        if (collection == null || collection.size() == 0) {
            throw new BizException(WebStatus.BAD_REQUEST.getCode(), getMessageFromSupplier(messageSupplier));
        }
    }

    /**
     * Assert that a {@code Map} contains entries; that is, it must not be {@code null} and must contain at least one
     * entry.
     *
     * @param map     The map to check.
     * @param message The exception message to use if the assertion fails.
     * @throws BizException If the map is null or contains no entries.
     */
    public static void notEmpty(Map<?, ?> map, String message) {
        if (map == null || map.size() == 0) {
            throw new BizException(WebStatus.BAD_REQUEST.getCode(), message);
        }
    }

    /**
     * Assert that a {@code Map} contains entries; that is, it must not be {@code null} and must contain at least one
     * entry.
     *
     * @param map             The map to check.
     * @param messageSupplier A supplier for the exception message to use if the assertion fails.
     * @throws BizException If the map is null or contains no entries.
     */
    public static void notEmpty(Map<?, ?> map, Supplier<String> messageSupplier) {
        if (map == null || map.size() == 0) {
            throw new BizException(WebStatus.BAD_REQUEST.getCode(), getMessageFromSupplier(messageSupplier));
        }
    }

    /**
     * Assert that an array contains no {@code null} elements.<br>
     * Note: Does not complain if the array is empty.
     *
     * @param array   The array to check.
     * @param message The exception message to use if the assertion fails.
     * @throws BizException If the object array contains a null element.
     */
    public static void notEmpty(Object[] array, String message) {
        if (array == null || array.length == 0) {
            throw new BizException(WebStatus.BAD_REQUEST.getCode(), message);
        }
    }

    /**
     * Assert that an array contains no {@code null} elements.<br>
     * Note: Does not complain if the array is empty.
     *
     * @param array           The array to check.
     * @param messageSupplier A supplier for the exception message to use if the assertion fails.
     * @throws BizException If the object array contains a null element.
     */
    public static void notEmpty(Object[] array, Supplier<String> messageSupplier) {
        if (array == null || array.length == 0) {
            throw new BizException(WebStatus.BAD_REQUEST.getCode(), getMessageFromSupplier(messageSupplier));
        }
    }

    /**
     * Assert that an object is not null.
     *
     * @param object  The object to check.
     * @param message The exception message to use if the assertion fails.
     * @throws BizException If the object is null.
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new BizException(WebStatus.BAD_REQUEST.getCode(), message);
        }
    }

    /**
     * Assert that an object is not null.
     *
     * @param object          The object to check.
     * @param messageSupplier A supplier for the exception message to use if the assertion fails.
     * @throws BizException If the object is null.
     */
    public static void notNull(Object object, Supplier<String> messageSupplier) {
        if (object == null) {
            throw new BizException(WebStatus.BAD_REQUEST.getCode(), getMessageFromSupplier(messageSupplier));
        }
    }

    /**
     * Assert a boolean expression, throwing an {@code BizException} if the expression evaluates to {@code false}.<br>
     * Call {@link #isTrue(boolean, String)} if you wish to throw an {@code BizException} on assertion failure.
     *
     * @param expression A boolean expression.
     * @param message    The exception message to use if the assertion fails.
     * @throws BizException If expression is false.
     */
    public static void state(boolean expression, String message) {
        if (!expression) {
            throw new BizException(WebStatus.BAD_REQUEST.getCode(), message);
        }
    }

    /**
     * Assert a boolean expression, throwing an {@code BizException} if the expression evaluates to {@code false}.<br>
     * Call {@link #isTrue(boolean, String)} if you wish to throw an {@code BizException} on assertion failure.
     *
     * @param expression      A boolean expression.
     * @param messageSupplier A supplier for the exception message to use if the assertion fails.
     * @throws BizException If expression is false.
     */
    public static void state(boolean expression, Supplier<String> messageSupplier) {
        if (!expression) {
            throw new BizException(WebStatus.BAD_REQUEST.getCode(), getMessageFromSupplier(messageSupplier));
        }
    }

    /**
     * Check whether the given {@code String} is ends with a colon ({@code ":"}), a semicolon ({@code ";"}), a comma
     * ({@code ","}) or a period ({@code "."}).
     *
     * @param message The {@code String} to check.
     * @return Value {@code true} when the given {@code String} ends with a colon ({@code ":"}), a semicolon
     * ({@code ";"}), a comma ({@code ","}) or a period ({@code "."}).
     */
    private static boolean endsWithSeparator(String message) {
        return (message.endsWith(":") || message.endsWith(";") || message.endsWith(",") || message.endsWith("."));
    }

    /**
     * Generate a message with specific type name.
     *
     * @param message  The message to be appended.
     * @param typeName The type name to append.
     * @return Appended message {@code String}.
     */
    private static String messageWithTypeName(String message, Object typeName) {
        return message + (message.endsWith(" ") ? "" : ": ") + typeName;
    }

    /**
     * Generate a {@code BizException} with an appropriate message when the object is not the type.
     *
     * @param type    The specific type.
     * @param obj     The checked object.
     * @param message The message to show.
     */
    private static void instanceCheckFailed(Class<?> type, Object obj, String message) {
        String className = (obj != null ? obj.getClass().getName() : "null");
        String result = "";
        boolean defaultMessage = true;

        if (!StringUtil.isBlank(message)) {
            if (endsWithSeparator(message)) {
                result = message + " ";
            } else {
                result = messageWithTypeName(message, className);
                defaultMessage = false;
            }
        }

        if (defaultMessage) {
            result += ("Object of class [" + className + "] must be an instance of " + type);
        }

        throw new BizException(WebStatus.BAD_REQUEST.getCode(), result);
    }

    /**
     * Generate a {@code BizException} with an appropriate message when the subtype is not assignable from the
     * supertype.
     *
     * @param superType The super type to check against.
     * @param subType   The subtype to check.
     * @param message   The message to show.
     */
    private static void assignableCheckFailed(Class<?> superType, Class<?> subType, String message) {
        String result = "";
        boolean defaultMessage = true;
        if (!StringUtil.isBlank(message)) {
            if (endsWithSeparator(message)) {
                result = message + " ";
            } else {
                result = messageWithTypeName(message, subType);
                defaultMessage = false;
            }
        }

        if (defaultMessage) {
            result += (subType + " is not assignable to " + superType);
        }

        throw new BizException(WebStatus.BAD_REQUEST.getCode(), result);
    }

    /**
     * Get a message from a supplier.
     *
     * @param messageSupplier A supplier which supply a exception message.
     * @return The message from the supplier or {@code null} when the supplier is null.
     */
    private static String getMessageFromSupplier(Supplier<String> messageSupplier) {
        return (messageSupplier != null) ? messageSupplier.get() : null;
    }

}
