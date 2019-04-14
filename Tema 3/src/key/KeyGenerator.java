package key;

import java.math.BigInteger;

public class KeyGenerator {
    private BigInteger primeFactorP, primeFactorQ; //p si q
    private BigInteger phi; //phi(n)

    //public key is here
    private BigInteger modulus; //n
    private BigInteger encryptionExponent; //e

    //private key is here
    private BigInteger decryptionExponent; //d

    public BigInteger getPrimeFactorP () {
        return primeFactorP;
    }

    private void setPrimeFactorP ( BigInteger primeFactorP ) {
        this.primeFactorP = primeFactorP;
    }

    public BigInteger getPrimeFactorQ () {
        return primeFactorQ;
    }

    private void setPrimeFactorQ ( BigInteger primeFactorQ ) {
        this.primeFactorQ = primeFactorQ;
    }

    public BigInteger getPhi () {
        return phi;
    }

    private void setPhi ( BigInteger phi ) {
        this.phi = phi;
    }

    public BigInteger getModulus () {
        return modulus;
    }

    private void setModulus ( BigInteger modulus ) {
        this.modulus = modulus;
    }

    BigInteger getEncryptionExponent () {
        return encryptionExponent;
    }

    private void setEncryptionExponent ( BigInteger encryptionExponent ) {
        this.encryptionExponent = encryptionExponent;
    }

    public BigInteger getDecryptionExponent () {
        return decryptionExponent;
    }

    private void setDecryptionExponent ( BigInteger decryptionExponent ) {
        this.decryptionExponent = decryptionExponent;
    }

    public KeyGenerator () {
        setPrimeFactorP (RandomlyChosenVariables.p);
        setPrimeFactorQ (RandomlyChosenVariables.q);
        setModulus (this.primeFactorP.multiply (primeFactorQ));
        setPhi (phi (this.primeFactorP, this.primeFactorQ));
        setEncryptionExponent (generateEncryptionExponent ());
        setDecryptionExponent (calculateDecryptionComponent ());
    }

    public KeyGenerator(BigInteger p, BigInteger q){
        setPrimeFactorP (p);
        setPrimeFactorQ (q);
        setModulus (this.primeFactorP.multiply (primeFactorQ));
        setPhi (phi (this.primeFactorP, this.primeFactorQ));
        setEncryptionExponent (generateEncryptionExponent ());
        setDecryptionExponent (calculateDecryptionComponent ());
    }

    private BigInteger phi ( BigInteger primeFactor1, BigInteger primeFactor2 ) {
        BigInteger result1 = primeFactor1.subtract (new BigInteger ("1"));
        BigInteger result2 = primeFactor2.subtract (new BigInteger ("1"));

        return result1.multiply (result2);
    }

    private BigInteger generateEncryptionExponent () {
        BigInteger e = RandomlyChosenVariables.eStart;
        while (!e.gcd (phi)
                .equals (BigInteger.ONE)) {
            e = e.add (BigInteger.ONE);
        }
        return e;
    }

    private BigInteger calculateDecryptionComponent () {
        BigInteger d;
        if(!RandomlyChosenVariables.d.equals (BigInteger.ZERO))
        {
            d = RandomlyChosenVariables.d;
            return d;
        }

        BigInteger k = RandomlyChosenVariables.kStart;

        while (!((k.multiply (phi)
                .add (BigInteger.ONE))
                .mod (encryptionExponent)
                .equals (BigInteger.ZERO))) {
            k = k.add (BigInteger.ONE);
        }
        d = (k.multiply (phi).add (BigInteger.ONE));
        d = d.divide (encryptionExponent);

        return d;
    }

    @Override
    public String toString () {
        return "Private key: (d: " + decryptionExponent + ")\n" +
               "Public key: (e: " + encryptionExponent + ", n: " + modulus + ")";
    }
}
