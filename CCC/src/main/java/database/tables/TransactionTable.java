/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database.tables;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import database.Database_Connection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import mainClasses.Transaction;

/**
 *
 * @author ntigo
 */
public class TransactionTable {


    private String findBestTrader(String[] array) {
        List<String> list = Arrays.asList(array);
        Map<String, Integer> stringsCount = new HashMap<>();
        for (String string : list) {
            if (string.length() > 0) {
                string = string.toLowerCase();
                Integer count = stringsCount.get(string);
                if (count == null) {
                    count = 0;
                }
                count++;
                stringsCount.put(string, count);
            }
        }
        Map.Entry<String, Integer> mostRepeated = null;
        for (Map.Entry<String, Integer> e : stringsCount.entrySet()) {
            if (mostRepeated == null || mostRepeated.getValue() < e.getValue()) {
                mostRepeated = e;
            }
        }
        try {
            return mostRepeated.getKey();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public String traderOfMonth(String traderDate) throws ClassNotFoundException, SQLException {
        ResultSet res;
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        String transactionDate, bestTrader = null;
        String[] merchantsNamesArray;
        ArrayList<String> merchantsNamesList = new ArrayList<>();
        MerchantTable mercantTable = new MerchantTable();
        try {
            res = stmt.executeQuery("SELECT * FROM transactions");
            while (res.next()) {
                //yyyy-MM-dd
                transactionDate = res.getString("transaction_date");
                //yyyy-MM
                transactionDate = transactionDate.substring(0, transactionDate.lastIndexOf('-'));
                //yyyy-MM == traderDate
                if (transactionDate.equals(traderDate)) {
                    merchantsNamesList.add(res.getString("merchant_name"));
                }
            }

            //Convert ArrayList to Array
            merchantsNamesArray = new String[merchantsNamesList.size()];
            for (int i = 0; i < merchantsNamesList.size(); i++) {
                merchantsNamesArray[i] = merchantsNamesList.get(i);
            }

            //Find best trader & 5% reduction
            if ((bestTrader = findBestTrader(merchantsNamesArray)) != null) {
                mercantTable.updateBestMerchant(bestTrader);
            }
        } catch (SQLException e) {
            System.err.println("Got an exception, in traderOfMonth()");
            System.err.println(e.getMessage());
        }
        return bestTrader;
    }

    public void addTransactionFromJSON(String json) throws SQLException, ClassNotFoundException {
        Gson gson = new Gson();
        Transaction tr = gson.fromJson(json, Transaction.class);
        addNewTransaction(tr);
    }

    /*Update returned of Transaction*/
    public void updateReturned(int transaction_id,int returned) throws SQLException, ClassNotFoundException{
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update="UPDATE transactions SET returned='"+returned+"' WHERE transaction_id = '"+transaction_id+"'";
        stmt.executeUpdate(update);
        con.close();
    }

    /* Returns a Transaction if exists in Database, else null */
    public Transaction databaseToTransaction(String product_name, String customer, String property, String merchant) throws ClassNotFoundException, SQLException {
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM transactions WHERE product_name = '" + product_name + "' AND customer_name = '" +customer+ "' AND customer_property = '"+property+"' AND merchant_name = '"+merchant+"' AND returned = '"+0+"'");
            rs.next();
            String json = Database_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Transaction com = gson.fromJson(json, Transaction.class);
            return com;
        } catch (JsonSyntaxException | SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    /*Get Array List of every Transaction*/
    public ArrayList<Transaction> ArrayOfTransactions(String name,String property) throws SQLException, ClassNotFoundException {
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Transaction> transactions=new ArrayList<Transaction>();
        ResultSet rs;
        try {
            if(property.equals("merchant")){
                rs = stmt.executeQuery("SELECT * FROM transactions WHERE merchant_name= '" + name + "'");
            }else{
                rs = stmt.executeQuery("SELECT * FROM transactions WHERE customer_name= '" + name + "'");
            }
            while (rs.next()) {
                String json = Database_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Transaction doc = gson.fromJson(json, Transaction.class);
                transactions.add(doc);
            }
            return transactions;

        } catch (JsonSyntaxException | SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    /*Get Array List of every Transaction of every Employee*/
    public ArrayList<Transaction> ArrayOfEmployees(String property) throws SQLException, ClassNotFoundException {
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Transaction> transactions=new ArrayList<Transaction>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM transactions WHERE customer_property= '" + property + "'");
            while (rs.next()) {
                String json = Database_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Transaction doc = gson.fromJson(json, Transaction.class);
                transactions.add(doc);
            }
            return transactions;

        } catch (JsonSyntaxException | SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    /* Adds a new Transaction to Database */
    public void addNewTransaction(Transaction transaction) throws SQLException, ClassNotFoundException {
        try (Connection con = Database_Connection.getConnection()) {
            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " transactions (product_name, customer_name, customer_property, merchant_name, transaction_date, transaction_amount, transaction_type, returned)"
                    + " VALUES ("
                    + "'" + transaction.getProductName() + "',"
                    + "'" + transaction.getCustomerName() + "',"
                    + "'" + transaction.getCustomerProperty() + "',"
                    + "'" + transaction.getMerchantName() + "',"
                    + "'" + transaction.getTransactionDate() + "',"
                    + "'" + transaction.getTransactionAmount() + "',"
                    + "'" + transaction.getTransactionType() + "',"
                    + "'" + transaction.getReturned() + "'"
                    + ")";

            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The transaction was successfully added in the database.");
            stmt.close();
            con.close();
        }
    }

    public void createTransactionTable() throws SQLException, ClassNotFoundException {
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE transactions "
                + "(transaction_id INTEGER not NULL AUTO_INCREMENT, "
                + "    product_name VARCHAR(40) not null,"
                + "    customer_name VARCHAR(30) not null,"
                + "    customer_property VARCHAR(30) not null,"
                + "    merchant_name VARCHAR(30) not null,"
                + "    transaction_date VARCHAR(20) not null,"
                + "    transaction_amount DOUBLE not null,"
                + "    transaction_type VARCHAR(20) not null,"
                + "    returned INTEGER not null,"
                + " PRIMARY KEY (transaction_id))";
        stmt.execute(sql);
        stmt.close();
        con.close();
    }
}
