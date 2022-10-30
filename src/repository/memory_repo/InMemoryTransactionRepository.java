package repository.memory_repo;

import model.Transaction;
import model.User;
import repository.TransactionRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTransactionRepository implements TransactionRepository
{
    private List<Transaction> transactionList;

    public InMemoryTransactionRepository() {
        this.transactionList = new ArrayList<>();
    }

    @Override
    public void add(Object o) {

    }

    @Override
    public void delete(Object o) {

    }

    @Override
    public void update(Object o, Object o2) {

    }

    @Override
    public Object findId(Object o) {
        return null;
    }
}
