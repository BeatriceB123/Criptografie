import key.PublicKey;
import message.TransmitedMessage;

import java.math.BigInteger;

public class Encryped {
    //ciphertext = m^e mod n
    private BigInteger encryptedText;

    Encryped ( PublicKey publicKey, TransmitedMessage plainMessage ) {
        encryptedText = plainMessage.getMessage ();
        encryptedText = encryptedText.modPow(publicKey.getEncryptionExponent (), publicKey.getModulus ());
    }

    @Override
    public String toString () {
        return "Encrypted : " + encryptedText;
    }

    BigInteger getEncryptedText () {
        return encryptedText;
    }
}
