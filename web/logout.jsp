<%-- 
    Document   : logout
    Created on : Dec 1, 2014, 6:03:55 PM
    Author     : Gleb
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%
        session.setAttribute("user", null);
        session.setAttribute("admin", 0);
        session.setAttribute("warning", "Log Out");
%>
<jsp:forward page="index.jsp"/>
</html>
