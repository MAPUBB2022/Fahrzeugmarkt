package repository.memory_repo;

import model.Advert;
import model.Transaktion;
import repository.TransactionRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTransactionRepository implements TransactionRepository
{
    private List<Transaktion> transaktionList;
    private int currentID;

    public InMemoryTransactionRepository() {
        this.transaktionList = new ArrayList<>();
        currentID = 0;
    }

    @Override
    public void add(Transaktion transaktion) {
        transaktion.setId(currentID++);
        transaktionList.add(transaktion);
    }

    @Override
    public void delete(Integer id)
    {
        Transaktion t = findId(id);
        if(t!=null)
            transaktionList.remove(t);
    }

    @Override
    public void update(Integer id, Transaktion newTransaktion)
    {
        Transaktion t = findId(id);
        if(t!=null)
        {
            newTransaktion.setId(id);
            transaktionList.set(transaktionList.indexOf(t), newTransaktion);
        }
    }

    @Override
    public Transaktion findId(Integer id)
    {
        for(Transaktion t: transaktionList)
        {
            if(t.getId() == id)
            {
                return t;
            }
        }
        return null;
    }

    @Override
    public List<Transaktion> findAll() {
        return transaktionList;
    }

    @Override
    public List<Transaktion> getTransactionsByCar(Advert advert)
    {
        List<Transaktion> transaktions = new ArrayList<>();
        for(Transaktion t: transaktionList)
        {
            if(t.getAd().getID() == advert.getID())
            {
                transaktions.add(t);
            }
        }
        return transaktions;
    }
}
