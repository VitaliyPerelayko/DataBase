package task7;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DAOListExpensesImpl implements DAOListExpenses {

    public static DAOListExpensesImpl getDAOListExpensesImpl(){
        if (daoListExpenses == null){
            daoListExpenses=new DAOListExpensesImpl();
        }
        return daoListExpenses;
    }

    public void getConnection() throws ListExpensesException {
        String dbURL ="jdbc:mysql://localhost:3306/ListExpenses";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dbURL, "ViTT", "password");
        } catch (Exception e) {
            throw new ListExpensesException("Can't create connection");
        }
    }

    public void closeConnection() throws ListExpensesException{
        try {
            connection.close();
        } catch (SQLException e) {
            throw new ListExpensesException("Can't close connection");
        }
    }


    @Override
    public Receiver getReceiver(int num) throws ListExpensesException {
        Receiver receiver = new Receiver();
        try (Statement statement = connection.createStatement()){
            ResultSet result = statement.
                    executeQuery("select rec.name from listexpenses.receivers as rec where rec.num="+num+";");
            if (result.next()) {
                receiver.setName(result.getString("name"));
            }else {
                return null;
            }
        } catch (SQLException e) {
            throw new ListExpensesException("The name of receiver wasn't set");
        }
        receiver.setNum(num);
        return receiver;
    }

    @Override
    public ArrayList<Receiver> getReceivers(String query) throws ListExpensesException {
        ArrayList<Receiver> receivers= new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            ResultSet result = statement.executeQuery(query);
            while (result.next()){
                Receiver receiver =new Receiver();
                receiver.setNum(result.getInt(1));
                receiver.setName(result.getString(2));
                receivers.add(receiver);
            }
        } catch (SQLException e) {
            throw new ListExpensesException();
        }
        return receivers;
    }

    @Override
    public ArrayList<Receiver> getReceivers() throws ListExpensesException {
        return getReceivers("select * from listexpenses.receivers;");
    }

    @Override
    public Expense getExpense(int num) throws ListExpensesException {
        Expense expense = new Expense();
        try (Statement statement = connection.createStatement()){
            ResultSet result = statement.
                    executeQuery("select exp.paydate,exp.receiver,exp.value from listexpenses.expenses as exp where exp.num="+num+";");
            if (result.next()) {
//                String[] date = result.getString("paydate").split("-");
//                expense.setPaydate(
//                        LocalDate.of(Integer.getInteger(date[0]), Integer.getInteger(date[1]), Integer.getInteger(date[2])));
                expense.setPaydate(result.getDate("paydate").toLocalDate());
                expense.setReseiver(result.getInt("receiver"));
                expense.setValue(new BigDecimal(result.getString("value")));
            }else {
                return null;
            }
        } catch (SQLException e) {
            throw new ListExpensesException("The data of receiver wasn't set");
        }
        expense.setNum(num);
        return expense;
    }

    @Override
    public ArrayList<Expense> getExpenses(String query) throws ListExpensesException {
        ArrayList<Expense> expenses= new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            ResultSet result = statement.executeQuery(query);
            while (result.next()){
                Expense expense =new Expense();
                expense.setNum(result.getInt(1));
//                String [] date = result.getString(2).split("-");
//                expense.setPaydate(
//                        LocalDate.of(Integer.getInteger(date[0]),Integer.getInteger(date[1]),Integer.getInteger(date[2])));
                expense.setPaydate(result.getDate(2).toLocalDate());
                expense.setReseiver(result.getInt(3));
                expense.setValue(new BigDecimal(result.getString(4)));
                expenses.add(expense);
            }
        } catch (SQLException e) {
            throw new ListExpensesException();
        }
        return expenses;
    }


    @Override
    public ArrayList<Expense> getExpenses() throws ListExpensesException {
        return getExpenses("select * from listexpenses.expenses;");
    }

    @Override
    public int addReceiver(Receiver receiver) throws ListExpensesException {
        int num;
        try (Statement statement = connection.createStatement()) {
            String query = "insert into listexpenses.receivers (name) values ('"+receiver.getName()+"');";
            if (statement.executeUpdate(query)!=0){
            ResultSet result = statement.executeQuery("select LAST_INSERT_ID()");
            result.next();
            num = result.getInt(1);
            receiver.setNum(num);
            }else {
                return -1;
            }
        } catch (SQLException e) {
            throw new ListExpensesException();
        }
        return num;
    }

    @Override
    public int addExpense(Expense expense) throws ListExpensesException {
        int num;
        try (Statement statement = connection.createStatement()) {
            LocalDate localDate = expense.getPaydate();
            String date =  localDate.getYear()+"-"+localDate.getMonthValue()+"-"+localDate.getDayOfMonth();
            String query = "insert into listexpenses.expenses (paydate, receiver, value) value ('"+date+"',"+
                    expense.getReseiver()+","+expense.getValue().toString()+");";

            if (statement.executeUpdate(query)!=0){
                ResultSet result = statement.executeQuery("select LAST_INSERT_ID()");
                result.next();
                num = result.getInt(1);
                expense.setNum(num);
            }else {
                return -1;
            }
        } catch (SQLException e) {
            throw new ListExpensesException();
        }
        return num;
    }

    @Override
    public ResultSet executeQueryAtListExpenses(String query) throws ListExpensesException {
        ResultSet resultSet;
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new ListExpensesException();
        }
        return resultSet;
    }

    @Override
    public boolean executeUpdateAtListExpenses(String query) throws ListExpensesException {
        boolean flag=true;
        try (Statement statement = connection.createStatement()) {
            if(statement.executeUpdate(query)==0){
                flag=false;
            }
        } catch (SQLException e) {
            throw new ListExpensesException();
        }
        return flag;
    }


    private static DAOListExpensesImpl daoListExpenses;

    private DAOListExpensesImpl(){}

    private static Connection connection;


}
