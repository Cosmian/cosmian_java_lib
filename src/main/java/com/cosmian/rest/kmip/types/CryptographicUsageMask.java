package com.cosmian.rest.kmip.types;

public class CryptographicUsageMask {

    /**
     * Allow for signing. Applies to Sign operation. Valid for PGP Key, Private Key
     */
    public final static int Sign = 0x0000_0001;

    /**
     * Allow for signature verification. Applies to Signature Verify and Validate operations. Valid for PGP Key,
     * Certificate and Public Key.
     */
    public final static int Verify = 0x0000_0002;

    /**
     * Allow for encryption. Applies to Encrypt operation. Valid for PGP Key, Private Key, Public Key and Symmetric Key.
     * Encryption for the purpose of wrapping is separate Wrap Key value.
     */
    public final static int Encrypt = 0x0000_0004;

    /**
     * Allow for decryption. Applies to Decrypt operation. Valid for PGP Key, Private Key, Public Key and Symmetric Key.
     * Decryption for the purpose of unwrapping is separate Unwrap Key value.
     */
    public final static int Decrypt = 0x0000_0008;

    /**
     * Allow for key wrapping. Applies to Get operation when wrapping is required by Wrapping Specification is provided
     * on the object used to Wrap. Valid for PGP Key, Private Key and Symmetric Key. Note: even if the underlying
     * wrapping mechanism is encryption, this value is logically separate.
     */
    public final static int Wrap_Key = 0x0000_0010;

    /**
     * Allow for key unwrapping. Applies to Get operation when unwrapping is required on the object used to Unwrap.
     * Valid for PGP Key, Private Key, Public Key and Symmetric Key. Not interchangeable with Decrypt. Note: even if the
     * underlying unwrapping mechanism is decryption, this value is logically separate.
     */
    public final static int Unwrap_Key = 0x0000_0020;

    /**
     * Allow for MAC generation. Applies to MAC operation. Valid for Symmetric Keys
     */
    public final static int MAC_Generate = 0x0000_0080;

    /**
     * Allow for MAC verification. Applies to MAC Verify operation. Valid for Symmetric Keys
     */
    public final static int MAC_Verify = 0x0000_0100;

    /**
     * Allow for key derivation. Applied to Derive Key operation. Valid for PGP Keys, Private Keys, Public Keys, Secret
     * Data and Symmetric Keys.
     */
    public final static int Derive_Key = 0x0000_0200;

    /**
     * Allow for Key Agreement. Valid for PGP Keys, Private Keys, Public Keys, Secret Data and Symmetric Keys
     */
    public final static int Key_Agreement = 0x0000_0800;

    /**
     * Allow for Certificate Signing. Applies to Certify operation on a private key. Valid for Private Keys.
     */
    public final static int Certificate_Sign = 0x0000_1000;

    /**
     * Allow for CRL Sign. Valid for Private Keys
     */
    public final static int CRL_Sign = 0x0000_2000;

    /**
     * Allow for Authentication. Valid for Secret Data.
     */
    public final static int Authenticate = 0x0010_0000;

    /**
     * Cryptographic Usage Mask contains no Usage Restrictions.
     */
    public final static int Unrestricted = 0x0020_0000;

    /**
     * Allow for Format Preserving Encrypt. Valid for Symmetric Keys, Public Keys and Private Keys
     */
    public final static int FPE_Encrypt = 0x0040_0000;

    /**
     * Allow for Format Preserving Decrypt. Valid for Symmetric Keys, Public Keys and Private Keys
     */
    public final static int FPE_Decrypt = 0x0080_0000;
    // Extensions XXX00000
}
