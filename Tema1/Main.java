//Anichitoaie Beatrice-Roxana; 2B4

import java.io.IOException;

public class Main
{



    public static void main(String[] args) throws IOException
    {
        //Text t = new Text();
       // t.printInformations ();
        EncryptedText e = new EncryptedText ("E:\\IC\\Tema 1\\src\\theLittleMermaid", ("FGGFFPGPG"));    //cheie gasita
        EncryptedText e1 = new EncryptedText ("E:\\IC\\Tema 1\\src\\theLittleMermaid", ("BBBBBBBB"));    //cheie gasita ca "B"(comprimare)
        EncryptedText e2 = new EncryptedText ("E:\\IC\\Tema 1\\src\\theLittleMermaid", ("BBBBBBBBBD"));  //cheie gasita (nu renunta la D)
        EncryptedText e3 = new EncryptedText ("E:\\IC\\Tema 1\\src\\theLittleMermaid", ("BBBBBBBBBC"));  //cheie GRESITA
        EncryptedText e4 = new EncryptedText ("E:\\IC\\Tema 1\\src\\theLittleMermaid", ("GGIIGGIIGGIIGH"));//cheie gasita
        EncryptedText e5 = new EncryptedText ("E:\\IC\\Tema 1\\src\\theLittleMermaid", ("ASDASDASDASDASA"));//cheie gasita(nu inlocuieste A cu S)


        //e.printEncryptedText ();
        //System.out.println (e.text);

        //primeste doar textul criptat
        DecryptedText d = new DecryptedText (e.getEncryptedText (), "E:\\IC\\Tema 1\\src\\geneticAlgorithms", true);
        d.printEncryptedText ();
        d.printDecryptedText ();
        d.printFoundKey ();
        d.printFoundKeyLength ();
        d.printBetterDecryption ();

        DecryptedText d1 = new DecryptedText (e1.getEncryptedText (), "E:\\IC\\Tema 1\\src\\geneticAlgorithms", true);
        DecryptedText d2 = new DecryptedText (e2.getEncryptedText (), "E:\\IC\\Tema 1\\src\\geneticAlgorithms", true);
        DecryptedText d3 = new DecryptedText (e3.getEncryptedText (), "E:\\IC\\Tema 1\\src\\geneticAlgorithms", true);
        DecryptedText d4 = new DecryptedText (e4.getEncryptedText (), "E:\\IC\\Tema 1\\src\\geneticAlgorithms", true);
        DecryptedText d5 = new DecryptedText (e5.getEncryptedText (), "E:\\IC\\Tema 1\\src\\geneticAlgorithms", true);

        d1.printFoundKey ();
        d2.printFoundKey ();
        d3.printFoundKey ();
        d4.printFoundKey ();
        d5.printFoundKey ();



    }
}