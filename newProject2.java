import javax.swing.text.View;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Scanner;
import java.sql.Statement;
import java.sql.ResultSet;



public class newProject2 {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/hospitalmanagement";
    private static final String username = "root";
    private static final String password = "Harshal@6321";

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            while (true) {
                System.out.println("==========-----+++++   WELL COME TO HOSPITAL APPLICATION  +++++-----==========");
                Scanner scanner = new Scanner(System.in);
                System.out.println("1.Register Your Number ");
                System.out.println("2.Cancel Register Name");
                System.out.println("0.Exit");
                System.out.println("Choose An Option :-");
                int choose = scanner.nextInt();
                switch (choose) {
                    case 1:
                        RegisterYourNumber(connection, scanner);
                        break;
                    case 2:
                        CancelRegisterName(connection, scanner);
                        break;
                    case 0:
                        Exit();
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice");
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private static void RegisterYourNumber(Connection connection, Scanner scanner) {
        try {
            System.out.println("Enter Patient Name:-");
            String fname = scanner.next();
            scanner.nextLine();
            System.out.println("Enter Your Address :-");
            String Address = scanner.next();
            System.out.println("Enter patient Contact Number :-");
            int contact = scanner.nextInt();

            String sql = "INSERT INTO hospitalregistration(patient_name, patient_address, patient_contactnum)" +
                    "VALUES ('" + fname + "' , '" + Address + "', '" + contact + "')";
            try (Statement statement = connection.createStatement()) {
                int AffectedRows = statement.executeUpdate(sql);
                if (AffectedRows > 0) {
                    System.out.println("-----------+++++Patient Registration Succesfully+++++----------");
                } else {
                    System.out.println("==========+++++registration Failed+++++==========");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            throw new RuntimeException();
        }


    }



    private static void CancelRegisterName(Connection connection, Scanner scanner) {
        try{
        System.out.println("Enter The CONTACT_NUMBER To Delete");
        String patient_contactnum = scanner.next();


        if (!patient_contactnumExit(connection, patient_contactnum)) {
            System.out.println("Registration not Found For The Given Number..");

            return;
        }
        String sql = "DELETE FROM hospitalregistration WHERE patient_contactnum = " + patient_contactnum;

        try (Statement statement = connection.createStatement()) {
            int AffectedRows = statement.executeUpdate(sql);

            if (AffectedRows > 0) {
                System.out.println("Patient Delete Sucessfully");
            } else {
                System.out.println("Patient Deletetion failed");
            }

        }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static boolean patient_contactnumExit(Connection connection, String patient_contactnum) {
        try {
            String sql ="SELECT patient_contactnum FROM hospitalregistration WHERE patient_contactnum =" + patient_contactnum;


            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql))
            {
                return resultSet.next();

            } catch (SQLException e)
            {
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }



    private static void Exit()throws InterruptedException {
        System.out.println("Exiting System....");
        int i = 5;
        while ( i != 0)
        {
            System.out.println(".");
            Thread.sleep(450);
            i--;

        }
        System.out.println("Tank you For Useing This Application.....");
    }

}
