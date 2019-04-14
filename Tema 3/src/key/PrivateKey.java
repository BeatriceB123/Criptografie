package key;

import java.math.BigInteger;

public class PrivateKey {
    private BigInteger decryptionExponent;

    public BigInteger getDecryptionExponent () {
        return decryptionExponent;
    }

    public void setDecryptionExponent ( BigInteger decryptionExponent ) {
        this.decryptionExponent = decryptionExponent;
    }

    public PrivateKey ( KeyGenerator key ) {
        setDecryptionExponent (key.getDecryptionExponent ());
    }

    public PrivateKey ( BigInteger key ) {
        setDecryptionExponent (key);
    }

    @Override
    public String toString () {
        return "Private key: (d: " + decryptionExponent + ")";
    }
}
