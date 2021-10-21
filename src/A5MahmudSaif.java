
/**
 * @author			Saif Mahmud
 * @version      	04/06/2020
 */

import javafx.util.Pair;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

public class A5MahmudSaif {
	
	    static private final int MINUTE = 60*1000;//in milliseconds
	    static final long SATOSHI = 100000000;// satoshi is the subunit of bitcoins (like 100 cents in a  dollar)
	    private static final int REWARD = 50;//bitcoins per each block
	    static private BigInteger blockReward = new BigInteger(String.valueOf(REWARD * SATOSHI)); // block reward in satoshis.
	    static int interBlockTime = 10* MINUTE;//one block every ten minutes
	    private int currentHeight=0;
	    static private String addressOfSatoshi = "16cou7Ht6WjTzuFyDBnht9hmvXytg6XdVT";
	    static BigInteger difficulty;
	    private ArrayList<Block>  blocks;
	    static private final int diffFreq =2016;
	    static  private final int numOfSimulatedBlocks =10;
	
	
	    public A5MahmudSaif()  {
	        blocks = new ArrayList<>();
	        int maxPower =77;//a 256 bit number can have a max value around 10^77
	        int initialOffset=5; //we want initial difficulty to be 10^5 only
	        //set initial block mining difficulty
	        difficulty = new BigInteger("9".repeat(maxPower - initialOffset));// 9 is the biggest digit
	
	    }
	
	    public static void main(String[] args) throws NoSuchAlgorithmException {
	
	        A5MahmudSaif bitcoin = new A5MahmudSaif();
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        System.out.println("The Bitcoin blockchain is created.");
	        System.out.println("Block reward is "+blockReward+" satoshis per block paid to the block's miner.");
	        System.out.println("There will be (approx.) "+interBlockTime+" miliseconds between two blocks");
	        System.out.println("Block reward halves every 210K blocks (takes around 4 years)");
	        System.out.println("The Peer-to-Peer network will be fake; we will simulate it.");
	        //genesis block starts the blockchain. It contains just one transaction that gives the block reward to the first miner.
	        Transaction coinbase = new Transaction("","",blockReward);
	        Block genesisBlock = new Block(addressOfSatoshi,null);
	        genesisBlock.addTx(coinbase);
	        genesisBlock.setBlockHash(UtilityFunctions.getSHA256(digest,coinbase.toString()));
	        bitcoin.addBlock(genesisBlock);
	        //genesis block creation ends.
	
	        System.out.println("The blockchain will end once the block "+numOfSimulatedBlocks+" is mined.");
	        //anyone in the network can be  a miner. Let's say Cuneyt has the address Cuneyt1357912
	        String miner = "Cuneyt1357912";
	
	        PeerToPeerNetwork p2p = new PeerToPeerNetwork();
	        Block prevBlock=genesisBlock;
	        while(bitcoin.currentHeight<numOfSimulatedBlocks){
	
	            Block b = new Block(miner,prevBlock);
	            // Coinbase transaction pays the miner for its effort.
	            Transaction currCoinbase = new Transaction("",miner,blockReward);
	            //end of the coinbase transaction
	            //ordinary transaction from users
	            ArrayList<Transaction> transactions = p2p.collectNewTransactions();
	            System.out.println("MemPool contained "+transactions.size()+" transactions.");
	            transactions.add(currCoinbase);
	            System.out.println("The coinbase transaction is created by the miner, and added to the block.");
	            for(Transaction t:transactions){
	                b.addTx(t);
	            }
	            MerkleTree tree =  new MerkleTree();
	            String topHash = tree.buildFrom(transactions);
	            String hash = prevBlock.getBlockHash()+topHash;
	            Pair<Long,String> result= mineTheBlock(digest,hash, difficulty);
	            long nonce = result.getKey();
	            String blockHash = result.getValue();
	            if(nonce!=-1) {
	                b.setBlockHash(blockHash);
	                System.out.println("\r\nBlock "+bitcoin.currentHeight+" is mined. "+
	                        prevBlock.getBlockHash()+"->"+b.getBlockHash());
	
	                b.setNonce(nonce);
	                bitcoin.addBlock(b);
	                prevBlock=b;
	
	                //optional: write code to update the difficulty level every (2016 blocks) 2 weeks.
	                if(bitcoin.currentHeight%diffFreq==0){
	                    // difficulty needs to be halved.
	                    // https://en.bitcoin.it/wiki/Difficulty
	                    //
	                }
	                //optional: write code to update block reward every (210K blocks) 4 years.
	                //https://www.bitcoinblockhalf.com/
	
	            }
	
	        }
	        // corrupt a transaction in a block
	        int blockToCorrupt= new Random().nextInt(numOfSimulatedBlocks-1);
	        blockToCorrupt+=1;//so that the zeroth block will not be selected
	        Block b= bitcoin.getBlock(blockToCorrupt);
	        int txToCorrupt= new Random().nextInt(b.numOfTx);
	        Transaction tx = b.getTransaction(txToCorrupt);
	        tx.setAmount("35");
	        //end of the corruption code
	
	        //detecting the induced corruption
	        int height= bitcoin.validate(digest);
	        System.out.println("Block "+blockToCorrupt+ " was chosen to be corrupted");
	        System.out.println("The code found the corruption:"+(height==blockToCorrupt));
	    }
	
	    private int validate(MessageDigest digest) throws NoSuchAlgorithmException {
	        //write your code here. Change the below code as you like
	        //digest is used to compute the SHA256 hash
	    	int height=-1;
	        Block prev;
	        Block curr;
	        MerkleTree tree = new MerkleTree();
	        ArrayList<Transaction> trans = new ArrayList<Transaction>();
	        String topHash;
	        
	        for(int i = 1; i < this.blocks.size()-1; i++)
	        {
	        	curr = this.blocks.get(i);
	        	topHash = tree.buildFrom(curr.getTransactions());
	        	
	        	if(curr.getBlockHash().equals(this.blocks.get(i).getBlockHash()))
	        	{
	        		
	        		System.out.println("a: " + this.blocks.get(i-1).getBlockHash());
	        		System.out.println("b: " + this.blocks.get(i).getBlockHash());
	        		System.out.println("a: " + this.blocks.get(i+1).getBlockHash());
	        	}
	        	prev = curr;
	        	
	        }
	        
	        
	
	        return height;
	    }
	
	    private Block getBlock(int blockToCorrupt) {
	        return this.blocks.get(blockToCorrupt);
	
	    }
	
	    private static Pair<Long, String> mineTheBlock(MessageDigest digest, String hash, BigInteger difficulty) 
	    {
	        long result=-1;
	        String blockHash="";
	        for(long nonce=0;nonce<Long.MAX_VALUE&&result==-1;nonce++){
	            String hexString= UtilityFunctions.getSHA256(digest,(hash+nonce));
	            
	            BigInteger bigInt = new BigInteger(hexString, 16);
	            if(bigInt.compareTo(difficulty)<0){
	                String blockHash1 = bigInt.toString();
	                System.out.println("Nonce: "+nonce+" satisfies the difficulty."+ blockHash1 +" < "+difficulty);
	                result=nonce;
	                blockHash = hexString;
	            }
	        }
	        return new Pair<>(result,blockHash);
	    }
	
	    private void addBlock(Block block) {
	        this.blocks.add(block);
	        this.currentHeight++;
	
	    }
	}

