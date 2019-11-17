import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class Decrypt {
    private static final Logger LOG = LogManager.getLogger(String.valueOf(MethodHandles.lookup().lookupClass()));

    private final static char REPLACEMENTCHAR = 'x';
    private int firstI;
    private int firstJ;
    private int secondI;
    private int secondJ;

    public Decrypt(String key, String encyptedPhrase){
        Cipher cipher = new Cipher(key);
        decryptPhrase(cipher,encyptedPhrase);
    }

    private void decryptPhrase(Cipher cipher, String encryptedPhrase){
        CharacterIterator it = new StringCharacterIterator(encryptedPhrase);
        String decryptedPhrase = new String();
        Character first;
        Character second;
        int firstAnswerI;
        int firstAnswerJ;
        int secondAnswerI;
        int secondAnswerJ;
        while (it.current() != CharacterIterator.DONE){
            first = it.current();
            it.next();
            if(it.current() != CharacterIterator.DONE) {
                if (it.current() != first ) {
                    second = it.current();
                    it.next();
                } else {
                    second = REPLACEMENTCHAR;
                }
            } else {
                second = REPLACEMENTCHAR;
            }
            LOG.debug("First is: " + first);
            LOG.debug("Second is: " + second);
            getPosition(first, second, cipher);
            LOG.debug("First I: " + firstI );
            LOG.debug("First J: " + firstJ);
            LOG.debug("Second I: " + secondI);
            LOG.debug("Second J: " + secondJ);
            if (firstI == secondI){
                firstAnswerI = firstI;
                secondAnswerI = firstI;
                if (firstJ != 0) {
                    firstAnswerJ = firstJ - 1;
                } else {
                    firstAnswerJ = 4;
                }
                if (secondJ != 0) {
                    secondAnswerJ = secondJ - 1;
                } else {
                    secondAnswerJ = 4;
                }
            } else if (firstJ == secondJ){
                firstAnswerJ = firstJ;
                secondAnswerJ = firstJ;
                if (firstI != 0) {
                    firstAnswerI = firstI - 1;
                } else {
                    firstAnswerI = 4;
                }
                if (secondI != 0) {
                    secondAnswerI = secondI - 1;
                } else {
                    secondAnswerI = 4;
                }
            } else {
                firstAnswerI = firstI;
                secondAnswerI = secondI;
                firstAnswerJ = secondJ;
                secondAnswerJ = firstJ;

            }

            LOG.debug("First Answer I: " + firstAnswerI );
            LOG.debug("First Answer J: " + firstAnswerJ);
            LOG.debug("Second Answer I: " + secondAnswerI);
            LOG.debug("Second Answer J: " + secondAnswerJ);
            LOG.debug("First encrypted letter: " + cipher.getCipher(firstAnswerI,firstAnswerJ));
            LOG.debug("Second Encrypted letter: " + cipher.getCipher(secondAnswerI,secondAnswerJ));
            decryptedPhrase = decryptedPhrase + cipher.getCipher(firstAnswerI,firstAnswerJ) + cipher.getCipher(secondAnswerI,secondAnswerJ);

        }
        LOG.info("The decrypted phrase is:");
        LOG.info(decryptedPhrase);
    }

    private void getPosition(Character first,Character second, Cipher cipher) {
        for (int i = 0; i < 5 ; i++ ) {
            for (int j = 0; j < 5; j++) {
                if (first.equals(cipher.getCipher(i,j))) {
                    firstI = i;
                    firstJ = j;
                }
            }
        }
        for (int i = 0; i < 5 ; i++ ) {
            for (int j = 0; j < 5; j++) {
                if (second.equals(cipher.getCipher(i,j))) {
                    secondI = i;
                    secondJ = j;
                }
            }
        }
    }

}
