package servlets;

import database.tables.CivilianTable;
import database.tables.CompanyTable;
import database.tables.EmployeeTable;
import database.tables.MerchantTable;
import mainClasses.Civilian;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentMap;

@WebServlet("/Delete")
public class Delete extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String name=request.getParameter("naming");
        String property=request.getParameter("property");

        if(property.equals("civilian")){
            CivilianTable search = new CivilianTable();
            try {
                search.deleteCivilian(name);
                response.setStatus(200);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }else if(property.equals("merchant")){
            MerchantTable msearch = new MerchantTable();
            try {
                msearch.deleteMerchant(name);
                response.setStatus(200);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }else if(property.equals("company")) {
            CompanyTable csearch = new CompanyTable();
            try {
                csearch.deleteCompany(name);
                response.setStatus(200);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }else{
            EmployeeTable esearch = new EmployeeTable();
            try {
                esearch.deleteEmployee(name);
                response.setStatus(200);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
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
