package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeamActions {
    private TeamActions(){ }
    
    public static void insertNew(Team tm){
        try{
            final String sqlInsert = "INSERT INTO teams (persons, leadername, status) "
                    + "VALUES ("
                    + tm.getPersons()
                    + ", '"
                    + tm.getLeadername()
                    + "', 0)";
            Connection con = DBC.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sqlInsert);
            ps.executeUpdate();
        }
        catch(SQLException e){ }
    }
    
    public static ArrayList<Team> getAll() throws SQLException{
        final String sqlGet = "SELECT * FROM teams";
        Connection con = DBC.getInstance().getConnection();
        final PreparedStatement ps = con.prepareStatement(sqlGet);
        final ResultSet rs = ps.executeQuery();
        ArrayList<Team> ret = new ArrayList<>();
        
        while(rs.next()){
            Team tm = new Team();
            tm.setId(rs.getInt("id"));
            tm.setPersons(rs.getInt("persons"));
            tm.setLeadername(rs.getString("leadername"));
            tm.setStatus(rs.getInt("status"));
            ret.add(tm);
        }
        return ret;
    }
    
    public static ArrayList<Team> getAllFree() throws SQLException{
        ArrayList<Team> teams = getAll();
        for(int i=0; i<teams.size(); i++){
            if(teams.get(i).getStatus() == 1){
                teams.remove(i);
            }
        }
        return teams;
    }
    
    public static String getAllAsHtmlTable(boolean buttons){
        ArrayList<Team> ret = new ArrayList<>();
        try{
            ret = getAll();
        }
        catch(SQLException e){
            return "No results";
        }
        if(ret.isEmpty()){
            return "No results";
        }
        String html = "<table style='width:100%'>"; 
                html += "<tr>";
                html += "<td>№</td>";
                html += "<td>Leader</td>";
                html += "<td>Person Qnt</td>";
                html += "<td>Status</td>";
                html += "</tr>";
        for(int i=0; i<ret.size(); i++){
            Team el = ret.get(i);
            html += "<tr>";
            html += "<td>" + (i+1) + ". </td>";
            html += "<td>" + el.getLeadername()+ "</td>";
            html += "<td>" + el.getPersons()+ "</td>";
            switch(el.getStatus()){
                case 0: html += "<td>Free</td>"; break;
                case 1: html += "<td>Busy</td>"; break;
                default: html += "<td>Not set</td>"; break;
            }
            if(buttons){
                if(el.getStatus() == 0){
                    html += "<td><a href='accept.jsp?item=team&accept=false&id=" + el.getId()
                            + "'><button style='background-color: #FF2348'>"
                            + "X</button></a></td>";
                }
                else if(el.getStatus() == 1){
                    html += "<td><button disabled>X</button></td>";
                }
            }
            html += "</tr>";
        }
        html += "</table>";
        return html;
    }
    
    public static String getAllAsSelector(){
        ArrayList<Team> ret = new ArrayList<>();
        try{
            ret = getAllFree();
        }
        catch(SQLException e){
            return "No results";
        }
        if(ret.isEmpty()){
            return "No results";
        }
        String html = "<select name='team'>"; 
        for (Team ret1 : ret) {
            html += "<option value='" + ret1.getLeadername();
            html += "'>" + ret1.getLeadername();
            html += "</option>";
        }
        html += "</select>";
        return html;
    }
    
    public static void setStatus(int status, String leader){
        try{
            String sqlGet = "UPDATE teams SET status=" 
                            + status 
                            + " WHERE leadername='" + leader + "'";
            Connection con = DBC.getInstance().getConnection();
            final PreparedStatement ps = con.prepareStatement(sqlGet);
            ps.executeUpdate();
        }
        catch(SQLException e){}
    }
    
    public static void removeById(int id) throws SQLException{
        final String sqlGet = "DELETE FROM teams WHERE id=" + id;
        Connection con = DBC.getInstance().getConnection();
        final PreparedStatement ps = con.prepareStatement(sqlGet);
         ps.executeUpdate();
    }
    
    public static boolean isBusy(String leader){
        try{
            String sql = "SELECT status FROM teams WHERE leadername='" + leader + "'";
            Connection con = DBC.getInstance().getConnection();
            final PreparedStatement ps = con.prepareStatement(sql);
            final ResultSet rs = ps.executeQuery();
            if(rs.next()){
                if(rs.getInt("status") == 0) return false;
            }
        }
        catch(Exception e){
            return true;
        }
        return true;
    }
}
