import controller.Controller;
import repository.AdsRepository;
import repository.TransactionRepository;
import repository.UserRepository;
import repository.memory_repo.InMemoryCarRepository;
import repository.memory_repo.InMemoryTransactionRepository;
import repository.memory_repo.InMemoryUserRepository;
import view.View;

public class Main {
    public static void main(String[] args)
    {
        //System.out.println("Hello world!");
        UserRepository userRepository = new InMemoryUserRepository();
        AdsRepository adsRepository = new InMemoryCarRepository();
        TransactionRepository transactionRepository = new InMemoryTransactionRepository();
        Controller controller = new Controller(userRepository, adsRepository, transactionRepository);
        View view = new View(controller, userRepository, adsRepository, transactionRepository);


        view.mainMenu();
        view.createAd();
    }
}