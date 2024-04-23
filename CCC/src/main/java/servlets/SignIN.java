package servlets;

import database.tables.CivilianTable;
import database.tables.CompanyTable;
import database.tables.EmployeeTable;
import database.tables.MerchantTable;
import mainClasses.Civilian;
import mainClasses.Company;
import mainClasses.Employee;
import mainClasses.Merchant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/SignIN")
public class SignIN extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");


        String name=request.getParameter("naming");
        String property=request.getParameter("property");

        try (PrintWriter out = response.getWriter()) {
            if(property.equals("civilian")) {
                CivilianTable search = new CivilianTable();
                Civilian uname = search.databaseToCivilian(name);
                if (uname == null) {
                    System.out.println("civilian doesnt exist!");
                    response.setStatus(404);
                } else {
                    //System.out.println("civilian exists!");
                    response.setStatus(200);
                    String json=search.civilianToJSON(uname);
                    out.println(json);
                }
            }else if(property.equals("merchant")) {
                MerchantTable msearch = new MerchantTable();
                Merchant mname = msearch.databaseToMerchant(name);
                if (mname == null) {
                    //System.out.println("merchant doesnt exist!");
                    response.setStatus(404);
                } else {
                    //System.out.println("merchant exists!");
                    response.setStatus(200);
                    String json=msearch.merchantToJSON(mname);
                    out.println(json);
                }
            }else if(property.equals("company")) {
                CompanyTable csearch = new CompanyTable();
                Company cname = csearch.databaseToCompany(name);
                if (cname == null) {
                    //System.out.println("company doesnt exist!");
                    response.setStatus(404);
                } else {
                    //System.out.println("company exists!");
                    response.setStatus(200);
                    String json=csearch.companyToJSON(cname);
                    out.println(json);
                }
            }else{
                EmployeeTable esearch = new EmployeeTable();
                Employee ename = esearch.databaseToEmployee(name);
                if (ename == null) {
                    response.setStatus(404);
                } else {
                    response.setStatus(200);
                    String json=esearch.employeeToJSON(ename);
                    out.println(json);
                }
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
