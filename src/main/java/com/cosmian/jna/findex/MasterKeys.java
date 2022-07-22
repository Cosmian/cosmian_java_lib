package com.cosmian.jna.findex;

import com.cosmian.CosmianException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MasterKeys {
    @JsonProperty("k")
    private  byte[] k;

    @JsonProperty("k_star")
    private  byte[] kStar;

    public MasterKeys() {}

    public MasterKeys(byte[] k, byte[] kStar) {
        this.k = k;
        this.kStar = kStar;
    }

    public byte[] getK() {
        return k;
    }

    public byte[] getKStar() {
        return kStar;
    }

    /**
     * This method is mostly used for local tests and serialization.
     *
     * @return the JSON string
     * @throws CosmianException if the serialization fails
     */
    public String toJson() throws CosmianException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new CosmianException("Failed serializing to JSON the MasterKeys.class: " + e.getMessage(), e);
        }
    }

    public static MasterKeys fromJson(String json) throws CosmianException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, MasterKeys.class);
        } catch (JsonProcessingException e) {
            throw new CosmianException("Failed deserializing from JSON the MasterKeys.class " + ": " + e.getMessage(),
                e);
        }
    }

}
