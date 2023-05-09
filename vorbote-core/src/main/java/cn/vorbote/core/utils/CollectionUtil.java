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
    private CollectionUtil() { }

    public static boolean isEmpty(Collection<?> collection) {
        return collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !collection.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }


}
