package task7;

import java.sql.ResultSet;
import java.util.ArrayList;

public interface DAOListExpenses {
    /**
     * Ищет получателя по номеру, если такого номера нет аозврращает null
     * @param num
     * @return Receiver
     */
    Receiver getReceiver(int num) throws ListExpensesException;

    /**
     * Ищет получателей по некоторому запросу
     * @param query результат запроса должен быть со структурой таблицы получателей
     * @return
     */
    ArrayList<Receiver> getReceivers(String query) throws ListExpensesException;

    /**
     * Составляет список всех получателей
     * @return
     */
    ArrayList<Receiver> getReceivers() throws ListExpensesException;

    /**
     * Ищет платёж по номеру, если такого номера нет аозврращает null
     * @param num
     */
    Expense getExpense(int num) throws ListExpensesException;

    /**
     * Ищет платежи по некоторому запросу
     * @param query результат запроса должен быть со структурой таблицы платежей
     * @return
     */
    ArrayList<Expense> getExpenses(String query) throws ListExpensesException;

    /**
     * Составляет список всех платежей
     * @return
     */
    ArrayList<Expense> getExpenses() throws ListExpensesException;

    /**
     * Добавляет получателя в базу данных
     * @param receiver
     * @return номер получателя в базе. Если добавить не получилось возвращает -1
     */
    int addReceiver(Receiver receiver) throws ListExpensesException;

    /**
     * Добавляет платёж в базу данных
     * @param expense
     * @return номер платежа в базе. Если добавить не получилось возвращает -1
     */
    int addExpense(Expense expense) throws ListExpensesException;

    /** Если предыдущих методов не хватит будут эти два универсальных
     * @param query
     * @return
     */
    ResultSet executeQueryAtListExpenses(String query) throws ListExpensesException;

    /**
     * @param query
     */
    boolean executeUpdateAtListExpenses(String query) throws ListExpensesException;

}
