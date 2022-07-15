package com.cosmian.rest.abe;

import com.cosmian.rest.kmip.types.CryptographicAlgorithm;
import com.cosmian.rest.kmip.types.KeyFormatType;
import com.cosmian.rest.kmip.types.VendorAttribute;

public class AbeSpecifications {
    private String implementation;

    public AbeSpecifications(String implementation) {
        this.implementation = implementation;
    }

    public String getImplementation() {
        return this.implementation;
    }

    public CryptographicAlgorithm getCryptographicAlgorithm() {
        if (implementation == "abe_gpsw") {
            return CryptographicAlgorithm.ABE;
        } else {
            return CryptographicAlgorithm.CoverCrypt;
        }
    }

    public KeyFormatType getKeyFormatType() {
        if (implementation == "abe_gpsw") {
            return KeyFormatType.AbeMasterSecretKey;
        } else {
            return KeyFormatType.CoverCryptSecretKey;
        }
    }

    public KeyFormatType getKeyFormatTypePublicKey() {
        if (implementation == "abe_gpsw") {
            return KeyFormatType.AbeMasterPublicKey;
        } else {
            return KeyFormatType.CoverCryptPublicKey;
        }
    }

    public String getAccessPolicyVendorAttribute() {
        if (implementation == "abe_gpsw") {
            return VendorAttribute.VENDOR_ATTR_ABE_ACCESS_POLICY;
        } else {
            return VendorAttribute.VENDOR_ATTR_COVER_CRYPT_ACCESS_POLICY;
        }
    }

    public String getPolicyVendorAttribute() {
        if (implementation == "abe_gpsw") {
            return VendorAttribute.VENDOR_ATTR_ABE_POLICY;
        } else {
            return VendorAttribute.VENDOR_ATTR_COVER_CRYPT_POLICY;
        }
    }

    public String getAbeAttrVendorAttribute() {
        if (implementation == "abe_gpsw") {
            return VendorAttribute.VENDOR_ATTR_ABE_ATTR;
        } else {
            return VendorAttribute.VENDOR_ATTR_COVER_CRYPT_ATTR;
        }
    }
}
