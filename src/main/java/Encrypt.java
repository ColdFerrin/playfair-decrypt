import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.*;

public class Encrypt {
    private static final Logger LOG = LogManager.getLogger(String.valueOf(MethodHandles.lookup().lookupClass()));
    private char[][] cipher = new char[5][5];
    private final static char REPLACEMENTCHAR = 'x';
    private int firstI;
    private int firstJ;
    private int secondI;
    private int secondJ;


    public Encrypt(String key, String phrase) {
        Cipher cipher = new Cipher(key);
        Phrase phraseObject = new Phrase(phrase);
        encryptPhrase(phraseObject, cipher);

    }


    private void encryptPhrase(Phrase phraseObject,Cipher cipher) {
        CharacterIterator it = new StringCharacterIterator(phraseObject.getPhrase());
        String encryptedPhrase = new String();
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
                if (firstJ != 4) {
                    firstAnswerJ = firstJ + 1;
                } else {
                    firstAnswerJ = 0;
                }
                if (secondJ != 4) {
                    secondAnswerJ = secondJ + 1;
                } else {
                    secondAnswerJ = 0;
                }
            } else if (firstJ == secondJ){
                firstAnswerJ = firstJ;
                secondAnswerJ = firstJ;
                if (firstI != 4) {
                    firstAnswerI = firstI + 1;
                } else {
                    firstAnswerI = 0;
                }
                if (secondI != 4) {
                    secondAnswerI = secondI + 1;
                } else {
                    secondAnswerI = 0;
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
            encryptedPhrase = encryptedPhrase + cipher.getCipher(firstAnswerI,firstAnswerJ) + cipher.getCipher(secondAnswerI,secondAnswerJ);
        }
        LOG.info("The encrypted phrase is:");
        LOG.info(encryptedPhrase);
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
