package com.nub.db;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Addmeal extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
                    
            
                    String dbName = request.getParameter("db");
                    String user = request.getParameter("name");
                    String daymeal = request.getParameter("day");
                    String nightmeal = request.getParameter("night");
                    
                    System.out.println(dbName+"\n"+user+"\n"+daymeal+"\n"+nightmeal);
                    
                    Query.addMeal(dbName,user,daymeal,nightmeal);
                    //Query.addMeal("xxx","sakib","1","1");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
