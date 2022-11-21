package repository.memory_repo;

import model.Advert;
import model.Seller;
import model.Transaction;
import model.User;
import repository.TransactionRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTransactionRepository implements TransactionRepository
{
    private List<Transaction> transactionList;
    private int currentID;

    public InMemoryTransactionRepository() {
        this.transactionList = new ArrayList<>();
        currentID = 0;
    }

    @Override
    public void add(Transaction transaction) {
        transaction.setId(currentID++);
        transactionList.add(transaction);
    }

    @Override
    public void delete(Integer id)
    {
        Transaction t = findId(id);
        if(t!=null)
            transactionList.remove(t);
    }

    @Override
    public void update(Integer id, Transaction newTransaction)
    {
        Transaction t = findId(id);
        if(t!=null)
        {
            newTransaction.setId(id);
            transactionList.set(transactionList.indexOf(t), newTransaction);
        }
    }

    @Override
    public Transaction findId(Integer id)
    {
        for(Transaction t: transactionList)
        {
            if(t.getId() == id)
            {
                return t;
            }
        }
        return null;
    }

    @Override
    public List<Transaction> findAll() {
        return transactionList;
    }

    @Override
    public List<Transaction> getTransactionsByCar(Advert advert)
    {
        List<Transaction> transactions = new ArrayList<>();
        for(Transaction t: transactionList)
        {
            if(t.getAd().getID() == advert.getID())
            {
                transactions.add(t);
            }
        }
        return transactions;
    }

    @Override
    public List<Transaction> getTransactionsBySeller(Seller seller)
    {
        List<Transaction> transactions = new ArrayList<>();
        for(Transaction t: transactionList)
        {
            if(t.getAd().getSeller().getUsername().equals(seller.getUsername()))
            {
                transactions.add(t);
            }
        }
        return transactions;
    }
}
