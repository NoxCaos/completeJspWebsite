package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RequestActions {
    private RequestActions(){
        
    }
    
    public static void insertNew(Request req){
        try{
            final String sqlInsert = "INSERT INTO requests (deadline, comment, worktype, submitter, status, address) "
                    + "VALUES ('"
                    + req.getDeadline()
                    + "', '"
                    + req.getComment()
                    + "', '"
                    + req.getWorktype() //change to statemanet
                    + "', '"
                    + req.getSubmitter()
                    + "', "
                    + req.getStatus()
                    + ", '"
                    + req.getAddress()
                    + "')";
            Connection con = DBC.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sqlInsert);
            int s = ps.executeUpdate();
        }
        catch(SQLException e){
                e.printStackTrace();
        }
    }
    
    public static ArrayList<Request> getAll() throws SQLException{
        final String sqlGet = "SELECT * FROM requests";
        Connection con = DBC.getInstance().getConnection();
        final PreparedStatement ps = con.prepareStatement(sqlGet);
        final ResultSet rs = ps.executeQuery();
        ArrayList<Request> ret = new ArrayList<Request>();
        
        while(rs.next()){
            Request req = new Request();
            req.setId(rs.getInt("id"));
            req.setComment(rs.getString("comment"));
            req.setDeadline(rs.getString("deadline"));
            req.setStatus(rs.getInt("status"));
            req.setWorktype(rs.getString("worktype"));
            req.setAddress(rs.getString("address"));
            req.setSubmitter(rs.getString("submitter"));
            ret.add(req);
        }
        return ret;
    }
    
    public static Request getById(int id) throws SQLException{
        final String sqlGet = "SELECT * FROM requests WHERE id=" + id;
        Connection con = DBC.getInstance().getConnection();
        final PreparedStatement ps = con.prepareStatement(sqlGet);
        final ResultSet rs = ps.executeQuery();
        Request req = new Request();
        
        if(rs.next()){
            req.setId(rs.getInt("id"));
            req.setComment(rs.getString("comment"));
            req.setDeadline(rs.getString("deadline"));
            req.setStatus(rs.getInt("status"));
            req.setWorktype(rs.getString("worktype"));
            req.setAddress(rs.getString("address"));
            req.setSubmitter(rs.getString("submitter"));
        }
        return req;
    }
    
    public static String getAllAsHtmlTable(boolean buttons){
        ArrayList<Request> ret = new ArrayList<>();
        try{
            ret = getAll();
        }
        catch(SQLException e){
            return "No results";
        }
        if(ret.isEmpty()){
            return "No results";
        }
        String html = "Waiting for submittion<br> "
                + "<table style='width:100%'>"; 
                html += "<tr>";
                html += "<td>№</td>";
                html += "<td>Worktype</td>";
                html += "<td>Deadline</td>";
                //html += "<td>Status</td>";
                html += "<td>Address</td>";
                html += "<td>Submitter</td>";
                html += "<td>Comment</td>";
                html += "</tr>";
        for(int i=0; i<ret.size(); i++){
            Request el = ret.get(i);
            html += "<tr>";
            html += "<td>" + (i+1) + ". </td>";
            html += "<td>" + el.getWorktype() + "</td>";
            html += "<td>" + el.getDeadline() + "</td>";
            //html += "<td>" + el.getStatus() + "</td>";
            html += "<td>" + el.getAddress()+ "</td>";
            html += "<td>" + el.getSubmitter() + "</td>";
            html += "<td>" + el.getComment() + "</td>";
            if(buttons){
                html += "<td><a href='accept.jsp?item=request&accept=true&id=" + el.getId()
                        + "'><button style='background-color: #67FF26'>"
                        + "+</button></a></td>";
                html += "<td><a href='accept.jsp?item=request&accept=false&id=" + el.getId()
                        + "'><button style='background-color: #FF2348'>"
                        + "X</button></a></td>";
            }
            html += "</tr>";
        }
        html += "</table>";
        return html;
    }
    
    public static void removeById(int id) throws SQLException{
        final String sqlGet = "DELETE FROM requests WHERE id=" + id;
        Connection con = DBC.getInstance().getConnection();
        final PreparedStatement ps = con.prepareStatement(sqlGet);
         ps.executeUpdate();
    }
}
