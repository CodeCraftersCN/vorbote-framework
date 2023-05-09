package cn.vorbote.core.utils;

import java.util.Collection;
import java.util.Map;

/**
 * CollectionUtil<br>
 * Created at 20/09/2022 12:24
 *
 * @author theod
 */
public final class CollectionUtil {

    // Make constructor as private, prevent from being instanced.
    private CollectionUtil() {
    }

    /**
     * Return {@code true} if the supplied Collection is {@code null} or empty. Otherwise, return {@code false}.
     *
     * @param collection the Collection to check
     * @return whether the given Collection is empty
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection.isEmpty();
    }

    /**
     * Return {@code true} if the supplied Map is {@code null} or empty. Otherwise, return {@code false}.
     *
     * @param map the Map to check
     * @return whether the given Map is empty
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }


}
