<%-- 
    Document   : index
    Created on : Nov 30, 2014, 6:31:52 PM
    Author     : NoxCaos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<html>
<body>
       <% if(session.getAttribute("username") == null){ %>
       <div style="height: 10%"></div>
       <div style="text-align: center; font-size: 25px; font-family: Segoe UI; color: #3C3C3B">
           <img src="images/house.png" style="width: 150px; height: 150px;"><br><br>
              Please, sign in or register<br><br>
              <form action="session.jsp" method="POST">
                     <input type="text"  name="username" placeholder="Username" style="font-size: 80%; width: 20%; color: #3C3C3B"></input>
                     <br>
                     <input type="password" name="password" placeholder="Password" style="font-size: 80%; width: 20%; color: #3C3C3B"></input>
                     <br><br>
                     <input type="submit" name="loginButton" value="Log in" style="font-size: 80%; width: 10%; color: #3C3C3B">
                     <input type="submit" name="registerButton" value="Sign up" style="font-size: 80%; width: 10%; color: #3C3C3B">
              </form>
       </div>
       <% } else { %>
              <jsp:forward page="mainpage.jsp"/>
       <%}%>
       <% if(session.getAttribute("warning") != null){%>
       <div style="text-align: center; font-size: 15px; font-family: Segoe UI; color: #FF1645">
           <%= session.getAttribute("warning") %>
       </div>
       <%session.setAttribute("warning", "");}%>
</body>
</html>