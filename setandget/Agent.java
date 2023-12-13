package model;

public class Agent extends User{
    int id;
    AgentRole roleName;
    
    public Agent(int userId, String username, String password, String emailAddress, AgentRole roleName) {
        super(userId, username, password, emailAddress);
        this.id = userId;
        this.roleName = roleName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRoleName(AgentRole roleName) {
        this.roleName = roleName;
    }

    public int getId() {
        return id;
    }

    public AgentRole getRoleName() {
        return roleName;
    }

    @Override
    public String toString() {
        return "Agent [id=" + id + ", roleName=" + roleName + "]";
    }
}
