package fractions;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ContinuousFractions {
    private List <Fraction> fractionList = new ArrayList <> ();
    private List <BigInteger> q = new ArrayList <> ();

    public Fraction getFractionOnPosition ( int position ) {
        try {
            if ( position < 0 || position > fractionList.size () )
                throw new Exception ("Out of list");
        } catch (Exception e) {
            System.out.println (e.getMessage ());
            return new Fraction (BigInteger.ONE, BigInteger.ONE);
        }
        return fractionList.get (position);
    }

    public ContinuousFractions ( BigInteger a, BigInteger b ) {
        while (a.compareTo (BigInteger.ZERO) > 0 && b.compareTo (BigInteger.ZERO) > 0) {
            q.add (a.divide (b));
            BigInteger aux = b;
            b = a.mod (b);
            a = aux;
        }

        BigInteger alpha = q.get (0);
        BigInteger betha = BigInteger.ONE;
        fractionList.add (new Fraction (alpha, betha));

        alpha = q.get (0).multiply (q.get (1)).add (BigInteger.ONE);
        betha = q.get (1);
        fractionList.add (new Fraction (alpha, betha));

        for (int i = 2; i < q.size (); ++i) {
            alpha = q.get (i).multiply (fractionList.get (i - 1).getA ())
                    .add (fractionList.get (i - 2).getA ());
            betha = q.get (i).multiply (fractionList.get (i - 1).getB ())
                    .add (fractionList.get (i - 2).getB ());
            fractionList.add (new Fraction (alpha, betha));
        }
    }


    @Override
    public String toString () {
        StringBuilder result = new StringBuilder ("Q: ");
        for (BigInteger bigInteger : q) result.append (bigInteger).append (" ");
        result.append ("\nFractions: ");
        for (Fraction fraction : fractionList) result.append (fraction);
        return result.toString ();
    }
}
