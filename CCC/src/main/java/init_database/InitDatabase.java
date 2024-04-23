/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package init_database;

import static database.Database_Connection.getInitialConnection;

import database.tables.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ntigo
 */
public class InitDatabase {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        InitDatabase init = new InitDatabase();
        init.initDatabase();
        init.initTables();
    }

    public void initDatabase() throws SQLException, ClassNotFoundException {
        Connection con = getInitialConnection();
        Statement stmt = con.createStatement();
        stmt.execute("CREATE DATABASE HY360_Project");
        stmt.close();
        con.close();
    }

    public void initTables() throws SQLException, ClassNotFoundException {
        CivilianTable civT = new CivilianTable();
        civT.createCivilianTable();

        CompanyTable comT = new CompanyTable();
        comT.createCompanyTable();

        MerchantTable merT = new MerchantTable();
        merT.createMerchantTable();

        TransactionTable tranT = new TransactionTable();
        tranT.createTransactionTable();

        EmployeeTable empT = new EmployeeTable();
        empT.createEmployeeTable();

        /* Create 5 instances of each object */
        civT.createDefaultExamples();
        comT.createDefaultExamples();
        empT.createDefaultExamples();
        merT.createDefaultExamples();
    }

}
