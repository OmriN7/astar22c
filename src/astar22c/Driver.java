package astar22c;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

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
		System.out.print("Type \"help\" for help\n");
		System.out.print("Please enter a filename...\n");
		
		while(!userInput.equals("exit"))
		{
			userInput = userScanner.nextLine().toLowerCase().trim();
			switch(userInput)
			{
				case "help":
					//FAWZAN & OMRI
					System.out.print("\n");
					System.out.print("HELP\tProvides Help information for commands.\n");
					System.out.print("EXIT\tyou would like to exit the program, enter exit.\n");
					System.out.print("\n");
					System.out.print("Any input that isn't a command would be considered as a filename that the program would try to open.\n");
					System.out.print("Programs should be formatted as follows:\n");
					System.out.print("1. Use ONLY the following characters:\n");
					System.out.print("\t'O' - \"Open\" is for open verticies.\n");
					System.out.print("\t'W' - \"Wall\"Open tiles.\n");
					System.out.print("\t'S' - \"Start\" is for open verticies that are the end location of the path. Should only contain one of these characters.\n");
					System.out.print("\t'T' - \"Target\" is for open verticies that are the starting location of the path. Should only contain one of these characters.\n");
					System.out.print("2. Every line needs to have the same amount of characters. For example...\n");
					System.out.print("\tWOOOOWW\n");
					System.out.print("\tOOOWOOW\n");
					System.out.print("\tOSOWOTO\n");
					System.out.print("\tWOOWOOO\n");
					System.out.print("\tWOOOOOW\n");
					System.out.print("\n");
					System.out.print("\n");
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
						AStarGraph<Integer> dataGraph = fileToGraph(fileScanner);
						if(dataGraph != null)
						{
							dataGraph.findShortestPath();
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
	public static AStarGraph fileToGraph(Scanner fileScanner)
	{
		String nextLine = fileScanner.nextLine();
		String dataString = nextLine;
		int rowSize = 1;
		int colSize = nextLine.length();
		final Set<String> strings = new HashSet<String>(Arrays.asList("O", "W", "S", "T"));
			
		
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
			else if(!strings.contains(dataString)) //I made the error be a bit specific to give the user more feedback. -- Omri
			{
	    		System.out.print("Error! Unkown characters detected.\n");
	    		System.out.print("Please make sure that the every line has either the character 'O', 'W', 'S' or 'T'.\n");
			}
			
			dataString = dataString + nextLine;
			rowSize++;
		}
		
		
		
		
		int dataArray[][] = new int[rowSize][colSize];
		char curChar;
		int indexCnt = 0;
		
		//index0 - x
		//index1 - y
		int startingVertexCoord[] = new int[2];
		int targetVertexCoord[] = new int[2];
		
		
		//Populate data array
	    for(int rowCnt = 0; rowCnt < rowSize; rowCnt++)
	    {
	    	for(int colCnt = 0; colCnt < colSize; colCnt++)
	    	{
	    		curChar = dataString.charAt((rowCnt*(rowSize+1))+colCnt);
	    		if(curChar != 'W') //If the char isn't a wall vertex
	    		{
	    			//Mark the key of the vertex
	    			dataArray[rowCnt][colCnt] = indexCnt;
	    			indexCnt++;
	    			
	    			//Keeps track of the coordinates of the start and target vertex
	    			if(curChar == 'S')
	    			{
	    				startingVertexCoord[0] = colCnt; //x coord
	    				startingVertexCoord[1] = rowCnt; //y coord
	    			}
	    			if(curChar == 'T')
	    			{
	    				targetVertexCoord[0] = colCnt; //x coord
	    				targetVertexCoord[1] = rowCnt; //y coord
	    			}
	    		}
	    	}
	    }
	    
	    
	    
	    AStarGraph<Integer> g = new AStarGraph();
	    AStarVertex<Integer>[][] vertexRefernces = new AStarVertex[rowSize][colSize];
	    
	    //Loop through the array of ints and created the verticies
	    for(int rowCnt = 0; rowCnt < rowSize; rowCnt++)
	    {
	    	for(int colCnt = 0; colCnt < colSize; colCnt++)
	    	{
	    		if(dataArray[rowCnt][colCnt] != (-1)) // If it's not a wall
	    		{
	    			vertexRefernces[rowCnt][colCnt] = g.addVertex(dataArray[rowCnt][colCnt], (colCnt*16), (rowCnt*16)); //Create a vertex!
	    		}
	    		else
	    		{
	    			vertexRefernces[rowCnt][colCnt] = null;
	    		}
	    	}
	    }
	    
	    //Set start and target nodes
	    g.setStartingPointVertex(vertexRefernces[startingVertexCoord[0]][startingVertexCoord[1]]);
	    g.setTargetVertex(vertexRefernces[targetVertexCoord[0]][targetVertexCoord[1]]);
	    
	    
	    
	    
	    for(int rowCnt = 0; rowCnt < rowSize; rowCnt++)
	    {
	    	for(int colCnt = 0; colCnt < colSize; colCnt++)
	    	{
	    		connectVerticies(vertexRefernces, rowCnt, colCnt, rowCnt-1, colCnt, 1);
	    		connectVerticies(vertexRefernces, rowCnt, colCnt, rowCnt+1, colCnt, 1);
	    		connectVerticies(vertexRefernces, rowCnt, colCnt, rowCnt, colCnt-1, 1);
	    		connectVerticies(vertexRefernces, rowCnt, colCnt, rowCnt, colCnt+1, 1);
	    	}
	    }
	    
	    return g; //Return to main! 
	}
	
	public static void connectVerticies(AStarVertex vertexRefernces[][], int vert0x, int vert0y, int vert1x, int vert1y, double weight)
	{
		try
		{
			if(vertexRefernces[vert0x][vert0y] == null || vertexRefernces[vert1x][vert1y] == null)
			{
				return;
			}
			else
			{
				vertexRefernces[vert0x][vert0y].addToAdjList(vertexRefernces[vert1x][vert1y], weight);
			}
		}
		catch (IndexOutOfBoundsException e)
		{
		     //Do nothing.
		}
	}
}


