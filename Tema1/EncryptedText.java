import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class EncryptedText extends TextPattern
{
    private String text; //cuvant peste {A,B,...,Z}, necriptat
    private String key;  //cheie peste {A,B,...,Z}^m, arbitara, cu care s-a criptat
    private String pathToText;

    //primeste calea catre un fisier si o cheie  ii face criptarea
    EncryptedText(String pathToText, String key) throws IOException
    {
        this.pathToText = pathToText;
        this.key = key;
        this.key = key;

        this.text = readTextFromFile (pathToText);
        this.text = removeUnacceptableCharacters (this.text);

        this.encryptedText = encrypt(this.text, this.key);
    }

    //cripteaza un text dat
    public static String encrypt(String originalText, String key)
    {
        String res = "";
        for (int i = 0, j = 0; i < originalText.length(); i++)
        {
            //identificam literele A,B,..,Z cu numerele 0,1,..,25
            //dar le folosim ca atare pentru o afisare frumoasa

            //yi = (xi + ki) mod 26
            res += (char) ((originalText.charAt(i) + key.charAt(j) - 2 * 'A') % 26 + 'A');
            j = (j + 1) % key.length();
        }
        return res;
    }

    //primeste calea catre un fisier si face el o cheie random si ii face criptarea
    //REV
}
