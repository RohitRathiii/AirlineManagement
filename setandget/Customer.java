package model;

public class Customer extends User{
    int id; 
    String firstName; 
    String lastName; 
    String address; 
    String mobileNo;

    public Customer(int userId, String username, String password, String emailAddress, String firstName,
            String lastName, String address, String mobileNo){
        super(userId, username, password, emailAddress);
        this.id = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.mobileNo = mobileNo;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public void setMobileNo(String mobileNo){
        this.mobileNo = mobileNo;
    }

    public int getId(){
        return id;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getAddress(){
        return address;
    }

    public String getMobileNo(){
        return mobileNo;
    }

    @Override
    public String toString() {
        return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", address=" + address
                + ", mobileNo=" + mobileNo + "]";
    }
}
