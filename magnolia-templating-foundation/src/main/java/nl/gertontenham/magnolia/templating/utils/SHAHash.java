package nl.gertontenham.magnolia.templating.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA message digest class
 *
 */
public class SHAHash {
    /**
     * Hash message using SHA-256 digester
     *
     * @param message
     * @return digested string
     * @throws NoSuchAlgorithmException
     */
    public static String getSHA256Digest(String message) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");

        md.update(message.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}
