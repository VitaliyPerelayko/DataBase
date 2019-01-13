package task7;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class Check {
    public static void main(String[] args) throws ListExpensesException {
        DAOListExpensesImpl dao = DAOListExpensesImpl.getDAOListExpensesImpl();
        dao.getConnection();

        dao.addReceiver(new Receiver("Yandex"));

        dao.addExpense(new Expense(LocalDate.of(2012,5,13),6,new BigDecimal("56000.0")));

        ArrayList<Expense> expenses = dao.getExpenses();
        for (Expense e:expenses){
            System.out.println(e);
        }

        System.out.println("=========================================");

        ArrayList<Receiver> receivers = dao.getReceivers();
        for (Receiver r:receivers){
            System.out.println(r);
        }

        System.out.println("=========================================");

        System.out.println(dao.getReceiver(4));

        System.out.println("=========================================");

        System.out.println(dao.getExpense(8));


        dao.closeConnection();
    }
}
