package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JobActions {
        
    private JobActions(){}
    
    public static void insertNew(Job req){
        try{
            final String sqlInsert = "INSERT INTO workplan (deadline, comment, worktype, submitter, status, address, team) "
                    + "VALUES ('"
                    + req.getDeadline()
                    + "', '"
                    + req.getComment()
                    + "', '"
                    + req.getWorktype()
                    + "', '"
                    + req.getSubmitter()
                    + "', "
                    + req.getStatus()
                    + ", '"
                    + req.getAddress()
                    + "', '"
                    + req.getTeam()
                    + "')";
            Connection con = DBC.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sqlInsert);
            ps.executeUpdate();
        }
        catch(SQLException e){
                e.printStackTrace();
        }
    }
    
    public static ArrayList<Job> getAll() throws SQLException{
        final String sqlGet = "SELECT * FROM workplan";
        Connection con = DBC.getInstance().getConnection();
        final PreparedStatement ps = con.prepareStatement(sqlGet);
        final ResultSet rs = ps.executeQuery();
        ArrayList<Job> ret = new ArrayList<Job>();
        
        while(rs.next()){
            Job req = new Job();
            req.setId(rs.getInt("id"));
            req.setComment(rs.getString("comment"));
            req.setDeadline(rs.getString("deadline"));
            req.setStatus(rs.getInt("status"));
            req.setWorktype(rs.getString("worktype"));
            req.setAddress(rs.getString("address"));
            req.setSubmitter(rs.getString("submitter"));
            req.setTeam(rs.getString("team"));
            ret.add(req);
        }
        return ret;
    }
    
    public static String getAllAsHtmlTable(int buttons){
        ArrayList<Job> ret = new ArrayList<>();
        try{
            ret = getAll();
        }
        catch(SQLException e){
            return "No results";
        }
        if(ret.isEmpty()){
            return "No results";
        }
        String html = "";
        html += formatTable("Waiting for submittion", 0, ret, buttons);
        html += formatTable("In progress", 1, ret, buttons);
        html += formatTable("Completed", 2, ret, buttons);
        return html;
    }
    
    private static String formatTable(String name, int status, ArrayList<Job> ret, int buttons){
        String html = "<table style='width:100%'>"; 
                html += name;
                html += "<br> <tr>";
                html += "<td>№</td>";
                html += "<td>Worktype</td>";
                html += "<td>Deadline</td>";
                html += "<td>Address</td>";
                html += "<td>Team</td>";
                html += "<td>Submitter</td>";
                html += "<td>Comment</td>";
                html += "</tr>";
                int counter = 1;
        for(int i=0; i<ret.size(); i++){
            Job el = ret.get(i);
            if(el.getStatus() != status) continue;
            html += "<tr>";
            html += "<td>" + counter + ". </td>";
            html += "<td>" + el.getWorktype() + "</td>";
            html += "<td>" + el.getDeadline() + "</td>";
            html += "<td>" + el.getAddress()+ "</td>";
            html += "<td>" + el.getTeam()+ "</td>";
            html += "<td>" + el.getSubmitter() + "</td>";
            html += "<td>" + el.getComment() + "</td>";
            counter++;
            String text = "", color = "";
            if(buttons == 1){
                if(status == 0){
                    html += "<td><a href='mainpage.jsp?action=runjob"
                                + "&id=" + el.getId()
                                + "'><button style='background-color: #54A1FF" 
                                +"'>Run</button></a></td>";
                }
                else{
                    switch(status){
                        case 1: text = "Complete"; color = "#44FF7F"; break;
                        case 2: text = "Remove"; color = "#FF2348"; break;
                    }
                    html += "<td><a href='accept.jsp?item=job&status=" + el.getStatus()
                                + "&id=" + el.getId()
                                + "&team=" + el.getTeam()
                                + "'><button style='background-color: "+ color 
                                +"'>" + text
                                + "</button></a></td>";
                }
            }
            else if(buttons == 2 && status == 0){
                html += "<td><a href='mainpage.jsp?action=takejob"
                                + "&id=" + el.getId()
                                + "'><button style='background-color: #54A1FF" 
                                +"'>Take</button></a></td>";
            }
            else if(buttons == 0 && status == 0){
                html += "<td><button disabled"
                        +">Take</button></td>";
            }
            html += "</tr>";
        }
        html += "</table><br>";
        return html;
    }
    
    public static void insertRequest(Request req){
        try{
            String sqlInsert = "INSERT INTO workplan (deadline, comment, worktype, submitter, status, address, team) "
                        + "VALUES ('"
                        + req.getDeadline()
                        + "', '"
                        + req.getComment()
                        + "', '"
                        + req.getWorktype()
                        + "', '"
                        + req.getSubmitter()
                        + "', 0, '"
                        + req.getAddress()
                        + "', '"
                        + "Not set"
                        + "')";
            Connection con = DBC.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sqlInsert);
            ps.executeUpdate();
            sqlInsert = "DELETE FROM requests WHERE id=" + req.getId();
            ps = con.prepareStatement(sqlInsert);
            ps.executeUpdate();
        }
        catch (SQLException e){}
    }
    
    public static void changeStatus(int status, int id, String newTeam){
        try{
            String sqlGet = "";
            if(newTeam.equals("")){
                sqlGet = "UPDATE workplan SET status=" 
                        + status 
                        + " WHERE id=" + id;
            }
            else{
                sqlGet = "UPDATE workplan SET status=" 
                        + status 
                        + ", team='" + newTeam 
                        + "' WHERE id=" + id;
            }
            Connection con = DBC.getInstance().getConnection();
            final PreparedStatement ps = con.prepareStatement(sqlGet);
            ps.executeUpdate();
        }
        catch (SQLException e){ }
    }
    
    public static void changeStatus(int status, int id){
        changeStatus(status, id, "");
    }
    
    public static void removeById(int id) throws SQLException{
        final String sqlGet = "DELETE FROM workplan WHERE id=" + id;
        Connection con = DBC.getInstance().getConnection();
        final PreparedStatement ps = con.prepareStatement(sqlGet);
         ps.executeUpdate();
    }
}
