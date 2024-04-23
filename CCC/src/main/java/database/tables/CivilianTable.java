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
import mainClasses.Civilian;

/**
 *
 * @author ntigo
 */
public class CivilianTable {

    /* Returns a list of Civilians */
    public ArrayList<Civilian> ArrayOfCivilians(String operator) throws SQLException, ClassNotFoundException {
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet res;
        ArrayList<Civilian> civilians = new ArrayList<>();
        try {
            switch (operator) {
                //Handle question
                case "state1":
                    //Debt free civilians (amount_due == 0)
                    res = stmt.executeQuery("SELECT * FROM civilians WHERE amount_due= '" + 0 + "'");
                    break;
                case "state2":
                    //Debt civilians (amount_due > 0)
                    res = stmt.executeQuery("SELECT * FROM civilians WHERE amount_due> '" + 0 + "'");
                    break;
                default:
                    //All civilians
                    res = stmt.executeQuery("SELECT * FROM civilians");
                    break;
            }
            while (res.next()) {
                //Convert database result set to JSON
                String json = Database_Connection.getResultsToJSON(res);
                //Define JSON Object
                Gson gson = new Gson();
                //Generate Civilian object from JSON and add it to the arrayList
                civilians.add(gson.fromJson(json, Civilian.class));
            }
            return civilians;
        } catch (JsonSyntaxException | SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    /* Converts Civilian object to Json */
    public String civilianToJSON(Civilian civilian) {
        Gson gson = new Gson();

        String json = gson.toJson(civilian, Civilian.class);
        return json;
    }

    /* Adds Civilian to database from Json */
    public void addCivilianFromJSON(String json) throws SQLException, ClassNotFoundException {
        Gson gson = new Gson();
        Civilian civ = gson.fromJson(json, Civilian.class);
        addNewCivilian(civ);
    }

    /*Update values of Civilian*/
    public void updateCivilian(String name,double owed, double balance) throws SQLException, ClassNotFoundException{
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update="UPDATE civilians SET amount_due='"+owed+"', available_balance ='"+balance+"' WHERE name = '"+name+"'";
        stmt.executeUpdate(update);
        con.close();
    }

    /*Update values of Civilian*/
    public void updateCivilianCredit(String name,double owed, double credit_limit) throws SQLException, ClassNotFoundException{
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update="UPDATE civilians SET amount_due='"+owed+"', credit_limit ='"+credit_limit+"' WHERE name = '"+name+"'";
        stmt.executeUpdate(update);
        con.close();
    }

    /*Update values of Civilian balance only*/
    public void updateCivilian(String name, double balance) throws SQLException, ClassNotFoundException{
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update="UPDATE civilians SET available_balance ='"+balance+"' WHERE name = '"+name+"'";
        stmt.executeUpdate(update);
        con.close();
    }

    /*Update values of Civilian credit only*/
    public void updateCivilianCredit(String name, double credit_limit) throws SQLException, ClassNotFoundException{
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update="UPDATE civilians SET credit_limit ='"+credit_limit+"' WHERE name = '"+name+"'";
        stmt.executeUpdate(update);
        con.close();
    }

    /* Adds a new Civilian to Database */
    public void addNewCivilian(Civilian civilian) throws SQLException, ClassNotFoundException {
        try (Connection con = Database_Connection.getConnection()) {
            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " civilians (name, account_number, exp_date, credit_limit, amount_due, available_balance)"
                    + " VALUES ("
                    + "'" + civilian.getName() + "',"
                    + "'" + civilian.getAccountNumber() + "',"
                    + "'" + civilian.getExpDate() + "',"
                    + "'" + civilian.getCreditLimit() + "',"
                    + "'" + civilian.getAmountDue() + "',"
                    + "'" + civilian.getAvailableBalance() + "'"
                    + ")";

            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The civilian was successfully added in the database.");
            stmt.close();
            con.close();
        }
    }

    /* Returns a Civilian if exists in Database, else null */
    public Civilian databaseToCivilian(String name) throws ClassNotFoundException, SQLException {
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM civilians WHERE name = '" + name + "'");
            rs.next();
            String json = Database_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Civilian civ = gson.fromJson(json, Civilian.class);
            return civ;
        } catch (JsonSyntaxException | SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    /* Deletes Civilian from Database */
    public void deleteCivilian(String name) throws ClassNotFoundException, SQLException {
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        try {
            stmt.executeUpdate("DELETE FROM civilians WHERE name = '" + name + "'");
            System.out.println("Civilian with name: " + name + " has been deleted!");
        } catch (JsonSyntaxException | SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        stmt.close();
        con.close();
    }

    /* Creates Civilian Table in Database */
    public void createCivilianTable() throws SQLException, ClassNotFoundException {
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE civilians "
                + "(civilian_id INTEGER not NULL AUTO_INCREMENT, "
                + "    name VARCHAR(30) not null,"
                + "    account_number VARCHAR(20) not null unique,"
                + "    exp_date VARCHAR(20) not null,"
                + "    credit_limit INTEGER,"
                + "    amount_due DOUBLE,"
                + "    available_balance DOUBLE,"
                + " PRIMARY KEY (civilian_id))";
        stmt.execute(sql);
        stmt.close();
        con.close();
    }

    /* Initialize 5 civilians when the database is created */
    public void createDefaultExamples() throws SQLException {
        String civilian1 = "{\"name\":\"Giorgos\",\"account_number\":\"1111111111111111\",\"exp_date\":\"2022-10-10\","
                + "\"credit_limit\":\"1000\",\"amount_due\":\"700\",\"available_balance\":\"3000\"}";
        String civilian2 = "{\"name\":\"Manos\",\"account_number\":\"1111111111111112\",\"exp_date\":\"2023-10-10\","
                + "\"credit_limit\":\"1000\",\"amount_due\":\"700\",\"available_balance\":\"3000\"}";
        String civilian3 = "{\"name\":\"Mpampis\",\"account_number\":\"1111111111111113\",\"exp_date\":\"2024-10-10\","
                + "\"credit_limit\":\"1000\",\"amount_due\":\"700\",\"available_balance\":\"3000\"}";
        String civilian4 = "{\"name\":\"Giannis\",\"account_number\":\"1111111111111114\",\"exp_date\":\"2024-11-11\","
                + "\"credit_limit\":\"1000\",\"amount_due\":\"700\",\"available_balance\":\"3000\"}";
        String civilian5 = "{\"name\":\"Kostas\",\"account_number\":\"1111111111111115\",\"exp_date\":\"2023-1-1\","
                + "\"credit_limit\":\"1000\",\"amount_due\":\"700\",\"available_balance\":\"3000\"}";
        CivilianTable ct = new CivilianTable();
        try {
            ct.addCivilianFromJSON(civilian1);
            ct.addCivilianFromJSON(civilian2);
            ct.addCivilianFromJSON(civilian3);
            ct.addCivilianFromJSON(civilian4);
            ct.addCivilianFromJSON(civilian5);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CivilianTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}