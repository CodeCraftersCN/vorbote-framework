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

    private JacksonSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public static JacksonSerializer getInstance() {
        if (_jacksonSerializer == null) {
            _jacksonSerializer = new JacksonSerializer(new ObjectMapper());
        }
        return _jacksonSerializer;
    }

    public String serialize(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public <T> T deserialize(String json, Class<T> target) throws JsonProcessingException {
        return objectMapper.readValue(json, target);
    }
}
