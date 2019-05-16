package kr.ac.cnu.cse.passwordmanager;

/* inspired by
- https://proandroiddev.com/security-best-practices-symmetric-encryption-with-aes-in-java-and-android-part-2-b3b80e99ad36
- https://hyperconnect.github.io/2018/06/03/android-secure-sharedpref-howto.html
- http://anilandroid123.blogspot.com/2017/12/encryption-decryption-example-android.html
- Secure Random: https://developer.android.com/reference/java/security/SecureRandom
 */

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESHelperVer1 {

    private static final String CHARSET = "UTF-8";
    private static final String HASH_SPEC = "SHA-256";
    private static final String ENCRYPT_SPEC = "AES/CBC/PKCS5Padding";


    //private static final byte[] ivBytes = new byte[16];
    //private static final byte[] ivBytes = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    public static SecretKeySpec generateKey(final String keystr)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        /* generate the IV */
        /*
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(ivBytes);*/

        /* derive the key */
      final MessageDigest digest = MessageDigest.getInstance(HASH_SPEC);
        byte[] bytes = keystr.getBytes(CHARSET);
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();


        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;
    }



    public static byte[] encrypt2(final SecretKeySpec key, final byte[] iv, final byte[] message)
            throws GeneralSecurityException {
        final Cipher cipher = Cipher.getInstance(ENCRYPT_SPEC);


        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] cipherText = cipher.doFinal(message);

        return cipherText;
    }


    public static byte[] decrypt2(final SecretKeySpec key, final byte[] iv, final byte[] decodedCipherText)
            throws GeneralSecurityException {
        final Cipher cipher = Cipher.getInstance(ENCRYPT_SPEC);


        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        byte[] decryptedBytes = cipher.doFinal(decodedCipherText);


        return decryptedBytes;
    }

}



