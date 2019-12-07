package org.erau.cs303;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;

public class Phrase {
    private static final Logger LOG = LogManager.getLogger(String.valueOf(MethodHandles.lookup().lookupClass()));

    private String phrase;

    public Phrase(String phrase){
        this.phrase=phrase;
        cleanPhrase();
    }

    private void cleanPhrase() {
        phrase = phrase.toLowerCase().replaceAll("\\s+", "");
        phrase = phrase.replaceAll("j", "i");
        printPhrase();
    }

    private void printPhrase() {
        LOG.debug("The phrase is:");
        LOG.debug(phrase);
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }
}
