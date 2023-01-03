import controller.Controller;
import repository.AdsRepository;
import repository.TransactionRepository;
import repository.UserRepository;
import repository.db_repo.DBCarRepository;
import repository.db_repo.DBTransactionRepository;
import repository.db_repo.DBUserRepository;
import view.View;

public class Main
{
    public static void main(String[] args)
    {
        UserRepository userRepository = new DBUserRepository();
        AdsRepository adsRepository = new DBCarRepository();
        TransactionRepository transactionRepository = new DBTransactionRepository();
        Controller controller = new Controller(userRepository, adsRepository, transactionRepository, false);
        View view = new View(controller, userRepository, adsRepository);

        view.mainMenu();
    }
}