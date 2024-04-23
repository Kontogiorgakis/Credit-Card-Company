package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.tables.TransactionTable;

/**
 *
 * @author George
 */
@WebServlet("/TraderOfMonth")
public class TraderOfMonth extends HttpServlet {

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
        try {
            TransactionTable transactionTable = new TransactionTable();
            String bestTrader = transactionTable.traderOfMonth(request.getParameter("date"));
            if (bestTrader != null) {
                try (PrintWriter printWriter = response.getWriter()) {
                    printWriter.write(bestTrader);
                    printWriter.flush();
                    printWriter.close();
                }
                response.setStatus(200);
            } else {
                response.setStatus(404);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(TraderOfMonth.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
