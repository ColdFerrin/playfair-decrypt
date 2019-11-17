import java.util.Scanner;

public class Encyption {

    public static void main( String args[]){
        Scanner read = new Scanner(System.in);
        System.out.println("Provide a key for the cyphers:\n");
        String key = read.nextLine();
        System.out.println("Provide the phrase to encrypt:\n");
        String phrase = read.nextLine();
        Encrypt encrypt = new Encrypt(key, phrase);
    }
}
