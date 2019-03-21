import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TextPattern
{
    public String encryptedText;

    protected String removeUnacceptableCharacters(String text)
    {
        text = text.toUpperCase();
        for(int i = 0; i < text.length(); i++)
            if(text.charAt(i) < 'A' || text.charAt(i) > 'Z')
            {
                text = text.substring(0, i) + text.substring(i+1);
                i--;
            }
        return text;
    }

    protected String readTextFromFile(String pathToText) throws IOException
    {
        String line;
        String finalText ="";

        //citim din fisier
        FileReader fileReader = new FileReader (pathToText);
        BufferedReader bufferReader = new BufferedReader (fileReader);

        //linie cu linie este pusa in acelasi string
        while ((line = bufferReader.readLine()) != null)
            finalText = finalText.concat(line);
        return finalText;
    }

    //afiseaza textul criptat
    public void printEncryptedText()
    {
        System.out.println ("Encrypted message:  " + encryptedText + '\n');
    }

    public String getEncryptedText()
    {
        return encryptedText;
    }
}
