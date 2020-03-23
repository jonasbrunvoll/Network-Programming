package Entity;


import org.eclipse.persistence.exceptions.OptimisticLockException;

import javax.persistence.*;
import java.util.List;

public class AccountDao {

    private EntityManagerFactory factory;


    public AccountDao(EntityManagerFactory emf){
        this.factory = emf;
    }

    private EntityManager getEM(){
        EntityManager em = factory.createEntityManager();
        return em;
    }

    private EntityManager closeEM(EntityManager em){
        if (em != null && em.isOpen()){
            em.close();
        }
        return null;
    }

    public void createNewAccount(Account newAccount, double accountBalance, String nameOwner){
        EntityManager em = getEM();
        try {
            em.getTransaction().begin();
            newAccount.setBalance(accountBalance);
            newAccount.setNameOwner(nameOwner);
            em.persist(newAccount);
            em.getTransaction().commit();
        } finally {
            closeEM(em);
        }
    }

    public List<Account> listAllAccounts(){
        EntityManager em = getEM();
        try{
            Query q = em.createQuery("SELECT o FROM Account o");
            return q.getResultList();
        }finally{
            closeEM(em);
        }
    }

    public Account findAccount(int accountnumber){
        EntityManager em = getEM();
        try{
            return em.find(Account.class, accountnumber);
        }finally{
            closeEM(em);
        }
    }

    public List<Account> allAccountsWithBalanceGreaterThen(int balance){
        EntityManager em = getEM();
        try{
            Query q = em.createQuery("SELECT a FROM Account a WHERE a.balance >='" + balance+"'");
            return q.getResultList();
        } finally {
            closeEM(em);
        }
    }

    public void updateAccount(Account account) {
        EntityManager em = getEM();
        try {
            em.getTransaction().begin();
            em.merge(account);
            em.getTransaction().commit();
        } catch (OptimisticLockException e){
            System.out.println(e);
        }
            finally {
            closeEM(em);
        }
    }
}
