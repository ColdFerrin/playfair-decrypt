package org.erau.cs303;

import org.erau.cs303.Encrypt;
import org.junit.Test;


public class EncyptTest { 

    @Test
    public void testEncrypt() throws Exception{
        try {
            Encrypt encrypt = new Encrypt("funny", "The meeting");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 
