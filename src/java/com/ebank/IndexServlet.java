/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebank;

import static com.ebank.LoginServlet.JDBC_DRIVER;
import com.ebank.beans.Transaction;
import com.ebank.beans.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author User
 */
//@WebServlet(name = "IndexServlet", urlPatterns = {"/IndexServlet"})
//@WebServlet(urlPatterns = {"/"})
public class IndexServlet extends HttpServlet {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/world"; 

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "{password}";
    Connection dbConnection = null;
      @Override
    public void init() throws ServletException {
        super.init();
        connectDB();
    }
      void connectDB(){
        try {
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            dbConnection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception ex) {
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
          System.out.print("get Index.jsp");
            HttpSession session = request.getSession();
         RequestDispatcher dispatcher = request.getRequestDispatcher("Index.jsp");
           session.setAttribute("navCursor", null);
        dispatcher.forward(request, response);
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
        //processRequest(request, response);
        System.out.print("In index Post");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
         System.out.print("get email:"+email);
         System.out.print("get password:"+password);
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher = null;
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            response.sendRedirect(request.getRequestURI());
        }
        Statement stmt;
        try {
            
              System.out.print("connecting now");
            if(dbConnection==null)
                connectDB();
            stmt = dbConnection.createStatement();

            String sql;
            sql = "SELECT * FROM user WHERE email='" + email+"'";
            ResultSet rs = stmt.executeQuery(sql);
            boolean val=rs.next();
            
            System.out.print("connect val"+val);
            if(val==false){
                //empty
                session.setAttribute("error", "User not exist");
                dispatcher = request.getRequestDispatcher("Index.jsp");
            }else{
                 String dbPass = rs.getString("password");
                if (dbPass.equals(password)) {
                       System.out.print("success login");
                User bean = new User();
                bean.setEmail(rs.getString("email"));
                bean.setName(rs.getString("name"));
                bean.setPassword(rs.getString("password"));
                bean.setPhone(rs.getString("phone"));
                
                String accountNo=rs.getString("accountNo");
                bean.setAccountNo(accountNo);
                bean.setAddress(rs.getString("address"));
                bean.setPostcode(rs.getString("postcode"));
                bean.setBalance(rs.getString("balance"));
                
                  System.out.print("success address "+rs.getString("address"));
                 
                  stmt = dbConnection.createStatement();
            System.out.print("login account "+accountNo+"");
            String sql_;
            PreparedStatement stmt_ = 
                    dbConnection.prepareStatement("SELECT * FROM transaction WHERE accountFrom=? OR accountTo=? "
                            + "order by TS desc");
            stmt_.setString(1, accountNo);
            stmt_.setString(2, accountNo); 
            ResultSet rs_ = stmt_.executeQuery();
            ArrayList<Transaction> listTrx=new ArrayList<Transaction>();
            while(rs_.next()){
                Transaction trx = new Transaction();
                 System.out.print("login account "+accountNo+" trx next");
                trx.setAccountFrom(rs_.getString("accountFrom"));
                trx.setAccountTo(rs_.getString("accountTo"));
                
                  System.out.print("login account "+rs_.getString("type")+" trx next");
                    System.out.print("login account "+rs_.getString("amount")+" trx next");
                      System.out.print("login account "+rs_.getString("accountFrom")+" trx next");
                      System.out.print("login account "+rs_.getString("accountTo")+" trx next");
                  
                trx.setAmount(rs_.getString("amount"));
                trx.setTS(rs_.getString("TS"));
                trx.setRemarks(rs_.getString("remarks"));
                trx.setType(rs_.getString("type")); 
                listTrx.add(trx);
            }
                session.setAttribute("listTrx",listTrx);
                   
                session.setAttribute("user", bean);
                 session.setAttribute("Username",bean.getName());
               
                 
                response.sendRedirect("/bank/index");
                } else {
                      session.setAttribute("error", "Invalid credential");
                   
                    dispatcher = request.getRequestDispatcher("Index.jsp");
                }
            }
          
        } catch (SQLException ex) {
               System.out.print("failed login");
                dispatcher = request.getRequestDispatcher("Index.jsp");
              
                //error
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (dispatcher != null) {
            dispatcher.forward(request, response);
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
