package us.lsi.dp1.newcorporder.configuration.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.collect.Multiset;
import us.lsi.dp1.newcorporder.util.CollectionUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MultisetDeserializer extends JsonDeserializer<Multiset<?>> implements ContextualDeserializer {

    private JavaType mapType;

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
        TypeFactory typeFactory = TypeFactory.defaultInstance();
        JavaType keyType = property.getType().containedType(0);
        JavaType valueType = typeFactory.constructType(int.class);

        this.mapType = typeFactory.constructMapType(HashMap.class, keyType, valueType);
        return this;
    }

    @Override
    public Multiset<?> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Map<?, Integer> map = ctxt.readValue(p, this.mapType);
        return CollectionUtils.asMultiset(map);
    }
}
