import java.lang.invoke.MethodHandles;
import java.util.SortedSet;
import java.util.TreeSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Cipher {
    private static final Logger LOG = LogManager.getLogger(String.valueOf(MethodHandles.lookup().lookupClass()));
    private String key;
    private char[][] cipher = new char[5][5];
    private final SortedSet<Character> letters = new TreeSet<Character>();


    public Cipher(String key){
        key = key.replaceAll("\\s+","").toLowerCase();
        key = key.replaceAll("j", "i");
        this.key = key;
        printKey();
        generateLetters();
        generateCipher();
    }

    public char getCipher(int i, int j){
        return cipher[i][j];
    }

    private void generateCipher() {

        for (int i = 0; i < 5 ; i++ ){
            for (int j = 0; j < 5 ; j++){
                if (key.isEmpty()) {
                    Character temp = letters.first();
                    letters.remove(temp);
                    cipher[i][j] = temp;
                } else {
                    Character temp = key.charAt(0);
                    key = key.replaceAll(temp.toString(), "");
                    letters.remove(temp);
                    cipher[i][j] = temp;
                }
            }
        }

        printCipher();
    }

    private void printCipher() {
        String log = new String("The cipher is:\n");
        for (int i = 0; i < 5 ; i++ ){
            for (int j = 0; j < 5 ; j++){
                log = log + cipher[i][j] + " ";
            }
            log = log + "\n";
        }
        log = log + "\n";
        LOG.info(log);
    }


    private void printKey() {
        LOG.info("The key is:");
        LOG.info(key);
    }




    private void generateLetters(){
        letters.add('a');
        letters.add('b');
        letters.add('c');
        letters.add('d');
        letters.add('e');
        letters.add('f');
        letters.add('g');
        letters.add('h');
        letters.add('i');
        letters.add('k');
        letters.add('l');
        letters.add('m');
        letters.add('n');
        letters.add('o');
        letters.add('p');
        letters.add('q');
        letters.add('r');
        letters.add('s');
        letters.add('t');
        letters.add('u');
        letters.add('v');
        letters.add('w');
        letters.add('x');
        letters.add('y');
        letters.add('z');
    }
}
