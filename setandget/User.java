package model;

public class User{
    int userId;
    String username;
    String password;
    String emailAddress;
    
    public User(int userId, String username, String password, String emailAddress){
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.emailAddress = emailAddress;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setEmailAddress(String emailAddress){
        this.emailAddress = emailAddress;
    }

    public int getUserId(){
        return userId;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getEmailAddress(){
        return emailAddress;
    }

    @Override
    public String toString(){
        return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", emailAddress="
                + emailAddress + "]";
    }
}
