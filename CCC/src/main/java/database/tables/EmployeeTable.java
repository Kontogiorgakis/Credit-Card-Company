package database.tables;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import database.Database_Connection;
import mainClasses.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeTable {

    public ArrayList<Employee> ArrayOfEmployees(String operator) throws SQLException, ClassNotFoundException {
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet res;
        ArrayList<Employee> employees = new ArrayList<>();
        try {
            switch (operator) {
                //Handle question
                case "state1":
                    //Debt free employees (amount_due == 0)
                    res = stmt.executeQuery("SELECT * FROM employees WHERE amount_due= '" + 0 + "'");
                    break;
                case "state2":
                    //Debt employees (amount_due > 0)
                    res = stmt.executeQuery("SELECT * FROM employees WHERE amount_due> '" + 0 + "'");
                    break;
                default:
                    //All employees
                    res = stmt.executeQuery("SELECT * FROM employees");
                    break;
            }
            while (res.next()) {
                //Convert database result set to JSON
                String json = Database_Connection.getResultsToJSON(res);
                //Define JSON Object
                Gson gson = new Gson();
                //Generate Employee object from JSON and add it to the arrayList
                employees.add(gson.fromJson(json, Employee.class));
            }
            return employees;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public void addEmployeeFromJSON(String json) throws SQLException, ClassNotFoundException {
        Gson gson = new Gson();
        Employee emp = gson.fromJson(json, Employee.class);
        addNewEmployee(emp);
    }

    /*Update values of Employee*/
    public void updateEmployee(String name,double owed, double balance) throws SQLException, ClassNotFoundException{
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update="UPDATE employees SET amount_due='"+owed+"', available_balance ='"+balance+"' WHERE name = '"+name+"'";
        stmt.executeUpdate(update);
        con.close();
    }

    /*Update values of Employee*/
    public void updateEmployeeCredit(String name,double owed, double credit_limit) throws SQLException, ClassNotFoundException{
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update="UPDATE employees SET amount_due='"+owed+"', credit_limit ='"+credit_limit+"' WHERE name = '"+name+"'";
        stmt.executeUpdate(update);
        con.close();
    }

    /*Update values of Employee*/
    public void updateEmployeePayment(String account_number,double owed, double balance) throws SQLException, ClassNotFoundException{
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update="UPDATE employees SET amount_due='"+owed+"', available_balance ='"+balance+"' WHERE account_number = '"+account_number+"'";
        stmt.executeUpdate(update);
        con.close();
    }

    /*Update values of Employee balance only*/
    public void updateEmployee(String name, double balance) throws SQLException, ClassNotFoundException{
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update="UPDATE employees SET available_balance ='"+balance+"' WHERE name = '"+name+"'";
        stmt.executeUpdate(update);
        con.close();
    }

    /*Update values of Employee credit only*/
    public void updateEmployeeCredit(String name, double credit_limit) throws SQLException, ClassNotFoundException{
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update="UPDATE employees SET credit_limit ='"+credit_limit+"' WHERE name = '"+name+"'";
        stmt.executeUpdate(update);
        con.close();
    }

    /*Update values of Employee balance only from Account Number*/
    public void updateFromAccountNum(String account_number,double owed, double balance) throws SQLException, ClassNotFoundException{
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update="UPDATE employees SET available_balance ='"+balance+"', amount_due ='"+owed+"' WHERE account_number = '"+account_number+"'";
        stmt.executeUpdate(update);
        con.close();
    }


    /*Update values of Employee creditLimit only from Account Number*/
    public void updateFromAccountNumCredit(String account_number,double owed, int creditLimit) throws SQLException, ClassNotFoundException{
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update="UPDATE employees SET credit_limit ='"+creditLimit+"', amount_due ='"+owed+"' WHERE account_number = '"+account_number+"'";
        stmt.executeUpdate(update);
        con.close();
    }

    /* Adds a new Employee to Database */
    public void addNewEmployee(Employee employee) throws SQLException, ClassNotFoundException {
        try (Connection con = Database_Connection.getConnection()) {
            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " employees (name, account_number, exp_date, credit_limit, amount_due, available_balance, company)"
                    + " VALUES ("
                    + "'" + employee.getName() + "',"
                    + "'" + employee.getAccountNumber() + "',"
                    + "'" + employee.getExpDate() + "',"
                    + "'" + employee.getCreditLimit() + "',"
                    + "'" + employee.getAmountDue() + "',"
                    + "'" + employee.getAvailableBalance() + "',"
                    + "'" + employee.getCompany() + "'"
                    + ")";

            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The employee was successfully added in the database.");
            stmt.close();
            con.close();
        }
    }

    /* Returns a Employee if exists in Database, else null */
    public Employee databaseToEmployee(String name) throws ClassNotFoundException, SQLException {
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM employees WHERE name = '" + name + "'");
            rs.next();
            String json = Database_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Employee emp = gson.fromJson(json, Employee.class);
            return emp;
        } catch (JsonSyntaxException | SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }


    public void deleteEmployee(String name) throws ClassNotFoundException, SQLException {
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        try {
            stmt.executeUpdate("DELETE FROM employees WHERE name = '" + name + "'");
            System.out.println("Employee with name: " + name + " has been deleted!");
        } catch (JsonSyntaxException | SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        stmt.close();
        con.close();
    }


    /* Creates Employee Table in Database */
    public void createEmployeeTable() throws SQLException, ClassNotFoundException {
        Connection con = Database_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE employees "
                + "(employee_id INTEGER not NULL AUTO_INCREMENT, "
                + "    name VARCHAR(30) not null,"
                + "    account_number VARCHAR(20) not null,"
                + "    exp_date VARCHAR(20) not null,"
                + "    credit_limit INTEGER,"
                + "    amount_due DOUBLE,"
                + "    available_balance DOUBLE,"
                + "    company VARCHAR(20) not null,"
                + " PRIMARY KEY (employee_id))";
        stmt.execute(sql);
        stmt.close();
        con.close();
    }


    public String employeeToJSON(Employee employee){
        Gson gson = new Gson();

        String json = gson.toJson(employee, Employee.class);
        return json;
    }

    /* Initialize 5 employees when the database is created */
    public void createDefaultExamples() throws SQLException {
        String employee1 = "{\"name\":\"Maria\",\"account_number\":\"1211111111111222\",\"exp_date\":\"2022-10-1\","
                + "\"credit_limit\":\"1000\",\"amount_due\":\"700\",\"available_balance\":\"3000\",\"company\":\"Microsoft\"}";
        String employee2 = "{\"name\":\"Georgia\",\"account_number\":\"1211111111111322\",\"exp_date\":\"2022-11-1\","
                + "\"credit_limit\":\"1000\",\"amount_due\":\"700\",\"available_balance\":\"3000\",\"company\":\"Tesla\"}";
        String employee3 = "{\"name\":\"Grigoris\",\"account_number\":\"1211111111111422\",\"exp_date\":\"2022-6-1\","
                + "\"credit_limit\":\"1000\",\"amount_due\":\"700\",\"available_balance\":\"3000\",\"company\":\"Apple\"}";
        String employee4 = "{\"name\":\"Mixalis\",\"account_number\":\"1211111111111522\",\"exp_date\":\"2022-8-1\","
                + "\"credit_limit\":\"1000\",\"amount_due\":\"700\",\"available_balance\":\"3000\",\"company\":\"Amazon\"}";
        String employee5 = "{\"name\":\"Panos\",\"account_number\":\"1211111111111622\",\"exp_date\":\"2022-3-1\","
                + "\"credit_limit\":\"1000\",\"amount_due\":\"700\",\"available_balance\":\"3000\",\"company\":\"Samsung\"}";
        EmployeeTable et = new EmployeeTable();
        try {
            et.addEmployeeFromJSON(employee1);
            et.addEmployeeFromJSON(employee2);
            et.addEmployeeFromJSON(employee3);
            et.addEmployeeFromJSON(employee4);
            et.addEmployeeFromJSON(employee5);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CompanyTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
