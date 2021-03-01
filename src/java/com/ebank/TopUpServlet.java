/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebank;

import static com.ebank.IndexServlet.JDBC_DRIVER;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
//@WebServlet(name = "TopUpServlet", urlPatterns = {"/TopUpServlet"})
public class TopUpServlet extends HttpServlet {

   static final String JDBC_DRIVER = IndexServlet.JDBC_DRIVER;
    static final String DB_URL = IndexServlet.DB_URL;

    //  Database credentials
    static final String USER = IndexServlet.USER;
    static final String PASS = IndexServlet.PASS;
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
          System.out.print("top up page get Index.jsp");
            HttpSession session = request.getSession();
         RequestDispatcher dispatcher = request.getRequestDispatcher("Index.jsp");
          session.setAttribute("navCursor", "TopUp");
          
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
        System.out.print("Top Up Post");
        String amountTopUp = request.getParameter("amountTopUp"); 
        String amountTopUpRemarks = request.getParameter("amountTopUpRemarks"); 
        //amountTopUpRemarks
         
        System.out.print("get amountTopUp:"+amountTopUp);
        HttpSession session = request.getSession();
        User u=(User)session.getAttribute("user");
        RequestDispatcher dispatcher = null;
        if (amountTopUp == null || u == null ) {
            response.sendRedirect(request.getRequestURI());
        }
        String uEmail=u.getEmail();
        
        
        
        try {
            if(dbConnection==null)
                connectDB();
            
            PreparedStatement stmt = dbConnection.prepareStatement("SELECT * FROM user WHERE email=?");
            stmt.setString(1, uEmail); 
 
            ResultSet rs = stmt.executeQuery();
            rs.next();
            
            String balance = rs.getString("balance");
            double bal=Double.parseDouble(balance);
            double topupAmount=Double.parseDouble(amountTopUp);
            bal=bal+topupAmount;
            
            String finalBalance = String.format("%.2f", bal);
             
            PreparedStatement stmt_ = dbConnection.prepareStatement("UPDATE user set balance=? WHERE email=?");
            stmt_.setString(1, finalBalance); 
             stmt_.setString(2, uEmail); 
             
                System.out.print("get finalBalance:"+finalBalance);
             int row = stmt_.executeUpdate();
             
             
             
            
            if (row>0) {
                
                
            PreparedStatement stmt1 = 
dbConnection.prepareStatement("insert into transaction(accountFrom,accountTo,TS,amount,type,remarks) values(?,?,?,?,?,?)");
            stmt1.setString(1, ""); 
            stmt1.setString(2, u.getAccountNo()); 
            String ts_= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            stmt1.setString(3, ts_); 
            stmt1.setString(4, amountTopUp); 
            stmt1.setString(5, "TopUp"); 
            stmt1.setString(6, amountTopUpRemarks); 
             
            System.out.print("get finalBalance:"+finalBalance);
             System.out.print("get finalBalance ts_:"+ts_);
             int row_ = stmt1.executeUpdate();
             
             
             
            
                if (row_>0) { 
                    
            PreparedStatement stmt_a = dbConnection.prepareStatement("SELECT * FROM transaction WHERE accountFrom=? OR accountTo=? order by TS desc");
            stmt_a.setString(1, u.getAccountNo());
            stmt_a.setString(2, u.getAccountNo()); 
            ResultSet rs_ = stmt_a.executeQuery();
            ArrayList<Transaction> listTrx=new ArrayList<Transaction>();
            while(rs_.next()){
                Transaction trx = new Transaction();
                trx.setAccountFrom(rs_.getString("accountFrom"));
                trx.setAccountTo(rs_.getString("accountTo"));
                trx.setAmount(rs_.getString("amount"));
                trx.setTS(rs_.getString("TS"));
                trx.setRemarks(rs_.getString("remarks"));
                trx.setType(rs_.getString("type")); 
                listTrx.add(trx);
            }
                session.setAttribute("listTrx",listTrx);
                    
                    
                    u.setBalance(finalBalance);

                    session.setAttribute("user", u);
                    session.setAttribute("Username",u.getName());
                    session.setAttribute("navCursor", null);


                    response.sendRedirect("/bank/index");
                }else{
                    session.setAttribute("error", "Top up failed");
                    dispatcher = request.getRequestDispatcher("Index.jsp");
                }
                  
            
            } else {
                session.setAttribute("error", "Top up failed");
                dispatcher = request.getRequestDispatcher("Index.jsp");
            }
        } catch (SQLException ex) {
            System.out.print("failed at top up");
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "TopUpServlet";
    }// </editor-fold>

}
