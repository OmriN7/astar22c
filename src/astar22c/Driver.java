package astar22c;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import astar22c.ArrayToGraph.TileData;

public class Driver 
{
	//Global Vars
	public static Scanner userScanner = new Scanner(System.in);
	
	//Main
	public static void main(String[] args)
	{
		String userInput = ""; //String to hold user input
		Scanner fileScanner; //Scanner to scan through the input file
		
		System.out.printf("Welcome to the pathfinding program!\n");
		
		while(!userInput.equals("exit"))
		{
			userInput = userScanner.nextLine().toLowerCase().trim();
			switch(userInput)
			{
				case "help":
					//Display the available commands...
					break; 

				case "exit":
					System.out.printf("Exiting program...\n");
					break; 
				
				default:
					fileScanner = openInputFile(userInput);
					if(fileScanner == null)
					{
						System.out.printf("File not found...\n\n");
					}
					else
					{
						char dataArray[][] = dataToArray(fileScanner);
						PathFindingGraph<TileData> pfg = ArrayToGraph.twoDArrayToGraph(dataArray);
						//Run pathfinding simulation
						//Show the path that was found...
					}
					break;
			}
		}
		//END OF PROGRAM
	} // end main
	
	
	public static Scanner openInputFile(String filename) 
	{
		File file = new File(filename);
		Scanner scanner;
		
		try 
		{
			scanner = new Scanner(file);
		} // end try
		catch (FileNotFoundException fe) 
		{
			return null; // array of 0 elements
		} // end catch
		
		return scanner;
	} // end openInputFile


	public static char[][] dataToArray(Scanner fileScanner)
	{
		//Program should check that the format of the data is OK! 
		//If it's not then print that the format in the file is bad due to the reason
		//and return null
		
		//? The way that you compute rows and columns implies we'll define the length
		//of the table in the beginning which is incorrect...
		
		//https://stackoverflow.com/questions/18902706/java-scanner-count-lines-in-file
	    int numRows = fileScanner.nextInt();
	    int numColumns = fileScanner.nextInt();
	    
	    char[][] dataArray = new char[numRows][numColumns];

	    for(int row = 0; row < numRows; row++)
	    {
	        String line = fileScanner.next();

	        for(int column = 0; column < numColumns; column++)
	        {
	        	dataArray[row][column] = line.charAt(column);
	        }
	    }
	    return dataArray;
	}
}	
