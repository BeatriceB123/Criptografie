import key.PrivateKey;
import key.PublicKey;
import message.TransmitedMessage;

import java.math.BigInteger;

public class Decrypted {
    //plaintext = c^d mod n
    private BigInteger decryptedText;
    private BigInteger p, q;

    Decrypted(TransmitedMessage cipherMessage, PublicKey publicKey, PrivateKey privateKey){
        decryptedText = cipherMessage.getMessage ();
        decryptedText = decryptedText.modPow(privateKey.getDecryptionExponent (), publicKey.getModulus ());
    }

    @Override
    public String toString () {
        return "Decrypted : " + getDecryptedText ();
    }

    private BigInteger getDecryptedText () {
        return decryptedText;
    }

}
