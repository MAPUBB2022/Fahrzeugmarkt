package repository;

import model.Advert;
import model.Seller;
import model.Transaction;

import java.util.List;

public interface TransactionRepository extends ICrudRepository <Integer, Transaction>
{
    List<Transaction> getTransactionsByCar(Advert advert);
    List<Transaction> getTransactionsBySeller(Seller seller);
}
