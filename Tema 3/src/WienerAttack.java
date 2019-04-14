import fractions.ContinuousFractions;

import java.math.BigInteger;

class WienerAttack {

    BigInteger d;

    WienerAttack ( BigInteger n, BigInteger e ){
        ContinuousFractions fractions = new ContinuousFractions (e, n);
        System.out.println (fractions);

        BigInteger d;
        BigInteger k;
        int i = 0;
        do {
            i++;
            k = fractions.getFractionOnPosition (i).getA ();
            d = fractions.getFractionOnPosition (i).getB ();

        } while (!criteriu (e, n, k, d));
        this.d = d;
        System.out.println ("A fost gasit d: " + d);
    }

    private static boolean criteriu ( BigInteger e, BigInteger n, BigInteger k, BigInteger d ) {
        if ( k.equals (BigInteger.ZERO) )
            return false;
        BigInteger phi = e.multiply (d).subtract (BigInteger.ONE);
        if ( !phi.mod (k).equals (BigInteger.ZERO) )
            return false;
        phi = phi.divide (k);

        BigInteger a = BigInteger.ONE;
        BigInteger b = n.subtract (phi).add (BigInteger.ONE);
        BigInteger c = n;
        BigInteger deltha = b.multiply (b).subtract (a.multiply (c).multiply (new BigInteger ("4")));
        if ( deltha.compareTo (BigInteger.ZERO) <= 0 )
            return false;

        BigInteger sqrtDelta = sqrt (deltha);
        return deltha.equals (sqrtDelta.multiply (sqrtDelta));
    }

    //stackovf
    private static BigInteger sqrt ( BigInteger x ) {
        BigInteger div = BigInteger.ZERO.setBit (x.bitLength () / 2);
        BigInteger div2 = div;
        // Loop until we hit the same value twice in a row, or wind
        // up alternating.
        for (; ; ) {
            BigInteger y = div.add (x.divide (div)).shiftRight (1);
            if ( y.equals (div) || y.equals (div2) )
                return y;
            div2 = div;
            div = y;
        }
    }
}
