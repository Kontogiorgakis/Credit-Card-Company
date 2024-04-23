package servlets;

import database.tables.*;
import mainClasses.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/Purchase")
public class Purchase extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String item=request.getParameter("item");
        String type=request.getParameter("transaction_type");
        String cost=request.getParameter("cost");
        String date=request.getParameter("date");
        String merchant=request.getParameter("merchant");
        String name=request.getParameter("name");
        String property=request.getParameter("property");

        MerchantTable msearch=new MerchantTable();
        try (PrintWriter out = response.getWriter()) {
            try {
                Merchant mer = msearch.databaseToMerchant(merchant);
                //search if merchant exists
                if (mer == null) {
                    //not exists
                    System.out.println("merchant doesn't exist!");
                    out.println("merchant doesn't exist!");
                    response.setStatus(404);
                }else{
                    //exists
                    Transaction transaction=new Transaction();
                    transaction.setProductName(item);
                    transaction.setCustomerName(name);
                    transaction.setCustomerProperty(property);
                    transaction.setMerchantName(merchant);
                    transaction.setTransactionDate(date);
                    transaction.setTransactionAmount(Double.parseDouble(cost));
                    transaction.setTransactionType(type);
                    transaction.setReturned(0);

                    if (property.equals("civilian")) {
                        CivilianTable civilian=new CivilianTable();
                        double parsedCost=Double.parseDouble(cost);

                        /*if type is charge*/
                        if(type.equals("charge")){
                            double balance=civilian.databaseToCivilian(name).getAvailableBalance();

                            /*update all*/
                            TransactionTable tupdate=new TransactionTable();
                            tupdate.addNewTransaction(transaction);

                            //update merchant profit
                            double newCost=mer.getTotalProfit()+parsedCost;
                            msearch.updateMerchant(merchant,newCost);

                            //update charge values of civilian
                            double owed=civilian.databaseToCivilian(name).getAmountDue()+parsedCost*10/100;
                            System.out.println("owed extra "+owed);
                            if(balance<0){
                                balance=0;
                            }
                            civilian.updateCivilian(name,owed,balance-parsedCost);
                            out.println("#Transaction was successful!");
                            response.setStatus(200);

                        }else{ /*if type is credit*/
                            System.out.println("CREDIT");
                            int creditLimit=civilian.databaseToCivilian(name).getCreditLimit();

                            /*update all*/
                            TransactionTable tupdate=new TransactionTable();
                            tupdate.addNewTransaction(transaction);

                            //update merchant profit
                            double newCost=mer.getTotalProfit()+Double.parseDouble(cost);
                            msearch.updateMerchant(merchant,newCost);

                            //update charge values of civilian
                            double owed=civilian.databaseToCivilian(name).getAmountDue()+parsedCost*10/100;
                            System.out.println("owed extra "+owed);
                            if(creditLimit<0){
                                creditLimit=0;
                            }
                            civilian.updateCivilianCredit(name,owed,creditLimit-parsedCost);
                            out.println("#Transaction was successful!");
                            response.setStatus(200);
                        }

                    } else if (property.equals("company")) {
                        CompanyTable company=new CompanyTable();
                        double balance=company.databaseToCompany(name).getAvailableBalance();

                        if(balance-Double.parseDouble(cost)<0){
                            //.out.println("Balance Insufficient!");
                            out.println("Balance Insufficient!");
                            response.setStatus(404);
                        }else{
                            /*update all*/
                            TransactionTable tupdate=new TransactionTable();
                            tupdate.addNewTransaction(transaction);

                            //update merchant profit
                            double newCost=mer.getTotalProfit()+Double.parseDouble(cost);
                            msearch.updateMerchant(merchant,newCost);

                            if(type.equals("charge")){
                                out.println(balance-Double.parseDouble(cost));
                            }else{
                                out.println(balance);
                            }
                            response.setStatus(200);
                        }

                    }else{
                        EmployeeTable employeeTable1=new EmployeeTable();
                        double parsedCost=Double.parseDouble(cost);

                        //if type is charge
                        if(type.equals("charge")){
                            /*update all*/
                            TransactionTable tupdate=new TransactionTable();
                            tupdate.addNewTransaction(transaction);

                            //update merchant profit
                            double newCost=mer.getTotalProfit()+parsedCost;
                            msearch.updateMerchant(merchant,newCost);

                            //check if employee uses same account number as company or not
                            EmployeeTable employeeTable=new EmployeeTable();

                            Employee employee = employeeTable.databaseToEmployee(name);

                            CompanyTable csearch = new CompanyTable();
                            Company getComp = csearch.databaseToCompany(employee.getCompany());
                            double owed=employee.getAmountDue()+parsedCost*10/100;

                            if (employee.getAccountNumber().equals(getComp.getAccountNumber())) {
                                //UPDATE VALUES OF employee and of company BALANCE
                                employeeTable.updateFromAccountNum(employee.getAccountNumber(), owed ,  employee.getAvailableBalance() - parsedCost);
                                csearch.updateCompany(getComp.getName(),owed, getComp.getAvailableBalance() - parsedCost);
                            } else {
                                //UPDATE ONLY EMPLOYEE BALANCE
                                employeeTable.updateEmployee(employee.getName(),owed, employee.getAvailableBalance() - parsedCost);
                            }
                            out.println("#Transaction was successful!");
                            response.setStatus(200);
                        }else{
                            /*update all*/
                            TransactionTable tupdate=new TransactionTable();
                            tupdate.addNewTransaction(transaction);

                            //update merchant profit
                            double newCost=mer.getTotalProfit()+parsedCost;
                            msearch.updateMerchant(merchant,newCost);

                            //check if employee uses same account number as company or not
                            EmployeeTable employeeTable=new EmployeeTable();

                            Employee employee = employeeTable.databaseToEmployee(name);

                            CompanyTable csearch = new CompanyTable();
                            Company getComp = csearch.databaseToCompany(employee.getCompany());
                            double owed=employee.getAmountDue()+parsedCost*10/100;

                            if (employee.getAccountNumber().equals(getComp.getAccountNumber())) {
                                //UPDATE VALUES OF employee and of company BALANCE
                                employeeTable.updateFromAccountNumCredit(employee.getAccountNumber(),owed, (int)(employee.getCreditLimit() - parsedCost));
                                csearch.updateCompanyCredit(getComp.getName(),owed, getComp.getCreditLimit() - parsedCost);
                            } else {
                                //UPDATE ONLY EMPLOYEE BALANCE
                                employeeTable.updateEmployeeCredit(employee.getName(), owed, employee.getCreditLimit() - parsedCost);
                            }

                            out.println("#Transaction was successful!");
                            response.setStatus(200);
                        }
                    }
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
