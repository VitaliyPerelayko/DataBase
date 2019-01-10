import java.sql.*;
import java.util.Scanner;

/** Задание 4. Напишите программу получающую данные о расходе и добавляющую этот расход в базу,
 *  после чего выводящую таблицу расходов на экран.
 *
 *  Задание 5. Измените задачу 4 таким образоим, чтобы она использовала перекомпилированные
 *  запросы.
 */

public class Task4 {

    //Данные для подключения
    public static final String dbURL ="jdbc:mysql://localhost:3306/ListExpenses";
    public static final String USER ="ViTT";
    public static final String PASSWORD ="password";

    //Запрос для вывода данных
    public static final
    String QUERYgetINFO =
            "select e.num,paydate,name,value from expenses as e,receivers as r where receiver=r.num;";



    // Метод добавляет данные в таблицу Expenses
    public static void addInfoToTableExpenses(Statement statement,String date,String receiver
                                                ,String value) throws SQLException {
        //Получаем номер получателя (так как вводится название оргаеизации)
        String query = "select num from receivers where name='" + receiver + "';";
        ResultSet result = statement.executeQuery(query);
        result.next();
        String num = result.getString("num");

        String newInfo = "insert into expenses (paydate, receiver, value) values ('" +
                date + "'," + num + "," + value + ");";
        statement.executeUpdate(newInfo);
    }
    // Метод добавляет данные в таблицу Expenses, но данные вводятся с консоли
    public static void addInfoToTableExpenses(Statement statement) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the date of payment");
        String date=scanner.next();
        System.out.println("Enter the name of receiver");
        String receiver=scanner.next();
        System.out.println("Enter the value of payment");
        String value=scanner.next();
        addInfoToTableExpenses(statement,date,receiver,value);
    }
    

    /** Реализация задания 5.
     * Методы похожие
     */

    public static void addInfoToTableExpensesTask5(Statement statement,String date,String receiver
            ,String value) throws SQLException {
        //Получаем номер получателя
        String query = "select num from receivers where name=?;";
        PreparedStatement preparedStatement=statement.getConnection().prepareStatement(query);
        preparedStatement.setString(1,receiver);
        ResultSet result = preparedStatement.executeQuery();
        result.next();

        String newInfo ="insert into expenses (paydate, receiver, value) values (?,?,?);";
        preparedStatement = statement.getConnection().prepareStatement(newInfo);
        preparedStatement.setString(1,date);
        preparedStatement.setString(2,result.getString("num"));
        preparedStatement.setString(3,value);
        preparedStatement.executeUpdate();
    }
    // Метод добавляет данные в таблицу Expenses, но данные вводятся с консоли
    public static void addInfoToTableExpensesTask5(Statement statement) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the date of payment");
        String date=scanner.next();
        System.out.println("Enter the name of receiver");
        String receiver=scanner.next();
        System.out.println("Enter the value of payment");
        String value=scanner.next();
        addInfoToTableExpensesTask5(statement,date,receiver,value);
    }


    //Вывод на консоль результата
    public static void getInfoFromDatabase(ResultSet result) throws SQLException {
        System.out.println("num     paydate     name        value");
        while (result.next()){
            System.out.print(result.getString("num")+"  ");
            System.out.print(result.getString("paydate")+"  ");
            System.out.print(result.getString("name")+"  ");
            System.out.print(result.getString("value")+"\n");
        }
    }


    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");


        try (Connection connection = DriverManager.getConnection(dbURL,USER,PASSWORD)){
            Statement statement = connection.createStatement();
            //Вывод исходной таблицы
            getInfoFromDatabase(statement.executeQuery(QUERYgetINFO));
            System.out.println("==============================");
            //Добавление данных
            addInfoToTableExpenses(connection.createStatement(),"2018-12-31","Интернет провайдер \"Соло\"","29000.0");
            addInfoToTableExpenses(connection.createStatement());
            //Метод и ззадания 5.
            addInfoToTableExpensesTask5(connection.createStatement(),"2018-11-25","Oracle","66600.0");
            System.out.println("==============================");
            //Вывод изменённой таблицы
            getInfoFromDatabase(statement.executeQuery(QUERYgetINFO));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

