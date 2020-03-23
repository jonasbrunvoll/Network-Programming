package Entity;

import org.eclipse.persistence.exceptions.OptimisticLockException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class Task3And4 {


    public static void main(String[] args) throws OptimisticLockException {
        EntityManagerFactory factory = null;
        AccountDao accountDao = null;
        try {
            factory = Persistence.createEntityManagerFactory("Account");
            AccountDao dao = new AccountDao(factory);


            //Creates two objects each of account 1 and 2.
            Account ac1 = dao.findAccount(1);
            Account ac2 = dao.findAccount(2);
            Account ac1b = dao.findAccount(1);
            Account ac2b = dao.findAccount(2);


        /*
            The correct answer from the database should be:
            balance account 1 = 0
            balance account 2 = 2000
         */
            double transferMoney = 500;
            ac1.withDrawMoneyFromAccount(transferMoney);
            ac1b.withDrawMoneyFromAccount(transferMoney);

            ac2.increaseMoneyInAccount(transferMoney);
            ac2b.increaseMoneyInAccount(transferMoney);


            Thread.sleep(5000);

            dao.updateAccount(ac1);
            dao.updateAccount(ac2);
            dao.updateAccount(ac1b);
            dao.updateAccount(ac2b);

            //but instead it returns:
            List<Account> accountList = dao.listAllAccounts();
            for (Account account : accountList) {
                System.out.println("\n" + account.toString());
            }

        } catch (Exception e){
            System.out.println(e);
        } finally {
            factory.close();
        }

    }

}
