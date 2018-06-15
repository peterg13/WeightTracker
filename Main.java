package WeightTracker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Main {

    //Main method for the program
    public static void main (String[] args){

        //This linked list will store each data point
        LinkedList<WeightData> dataList = new LinkedList<WeightData>();

        //source of our file we are reading in as well as initializing the reader
        String fileSource = "src\\weightSpreadsheet.csv";
        BufferedReader bufferedReader = null;

        //this try block will attempt to read in each line from the given file, break up the line into a date/weight, save those into a new WeightData object, and finally add that object to the linked list
        try {
            //initializes the readers
            FileReader fileReader = new FileReader(fileSource);
            bufferedReader = new BufferedReader(fileReader);

            //this while loop will go until end of file.  Each line gets split and each fiels added to a new WeightData object
            String line;
            while((line = bufferedReader.readLine()) != null){
                //splits the string. [0] should be date and [1] should be weight
                String[] splitData = line.split(",");
                //converts the weight from a string to float
                Float tempWeight = Float.parseFloat(splitData[1]);
                //creates a new object and adds it to our main linked list
                WeightData tempWeightData = new WeightData(splitData[0], tempWeight);
                dataList.add(tempWeightData);
            }
        }
        catch (IOException e){
            System.out.println(e);
        }
        //once all the above code executes (whether successfully or not) the reader will be closed
        finally {
            try {
                //closes the reader
                bufferedReader.close();
            }
            catch (IOException e){
                System.out.println(e);
            }
        }

        for(int i = 0; i < dataList.size(); i++){
            System.out.println("Date: " + dataList.get(i).date + " - Weight: " + dataList.get(i).weight);
        }


    }

}
