package data;

public class Job {
    private int id;
    private String deadline;
    private String comment;
    private String submitter;
    private String address;
    private String worktype;
    private String team;
    private int status;
    
    public Job(){
        
    }
    
    public int getId(){
        return this.id;
    }
    
    public String getDeadline(){
        return this.deadline;
    }
    
    public String getComment(){
        return this.comment;
    }
    
    public String getSubmitter(){
        return this.submitter;
    }
    
    public String getAddress(){
        return this.address;
    }
    
    public String getWorktype(){
        return this.worktype;
    }
    
    public int getStatus(){
        return this.status;
    }
    
    public String getTeam(){
        return this.team;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public void setDeadline(String dl){
        this.deadline = dl;
    }
    
    public void setComment(String com){
        this.comment = com;
    }
    
    public void setSubmitter(String sb){
        this.submitter = sb;
    }
    
    public void setAddress(String ad){
        this.address = ad;
    }
    
    public void setWorktype(String wt){
        this.worktype = wt;
    }
    
    public void setTeam(String tm){
        this.team = tm;
    }
    
    public void setStatus(int st){
        this.status = st;
    }
}
