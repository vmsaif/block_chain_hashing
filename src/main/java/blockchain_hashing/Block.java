package blockchain_hashing;

/**
 * COMP 2140   		SECTION A01
 * INSTRUCTOR   	Cuneyt Akcora
 * Assignment       5
 * @author			Saif Mahmud
 * @id				7808507
 * @version      	04/06/2020
 */
import java.util.ArrayList;
import java.util.HashMap;

public class Block {
    String minerAddress;
    int numOfTx;
    long blockTime;
    HashMap<Integer,Transaction> transactions;
    Block prevBlock;
    private String blockHash;
    private long nonce;

    public Block(String minerAddress,Block prevBlock) {
        this.minerAddress = minerAddress;
        this.transactions = new HashMap<>();
        this.numOfTx=0;
        this.blockTime= System.nanoTime();
        this.prevBlock=prevBlock;
    }

    public void addTx(Transaction transaction) {
        transactions.put(numOfTx,transaction);
        numOfTx++;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public String getBlockHash(){
        return this.blockHash;
    }

    public void setNonce(long nonce) {
        this.nonce=nonce;
    }

    public Transaction getTransaction(int index) {
        return this.transactions.get(index);
    }


    public ArrayList<Transaction> getTransactions() {
        ArrayList<Transaction> txList = new ArrayList<>();
        for(int i=0;i<transactions.size();i++){
            txList.add(transactions.get(i));
        }
        return txList;
    }

    public int getNumOfTx() {
        return this.numOfTx;
    }

    public long getNonce() {
        return this.nonce;
    }
}
