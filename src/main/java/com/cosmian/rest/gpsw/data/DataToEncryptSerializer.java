package com.cosmian.rest.gpsw.data;

import java.io.IOException;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.Hex;

import com.cosmian.rest.gpsw.acccess_policy.Attr;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DataToEncryptSerializer extends JsonSerializer<DataToEncrypt> {

    private static final Logger logger = Logger.getLogger(DataToEncryptSerializer.class.getName());

    @Override
    public void serialize(DataToEncrypt attr, JsonGenerator generator, SerializerProvider serializers)
        throws IOException {

        logger.finer(() -> "Serializing a " + attr.getClass().toString());

        generator.writeStartObject();
        generator.writeFieldName("PolicyAttributes");
        generator.writeStartArray();
        for (Attr attribute : attr.getPolicyAttributes()) {
            generator.writeString(attribute.toString());
        }
        generator.writeEndArray();
        generator.writeFieldName("Data");
        generator.writeString(Hex.encodeHexString(attr.getData()));
        generator.writeEndObject();
    }
}
