/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import database.Database_Connection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import mainClasses.Merchant;

/**
 *
 * @author ntigo
 */
public class MerchantTable {

    private double calculateReduction(double amount_due) {
        double new_amount_due;
        if (amount_due > 0) {
            //5 mean 5% reduction
            new_amount_due = ((100 - 5) * amount_due) / 100;
            return new_amount_due;
        } else {
            return amount_due;
        }
    }

    public void updateBestMerchant(String name) throws ClassNotFoundException, SQLException {
        ResultSet res;
        String updateQuery;
        Double merchantAmmountDue;
        try (Connection con = Database_Connection.getConnection(); Statement stmt = con.createStatement()) {

            res = stmt.executeQuery("SELECT * FROM merchants WHERE name= '" + name + "'");

            if (res.next()) {
                merchantAmmountDue = calculateReduction(res.getDouble("amount_due"));
                updateQuery = "UPDATE merchants SET amount_due='"
                        + merchantAmmountDue
                        + "' WHERE name='" + name + "'";
                stmt.executeUpdate(updateQuery);
            }
        } catch (SQLException e) {
            System.err.println("Got an exception, in updateBestMerchant()");
            System.err.println(e.getMessage());
        }
    }

    public ArrayList<Merchant> ArrayOfMerchants(String operator) throws SQLException, ClassNotFoundException {
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet res;
        ArrayList<Merchant> merchants = new ArrayList<>();
        try {
            switch (operator) {
                //Handle question
                case "state1":
                    //Debt free merchants (amount_due == 0)
                    res = stmt.executeQuery("SELECT * FROM merchants WHERE amount_due= '" + 0 + "'");
                    break;
                case "state2":
                    //Debt merchants (amount_due > 0)
                    res = stmt.executeQuery("SELECT * FROM merchants WHERE amount_due> '" + 0 + "'");
                    break;
                default:
                    //All merchants
                    res = stmt.executeQuery("SELECT * FROM merchants");
                    break;
            }
            while (res.next()) {
                //Convert database result set to JSON
                String json = Database_Connection.getResultsToJSON(res);
                //Define JSON Object
                Gson gson = new Gson();
                //Generate Merchant object from JSON and add it to the arrayList
                merchants.add(gson.fromJson(json, Merchant.class));
            }
            return merchants;
        } catch (JsonSyntaxException | SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }


    public void addMerchantFromJSON(String json) throws SQLException, ClassNotFoundException {
        Gson gson = new Gson();
        Merchant mer = gson.fromJson(json, Merchant.class);
        addNewMerchant(mer);
    }

    public String merchantToJSON(Merchant merchant){
        Gson gson = new Gson();

        String json = gson.toJson(merchant, Merchant.class);
        return json;
    }

    /*Update values of Merchant (payment)*/
    public void updateMerchant(String name, double owed, double total_profit) throws SQLException, ClassNotFoundException{
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update="UPDATE merchants SET amount_due='"+owed+"', total_profit='"+total_profit+"' WHERE name = '"+name+"'";
        stmt.executeUpdate(update);
        con.close();
    }

    /*Update values of Merchant total profit only*/
    public void updateMerchant(String name, double total_profit) throws SQLException, ClassNotFoundException{
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update="UPDATE merchants SET  total_profit='"+total_profit+"' WHERE name = '"+name+"'";
        stmt.executeUpdate(update);
        con.close();
    }

    /* Adds a new Merchant to Database */
    public void addNewMerchant(Merchant merchant) throws SQLException, ClassNotFoundException {
        try (Connection con = Database_Connection.getConnection()) {
            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " merchants (name, account_number, supply, total_profit, amount_due)"
                    + " VALUES ("
                    + "'" + merchant.getName() + "',"
                    + "'" + merchant.getAccountNumber() + "',"
                    + "'" + merchant.getSupply() + "',"
                    + "'" + merchant.getTotalProfit() + "',"
                    + "'" + merchant.getAmountDue() + "'"
                    + ")";

            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The merchant was successfully added in the database.");
            stmt.close();
            con.close();
        }
    }

    /* Returns a Merchant if exists in Database, else null */
    public Merchant databaseToMerchant(String name) throws ClassNotFoundException, SQLException {
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM merchants WHERE name = '" + name + "'");
            rs.next();
            String json = Database_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Merchant mer = gson.fromJson(json, Merchant.class);
            return mer;
        } catch (JsonSyntaxException | SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    /*Deletes merchant from Merchant Table*/
    public void deleteMerchant(String name) throws SQLException, ClassNotFoundException{
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        String deleteQuery = "DELETE FROM merchants WHERE name='" + name + "'";
        stmt.executeUpdate(deleteQuery);
        System.out.println("# The merchant was successfully deleted from the database.");
        stmt.close();
        con.close();
    }

    public void createMerchantTable() throws SQLException, ClassNotFoundException {
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE merchants "
                + "(merchant_id INTEGER not NULL AUTO_INCREMENT, "
                + "    name VARCHAR(30) not null,"
                + "    account_number VARCHAR(20) not null unique,"
                + "    supply DOUBLE,"
                + "    total_profit DOUBLE,"
                + "    amount_due DOUBLE,"
                + " PRIMARY KEY (merchant_id))";
        stmt.execute(sql);
        stmt.close();
        con.close();
    }

    /* Initialize 5 merchants when the database is created */
    public void createDefaultExamples() throws SQLException {
        String merchant1 = "{\"name\":\"Ioanna\",\"account_number\":\"1234111111111119\","
                + "\"supply\":\"200\",\"amount_due\":\"500\",\"total_profit\":\"0\"}";
        String merchant2 = "{\"name\":\"Thanasis\",\"account_number\":\"1234511111111119\","
                + "\"supply\":\"200\",\"amount_due\":\"500\",\"total_profit\":\"0\"}";
        String merchant3 = "{\"name\":\"Katerina\",\"account_number\":\"1234611111111119\","
                + "\"supply\":\"200\",\"amount_due\":\"500\",\"total_profit\":\"0\"}";
        String merchant4 = "{\"name\":\"Nikos\",\"account_number\":\"1234711111111119\","
                + "\"supply\":\"200\",\"amount_due\":\"500\",\"total_profit\":\"0\"}";
        String merchant5 = "{\"name\":\"Sotiris\",\"account_number\":\"1234811111111119\","
                + "\"supply\":\"200\",\"amount_due\":\"500\",\"total_profit\":\"0\"}";
        MerchantTable ct = new MerchantTable();
        try {
            ct.addMerchantFromJSON(merchant1);
            ct.addMerchantFromJSON(merchant2);
            ct.addMerchantFromJSON(merchant3);
            ct.addMerchantFromJSON(merchant4);
            ct.addMerchantFromJSON(merchant5);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MerchantTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}