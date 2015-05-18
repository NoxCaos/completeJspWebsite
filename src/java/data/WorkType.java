package data;

public class WorkType {
    private String type;
    
    public WorkType(String wt){
        this.type = wt;
    }
    
    public WorkType getById(int id){
        return null;
    }
    
    @Override
    public String toString(){
        return this.type;
    }
    
}
