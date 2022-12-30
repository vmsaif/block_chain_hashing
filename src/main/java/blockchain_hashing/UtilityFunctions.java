package blockchain_hashing;
/**
 * COMP 2140   		SECTION A01
 * INSTRUCTOR   	Cuneyt Akcora
 * Assignment       5
 * @author			Saif Mahmud
 * @id				7808507
 * @version      	04/06/2020
 */
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class UtilityFunctions {

    //function taken from https://www.baeldung.com/sha-256-hashing-java
    static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    static String getSHA256(MessageDigest digest, String txContent) {
        //find the hash of this transactions
        byte[] encodedhash = digest.digest(
                txContent.getBytes(StandardCharsets.UTF_8));
        String hashedTxContent = UtilityFunctions.bytesToHex(encodedhash);
//       System.out.println("Sha256:"+hashedTxContent+".  "+txContent);
        return hashedTxContent;
    }
}
