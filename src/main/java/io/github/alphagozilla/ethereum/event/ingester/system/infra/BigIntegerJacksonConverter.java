package io.github.alphagozilla.ethereum.event.ingester.system.infra;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.github.alphagozilla.ethereum.event.ingester.ingester.util.StringUtil;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.math.BigInteger;

/**
 * @author AlphaGodzilla
 * @date 2022/3/30 14:06
 */
@JsonComponent
public class BigIntegerJacksonConverter {
    public static class BigIntegerSerializer extends JsonSerializer<BigInteger> {
        @Override
        public void serialize(BigInteger bigInteger, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (bigInteger != null) {
                jsonGenerator.writeString(bigInteger.toString());
            }
        }
    }

    public static class BigIntegerDeSerializer extends JsonDeserializer<BigInteger> {
        @Override
        public BigInteger deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            String valueAsString = jsonParser.getValueAsString();
            if (StringUtil.isEmpty(valueAsString)) {
                return null;
            }
            return new BigInteger(valueAsString);
        }
    }
}
