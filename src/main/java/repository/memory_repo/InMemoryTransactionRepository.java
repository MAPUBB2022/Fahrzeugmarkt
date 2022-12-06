package repository.memory_repo;

import model.*;
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
        //populate();
    }

    private void populate()
    {
        Seller s2 = new Seller("unchiu", "1234", "pe Germania");
        Advert c = new Car(s2, 20, "Dacia", "Papuc", 2000, 1299, 150, 200, false, false, 5, 4, 4000, 800);
        Buyer b = new Buyer("iordache", "melissa", "BV");
        Transaktion transaktion = new Transaktion(b, c, 1020, true);
        this.add(transaktion);
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
