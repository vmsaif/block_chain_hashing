package blockchain_hashing;
/**
 * COMP 2140   		SECTION A01
 * INSTRUCTOR   	Cuneyt Akcora
 * Assignment       5
 * @author			Saif Mahmud
 * @id				7808507
 * @version      	04/06/2020
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class MerkleTree 
{

    public String buildFrom(ArrayList<Transaction> transactions) throws NoSuchAlgorithmException 
    {
    	ArrayList<String> hashedArray = new ArrayList<String>();
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        for(int i = 0; i < transactions.size(); i = i+1)
        {
            Transaction tx1 = transactions.get(i);
            String h1 = UtilityFunctions.getSHA256(digest, tx1.toString());
            hashedArray.add(h1);
        }
        int level = 0;
        String topHash = makeTree(hashedArray, level);
		System.out.println( "Merkle top hash is: "+topHash);
        return topHash;
    }// buildfrom

	private String makeTree(ArrayList<String> hashedArray, int level) throws NoSuchAlgorithmException 
	{
    	int size = hashedArray.size();
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		String topHash = "";
		if(hashedArray.size() == 1)
        {
        	topHash = hashedArray.get(0);
        } else {
        	ArrayList<String> oneLevelUp = new ArrayList<String>();
        	for(int i = 0; i < hashedArray.size(); i = i + 2)
        	{
        		if(i + 1 < hashedArray.size())
        		{
	                String newHashed = UtilityFunctions.getSHA256(digest, hashedArray.get(i)) + 
	                		UtilityFunctions.getSHA256(digest, hashedArray.get(i + 1));
	                oneLevelUp.add(newHashed);
        		} else {
        			String newHashed = UtilityFunctions.getSHA256(digest, hashedArray.get(i));
	                oneLevelUp.add(newHashed);
        		}
            }
        	System.out.println("Merkle Tree, Bottom Up, Level: "+level+", number of hashes: "+size);
            level++;
        	topHash = makeTree(oneLevelUp, level);
        }
		return topHash;
	}// maketree
}// class
	