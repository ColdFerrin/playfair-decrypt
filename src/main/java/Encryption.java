import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.zip.GZIPInputStream;

public class Encryption {

    private static final Logger LOG = LogManager.getLogger(String.valueOf(MethodHandles.lookup().lookupClass()));
    public static final String wikiFile = "enwiki-latest-all-titles-in-ns0.gz";
    public static final String wikiDataUrl = "https://dumps.wikimedia.org/enwiki/latest/enwiki-latest-all-titles-in-ns0.gz";

    public static void main( String args[]) throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.println("press 1 for basic use or 2 for decrypt mode");
        int use = Integer.parseInt(in.nextLine());
        if(use == 1){
            LOG.debug("normal mode");
            normalUse(in);
            return;
        } else if (use > 2 || use < 1){
            LOG.error("Bad use mode");
            return;
        }

        System.out.println("Playfair decrypt is designed to find possible encryption keys for a string \nencrypted with the Playfair cipher.\n\n");
        System.out.println("For this to work you must have a string of plain text and a string of cypher \n" +
                "text. For best results you must also know if it is a chosen-plaintext or a \n" +
                "chosen-ciphertext attack, because the cypher is asymmetric.\n\n" +
                "Please leave all padding in the return of a chosen ciphertext return.\n\n");

        System.out.println("Enter your plaintext:");
        String plaintext = in.nextLine();
        LOG.debug("plaintext: " + plaintext);

        System.out.println("\n\nEnter your ciphertext:");
        String ciphertext = in.nextLine();
        LOG.debug("ciphertext: " + ciphertext);

        System.out.println("\n\nData mode\n---------");
        System.out.println("If you have obtained ciphertext from a chosen plaintext string choose cp");
        System.out.println("If you have obtained plaintext from a chosen ciphertext string choose cc");
        System.out.println("Choose a mode [cp/cc]:");
        String mode = in.nextLine().strip();
        LOG.debug("mode: " + mode);

        in = null;

        if(!mode.equalsIgnoreCase("cc") && !mode.equalsIgnoreCase("cp")){
            LOG.info("Bad Crypto Mode");
            return;
        }

        String tempPath = System.getProperty("java.io.tmpdir");
        LOG.debug("temp path: " + tempPath);
        int cores = Runtime.getRuntime().availableProcessors();
        LOG.debug("cores: "+ cores);

        String wikiFilePathZip = tempPath + wikiFile;
        LOG.debug("wikiFilePathZip: "+ wikiFilePathZip);
        downloadFile(wikiFilePathZip, wikiDataUrl);
        String wikiFilePath = wikiFilePathZip.split("\\.")[0] + ".txt";
        LOG.debug("wikiFilePath: "+ wikiFilePath);
        unGnuZipFile(wikiFilePathZip, wikiFilePath);

        ConcurrentLinkedQueue<String> validWords = new ConcurrentLinkedQueue<>();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(cores);

        BufferedReader reader;
        String line;

        reader = new BufferedReader(new FileReader(wikiFilePath));
        line = reader.readLine();
        if(line != null) {
            line = line.replaceAll("[^a-zA-Z]", "").toLowerCase();
        }
        while (line != null){
            CryptoTask cryptoTask = new CryptoTask(plaintext, ciphertext, line, validWords, mode);
            executor.execute(cryptoTask);
            line = reader.readLine();
            if(line != null) {
                line = line.replaceAll("[^a-zA-Z]", "").toLowerCase();
            }
        }
        executor.shutdown();

        System.out.println(Arrays.toString(validWords.toArray()));
    }

    public static void normalUse(Scanner read){
        System.out.println("Provide a key for the cyphers:\n");
        String key = read.nextLine();
        System.out.println("Provide the phrase to encrypt:\n");
        String phrase = read.nextLine();
        Encrypt encrypt = new Encrypt(key, phrase);
        String encrypted = encrypt.getEncryptedPhrase();
        Decrypt decrypt = new Decrypt(key, encrypted);
        String decrypted = decrypt.getDecryptedPhrase();
        LOG.info("Key: " + key);
        LOG.info("phrase: " + phrase);
        LOG.info("encrypted: " + encrypted);
        LOG.info("decrypted: " + decrypted);
    }

    private static void downloadFile(String filePath, String fileURL) throws IOException {
        InputStream in = new URL(fileURL).openStream();
        Files.copy(in, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
    }

    private static void unGnuZipFile(String compressedFile, String decompressedFile) throws IOException{
        byte[] buffer = new byte[1024];

        FileInputStream fileIn = new FileInputStream(compressedFile);
        GZIPInputStream gZIPInputStream = new GZIPInputStream(fileIn);
        FileOutputStream fileOutputStream = new FileOutputStream(decompressedFile);

        int bytes_read;
        while ((bytes_read = gZIPInputStream.read(buffer)) > 0) {
            fileOutputStream.write(buffer, 0, bytes_read);
        }

        gZIPInputStream.close();
        fileOutputStream.close();

    }
}
