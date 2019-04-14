package message;

import java.math.BigInteger;

public class TransmitedMessage {

    private BigInteger message; //represents the message as an integer in the interval 0..n-1

    private void setMessage ( BigInteger message ) {
        this.message = message;
    }

    public BigInteger getMessage () {
        return message;
    }

    public TransmitedMessage () {
        setMessage (RandomlyMessages.message);
    }

    public TransmitedMessage ( BigInteger message ) {
        setMessage (message);
    }

    @Override
    public String toString () {
        return "PlainText message: " + message;
    }
}
