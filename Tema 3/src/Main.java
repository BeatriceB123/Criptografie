import key.KeyGenerator;
import key.PrivateKey;
import key.PublicKey;
import message.TransmitedMessage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main ( String[] args ) {

        //searchForPQFOrAttack ();
        KeyGenerator keyGenerator = new KeyGenerator ();
        PrivateKey privateKey = new PrivateKey (keyGenerator);
        PublicKey publicKey = new PublicKey (keyGenerator);
        System.out.println (privateKey);
        System.out.println (publicKey + "\n");

        TransmitedMessage transmitedMessage1 = new TransmitedMessage ();
        Encryped encrypedMessage = new Encryped (publicKey, transmitedMessage1);
        TransmitedMessage transmitedMessage2 = new TransmitedMessage (encrypedMessage.getEncryptedText ());
        Decrypted decryptedMessage = new Decrypted (transmitedMessage2, publicKey, privateKey);
        System.out.println (transmitedMessage1);
        System.out.println (encrypedMessage);
        System.out.println (decryptedMessage + "\n");

        // WienerAttack attack = new WienerAttack (new BigInteger ("64741"), new BigInteger ("42667"));

        if ( wienerAttackCanWork (keyGenerator.getDecryptionExponent (), keyGenerator.getModulus (), keyGenerator.getPrimeFactorP (), keyGenerator.getPrimeFactorQ ()) )
        {
            System.out.println ("Can be broken");
            WienerAttack attack = new WienerAttack (publicKey.getModulus (), publicKey.getEncryptionExponent ());
            PrivateKey privateKeyFromAttack = new PrivateKey (attack.d);

            Decrypted decryptedFromAttack = new Decrypted (transmitedMessage2, publicKey, privateKeyFromAttack);
            System.out.println (decryptedFromAttack);

        }
        else
            System.out.println ("Cannot be broken");
    }

    private static boolean wienerAttackCanWork ( BigInteger d, BigInteger n, BigInteger p, BigInteger q ) {
        boolean inegalitateBuna = false;
        if ( p.compareTo (q) < 0 && q.compareTo (p.add (p)) < 0 )
            inegalitateBuna = true;
        if ( q.compareTo (p) < 0 && p.compareTo (q.add (q)) < 0 )
            inegalitateBuna = true;
        if ( !inegalitateBuna ) {
            return false;
        }

        BigInteger calcul = new BigInteger ("1");
        calcul = calcul.multiply (d);
        calcul = calcul.pow (4);
        calcul = calcul.multiply (new BigInteger ("3"));
        //System.out.println ("calcul, n:\n" + calcul.bitLength () + ",\n" + n.bitLength () + "\n" + "si n ar trebui sa fie mai mare ca sa mearga");

        return calcul.compareTo (n) < 0;
    }

    private static void searchForPQFOrAttack () {
        String fileName = "E:\\IC\\Tema 3\\src\\possiblePQ"; //TO DO: vezi cum se poate face sa nu depinda de locul in care e rulat
        List <BigInteger> listOfPossiblePQ = new ArrayList <> ();
        String line = null;
        try {
            FileReader fileReader = new FileReader (fileName);
            BufferedReader bufferedReader = new BufferedReader (fileReader);
            while ((line = bufferedReader.readLine ()) != null) {
                listOfPossiblePQ.add (new BigInteger (line.replace (",", "")));
            }
            bufferedReader.close ();
        } catch (FileNotFoundException ex) {
            System.out.println ("Unable to open file '" + fileName + "'");
        } catch (IOException ex) {
            System.out.println ("Error reading file '" + fileName + "'");
        }

        for (int i = 0; i < listOfPossiblePQ.size (); ++i) {
            for (int j = i + 1; j < listOfPossiblePQ.size (); ++j) {
                KeyGenerator keyGenerator = new KeyGenerator (listOfPossiblePQ.get (i), listOfPossiblePQ.get (j));
                System.out.println ("(" + i + " " + j + "): " + keyGenerator);
                if ( wienerAttackCanWork (keyGenerator.getDecryptionExponent (), keyGenerator.getModulus (), keyGenerator.getPrimeFactorP (), keyGenerator.getPrimeFactorQ ()) ) {
                    System.out.println ("This can be break:");
                    System.out.println (keyGenerator);
                }
            }
        }
    }
}
