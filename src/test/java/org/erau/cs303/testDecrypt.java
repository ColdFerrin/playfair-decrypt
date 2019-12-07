package org.erau.cs303;

import org.erau.cs303.Decrypt;
import org.junit.Test;

public class testDecrypt {

    @Test
    public void testDecrypt() throws Exception{
        try {
            Decrypt decrypt = new Decrypt("funny", "fogllybxkuez");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

