import controller.Controller;
import repository.AdsRepository;
import repository.TransactionRepository;
import repository.UserRepository;
import repository.memory_repo.InMemoryCarRepository;
import repository.memory_repo.InMemoryTransactionRepository;
import repository.memory_repo.InMemoryUserRepository;
import view.View;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;

public class Main {
    public static void main(String[] args)
    {
        //System.out.println("Hello world!");
        UserRepository userRepository = new InMemoryUserRepository();
        AdsRepository adsRepository = new InMemoryCarRepository();
        TransactionRepository transactionRepository = new InMemoryTransactionRepository();
        Controller controller = new Controller(userRepository, adsRepository, transactionRepository);
        View view = new View(controller, userRepository, adsRepository, transactionRepository);

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
        EntityManager manager = factory.createEntityManager();

        Query q = manager.createNativeQuery("select * from Benutzer");
        List<BigDecimal> values = q.getResultList();


        view.mainMenu();
        view.createAd();
    }
}