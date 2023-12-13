package model;

public class Flight{
    int flightNo;
    String departureCity;
    String arrivalCity;
    
    public Flight(int flightNo, String departureCity, String arrivalCity){
        this.flightNo = flightNo;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
    }

    public void setFlightNo(int flightNo){
        this.flightNo = flightNo;
    }

    public void setDepartureCity(String departureCity){
        this.departureCity = departureCity;
    }

    public void setArrivalCity(String arrivalCity){
        this.arrivalCity = arrivalCity;
    }

    public int getFlightNo(){
        return flightNo;
    }

    public String getDepartureCity(){
        return departureCity;
    }

    public String getArrivalCity(){
        return arrivalCity;
    }

    @Override
    public String toString(){
        return "Flight [flightNo=" + flightNo + ", departureCity=" + departureCity + ", arrivalCity=" + arrivalCity
                + "]";
    }
}
