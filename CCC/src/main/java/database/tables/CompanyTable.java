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

import mainClasses.Company;

/**
 *
 * @author ntigo
 */
public class CompanyTable {

    public ArrayList<Company> ArrayOfCompanies(String operator) throws SQLException, ClassNotFoundException {
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet res;
        ArrayList<Company> companies = new ArrayList<>();
        try {
            switch (operator) {
                //Handle question
                case "state1":
                    //Debt free companies (amount_due == 0)
                    res = stmt.executeQuery("SELECT * FROM companies WHERE amount_due= '" + 0 + "'");
                    break;
                case "state2":
                    //Debt companies (amount_due > 0)
                    res = stmt.executeQuery("SELECT * FROM companies WHERE amount_due> '" + 0 + "'");
                    break;
                default:
                    //All companies
                    res = stmt.executeQuery("SELECT * FROM companies");
                    break;
            }
            while (res.next()) {
                //Convert database result set to JSON
                String json = Database_Connection.getResultsToJSON(res);
                //Define JSON Object
                Gson gson = new Gson();
                //Generate Company object from JSON and add it to the arrayList
                companies.add(gson.fromJson(json, Company.class));
            }
            return companies;
        } catch (JsonSyntaxException | SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public void addCompanyFromJSON(String json) throws SQLException, ClassNotFoundException {
        Gson gson = new Gson();
        Company com = gson.fromJson(json, Company.class);
        addNewCompany(com);
    }

    /*company to JSON*/
    public String companyToJSON(Company company){
        Gson gson = new Gson();

        String json = gson.toJson(company, Company.class);
        return json;
    }

    /*Update values of company*/
    public void updateCompany(String name,double owed, double balance) throws SQLException, ClassNotFoundException{
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update="UPDATE companies SET amount_due='"+owed+"', available_balance ='"+balance+"' WHERE name = '"+name+"'";
        stmt.executeUpdate(update);
        con.close();
    }

    /*Update values of company balance only*/
    public void updateCompany(String name, double balance) throws SQLException, ClassNotFoundException{
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update="UPDATE companies SET  available_balance ='"+balance+"' WHERE name = '"+name+"'";
        stmt.executeUpdate(update);
        con.close();
    }

    /*Update values of company*/
    public void updateCompanyCredit(String name,double owed, double credit_limit) throws SQLException, ClassNotFoundException{
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update="UPDATE companies SET amount_due='"+owed+"', credit_limit ='"+credit_limit+"' WHERE name = '"+name+"'";
        stmt.executeUpdate(update);
        con.close();
    }

    /*Update values of company balance only*/
    public void updateCompanyCredit(String name, double credit_limit) throws SQLException, ClassNotFoundException{
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update="UPDATE companies SET  credit_limit ='"+credit_limit+"' WHERE name = '"+name+"'";
        stmt.executeUpdate(update);
        con.close();
    }

    /* Adds a new Company to Database */
    public void addNewCompany(Company company) throws SQLException, ClassNotFoundException {
        try (Connection con = Database_Connection.getConnection()) {
            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " companies (name, account_number, exp_date, credit_limit, amount_due, available_balance,"
                    + " employee_name, employee_identity)"
                    + " VALUES ("
                    + "'" + company.getName() + "',"
                    + "'" + company.getAccountNumber() + "',"
                    + "'" + company.getExpDate() + "',"
                    + "'" + company.getCreditLimit() + "',"
                    + "'" + company.getAmountDue() + "',"
                    + "'" + company.getAvailableBalance() + "',"
                    + "'" + company.getEmployeeName() + "',"
                    + "'" + company.getEmployeeIdentity() + "'"
                    + ")";

            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The company was successfully added in the database.");
            stmt.close();
            con.close();
        }
    }

    /* Returns a Company if exists in Database, else null */
    public Company databaseToCompany(String name) throws ClassNotFoundException, SQLException {
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM companies WHERE name = '" + name + "'");
            rs.next();
            String json = Database_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Company com = gson.fromJson(json, Company.class);
            return com;
        } catch (JsonSyntaxException | SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    /* Returns a Company if exists in Database, else null (accountNumberSearch) */
    public Company accountNumberSearch(String account_number) throws ClassNotFoundException, SQLException {
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM companies WHERE account_number = '" +account_number+ "'");
            rs.next();
            String json = Database_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Company com = gson.fromJson(json, Company.class);
            return com;
        } catch (JsonSyntaxException | SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    /*Delete Company from Company Table*/
    public void deleteCompany(String name) throws SQLException, ClassNotFoundException{
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        String deleteQuery = "DELETE FROM companies WHERE name='" + name + "'";
        stmt.executeUpdate(deleteQuery);
        System.out.println("# The company was successfully deleted from the database.");
        stmt.close();
        con.close();
    }


    public void createCompanyTable() throws SQLException, ClassNotFoundException {
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE companies "
                + "(company_id INTEGER not NULL AUTO_INCREMENT, "
                + "    name VARCHAR(30) not null,"
                + "    account_number VARCHAR(20) not null unique,"
                + "    exp_date VARCHAR(20) not null,"
                + "    credit_limit INTEGER,"
                + "    amount_due DOUBLE,"
                + "    available_balance DOUBLE,"
                + "    employee_name VARCHAR(40) not null,"
                + "    employee_identity VARCHAR(30) not null,"
                + " PRIMARY KEY (company_id))";
        stmt.execute(sql);
        stmt.close();
        con.close();
    }

    /* Initialize 5 companies when the database is created */
    public void createDefaultExamples() throws SQLException {
        String company1 = "{\"name\":\"Microsoft\",\"account_number\":\"1211111111111111\",\"exp_date\":\"2022-10-1\","
                + "\"credit_limit\":\"10000\",\"amount_due\":\"7000\",\"available_balance\":\"30000\"}";
        String company2 = "{\"name\":\"Tesla\",\"account_number\":\"1311111111111112\",\"exp_date\":\"2023-10-1\","
                + "\"credit_limit\":\"10000\",\"amount_due\":\"7000\",\"available_balance\":\"30000\"}";
        String company3 = "{\"name\":\"Apple\",\"account_number\":\"1411111111111113\",\"exp_date\":\"2024-10-1\","
                + "\"credit_limit\":\"10000\",\"amount_due\":\"7000\",\"available_balance\":\"30000\"}";
        String company4 = "{\"name\":\"Amazon\",\"account_number\":\"1511111111111114\",\"exp_date\":\"2024-11-1\","
                + "\"credit_limit\":\"10000\",\"amount_due\":\"7000\",\"available_balance\":\"30000\"}";
        String company5 = "{\"name\":\"Samsung\",\"account_number\":\"1611111111111115\",\"exp_date\":\"2023-1-1\","
                + "\"credit_limit\":\"10000\",\"amount_due\":\"7000\",\"available_balance\":\"30000\"}";
        CompanyTable ct = new CompanyTable();
        try {
            ct.addCompanyFromJSON(company1);
            ct.addCompanyFromJSON(company2);
            ct.addCompanyFromJSON(company3);
            ct.addCompanyFromJSON(company4);
            ct.addCompanyFromJSON(company5);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CompanyTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
