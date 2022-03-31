package io.github.alphagozilla.ethereum.event.ingester.system.infra;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.ingester.util.StringUtil;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

/**
 * @author AlphaGodzilla
 * @date 2022/3/30 14:15
 */
@JsonComponent
public class AddressJacksonConverter {
    public static class AddressSerializer extends JsonSerializer<Address> {
        @Override
        public void serialize(Address address, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (address != null) {
                jsonGenerator.writeString(address.getValue());
            }
        }
    }

    public static class AddressDeserializer extends JsonDeserializer<Address> {
        @Override
        public Address deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            String valueAsString = jsonParser.getValueAsString();
            if (StringUtil.isEmpty(valueAsString)) {
                return null;
            }
            return new Address(valueAsString);
        }
    }
}
