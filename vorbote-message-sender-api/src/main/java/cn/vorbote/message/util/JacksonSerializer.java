package cn.vorbote.message.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JacksonSerializer<br>
 * Created at 02/09/2022 15:17
 *
 * @author theod
 */
public final class JacksonSerializer {

    private static JacksonSerializer _jacksonSerializer;

    private final ObjectMapper objectMapper;

    protected JacksonSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Get the instance of the serializer.
     *
     * @return The instance of the serializer.
     */
    public static JacksonSerializer getInstance() {
        if (_jacksonSerializer == null) {
            _jacksonSerializer = new JacksonSerializer(new ObjectMapper());
        }
        return _jacksonSerializer;
    }

    /**
     * Serialize an object to string.
     *
     * @param object The object to be converted to a json string.
     * @return A converted json string.
     * @throws JsonProcessingException ObjectMapper could make this exception because
     *                                 of the data is not serializable.
     */
    public String serialize(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    /**
     * Deserialize a json string to object.
     *
     * @param json   The object to be converted to a json string.
     * @param target The target type of the object.
     * @param <T>    The target type of the object.
     * @return A converted object.
     * @throws JsonProcessingException ObjectMapper could make this exception because
     *                                 of the data is not serializable.
     */
    public <T> T deserialize(String json, Class<T> target) throws JsonProcessingException {
        return objectMapper.readValue(json, target);
    }
}
