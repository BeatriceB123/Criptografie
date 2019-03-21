import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DecryptedText extends TextPattern
{
    public String decryptedText;
    public int foundKeyLength;
    public String foundKey;
    public String plainTextForStatistics;

    //populam cu toate informatiile pe care le putem afla din structura criptata
    DecryptedText(String encryptedText, String pathToText, boolean path) throws IOException
    {
        if(path == true)
            this.plainTextForStatistics = readTextFromFile (pathToText);
        else
            this.plainTextForStatistics = readTextFromFile ("E:\\IC\\Tema 1\\src\\neocortex");
        this.plainTextForStatistics = removeUnacceptableCharacters (plainTextForStatistics);
        setEncryptedText(encryptedText);
        this.foundKeyLength = lengthOfTheKey (encryptedText);
        this.foundKey = determineTheKey (encryptedText, this.foundKeyLength);
        this.decryptedText = decrypt (encryptedText, this.foundKey);
        compressKey ();
    }

    DecryptedText(String encryptedText, String key) throws IOException
    {
        this.plainTextForStatistics = readTextFromFile ("E:\\IC\\Tema 1\\src\\neocortex");
        this.plainTextForStatistics = removeUnacceptableCharacters (plainTextForStatistics);
        setEncryptedText(encryptedText);
        this.foundKeyLength = key.length();
        setFoundKey(key);
        this.decryptedText = decrypt (encryptedText, this.foundKey);
        compressKey ();
    }

    private void compressKey()
    {
        //ia cheia si lungimea ei din clasa, care da, sunt corecte, si le minimizeaza
        //adica daca avem cheia = (subsir) de k ori, cheia devine subsir si lungimea va fi k
        String auxKey;   //o copie pentru cheia actuala
        boolean compress = true;
        do {
            auxKey = foundKey;
            compress = false ;
            for(int i=0; i<auxKey.length (); i++, auxKey = foundKey)
            {
                String aux2 = auxKey.substring (0, i); //incercam sa vedem daca putem forma toata cheia cu un subsit al ei
                auxKey = auxKey.replaceAll (aux2, "");
                if(auxKey.equals ("")) // am acoperit toata cheia cu un subsir al ei
                {
                    compress = true;
                    foundKey = aux2;
                    foundKeyLength = aux2.length ();
                }
            }
        }while (compress);
    }

    public void setEncryptedText(String text)
    {
        this.encryptedText = removeUnacceptableCharacters(text);
            if(encryptedText.length () != text.length ())
                System.out.println ("Wrong encrypted message. We adjust it. ");
    }

    public void setFoundKey(String key)
    {
        String aux = removeUnacceptableCharacters(key);
        if((aux.length () != key.length ()) || aux.equals(null))
        {
            System.out.println ("Key not found or it's wrong");
        }
        else
        {
            this.foundKey = key;
            this.foundKeyLength = key.length();
        }
    }
    public String getFoundKey () {
        return foundKey;
    }
    public String getDecryptedText () {
        return decryptedText;
    }
    public int getFoundKeyLength () {
        return foundKeyLength;
    }
    public void printDecryptedText () {
        System.out.println ("Decrypted text  : " + getDecryptedText ()  + '\n');
    }
    public void printFoundKey () {
        System.out.println ("Key found :       " + getFoundKey ()  + '\n');
    }
    public void printFoundKeyLength() {
        System.out.println ("Key length :      " + getFoundKeyLength ()  + '\n') ;
    }
    public void printBetterDecryption() throws IOException {
        System.out.println ("Another decryption:" + getBetterDecription (getDecryptedText ())  + '\n');
    }

    //numarul de aparitii ale literei "letter" in textul "text"
    private int occurencesOfLetterInText(String text, char letter)
    {
        int occurences = 0;
        for(int i=0; i<text.length(); ++i)
            if(text.charAt (i) == letter)
                occurences++;
        return occurences;
    }

    //probabilitatea ca extragand doua litere din x acestea sa fie egale.
    private double indexOfCoincidence(String text) //IC
    {
        double index = 0;
        for(int i = 0; i < 26; i++)
        {
            double f = occurencesOfLetterInText (text, (char)(i + 'A'));
            index += (f / text.length ()) * ( (f -1) / (text.length () - 1));
        }
        return index; //daca e un text in limba engleza necriptat, indicele e in jur de 0.065
    }

    //o functie ce returneaza un text format din simboluri din textul original
    //ce se afla pe o pozitie multiplu de m
    //incepand numaratoarea de pe pozitia k(care poate fi si 0)
    private String selectSymbols (String cryptotext, int m, int k)
    {
        String outputText ="";
        for(int i = k; i < cryptotext.length (); i+=m)
            outputText += (cryptotext.charAt (i));
        return outputText;
    }

    private int lengthOfTheKey(String cryptotext)
    {
        int length = 0;
        double error = 0;

        //doi vectori ce simuleaza un vector de perechi.
        ArrayList <Double> littleErrors =  new ArrayList <> (); //eroare fata de realitate
        ArrayList<Integer> lengthErrors = new ArrayList <> (); //lungimea pe care o sugereaza eroarea de pe pozitia corespunzatoare

        do {
            ++length;
            for (int i = 0; i < length; i++)
            {
                String text = selectSymbols (cryptotext, length, i);
                double index = Math.abs (indexOfCoincidence (text) - 0.065);
                error += index;
            }

            //o medie a erorilor micilor texte rezultate care ne arata cat de departe suntem de rezultatul bun
            error = error / length;

            littleErrors.add (error);
            lengthErrors.add (length);

        }
        while( error > 0.0001 && length < 1000);//ne oprim cand avem ceva super apropiat de 0.065 sau e prea mare cheia;

        Double minim = 100.0;
        int lengthOfTheKey = 1; //asta este criptosistemul s(26)
        for(int i=0; i < littleErrors.size (); i++)
        {
            if (minim > littleErrors.get(i))
            {
                minim = littleErrors.get(i);
                lengthOfTheKey = lengthErrors.get(i);
            }
        }
        return lengthOfTheKey;
    }

    //probabilitatea ca extragand o litera din primul text si o litera din al doilea sa obtineam aceeasi litera.
    private double mutualIndexOfCoincidence(String x, String y) //MIC
    {
        double index = 0;
        for(int i = 0; i < 26; i++)
        {
            double fx = occurencesOfLetterInText (x, (char)( i + 'A' ));
            double fy = occurencesOfLetterInText (y, (char)( i + 'A' ));
            index += (fx / x.length ()) * ( fy / y.length ());
        }
        return index;
        //daca x si y sunt texte in limba engleza necriptate, indicele e in jur de 0.065
        //sau daca sunt secvente din texte corecte
    }

    private String shift(String text, int numberOfPositions)
    {
        String outputText ="";
        for(int i = 0; i < text.length (); i++)
            outputText += (char)((text.charAt (i) - 'A' + 26 - numberOfPositions) % 26 +'A');
        return outputText;
    }

    private String determineTheKey(String cryptotext, int lengthOfTheKey)
    {
        String key = "";
        double index;

        //determinam toate componentele cheii
        for(int j = 0; j < lengthOfTheKey; j++)
        {
            int s = -1;
            int bestS = -1;
            double bestIndex = 0;

            //testam cele maxim 26 posibile shiftari
            do{
                s++;
                String text = selectSymbols (cryptotext, lengthOfTheKey, j);
                text = shift(text, s);
                index = mutualIndexOfCoincidence (plainTextForStatistics, text);
                if(index > bestIndex)
                {
                    bestIndex = index;
                    bestS = s;
                }
            }while(index < 0.55 && s < 27);

            key += (char)( bestS % 26 + 'A');
        }
        return key;
    }

    public static String decrypt(String cryptotext, String key)
    {
        String res = "";
        for (int i = 0, j = 0; i < cryptotext.length(); i++)
        {
            res += (char) ((cryptotext.charAt(i) - key.charAt(j) + 26) % 26 + 'A'); //ceva negativ % da negativ!!
            j = (j + 1) % key.length();
        }
        return res;
    }

    public String getBetterDecription(String initialDecryption) throws IOException
    {
        String finalDecription = initialDecryption;
        String line;

        //citim din fisier
        FileReader fileReader = new FileReader ("E:\\IC\\Tema 1\\src\\mostCommonWords");
        BufferedReader bufferReader = new BufferedReader (fileReader);

        //linie cu linie este pusa in acelasi string
        while ((line = bufferReader.readLine()) != null)
        {
            line = removeUnacceptableCharacters (line);
            finalDecription = finalDecription.replaceAll (line,line + " ");
        }
        return finalDecription;
    }
}