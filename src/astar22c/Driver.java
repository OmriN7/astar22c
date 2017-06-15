package astar22c;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Driver
{
	//Global Vars
	public static Scanner userScanner = new Scanner(System.in);
	
	//Main
	public static void main(String[] args)
	{
		String userInput = ""; //String to hold user input
		Scanner fileScanner; //Scanner to scan through the input file
		char dataArray[][];
		
		System.out.print("Welcome to the pathfinding program!\n");
		
		while(!userInput.equals("exit"))
		{
			userInput = userScanner.nextLine().toLowerCase().trim();
			switch(userInput)
			{
				case "help":
					//Display the available commands...
					//FAWZAN -- try to think of something helpful to print here
					//when a user types "help"
					break; 

				case "exit":
					System.out.print("Exiting program...\n");
					break; 
				
				default:
					fileScanner = openInputFile(userInput);
					if(fileScanner == null)
					{
						System.out.print("File not found...\n\n");
					}
					else
					{
						Graph<AStarVertex> dataGraph = fileToGraph(fileScanner);
						if(dataGraph != null)
						{
							//LinkedStack<AStarVertex> shortestPath = findShortestPath(Graph dataGraph, AStarVertex startVertex, AStarVertex, targetVertex)
							//printShortestPath(Graph<AStarVertex> dataGraph, LinkedStack<AStarVertex> shortestPath)
						}
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


	//YANG -- after Vaughn and Fawzan are done w/ their tasks on this method...
	//Try to split it up into about 4 methods to keep things clean!
	public static Graph fileToGraph(Scanner fileScanner)
	{
		String nextLine = fileScanner.nextLine();
		String dataString = nextLine;
		int rowSize = 1;
		int colSize = nextLine.length();
		
		while(fileScanner.hasNext())
		{
			nextLine = fileScanner.nextLine();

        	//VAUGHN! make sure that you check here if the characters that we're putting in are from the 4 characters that we've defined thus far
			if(nextLine.length() != colSize)
			{
	    		System.out.print("Error! the file given has a formatting error.\n");
	    		System.out.print("Please make sure that the every line has the same amount of characters!\n");
				return null;
			}
			
			dataString = dataString + nextLine;
			rowSize++;
		}
		
		
		
		
		int dataArray[][] = new int[rowSize][colSize];
		int indexCnt = 0;
		
		//Populate data array
	    for(int rowCnt = 0; rowCnt < rowSize; rowCnt++)
	    {
	    	for(int colCnt = 0; colCnt < colSize; colCnt++)
	    	{
	    		switch(dataString.charAt(rowCnt*colSize + colCnt) + "")
	    		{
		    		case "O": //Open
		    		case "S": //Start
		    		case "T": //Target
		    			dataArray[rowCnt][colCnt] = indexCnt;
		    			indexCnt++;
		    			break;
		    			
		    		default: //Wall
		    			dataArray[rowCnt][colCnt] = -1;
		    			break;
	    		}
	    	}
	    }
	    
	    
	    
	    
	    
	    Graph<AStarVertex> g = new Graph();
	    
	    //Loop through the array of ints and created the verticies
	    for(int rowCnt = 0; rowCnt < rowSize; rowCnt++)
	    {
	    	for(int colCnt = 0; colCnt < colSize; colCnt++)
	    	{
	    		if(dataArray[rowCnt][colCnt] != (-1)) // If it's not a wall
	    		{
	    			g.addToVertexSet(new AStarVertex(dataArray[rowCnt][colCnt], (colCnt*16), (rowCnt*16))); //Create a vertex!
	    			//Save the return values of this in a 2D array of references possibly? ask teacher
	    		}
	    	}
	    }
	    
	    
	    
	    //FAWZAN
	    //Loop through the array we've created of the references to the verticies
	    //and connect them to each other using addToAdjList(reference, weight)
	    
	    return g; //Return to main! 
	}
}	
