<%-- 
    Document   : mainpage
    Created on : Nov 30, 2014, 11:29:35 PM
    Author     : Gleb
--%>

<%@page import="data.JobActions"%>
<%@page import="data.TeamActions"%>
<%@page import="data.RequestActions"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" rel="stylesheet" href="css/style.css" />
        <title>Apartment essentials</title>
    </head>
    <body>
        <%
            String user = (String)session.getAttribute("user");
            if(user == null){
                session.setAttribute("warning", "Session expired. Please log in again");
                %><jsp:forward page="index.jsp"/><%
            }
        %>
        <div class="body-content">
            <div class="header">
                <a href="mainpage.jsp">
                <img class="logo" src="images/logo.png">
                </a>
                <div>
                    <span class="profile">Hello, <%= user%></span><br>
                    <a href="mainpage.jsp?action=jobs" class="button">Jobs</a>
                    <a href="mainpage.jsp?action=requests" class="button">Requests</a>
                    <a href="mainpage.jsp?action=teams" class="button">Teams</a>
                    <%
                        int i = (int)session.getAttribute("admin");
                        if(i == 1){
                   %>
                    <a href="mainpage.jsp?action=newrequest" class="button">New</a>
                    <%}else{%>
                    <a href="mainpage.jsp?action=newrequest" class="button">Submit</a>
                    <%}%>
                    <a href="logout.jsp" class="button">LogOut</a>
                </div>
                <div class="line"></div>
                <div class="body-content-inner">
                    <%
                        String s = request.getParameter("action");
                        if(s == null) s = "NaN";
                        int id = 0;
                        try{  id = Integer.parseInt(request.getParameter("id")); }
                        catch(Exception e){}
                        switch(s){
                            case "newrequest":%>
                                <jsp:forward page="insert.jsp?action=request"/>
                            <%break;
                           case "newteam":
                                if(TeamActions.isBusy(user) && i != 1){
                                    %>You already have a team!<%
                                }else{
                                %><jsp:forward page="insert.jsp?action=team"/>
                            <%}break;
                            case "requests":%>
                                <%if(i==1){%>
                                <%= RequestActions.getAllAsHtmlTable(true)%>
                                <%}else{%>
                                <%= RequestActions.getAllAsHtmlTable(false)%>
                                <%}%>
                            <%break;
                            case "jobs":
                                boolean busy = TeamActions.isBusy(user);
                                if(i==1){%>
                                <%= JobActions.getAllAsHtmlTable(1)%>
                                <%}else if(!busy){%>
                                <%= JobActions.getAllAsHtmlTable(2)%>
                                <%}else{%>
                                <%= JobActions.getAllAsHtmlTable(0)%>
                                <%}%>
                            <%break;
                            case "teams":%>
                                <%if(i==1){%>
                                <%= TeamActions.getAllAsHtmlTable(true)%>
                                <%}else{%>
                                <%= TeamActions.getAllAsHtmlTable(false)%>
                                <%}%>
                                <br><br>
                                <a href="mainpage.jsp?action=newteam"><button>Add new team</button></a>
                            <%break;
                            default: if(i==0){%>
                            <br>
                            <span style="font-size: 30px;">Not sure how to start? Here are some steps to help:</span><br><br>
                            <a href="mainpage.jsp?action=newrequest" class="button" style="font-size: 25px; margin-left: 10px;">1. Submit your request</a><br>
                            <span style="font-size: 25px;">2. Get support for the idea</span><br>
                            <span style="font-size: 25px;">3. Supported idea will be approved in a month</span><br>
                            <span style="font-size: 25px;">4. Figure out a team that brings your idea to life!</span><br>
                            <div class="article">
                                <!--img class="article-image" src="images/upload-256.png"-->
                                <div class="article-text"></div>
                            </div>
                            <%}else{%>
                            <jsp:forward page="mainpage.jsp?action=requests"/>
                           <%} break;
                            case "runjob": 
                            String submit = "accept.jsp?item=job&status=0&id=" + id;
                            %>
                            <br><br>
                            <div class="lBlock">
                                <center>
                                    Select a team for this job:<br>
                                    <form action='<%= submit%>' method='POST'>
                                        <%= TeamActions.getAllAsSelector()%> <br><br>
                                        <input type='submit' name='Submit' value='Submit' style="font-size: 15px; width: 10%; color: #3C3C3B">
                                    </form>
                                    <a href="mainpage.jsp?action=jobs"><button>Close</button></a>
                                </center>
                            </div>
                            <%break;
                            case "takejob":
                                String sb = "accept.jsp?item=job&status=0&id=" + id
                                        + "&team=" + user;
                                pageContext.forward(sb);
                            break; }%>
                </div>
            </div>        
        </div>
    </body>
</html>