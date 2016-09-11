<%-- 
    Document   : editDog
    Created on : 23/ago/2016, 20:08:43
    Author     : achibel
--%>

<%@ page errorPage = "error.jsp" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>	
<%@ taglib uri = "http://java.sun.com/jsp/jstl/sql" prefix = "sql" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <sql:setDataSource 
            
            var="myDS"
            driver="org.postgresql.Driver"
            url="jdbc:postgresql://localhost:5432/Dogs"
            user="fred"
            password="secret"
        
        />
            
            <sql:query var="listStuff" dataSource="${myDS}">
                SELECT * FROM dogsstuff WHERE id=${param.id};
            </sql:query>
                
            <div>
                <fieldset>Edit Dog Record</fieldset>
                <form action="DogDataVerifier" method="post">
                    <table>
                    <tbody> 
                        <tr>
                            <td><label>ID:</label></td>
                            <td><input id="id" name="id"
                                       value="${param.id}"
                                       type="text"
                                       readonly
                            /></td>
                        </tr>
                                                <tr>
                        <td><label>Dog Name</label></td>
                            <td><input id="name" name="name"
                                       value="${listStuff.rows[0].name}"
                                       type="text"
                            /></td>
                        </tr>
                    </tbody>
                    </table>
                    <p><input type = "submit" value = " Save edits "/></p>
                    <p><a href="${pageContext.request.contextPath}/DogInventario.jsp">Back</a></p>
                </form>
            </div>
        
        
        
    </body>
</html>
