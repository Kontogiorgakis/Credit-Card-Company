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

@WebServlet("/Return")
public class Return extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String item = request.getParameter("item");
        String merchant = request.getParameter("merchant");
        String name = request.getParameter("name");
        String property = request.getParameter("property");

        TransactionTable transactionTable = new TransactionTable();
        try {
            try (PrintWriter out = response.getWriter()) {
                MerchantTable msearch=new MerchantTable();
                Merchant mer = msearch.databaseToMerchant(merchant);
                //search if merchant exists
                if (mer == null) {
                    //not exists
                    System.out.println("merchant doesn't exist!");
                    out.println("merchant doesn't exist!");
                    response.setStatus(404);
                }else {
                    //check if transaction exists
                    Transaction transaction = transactionTable.databaseToTransaction(item, name, property, merchant);
                    if (transaction == null) {
                        System.out.println("Transaction doesn't exist!");
                        out.println("Transaction doesn't exist!");
                        response.setStatus(404);
                    } else {
                        if(transaction.getTransactionType().equals("charge")){
                            System.out.println("Transaction isn't credit!");
                            out.println("Transaction isn't credit!");
                            response.setStatus(404);
                        }else{
                            //update return value to 1 so not go again there
                            int id = transaction.getTransactionId();
                            transactionTable.updateReturned(id, 1);

                            double cost = transaction.getTransactionAmount();
                            /*change values*/

                            //merchant first
                            msearch.updateMerchant(merchant,mer.getAmountDue()+cost,mer.getTotalProfit()-cost);
                            /*not sure*/
                            //mer.setAmountDue(mer.getAmountDue()+cost);

                            //customer after
                            if(property.equals("civilian")){
                                CivilianTable civilianTable = new CivilianTable();
                                Civilian civilian = civilianTable.databaseToCivilian(name);
                                civilianTable.updateCivilianCredit(name, civilian.getCreditLimit() + cost);
                                //System.out.println(civilian.getAvailableBalance()+cost); 

                            }else if(property.equals("company")){
                                CompanyTable companyTable=new CompanyTable();
                                Company company=companyTable.databaseToCompany(name);
                                companyTable.updateCompany(name,company.getAvailableBalance()+cost);

                            }else{
                                //check if account num exists in Companies
                                EmployeeTable employeeTable=new EmployeeTable();
                                Employee employee = employeeTable.databaseToEmployee(name);

                                CompanyTable csearch = new CompanyTable();
                                Company getComp = csearch.databaseToCompany(employee.getCompany());

                                if (employee.getAccountNumber().equals(getComp.getAccountNumber())) {
                                    //UPDATE VALUES OF employee and of company
                                    employeeTable.updateFromAccountNumCredit(employee.getAccountNumber(),employee.getAmountDue(),(int)(employee.getCreditLimit()+cost));
                                    csearch.updateCompanyCredit(getComp.getName(),employee.getAmountDue(),getComp.getCreditLimit() + cost);
                                } else {
                                    //UPDATE ONLY EMPLOYEE
                                    employeeTable.updateEmployeeCredit(employee.getName(),employee.getCreditLimit()+ cost);
                                }
                            }

                            out.println("#Return was successful");
                            response.setStatus(200);
                        }
                    }
                }
            }


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
