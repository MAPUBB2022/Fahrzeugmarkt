package repository;

import model.Advert;
import model.Seller;
import model.Transaktion;

import java.util.List;

public interface TransactionRepository extends ICrudRepository <Integer, Transaktion>
{
    List<Transaktion> getTransactionsByCar(Advert advert);
    List<Transaktion> getTransactionsBySeller(Seller seller);
}
