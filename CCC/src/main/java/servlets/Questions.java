package servlets;

import database.tables.EmployeeTable;
import database.tables.TransactionTable;
import mainClasses.Transaction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@WebServlet("/Questions")
public class Questions extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Posting");
        String name=request.getParameter("naming");
        String property=request.getParameter("property");
        String fromDate=request.getParameter("from");
        String toDate=request.getParameter("to");

        //Get those transactions into an arrayList
        TransactionTable transactionTable=new TransactionTable();
        try {
            try (PrintWriter out = response.getWriter()) {
                ArrayList<Transaction> everyTransaction = transactionTable.ArrayOfTransactions(name, property);

                //0 transactions
                if (everyTransaction.size() == 0) {
                    System.out.println("YOU HAVENT MADE A TRANSACTION YET");
                    out.println("You haven't made a transaction yet!");
                    response.setStatus(404);
                } else {

                    String[] transactions=new String[everyTransaction.size()]; //Array of all the transactions

                    //Date manipulation
                    Date[] dates=new Date[everyTransaction.size()]; //an array of dates
                    SimpleDateFormat thisDateForm=new SimpleDateFormat("yyyy-MM-dd");
                    String thisDate;

                    SimpleDateFormat fdate=new SimpleDateFormat("yyyy-MM-dd"); //format Date for from
                    SimpleDateFormat tdate=new SimpleDateFormat("yyyy-MM-dd"); //format Date for to
                    Date from=fdate.parse(fromDate);
                    Date to=tdate.parse(toDate);


                    for(int i=0; i<everyTransaction.size(); i++){
                        transactions[i]="{";
                        transactions[i]=transactions[i]+"\"customer_name\":\""+everyTransaction.get(i).getCustomerName()+"\", ";
                        transactions[i]=transactions[i]+"\"product_name\":\""+everyTransaction.get(i).getProductName()+"\", ";
                        transactions[i]=transactions[i]+"\"merchant_name\":\""+everyTransaction.get(i).getMerchantName()+"\", ";
                        transactions[i]=transactions[i]+"\"transaction_date\":\""+everyTransaction.get(i).getTransactionDate()+"\", ";
                        transactions[i]=transactions[i]+"\"transaction_amount\":\""+everyTransaction.get(i).getTransactionAmount()+"\", ";
                        transactions[i]=transactions[i]+"\"transaction_type\":\""+everyTransaction.get(i).getTransactionType()+"\"}";

                        //Dates
                        thisDate=everyTransaction.get(i).getTransactionDate();
                        dates[i]=thisDateForm.parse(thisDate);
                    }

                    /*for(int i=0; i<transactions.length; i++){
                        System.out.println(transactions[i]);
                    }*/

                    // Keep the right transactions according to dates
                    String results="[";
                    for(int i=0; i<everyTransaction.size();i++){
                        if(i+1==everyTransaction.size()){
                            if(dates[i].after(from) && dates[i].before(to)){
                                results=results+transactions[i]+"]";
                            }
                        }else{
                            if(dates[i].after(from) && dates[i].before(to)){
                                results=results+transactions[i]+",";
                            }
                        }
                    }

                    if(results.toString().endsWith(",")){
                        results =results.substring(0, results.length() - 1);
                        results=results+"]";
                    }

                    if(results.equals("[")){
                        System.out.println("Can't find transaction on these dates!");
                        out.println("Can't find transaction on these dates!");
                        response.setStatus(404);
                    }else{
                        System.out.println(results);
                        out.println(results);
                        response.setStatus(200);
                    }

                }
            }



        } catch (SQLException | ClassNotFoundException | ParseException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("getting");
        String name=request.getParameter("naming");
        String fromDate=request.getParameter("from");
        String toDate=request.getParameter("to");

        TransactionTable transactionTable=new TransactionTable();
        try {
            try (PrintWriter out = response.getWriter()) {
                ArrayList<Transaction> everyTransaction = transactionTable.ArrayOfEmployees("employee");
                System.out.println(everyTransaction);

                //0 transactions
                if (everyTransaction.size() == 0) {
                    System.out.println("Employees haven't made a transaction yet");
                    out.println("Employees haven't made a transaction yet!");
                    response.setStatus(404);
                } else {
                    //Date manipulation
                    Date[] dates=new Date[everyTransaction.size()]; //an array of dates
                    SimpleDateFormat thisDateForm=new SimpleDateFormat("yyyy-MM-dd");
                    String thisDate;

                    SimpleDateFormat fdate=new SimpleDateFormat("yyyy-MM-dd"); //format Date for from
                    SimpleDateFormat tdate=new SimpleDateFormat("yyyy-MM-dd"); //format Date for to
                    Date from=fdate.parse(fromDate);
                    Date to=tdate.parse(toDate);

                    //Take employees that belongs to the company
                    EmployeeTable employeeTable=new EmployeeTable();
                    int size=0;

                    System.out.println(everyTransaction.size());
                    while(size<everyTransaction.size()){
                        System.out.println(name+"  "+employeeTable.databaseToEmployee(everyTransaction.get(size).getCustomerName()).getCompany());
                        if(!name.equals(employeeTable.databaseToEmployee(everyTransaction.get(size).getCustomerName()).getCompany())) {
                            System.out.println(size);
                            everyTransaction.remove(size);
                            System.out.println("yoloo");
                        }else{
                            size++;
                        }
                    }
                    System.out.println(size);
                    String[] transactions=new String[everyTransaction.size()]; //Array of all the transactions

                    for(int i=0; i<everyTransaction.size(); i++){
                        
                        transactions[i] = "{";
                        transactions[i] = transactions[i] + "\"customer_name\":\"" + everyTransaction.get(i).getCustomerName() + "\", ";
                        transactions[i] = transactions[i] + "\"product_name\":\"" + everyTransaction.get(i).getProductName() + "\", ";
                        transactions[i] = transactions[i] + "\"merchant_name\":\"" + everyTransaction.get(i).getMerchantName() + "\", ";
                        transactions[i] = transactions[i] + "\"transaction_date\":\"" + everyTransaction.get(i).getTransactionDate() + "\", ";
                        transactions[i] = transactions[i] + "\"transaction_amount\":\"" + everyTransaction.get(i).getTransactionAmount() + "\", ";
                        transactions[i] = transactions[i] + "\"transaction_type\":\"" + everyTransaction.get(i).getTransactionType() + "\"}";

                        //Dates
                        thisDate = everyTransaction.get(i).getTransactionDate();
                        dates[i] = thisDateForm.parse(thisDate);
                    }

                    for(int i=0; i<transactions.length; i++){
                        System.out.println(transactions[i]);
                    }

                    // Keep the right transactions according to dates
                    String results="[";
                    for(int i=0; i<everyTransaction.size();i++){
                        if(i+1==everyTransaction.size()){
                            if(dates[i].after(from) && dates[i].before(to)){
                                results=results+transactions[i]+"]";
                            }
                        }else{
                            if(dates[i].after(from) && dates[i].before(to)){
                                results=results+transactions[i]+",";
                            }
                        }
                    }

                    if(results.endsWith(",")){
                        results =results.substring(0, results.length() - 1);
                        results=results+"]";
                    }

                    if(results.equals("[")){
                        System.out.println("Can't find transaction on these dates of your Employees!");
                        out.println("Can't find transaction on these dates of your Employees!");
                        response.setStatus(404);
                    }else{
                        System.out.println(results);
                        out.println(results);
                        response.setStatus(200);
                    }
                }
            }



        } catch (SQLException | ClassNotFoundException | ParseException throwables) {
            throwables.printStackTrace();
        }
    }
}
