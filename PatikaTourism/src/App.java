import core.Helper;
import view.LoginView;
import core.Db;
import java.sql.Connection;

public class App {
    public static void main(String[] args) {
        Helper.setTheme("Nimbus");
        Connection connection = Db.getInstance();
        if (connection != null) {
            System.out.println("Database connection established successfully.");
        } else {
            System.out.println("Failed to establish database connection.");
        }
        new LoginView();
    }
}

