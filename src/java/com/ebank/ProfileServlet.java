/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebank;

import com.ebank.beans.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author User
 */
public class ProfileServlet extends HttpServlet {

      // JDBC driver name and database URL
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

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { 
          HttpSession session = request.getSession();
         RequestDispatcher dispatcher = request.getRequestDispatcher("Index.jsp");
          session.setAttribute("navCursor", "Profile");
        dispatcher.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //
        System.out.print("Profile  Post");
        HttpSession session = request.getSession();
        User u=(User)session.getAttribute("user");
        RequestDispatcher dispatcher = null;
      
        String uEmail=u.getEmail();
        
        String password_update = request.getParameter("password");
        
        if(password_update==null){
        //update detail info
            String email_update = request.getParameter("email"); 
            String name_update = request.getParameter("name"); 
            String phone_update = request.getParameter("phone"); 
            String address_update = request.getParameter("address"); 
            if (email_update == null || name_update == null ) {
                response.sendRedirect(request.getRequestURI());
            }
            
             try {
            if(dbConnection==null)
                connectDB();
             
             
            PreparedStatement stmt_ = dbConnection.prepareStatement("UPDATE user set email=?, name=?,phone=?,address=? WHERE email=?");
            stmt_.setString(1, email_update); 
             stmt_.setString(2, name_update); 
              stmt_.setString(3, phone_update); 
             stmt_.setString(4, address_update); 
             stmt_.setString(5, uEmail); 
             
             int row = stmt_.executeUpdate(); 
            if (row>0) { 
                u.setEmail(email_update);
                  u.setName(name_update);
                    u.setPhone(phone_update);
                      u.setAddress(address_update);
                   
                session.setAttribute("user", u);
                 session.setAttribute("Username",u.getName()); 
                 // session.setAttribute("navCursor", null);
                 
                //response.sendRedirect("/bank/index");
                  session.setAttribute("successDetails", "Update succeeded");
                    session.setAttribute("successPwd", null);
                dispatcher = request.getRequestDispatcher("Index.jsp");
            } else {
                 session.setAttribute("successDetails", null);
            session.setAttribute("successPwd", null);
                session.setAttribute("error", "Save detail failed");
                dispatcher = request.getRequestDispatcher("Index.jsp");
            }
        } catch (SQLException ex) {
            System.out.print("failed at save details");
               session.setAttribute("successDetails", null);
            session.setAttribute("successPwd", null);
            session.setAttribute("error", "Something went wrong");
            dispatcher = request.getRequestDispatcher("Index.jsp");
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        }else{
        //update pwd
        
        
        
            
            String newpasswordInput_update = request.getParameter("newpasswordInput"); 
            String oldpasswordInput_ = request.getParameter("password"); 
              try {
            if(dbConnection==null)
                connectDB();
              
            
            
            PreparedStatement stmt_validate = dbConnection.prepareStatement("SELECT * FROM user WHERE email=? AND password=?");
            stmt_validate.setString(1, uEmail); 
            stmt_validate.setString(2, oldpasswordInput_); 
 
            ResultSet rs_vali = stmt_validate.executeQuery();
            boolean validate=rs_vali.next();
            //chk if the password is correct
            if(validate==false){
                //means the old password is wrong
                session.setAttribute("error", "Update failed, Password is not correct!");
                dispatcher = request.getRequestDispatcher("Index.jsp");
            }else{
                //continue..
             
            PreparedStatement stmt_ = dbConnection.prepareStatement("UPDATE user set password=? WHERE email=?");
            stmt_.setString(1, newpasswordInput_update);  
             stmt_.setString(2, uEmail); 
             
             int row = stmt_.executeUpdate(); 
            if (row>0) { 
                u.setPassword(newpasswordInput_update);  
                   
                session.setAttribute("user", u);
                 session.setAttribute("Username",u.getName()); 
                //  session.setAttribute("navCursor", null);
                 
                //response.sendRedirect("/bank/index");
                session.setAttribute("successDetails", null);
                session.setAttribute("successPwd", "Update succeeded");
                dispatcher = request.getRequestDispatcher("Index.jsp");
            } else {
                session.setAttribute("successDetails", null);
                session.setAttribute("successPwd", null);
                session.setAttribute("error", "Password update failed");
                dispatcher = request.getRequestDispatcher("Index.jsp");
            }
            
          }
        } catch (SQLException ex) {
            System.out.print("failed at update password");
            session.setAttribute("successDetails", null);
            session.setAttribute("successPwd", null);
            session.setAttribute("error", "Something went wrong");
            dispatcher = request.getRequestDispatcher("Index.jsp");
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
         
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
