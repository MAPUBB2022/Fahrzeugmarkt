import controller.Controller;
import repository.AdsRepository;
import repository.TransactionRepository;
import repository.UserRepository;
import repository.db_repo.DBCarRepository;
import repository.db_repo.DBTransactionRepository;
import repository.db_repo.DBUserRepository;
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
    public static void main(String[] args) throws IllegalAccessException {
        UserRepository userRepository = new DBUserRepository();
        AdsRepository adsRepository = new DBCarRepository();
        TransactionRepository transactionRepository = new DBTransactionRepository();
        Controller controller = new Controller(userRepository, adsRepository, transactionRepository, false);
        View view = new View(controller, userRepository, adsRepository, transactionRepository);

        view.mainMenu();
    }
}