package WeightTracker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

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

        System.out.println("There are " + dataList.size() + " entries in the database");

        //creates an input scanner and begins the programs main flow
        Scanner inputScanner = new Scanner(System.in);
        inputFlow(inputScanner);

        //once finished close the scanner
        inputScanner.close();


    }

    //once the program is loaded, input flow will be the main controller for the actions you wish to take
    private static void inputFlow(Scanner inputScanner){
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
                        System.out.println("2 was selected.  TODO");
                        break;
                    case 3:
                        System.out.println("3 was selected.  TODO");
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

}
