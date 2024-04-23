/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import database.tables.CivilianTable;
import database.tables.CompanyTable;
import database.tables.EmployeeTable;
import database.tables.MerchantTable;
import mainClasses.Company;
import mainClasses.Employee;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Payment")
public class Payment extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        Double amount = Double.parseDouble(request.getParameter("costing"));
        String property = request.getParameter("property");
        Double owed = Double.parseDouble(request.getParameter("owed"));
        Double balance = Double.parseDouble(request.getParameter("balance"));
        System.out.println("name " + name + " pay " + amount + " owed " + owed + " balance " + balance + " PROPERTY " + property);

        if(amount <= owed && amount <= balance){
            CivilianTable civt = new CivilianTable();
            CompanyTable comt = new CompanyTable();
            MerchantTable mert = new MerchantTable();
            EmployeeTable emp = new EmployeeTable();

            try {
                if(property.equals("civilian")){
                    civt.updateCivilian(name, owed - amount, balance - amount);
                }else if(property.equals("company")){
                    comt.updateCompany(name, owed - amount, balance - amount);
                }else if(property.equals("merchant")){
                    mert.updateMerchant(name, owed - amount, balance - amount);
                }else{
                    //check if account num exists in Companies
                    EmployeeTable employeeTable=new EmployeeTable();
                    Employee employee = employeeTable.databaseToEmployee(name);

                    CompanyTable csearch = new CompanyTable();
                    Company getComp = csearch.databaseToCompany(employee.getCompany());

                    if (employee.getAccountNumber().equals(getComp.getAccountNumber())) {
                        //UPDATE VALUES OF employee and of company
                        employeeTable.updateEmployeePayment(employee.getAccountNumber(), employee.getAmountDue() - amount, employee.getAvailableBalance() - amount);
                        csearch.updateCompany(getComp.getName(), getComp.getAmountDue() - amount, getComp.getAvailableBalance() - amount);
                    } else {
                        //UPDATE ONLY EMPLOYEE
                        employeeTable.updateEmployee(employee.getName(), employee.getAmountDue() - amount, employee.getAvailableBalance() - amount);
                    }
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Payment.class.getName()).log(Level.SEVERE, null, ex);
            }

            response.setStatus(200);
            response.getWriter().write("Success! Payment completed!");
        }else if(amount > owed){
            response.setStatus(400);
            response.getWriter().write("Error! Payment is greater than Amount owed!");
        }else if(amount > balance){
            response.setStatus(400);
            response.getWriter().write("Error! You dont have that amount of cash in your account!");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}