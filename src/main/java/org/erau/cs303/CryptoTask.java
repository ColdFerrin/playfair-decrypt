package org.erau.cs303;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CryptoTask implements Runnable {
    private static final Logger LOG = LogManager.getLogger(String.valueOf(MethodHandles.lookup().lookupClass()));
    private String plaintext;

    private String cipherText;

    private String key;

    private ConcurrentLinkedQueue<String> validKeys;

    private String mode;

    public CryptoTask(String plaintext, String cipherText, String key, ConcurrentLinkedQueue<String> validKeys, String mode) {
        this.plaintext = plaintext;
        this.cipherText = cipherText;
        this.key = key;
        this.validKeys = validKeys;
        this.mode = mode;
    }

    @Override
    public void run() {
        LOG.info("plaintext: " + plaintext + " | ciphertext: " + cipherText + " | key: " + key);
        if(mode.equalsIgnoreCase("cp")){
            Encrypt encrypt = new Encrypt(key, plaintext);
            String returned = encrypt.getEncryptedPhrase();
            if(returned.equalsIgnoreCase(cipherText)){
                validKeys.add(key);
            }
        } else {
            Decrypt decrypt = new Decrypt(key, cipherText);
            String returned = decrypt.getDecryptedPhrase();
            if(returned.equalsIgnoreCase(plaintext)){
                validKeys.add(key);
            }
        }
    }
}
