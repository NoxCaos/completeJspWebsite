package data;

public class Team {
    private int id;
    private int persons;
    private String leadername;
    private int status;
    
    public Team(){}
    
    public void setId(int id){
        this.id = id;
    }
    
    public void setPersons(int per){
        this.persons = per;
    }
    
    public void setLeadername(String name){
        this.leadername = name;
    }
    
    public void setStatus(int st){
        this.status = st;
    }
    
    public int getId(){
        return this.id;
    }
    
    public int getPersons(){
        return this.persons;
    }
    
    public String getLeadername(){
        return this.leadername;
    }
    
    public int getStatus(){
        return this.status;
    }
}
