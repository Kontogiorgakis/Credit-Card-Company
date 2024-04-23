package servlets;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mainClasses.Civilian;

import database.tables.CivilianTable;
import database.tables.CompanyTable;
import database.tables.EmployeeTable;
import database.tables.MerchantTable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainClasses.Company;
import mainClasses.Employee;
import mainClasses.Merchant;

/**
 *
 * @author George
 */
@WebServlet("/UsersStatus")
public class UsersStatus extends HttpServlet {

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

        System.out.println("YOO");
        try {
            //Valid ArrayLists counter
            int validTables = 0;
            //(State1 - Good Users) or (state2 - Bad Users)
            String executeOp = request.getParameter("operation");
            String jsonArray[];
            ArrayList<String> jsonList = new ArrayList<>();

            //Database Tables
            CivilianTable civilianTable = new CivilianTable();
            MerchantTable merchantTable = new MerchantTable();
            CompanyTable companyTable = new CompanyTable();
            EmployeeTable employeeTable = new EmployeeTable();

            //ArrayLists of Objects
            ArrayList<Civilian> allCivilians = civilianTable.ArrayOfCivilians(executeOp);
            ArrayList<Merchant> allMerchants = merchantTable.ArrayOfMerchants(executeOp);
            ArrayList<Company> allCompanies = companyTable.ArrayOfCompanies(executeOp);
            ArrayList<Employee> allEmployees = employeeTable.ArrayOfEmployees(executeOp);

            try (PrintWriter printWriter = response.getWriter()) {
                /*We check the arrayLists one by one to see if they contain objects.
                If so, increase the counter indicating the number of valid arrayLists
                by one and then convert arrayLists of objects to JSON.*/

                if (!allCivilians.isEmpty()) {
                    validTables++;
                    for (int i = 0; i < allCivilians.size(); i++) {
                        Gson gson = new Gson();
                        jsonList.add(gson.toJson(allCivilians.get(i)));
                    }
                }
                if (!allMerchants.isEmpty()) {
                    validTables++;
                    for (int i = 0; i < allMerchants.size(); i++) {
                        Gson gson = new Gson();
                        jsonList.add(gson.toJson(allMerchants.get(i)));
                    }
                }
                if (!allCompanies.isEmpty()) {
                    validTables++;
                    for (int i = 0; i < allCompanies.size(); i++) {
                        Gson gson = new Gson();
                        jsonList.add(gson.toJson(allCompanies.get(i)));
                    }
                }
                if (!allEmployees.isEmpty()) {
                    validTables++;
                    for (int i = 0; i < allEmployees.size(); i++) {
                        Gson gson = new Gson();
                        jsonList.add(gson.toJson(allEmployees.get(i)));
                    }
                }

                System.out.println(allCivilians);

                //Array of strigns with size of jsonList
                jsonArray = new String[jsonList.size()];

                //Assigned each element into String Array
                for (int i = 0; i < jsonList.size(); i++) {
                    jsonArray[i] = jsonList.get(i);
                }

                //Printing using for each loop
                for (String k : jsonArray) {
                    System.out.println("jsonArray[]" + k);
                }
                System.out.println("Servlet JSON is : " + Arrays.toString(jsonArray));

                printWriter.write(Arrays.toString(jsonArray));
                printWriter.flush();
                printWriter.close();
            }
            if (validTables > 0) {
                response.setStatus(200);
            } else {
                response.setStatus(404);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UsersStatus.class.getName()).log(Level.SEVERE, null, ex);
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
    } //</editor-fold>

}
