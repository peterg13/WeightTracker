package WeightTracker;

public class WeightData {

    String date;
    Float weight;

    public WeightData(String newDate, Float newWeight){
        this.date = newDate;
        this.weight = newWeight;
    }

    public WeightData(){
        this.date = "";
        this.weight = 0f;
    }

    public Float getWeight(){
        return this.weight;
    }

    public String getDate(){
        return this.date;
    }

    public void setDate(String newDate){
        this.date = newDate;
    }

    public void setWeight(Float newWeight){
        this.weight = newWeight;
    }

    public String getDataAsString(){
        String returnString = this.date + "," + this.weight.toString();
        return returnString;
    }

}
