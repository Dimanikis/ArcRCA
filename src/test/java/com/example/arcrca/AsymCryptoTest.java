package com.example.arcrca;

import com.example.arcrca.asymcrypt.AsymParty;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AsymCryptoTest extends BaseTest {

    private static final String USER2_ID = "user@prog.kiev.ua";
    private static final String TEXT = "I'm a very secret message :)";

    private static final String PUBLIC_FILE = "public.key";
    private static final String PRIVATE_FILE = "private.key";
    private static final String PRIVATE_PASSWORD = "password";

    @Test
    public void asymCryptoTest() {
        AsymParty user1 = new AsymParty();
        AsymParty user2 = new AsymParty();

        user1.generateKeys();
        user2.generateKeys();
/*
        user1.saveKeys(PUBLIC_FILE, PRIVATE_FILE, PRIVATE_PASSWORD, "123");
        user1.loadKeys(PUBLIC_FILE, PRIVATE_FILE, PRIVATE_PASSWORD, "123");


        user1.addFriend(USER2_ID, user2.getPublicKey());

        String encryptedMessage = user1.sendMessage(USER2_ID, TEXT);
        log("Encrypted message: " + encryptedMessage);

        String decryptedMessage = user2.receiveMessage(encryptedMessage);
        log("Decrypted message: " + decryptedMessage);
        logger.trace("123");

        assertEquals(TEXT, decryptedMessage);
        */
    }
}
