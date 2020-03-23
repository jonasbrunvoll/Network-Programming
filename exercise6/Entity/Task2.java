package Entity;

import org.eclipse.persistence.exceptions.OptimisticLockException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;



public class Task2 {
    public static void main(String[] args) throws OptimisticLockException {

        EntityManagerFactory factory = null;
        AccountDao accountDao;

        try {
            factory = Persistence.createEntityManagerFactory("Account");
            accountDao = new AccountDao(factory);


            //Create two accounts
            double accountBalance = 1000;
            String nameOwner1 = "Herman";
            String nameOwner2 = "Petter";
            Account account1 = new Account();
            Account account2 = new Account();
            accountDao.createNewAccount(account1, accountBalance, nameOwner1);
            accountDao.createNewAccount(account2, accountBalance, nameOwner2);

            //List out the accounts values
            List<Account> accountList = accountDao.listAllAccounts();
            System.out.println("Task 2");
            for (Account account : accountList) {
                System.out.println("\n" + account.toString());
            }


            //Creates two account objects equal to the account 1 and 2 in the database.
            Account ac1 = accountDao.findAccount(1);
            Account ac2 = accountDao.findAccount(2);


            //Lists out all accounts with balance greater than 'chechbalance'
            int chechBalance = 500;
            accountList = accountDao.allAccountsWithBalanceGreaterThen(chechBalance);
            System.out.println("\n");
            for (Account account : accountList) {
                System.out.println(account.getNameOwner() + "´s account has balance greater than " + chechBalance);
            }


            //Change owner of account number 1
            String newName = "Kristian";
            ac1.setNameOwner(newName);
            accountDao.updateAccount(ac1);
            System.out.println("\nNew name account number 1: " + accountDao.findAccount(1).getNameOwner());



            //Reseting test data.
            ac1.setNameOwner("Herman");
            ac1.setBalance(1000);
            ac2.setBalance(1000);
            accountDao.updateAccount(ac1);
            accountDao.updateAccount(ac2);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            factory.close();
        }

    }
}
