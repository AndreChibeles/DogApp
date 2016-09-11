/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataV;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;                   // for prices
import java.sql.DriverManager;                 // handles communication with the DB
import java.sql.Connection;                    // a connection to the DB
import java.sql.Statement;
import java.sql.PreparedStatement;             // an SQL statement for the DB to execute
import java.sql.ResultSet;                     // a table of rows generated from an SQL query
import java.sql.SQLException;                  // what's thrown when the JDBC operations fail
import java.util.Properties;                   // key/value pairs
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.http.HTTPException;
import java.io.IOException;

/**
 *
 * @author achibel
 */
@WebServlet(name = "dogDataVerifier", urlPatterns = {"/dogDataVerifier"})
public class dogDataVerifier extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet dogDataVerifier</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet dogDataVerifier at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
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
        Connection conn = getConnection();
	verifyUserInputsAndUpdateDB(conn, request, response);
        try {
	    conn.close();
	}
	catch(SQLException e) { }
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
    
    
    private void verifyUserInputsAndUpdateDB(Connection conn, HttpServletRequest req, HttpServletResponse res) {
        String name = req.getParameter("name");
        
        if (req.getParameter("id") == null) { // create rather than edit
	    if(handleCreate(conn, res, name)){
		sendResponse(req, res, name + " added to the DB.", false);
            }
	    else
		sendResponse(req, res, "Problem saving " + name + " to the DB.", true);
	}
	else { // edit rather than create
	    if (handleEdit(conn, res, req.getParameter("id"), name)){
		sendResponse(req, res, name + " updated successfully.", false);
            }
	    else
		sendResponse(req, res, "Problem updating " + name, true);
	}
        
        
    }
    
    private boolean handleCreate(Connection conn, HttpServletResponse res, String name) {
	boolean flag = false;
	String sql = "INSERT INTO dogsstuff(name) VALUES (?)";

	try {
	    PreparedStatement pstmt = conn.prepareStatement(sql);
	    pstmt.setString(1, name);
	    pstmt.executeUpdate();
	    flag = true;
	}
	catch (SQLException e) { }
	return flag;
    }
    
    private boolean handleEdit(Connection conn, HttpServletResponse res, String idString, String name) {
       	boolean flag = false;
	String sql = "UPDATE dogsstuff SET name = ? where id = ?";
	
	try {
	    int id = Integer.parseInt(idString.trim());
	    PreparedStatement pstmt = conn.prepareStatement(sql);
	    pstmt.setString(1, name);
	    pstmt.setInt(2, id);
	    pstmt.executeUpdate();
	    flag = true;
	}
	catch (NumberFormatException e) { }
	catch (SQLException e) { }
	return flag;
    }
    
    private void sendResponse(HttpServletRequest req, HttpServletResponse res, String msg, boolean error) {
	try {
	    HttpSession session = req.getSession();
	    session.setAttribute("result", msg);
	    if (error)
		res.sendRedirect("badDogResult.jsp");
	    else
		res.sendRedirect("goodDogResult.jsp");
	}
	catch(IOException e) { }
    }
    
    
    private Connection getConnection() {
	String uri = "jdbc:postgresql://localhost/Dogs"; 
	Properties props = setLoginForDB("fred", "secret");
	Connection conn = null;
	try {
	    Class.forName("org.postgresql.Driver"); //*** load the PostreSQL driver
	    conn = DriverManager.getConnection(uri, props);
	}
	catch(ClassNotFoundException e) { }
	catch(SQLException e) { }
	return conn;
    }

    private Properties setLoginForDB(final String uname, final String passwd) {
	Properties props = new Properties();
	props.setProperty("user", uname);
	props.setProperty("password", passwd);
	return props;
    }

}
