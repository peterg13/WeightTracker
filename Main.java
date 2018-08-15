package WeightTracker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static String fileSource = "src\\weightSpreadsheet.csv";

    //Main method for the program
    public static void main (String[] args){

        //This linked list will store each data point
        LinkedList<WeightData> dataList = new LinkedList<WeightData>();

        //initializes the reader
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

        System.out.println("There are " + dataList.size() + " entries in the database");

        //creates an input scanner and begins the programs main flow
        Scanner inputScanner = new Scanner(System.in);
        inputFlow(inputScanner, dataList);

        //once finished close the scanner
        inputScanner.close();


    }

    //once the program is loaded, input flow will be the main controller for the actions you wish to take
    private static void inputFlow(Scanner inputScanner, LinkedList<WeightData> dataList){
        Boolean exit = false;
        while(exit == false){
            System.out.println();
            System.out.println("-------------------------------------");
            System.out.println("Please select from the following:");
            System.out.println("1. Display graph");
            System.out.println("2. Predict date of target weight");
            System.out.println("3. Add a new weight");
            System.out.println("4. Exit program");
            System.out.println("-------------------------------------");

            try {
                //if a non in input it entered it will throw an exception
                int input = inputScanner.nextInt();

                switch (input) {
                    case 1:
                        System.out.println("1 was selected.  TODO");
                        break;
                    case 2:
                        predictWeight(inputScanner, dataList);
                        break;
                    case 3:
                        addWeight(inputScanner, dataList);
                        break;
                    case 4:
                        System.out.println("Exiting Program");
                        exit = true;
                        break;
                    default:
                        System.out.println("Please enter a valid input");
                }
            }
            //if an error is thrown it consumes the bad input and continues on with the loop
            catch(Exception e){
                inputScanner.next();
                System.out.println(e);
                System.out.println("Please enter a valid input");
            }


        }
    }


    //calculates how long it will take to reach goal weight.  To do this is will ask for the range in dates, then find a line that fits over that range and predict the date.
    public static void predictWeight(Scanner inputScanner, LinkedList<WeightData> dataList){
        System.out.println("Please enter the start date (m/d/yyyy):");
        String startDate = inputScanner.next();
        System.out.println("Please enter the end date (m/d/yyyy):");
        String endDate = inputScanner.next();
        System.out.println("Please enter your goal weight:");
        Float goalWeight = inputScanner.nextFloat();

        //now that we have our range we need to find where in the linked list to start
        int startIndex = -1;
        for(int i = 0; i < dataList.size(); i++){
            if(dataList.get(i).date.equals(startDate)){
                startIndex = i;
                break;
            }
        }
        if(startIndex == -1){
            System.out.println("could not find the start date");
            return;
        }
        //now lets find that end date
        int endIndex = -1;
        for(int i = startIndex; i < dataList.size(); i++){
            if(dataList.get(i).date.equals(endDate)){
                endIndex = i;
                break;
            }
        }
        if(endIndex == -1){
            System.out.println("could not find the end date");
            return;
        }

        //now that we have the index of the start and end, lets do some calculations.  Since I currently weigh myself every day, the slope will always be (difference between weight 1 and 2)/1.
        LinkedList<Float> slopes = new LinkedList<Float>();
        for(int i = startIndex; i < endIndex -1; i++){
            Float newSlope = dataList.get(i+1).getWeight() - dataList.get(i).getWeight();
            slopes.add(newSlope);
        }
        //now lets get an average of all of the slopes in the range
        Float slopesTotal = 0f;
        for(int i = 0; i < slopes.size(); i++){
            slopesTotal += slopes.get(i);
        }
        Float averageSlope = slopesTotal/slopes.size();

        //Now that we have the slope, lets take the last weight and add the slope until we reach to goal weight
        int daysToGoalWeight = (int)((goalWeight - dataList.getLast().getWeight())/averageSlope);

        System.out.println("You currently weight: " + dataList.getLast().getWeight());
        System.out.println("Your goal weight is: " + goalWeight);
        System.out.println("You gain an average of " + averageSlope + " pounds per day and " + averageSlope*7 + " pounds per week");
        System.out.println("It will take you " + daysToGoalWeight + " days to reach your goal");
    }

    //adds a weight to our weight document
    //TODO: Error check the input and that the newWeight fields are full before writing to file
    private static void addWeight(Scanner inputScanner, LinkedList<WeightData> dataList){
        WeightData newWeight = new WeightData();
        Boolean exit = false;
        while(exit == false){
            System.out.println("Please enter the date (m/d/yyyy):");
            String inputDate = inputScanner.next();
            newWeight.setDate(inputDate);
            System.out.println("Please enter the weight");
            Float inputWeight = inputScanner.nextFloat();
            newWeight.setWeight(inputWeight);


            FileWriter fileWriter = null;
            try{
                fileWriter = new FileWriter(fileSource, true);
                fileWriter.write("\n" + newWeight.getDataAsString());
                dataList.add(newWeight);
            }
            catch(IOException e){
                System.out.println(e);
            }
            finally {
                try {
                    //closes the reader
                    fileWriter.close();
                }
                catch (IOException e){
                    System.out.println(e);
                }
            }

            exit = true;
        }


    }

}
