package fractions;

import java.math.BigInteger;

public class Fraction {
    private BigInteger a;
    private BigInteger b;

    Fraction ( BigInteger a, BigInteger b ) {
        setA (a);
        setB (b);
    }

    public BigInteger getA () {
        return a;
    }

    private void setA ( BigInteger a ) {
        this.a = a;
    }

    public BigInteger getB () {
        return b;
    }

    private void setB ( BigInteger b ) {
        this.b = b;
    }

    @Override
    public String toString () {
        return " ( " + a + " / " + b + " ) ";
    }
}
