package com.nub.db;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;

public class AllMeal extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
           
            String dbName = request.getParameter("db");
            //String dbName = "ibnsina";
            
            String allMeal = Query.getAllMeal(dbName);
            
            //allMeal.replaceAll(null, "0+0");
            System.out.println("Your meal String "+allMeal);
            out.write(allMeal);
            
//            String meal [] = allMeal.split("@");
//            if(meal[1]!=null){
//               // out.write("<br>"+meal[1]);
//            }
//            out.write("<br>");
//            
//            String daywise [] = meal[1].split("-");
//            
//            for(String today:daywise){
//                //out.write(today+"<br>");
//                String dateAndMeal [] = today.split(",");
//                for(int i=0;i<dateAndMeal.length;i++){
//                    if(i==0){
//                        out.write("date = "+dateAndMeal[i]+" Meal ");
//                    }else{
//                        out.write(dateAndMeal[i]+" ");
//                    }
//                }
//                out.write("<br>");
//            }
            
            
            
        } catch (JSONException ex) {
            System.out.println("meal retrive error");
            ex.printStackTrace();
        }
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
        processRequest(request, response);
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
