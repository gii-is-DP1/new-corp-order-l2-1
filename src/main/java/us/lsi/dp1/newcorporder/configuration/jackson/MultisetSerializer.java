package us.lsi.dp1.newcorporder.configuration.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.collect.Multiset;
import us.lsi.dp1.newcorporder.util.CollectionUtils;

import java.io.IOException;

public class MultisetSerializer extends StdSerializer<Multiset<?>> {

    public MultisetSerializer() {
        super(TypeFactory.defaultInstance().constructType(new TypeReference<Multiset<?>>() {}));
    }

    @Override
    public void serialize(Multiset<?> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeObject(CollectionUtils.asMap(value));
    }
}
