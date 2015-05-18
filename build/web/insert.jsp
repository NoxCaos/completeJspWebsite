<%-- 
    Document   : mainpage
    Created on : Nov 30, 2014, 11:29:35 PM
    Author     : Gleb
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="data.*" %>
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
                    <% int i = (int)session.getAttribute("admin");
                        if(i == 1){ %>
                    <a href="mainpage.jsp?action=newrequest" class="button">New</a>
                    <%}else{%>
                    <a href="mainpage.jsp?action=newrequest" class="button">Submit</a>
                    <%}%>
                    <a href="logout.jsp" class="button">LogOut</a>
                </div>
                <div class="line"></div>
                <!--div class="text" style="font-size: 40px"> Submitting a new item </div-->
                <div class="body-content-inner">
                    <%
                        String s = request.getParameter("action");
                        if(s == null) s = "NaN";
                        boolean success = false;
                        if(request.getParameter("makeinsert") != null){
                            if(s.equals("request")){
                                try{
                                    Request req = new Request();
                                    req.setComment(request.getParameter("comment"));
                                    req.setDeadline(request.getParameter("deadline"));
                                    req.setStatus(0);
                                    req.setSubmitter(user);
                                    req.setWorktype(request.getParameter("worktype"));
                                    req.setAddress(request.getParameter("address"));
                                    RequestActions.insertNew(req);
                                    success = true;
                                }
                                catch(Exception e){
                                    success = false;                                    
                                }
                            }
                            else if(s.equals("team")){
                                Team tm = new Team();
                                try{
                                    int pers = Integer.parseInt(request.getParameter("persons"));
                                    tm.setPersons(pers);
                                    if(i==1) tm.setLeadername(request.getParameter("leadername"));
                                    else tm.setLeadername(user);
                                    TeamActions.insertNew(tm);
                                    success = true;
                                }
                                catch(Exception e){
                                    success = false;                                    
                                }  
                            }
                            if(success){ %> New data was successfully added!<%
                            }
                            else{ %> NO data was added! Something went wrong<%
                            }
                        }
                        else{
                            if(s.equals("request")){
                                %>
                                Submitting new request<br><br>
                                <form action="insert.jsp?action=request&makeinsert=true" method="POST">
                                    <input type="text"  name="deadline" placeholder="Deadline" style="font-size: 80%; width: 40%; color: #3C3C3B"></input>
                                    <br><br>
                                    <input type="text"  name="worktype" placeholder="Worktype" style="font-size: 80%; width: 40%; color: #3C3C3B"></input>
                                    <br><br>
                                    <input type="text"  name="address" placeholder="Address" style="font-size: 80%; width: 40%; color: #3C3C3B"></input>
                                    <!--input type="hidden"  name="submitter" value="<%=user%>"-->
                                    <br><br>
                                    <textarea name="comment"  placeholder="Comment" cols="100" rows="8" style="font-size: 70%; width: 100%; color: #3C3C3B"></textarea> <br>
                                    <input type="submit" name="submit" value="Submit" style="font-size: 80%; width: 10%; color: #3C3C3B">
                                </form>
                                <%
                            }
                            else if(s.equals("team")){
                                %>
                                Submitting new team<br><br>
                                <form action="insert.jsp?action=team&makeinsert=true" method="POST">
                                    <% if(i==1){ %>
                                    <input type="text"  name="leadername" placeholder="Team leader" style="font-size: 80%; width: 40%; color: #3C3C3B"></input>
                                    <br><br>
                                    <%}%>
                                    <input type="text"  name="persons" placeholder="How much members?" style="font-size: 80%; width: 40%; color: #3C3C3B"></input>
                                    <br><br>
                                    <input type="submit" name="submit" value="Submit" style="font-size: 80%; width: 10%; color: #3C3C3B">
                                </form>
                                <%
                            }
                            else{
                                %><jsp:forward page="mainpage.jsp"/><%
                            }
                        }
                   %>
                </div>
            </div>        
        </div>
    </body>
</html>
