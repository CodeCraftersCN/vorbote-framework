package cn.vorbote.message.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * JacksonSerializer<br>
 * Created at 02/09/2022 15:17
 *
 * @author theod
 */
@Slf4j
public final class JacksonSerializer {

    private final ObjectMapper objectMapper;

    /**
     * Constructor
     *
     * @param objectMapper The object mapper.
     */
    private JacksonSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
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

    private static JacksonSerializer _jacksonSerializer;

    /**
     * Get the Jackson Serializer with specified Object Mapper.<br>
     * <p><b>
     *     Note:<br>
     *     You had better not to invoke this method on your own.
     * </b></p>
     *
     * @param objectMapper The object mapper.
     * @return A Jackson Serializer.
     */
    public static JacksonSerializer getJacksonSerializer(ObjectMapper objectMapper) {
        if (_jacksonSerializer == null) {
            log.debug("Creating a new JacksonSerializer");
            _jacksonSerializer = new JacksonSerializer(objectMapper);
        }
        return _jacksonSerializer;
    }
}
