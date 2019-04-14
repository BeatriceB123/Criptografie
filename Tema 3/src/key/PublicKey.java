package key;

import java.math.BigInteger;

public class PublicKey {
    private BigInteger modulus; //n
    private BigInteger encryptionExponent; //e

    public BigInteger getModulus () {
        return modulus;
    }

    public void setModulus ( BigInteger modulus ) {
        this.modulus = modulus;
    }

    public BigInteger getEncryptionExponent () {
        return encryptionExponent;
    }

    public void setEncryptionExponent ( BigInteger encryptionExponent ) {
        this.encryptionExponent = encryptionExponent;
    }

    public PublicKey ( KeyGenerator key ) {
        setModulus (key.getModulus ());
        setEncryptionExponent (key.getEncryptionExponent ());
    }

    @Override
    public String toString () {
        return "Public key: (e: " + encryptionExponent + ", n: " + modulus + ")";
    }
}
