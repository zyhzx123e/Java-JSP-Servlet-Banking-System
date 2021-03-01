/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebank;

import static com.ebank.LoginServlet.JDBC_DRIVER;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
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
public class SignupServlet extends HttpServlet {

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
        RequestDispatcher dispatcher = request.getRequestDispatcher("Signup.jsp");
        dispatcher.forward(request, response);
    }
    
    
    String generateAcc(){
         String start = "AC";
        Random value = new Random();

    //Generate two values to append to 'BE'
    int r1 = value.nextInt(10);
    int r2 = value.nextInt(10);
    start += Integer.toString(r1) + Integer.toString(r2) + " ";

    int count = 0;
    int n = 0;
    for(int i =0; i < 12;i++)
    {
        if(count == 4)
        {
            start += " ";
            count =0;
        }
        else 
            n = value.nextInt(10);
            start += Integer.toString(n);
            count++;            

    }
    start=start.replaceAll(" ","");
    System.out.println(start);
      if(dbConnection==null){connectDB();}
      
     

       try{
            Statement  stmt = dbConnection.createStatement();
            String sql;
            sql = "SELECT * FROM user WHERE accountNo='" + start+"'";
            ResultSet rs = stmt.executeQuery(sql);
            boolean val=rs.next();
             if(val==false){
                System.out.println("start["+start+"] is new"); //prints this message if your resultset is empty
                return start;
        
             }else{
                 //not empty
                   String email = rs.getString("email");
                  System.out.println("start["+start+"] is duplicated with user["+email+"]");
                 return generateAcc(); 
             } 
       }catch (SQLException ex) {
            System.out.print("failed login");
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    
    return start;
        
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
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        
         String address = request.getParameter("address");
          String postcode = request.getParameter("postcode");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("password_confirmation");
        System.out.println("sign up now...");
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher = null;
        if (!password.equals(confirmPassword)) {
            session.setAttribute("error", "Passwords should match!");
            dispatcher = request.getRequestDispatcher("Signup.jsp");
            dispatcher.forward(request, response);
            return;
        }

        //Statement stmt;
        try {
            if (dbConnection == null) {
                connectDB();
            }
            
            String acc_=generateAcc();
            //stmt = dbConnection.createStatement();

            //String sql;
            //sql = "INSERT INTO user ('email','password','name','phone') VALUES ('"
            //+ email+"','"+password+"','"+name+"','"+phone+"')";
            PreparedStatement stmt = dbConnection.prepareStatement("INSERT INTO user (email,password,name,phone,address,postcode,accountNo,balance) VALUES (?, ?, ?, ?,?,?,?,?)");
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, name);
            stmt.setString(4, phone);
            stmt.setString(5, address);
            stmt.setString(6, postcode);
            
            stmt.setString(7, acc_);
            stmt.setString(8, "0");
            
            //accountNo
            //balance

            int row = stmt.executeUpdate();
            stmt.close();
            if (row > 0) {
                System.out.println("Signup Successful");
                //response.sendRedirect("profile");
                  session.setAttribute("navCursor", "Profile");
          
                   response.sendRedirect("/bank/index");
            } else {
                session.setAttribute("error", "SQL Error!");
                dispatcher = request.getRequestDispatcher("Signup.jsp");
            }

        } catch (SQLException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            dispatcher = request.getRequestDispatcher("Signup.jsp");
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
