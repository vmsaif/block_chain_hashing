/**
 * COMP 2140   		SECTION A01
 * INSTRUCTOR   	Cuneyt Akcora
 * Assignment       5
 * @author			Saif Mahmud
 * @id				7808507
 * @version      	04/06/2020
 */
import java.math.BigInteger;

public class Transaction {

    String senderAddress;
    String receiverAddress;
    BigInteger amount;

    public Transaction(String fromAddress, String toAddress, BigInteger transactionAmount) {
        senderAddress = fromAddress;
        receiverAddress = toAddress;
        amount=transactionAmount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "senderAddress='" + senderAddress + '\'' +
                ", receiverAddress='" + receiverAddress + '\'' +
                ", amount=" + amount +
                '}';
    }

    public void setAmount(String a) {
        this.amount=new BigInteger(a);
    }
}
