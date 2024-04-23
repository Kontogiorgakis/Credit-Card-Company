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
import mainClasses.Civilian;
import mainClasses.Company;
import mainClasses.Employee;
import mainClasses.Merchant;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Servlet")
public class SignUP extends HttpServlet {

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
            throws ServletException, IOException, SQLException, ClassNotFoundException {
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

        response.setContentType("text/html;charset=UTF-8");


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
        response.setContentType("text/html;charset=UTF-8");

        /*search if civilian already exists using its name*/
        String name=request.getParameter("name");
        String number=request.getParameter("account_number");
        String year=request.getParameter("year");
        String month=request.getParameter("month");
        String property=request.getParameter("property");
        String ceo=request.getParameter("ceo");
        String employee=request.getParameter("employee");

        System.out.println(name);

        /*company tables*/
        CivilianTable search=new CivilianTable();
        MerchantTable msearch=new MerchantTable();
        CompanyTable csearch=new CompanyTable();
        EmployeeTable esearch=new EmployeeTable();

        try (PrintWriter out = response.getWriter()) {
            if(property.equals("civilian")) {
                Civilian uname = search.databaseToCivilian(name);
                if (uname == null) {
                    System.out.println("user doesnt exist!");
                    Civilian civilian = new Civilian();
                    civilian.setName(name);
                    civilian.setAccountNumber(number);
                    civilian.setExpDate(expDate(year,month));
                    civilian.setCreditLimit(1000);
                    civilian.setAmountDue(700);
                    civilian.setAvailableBalance(3000);

                    search.addNewCivilian(civilian);
                    System.out.println("200");
                    response.setStatus(200);
                } else {
                    System.out.println("404");
                    response.setStatus(404);
                }
            }else if(property.equals("merchant")) {
                Merchant mname=msearch.databaseToMerchant(name);
                if (mname == null) {
                    System.out.println("user doesnt exist!");
                    Merchant merchant = new Merchant();
                    merchant.setName(name);
                    merchant.setAccountNumber(number);
                    merchant.setAmountDue(500);
                    merchant.setSupply(200);
                    merchant.setTotalProfit(0);

                    msearch.addNewMerchant(merchant);
                    System.out.println("200");
                    response.setStatus(200);
                } else {
                    System.out.println("404");
                    response.setStatus(404);
                }
            }else if(property.equals("company")) {
                Company cname=csearch.databaseToCompany(name);
                if(cname == null){
                    System.out.println("company doest exist!");
                    Company company=new Company();

                    company.setName(name);
                    company.setAccountNumber(number);
                    company.setExpDate(expDate(year,month));
                    company.setCreditLimit(10000);
                    company.setAmountDue(7000);
                    company.setAvailableBalance(30000);
                    company.setEmployeeName(ceo);
                    company.setEmployeeIdentity("1");

                    csearch.addNewCompany(company);
                    System.out.println("200");
                    response.setStatus(200);
                } else {
                    System.out.println("404");
                    response.setStatus(404);
                }
        }else{
                //HERE WE CHECK ACCOUNT NUMBER
                System.out.println(number);
                Company nameComp=csearch.databaseToCompany(name);
                Company accNum=csearch.accountNumberSearch(number);
                //System.out.println("This is name=   "+accNum.getName());

                if(nameComp==null){
                    System.out.println("Company doesn't exist so Employee cant sign UP!");
                    out.println("Company doesn't exist so Employee cant sign UP!");
                    response.setStatus(600);
                }else{
                    Employee ename = esearch.databaseToEmployee(employee);
                    if (ename == null) {
                        System.out.println("employee doest exist!");
                        Employee employee1 = new Employee();

                        employee1.setName(employee);
                        employee1.setAccountNumber(number);
                        employee1.setExpDate(expDate(year, month));
                        employee1.setCompany(name);

                        if (accNum == null) {
                            System.out.println("yoo");
                            employee1.setCreditLimit(1000);
                            employee1.setAmountDue(700);
                            employee1.setAvailableBalance(3000);
                            esearch.addNewEmployee(employee1);
                            response.setStatus(200);
                        } else {
                            System.out.println("This is name=   " + accNum.getName());
                            System.out.println(name + "   " + accNum.getName());
                            if (name.equals(accNum.getName())) {
                                employee1.setCreditLimit(accNum.getCreditLimit());
                                employee1.setAmountDue(accNum.getAmountDue());
                                employee1.setAvailableBalance(accNum.getAvailableBalance());
                                esearch.addNewEmployee(employee1);
                                response.setStatus(200);
                            } else {
                                System.out.println("Company name doesn't correspond with the right company of this account number! Try again.");
                                out.println("Company name doesn't correspond with the right company of this account number! Try again.");
                                response.setStatus(500);
                            }
                        }
                    } else {
                        System.out.println("404");
                        response.setStatus(404);
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    /*expiration date*/
    protected String expDate(String year, String month){

        int value;

        if(month.equals("january")){
            value=1;
        }else if(month.equals("february")){
            value=2;
        }else if(month.equals("march")){
            value=3;
        }else if(month.equals("april")){
            value=4;
        }else if(month.equals("may")){
            value=5;
        }else if(month.equals("june")){
            value=6;
        }else if(month.equals("july")){
            value=7;
        }else if(month.equals("august")){
            value=8;
        }else if(month.equals("september")){
            value=9;
        }else if(month.equals("october")){
            value=10;
        }else if(month.equals("november")) {
            value=11;
        }else{
            value=12;
        }


        String date=year+"-"+value+"-"+value;
        return date;
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
