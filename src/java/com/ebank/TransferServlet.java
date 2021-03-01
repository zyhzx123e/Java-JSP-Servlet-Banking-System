/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebank;

import com.ebank.beans.Transaction;
import com.ebank.beans.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
@WebServlet(name = "TransferServlet", urlPatterns = {"/TransferServlet"})
public class TransferServlet extends HttpServlet {

    
    
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
          System.out.print("Transfer page get Index.jsp");
         
            HttpSession session = request.getSession();
         RequestDispatcher dispatcher = request.getRequestDispatcher("Index.jsp");
          session.setAttribute("navCursor", "Transfer");
          
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
        System.out.print("Transfer Post");
        String amountTransfer = request.getParameter("amountTransfer"); 
        String amountTransferRemarks = request.getParameter("amountTransferRemarks"); 
        String amountTransferAccount = request.getParameter("amountTransferAccount"); 
        //amountTopUpRemarks
         
        System.out.print("get amountTransfer:"+amountTransfer);
        System.out.print("get amountTransferAccount:"+amountTransferAccount);
        
        HttpSession session = request.getSession();
        User u=(User)session.getAttribute("user");
        RequestDispatcher dispatcher = null;
        if (amountTransfer == null || u == null ) {
            response.sendRedirect(request.getRequestURI());
        }
        String uEmail=u.getEmail();
        
        
        
        try {
            if(dbConnection==null)
                connectDB();
            
            
            PreparedStatement stmt_begin = dbConnection.prepareStatement("SELECT * FROM user WHERE accountNo=?");
            stmt_begin.setString(1, amountTransferAccount); 
            
            ResultSet rs_begin = stmt_begin.executeQuery();
            boolean chk=rs_begin.next();
            if(chk==false){
                //means the account not exist
                session.setAttribute("error", "Sorry, Account ["+amountTransferAccount+"] does not exist! Please check again!");
                 
                dispatcher = request.getRequestDispatcher("Index.jsp");
                
            }else{
                //account exist
            
           
            
            PreparedStatement stmt = dbConnection.prepareStatement("SELECT * FROM user WHERE email=?");
            stmt.setString(1, uEmail); 
 
            ResultSet rs = stmt.executeQuery();
            rs.next();
            
            String balance = rs.getString("balance");
            double bal=Double.parseDouble(balance);
            double transferAmount=Double.parseDouble(amountTransfer);
            
            if(transferAmount>bal){
                session.setAttribute("error", "Sorry, you have not enough balance to transfer!");
                 
                dispatcher = request.getRequestDispatcher("Index.jsp");
                //session.setAttribute("navCursor", "Transfer");

                // dispatcher.forward(request, response);
                
            }else{
            
            
           
            
            
            //update my own account balance
            bal=bal-transferAmount;
            
            String finalBalance = String.format("%.2f", bal);
             
            PreparedStatement stmt_ = dbConnection.prepareStatement("UPDATE user set balance=? WHERE email=?");
            stmt_.setString(1, finalBalance); 
            stmt_.setString(2, uEmail); 
             
            System.out.print("get finalBalance:"+finalBalance);
            int row = stmt_.executeUpdate(); 
            
            
            if (row>0) {
                
                
            PreparedStatement stmt1 = 
dbConnection.prepareStatement("insert into transaction(accountFrom,accountTo,TS,amount,type,remarks) values(?,?,?,?,?,?)");
            stmt1.setString(1, u.getAccountNo()); 
            stmt1.setString(2, amountTransferAccount); 
            String ts_= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            stmt1.setString(3, ts_); 
            stmt1.setString(4, amountTransfer); 
            stmt1.setString(5, "Transfer"); 
            stmt1.setString(6, amountTransferRemarks); 
             
            System.out.print("get finalBalance:"+finalBalance);
             System.out.print("get finalBalance ts_:"+ts_);
             int row_ = stmt1.executeUpdate();
             
             
             
            
                if (row_>0) { 
                    

                    //update the transfer to user balance 

                    System.out.print("get finalBalance:"+finalBalance);

                    //get his cur balance
                    PreparedStatement stmt_get = dbConnection.prepareStatement("select * from user WHERE accountNo=? limit 1");
                    stmt_get.setString(1, amountTransferAccount);  

                    ResultSet rs_endChk = stmt_get.executeQuery();
                    rs_endChk.next();
                    String hisBalance=rs_endChk.getString("balance");
                    double hisBal=Double.parseDouble(hisBalance);
                    hisBal=hisBal+transferAmount;//   double transferAmount=Double.parseDouble(amountTransfer);

                    String hisFinalBalance = String.format("%.2f", hisBal);

                    PreparedStatement stmt_end = dbConnection.prepareStatement("UPDATE user set balance=? WHERE accountNo=?");
                    stmt_end.setString(1, hisFinalBalance); 
                    stmt_end.setString(2, amountTransferAccount); 

                    int row_end = stmt_end.executeUpdate(); 

                    if (row_end>0) {

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
                            session.setAttribute("error", "Transfer failed");
                           // response.sendRedirect("/bank/index");
                           dispatcher = request.getRequestDispatcher("Index.jsp");

                    }
                }else{
                    session.setAttribute("error", "Transfer failed");
                   // response.sendRedirect("/bank/index");
                   dispatcher = request.getRequestDispatcher("Index.jsp");
                }
                  
            
            } else {
                session.setAttribute("error", "Transfer failed");
                
                //  response.sendRedirect("/bank/index");
                dispatcher = request.getRequestDispatcher("Index.jsp");
            }
            
           }
            
         }
        } catch (SQLException ex) {
            System.out.print("failed at Transfer");
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
              dispatcher = request.getRequestDispatcher("Index.jsp");
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
