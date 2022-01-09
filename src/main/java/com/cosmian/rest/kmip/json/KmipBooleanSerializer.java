package com.cosmian.rest.kmip.json;

import java.io.IOException;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class KmipBooleanSerializer extends StdSerializer<Boolean> {

    private static final Logger logger = Logger.getLogger(KmipBooleanSerializer.class.getName());

    private final String tag;

    public KmipBooleanSerializer(String tag) {
        super(Boolean.class);
        this.tag = tag;
    }

    @Override
    public void serialize(Boolean value, JsonGenerator generator, SerializerProvider serializers) throws IOException {

        logger.finer(() -> "Serializing a " + value.getClass().toString());

        generator.writeStartObject();
        generator.writeFieldName("tag");
        generator.writeString(tag);
        generator.writeFieldName("type");
        generator.writeString("Boolean");
        generator.writeFieldName("value");
        generator.writeBoolean(value);
        generator.writeEndObject();
    }
}
