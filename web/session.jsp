<%-- 
    Document   : session
    Created on : Nov 30, 2014, 6:38:32 PM
    Author     : Gleb
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="data.User, data.UserActions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<%
    session.setAttribute("warning", "");
    String login = request.getParameter("loginButton");
    String req = request.getParameter("registerButton");
    if(login != null){
        User us = UserActions.getUserFromDB(request.getParameter("username"), request.getParameter("password"));
        if(us == null) {
            session.setAttribute("warning", "Wrong name or/and password"); 
%><jsp:forward page="index.jsp"/><%
        } else {
            session.setAttribute("user", us.getLogin());
            session.setAttribute("admin", us.getIsAdmin());
%><jsp:forward page="mainpage.jsp"/><%
        }
    }
    else if(req != null){
        User user = UserActions.getUserFromDB(request.getParameter("username"), request.getParameter("password"));
        if(user != null) {
            session.setAttribute("warning", "Such name is already used"); 
            %><jsp:forward page="index.jsp"/><%
            return;
        }
        User us = new User(request.getParameter("username"), request.getParameter("password"), 0);
        UserActions.insertInDB(us);
        session.setAttribute("warning", "User was created. Now you can log in");
%><jsp:forward page="index.jsp"/><%
    }
    else {
         session.setAttribute("warning", "Unknown error occured :("); 
%><jsp:forward page="index.jsp"/><%
    } %>
</html>
