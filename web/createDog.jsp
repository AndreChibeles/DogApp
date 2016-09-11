<%-- 
    Document   : createDog
    Created on : 30/ago/2016, 20:41:05
    Author     : achibel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Dog</title>
    </head>
    <body>
        
            <div>
                <fieldset>Edit Dog Record</fieldset>
                <form action="DogDataVerifier" method="post">
                    <table>
                    <tbody> 
                        <tr>
                        <td><label>Dog Name</label></td>
                            <td><input id="name" name="name"
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
