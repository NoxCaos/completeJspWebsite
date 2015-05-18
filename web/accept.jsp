<%-- 
    Document   : accept
    Created on : Dec 5, 2014, 11:50:25 AM
    Author     : Gleb
--%>

<%@page import="data.TeamActions"%>
<%@page import="data.Job"%>
<%@page import="data.JobActions"%>
<%@page import="data.Request"%>
<%@page import="data.RequestActions"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <% 
        int id = Integer.parseInt(request.getParameter("id"));
        if(request.getParameter("item").equals("job")){
            int status = Integer.parseInt(request.getParameter("status"));
            String team = request.getParameter("team");
            switch(status){
                case 0: JobActions.changeStatus(1, id, team); TeamActions.setStatus(1, team); break;
                case 1: JobActions.changeStatus(2, id); TeamActions.setStatus(0, team); break;
                case 2: JobActions.removeById(id); break;
            }
            %><jsp:forward page="mainpage.jsp?action=jobs"/><%
        }
        else if(request.getParameter("item").equals("request")){
            if(request.getParameter("accept").equals("true")){
                Request req = RequestActions.getById(id);
                JobActions.insertRequest(req);
            }else if(request.getParameter("accept").equals("false")){
                RequestActions.removeById(id);
            }
            %><jsp:forward page="mainpage.jsp?action=requests"/><%
        } 
        else if(request.getParameter("item").equals("team")){
            if(!request.getParameter("accept").equals("true")){
                TeamActions.removeById(id);
            }
            %><jsp:forward page="mainpage.jsp?action=teams"/><%
        }%>
</html>
