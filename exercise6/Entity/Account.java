package Entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity

public class Account implements Serializable {
    @TableGenerator(
            name = "yourTableGenerator",
            allocationSize = 1,
            initialValue = 1)
    @Id
    @GeneratedValue(
            strategy=GenerationType.TABLE,
            generator="yourTableGenerator")

    private int accountNumber;

    @Version
    private int locker; // Lock for task 4.
    private double balance;
    private String nameOwner;

    public Account() {}
    public Account(int accountNumber, double balance, String nameOwner){
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.nameOwner = nameOwner;
    }


    public int getAccountNumber(){
        return accountNumber;
    }

    public double getBalance(){
        return  balance;
    }

    public String getNameOwner(){
        return nameOwner;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setNameOwner(String nameOwner) {
        this.nameOwner = nameOwner;
    }

    public void withDrawMoneyFromAccount(double money){
        this.balance = balance - money;
    }
    public void increaseMoneyInAccount(double money){
        this.balance = balance + money;
    }
    public String toString(){
        return "Name account owner: " + nameOwner + "\nAccount number: " + accountNumber + "\nAccount balance: " + balance;
    }
}
