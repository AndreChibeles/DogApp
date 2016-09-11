<%-- 
    Document   : DogInventario
    Created on : 21/ago/2016, 15:37:30
    Author     : achibel
--%>

<%@ page errorPage = "error.jsp" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>	
<%@ taglib uri = "http://java.sun.com/jsp/jstl/sql" prefix="sql" %>   

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DogApp</title>
    </head>
    <body>
        
        
    <sql:setDataSource                    
      var = "myDS"                                           
      driver = "org.postgresql.Driver"
      url = "jdbc:postgresql://localhost:5432/Dogs"
      user = "fred" 
      password = "secret"
    />

    <sql:query var = "listStuff" dataSource = "${myDS}"> 
      SELECT * FROM dogsstuff ORDER BY id;
    </sql:query>
        
        <center>
            <h3>Os animais do DR. André</h3>
        </center>
    
        <div>
            
            <table class="peopledogs" border="1" cellpading="5">
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                </tr>
                
                <c:forEach var="item" items="${listStuff.rows}">
                    <tr>
                        <td class="rght"><c:out value="${item.id}" /></td>
                        <td class="cent"><c:out value="${item.name}" /></td>
                        <td><a href="${pageContext.request.contextPath}/editDog.jsp?id=${item.id}">Edit</a></td>
                        <td><a href="${pageContext.request.contextPath}/deleteDog.jsp?id=${item.id}">Delete</a></td>
                    </tr>
                    
                </c:forEach>
                
            </table>
            <p><a href="${pageContext.request.contextPath}/createDog.jsp">Create a Dog</a></p>
        </div>
        
      
        
    </body>
</html>
