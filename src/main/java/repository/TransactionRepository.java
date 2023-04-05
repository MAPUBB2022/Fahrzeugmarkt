package repository;

import model.Advert;
import model.Seller;
import model.Transaktion;

import java.util.List;

public interface TransactionRepository extends ICrudRepository <Integer, Transaktion>
{
    /**
     * gets all the transactions regarding a given advert from the repository
     * @param advert the advert that the transactions should regard
     * @return a list of all transactions regarding a car
     */
    List<Transaktion> getTransactionsByCar(Advert advert);
}
