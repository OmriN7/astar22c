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
					System.out.print("ADDEDGE\tNeed to add...\n");
					System.out.print("REMOVEEDGE\tNeed to add...\n");
					System.out.print("UNDEOREMOVE\tNeed to add...\n");
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
						AStarGraph<AStarTile> dataGraph = fileToGraph(fileScanner);
						if(dataGraph != null)
						{
							//I commented these out as a template for which methods I need to add. - Omri
							
							//System.out.print("Graph recieved:\n");
							//dataGraph.printGraph();
							
							dataGraph.findShortestPath();
							
							//System.out.print("Solved Graph:\n");
							//dataGraph.printGraph();
							
							//dataGraph.saveGraphToFile();
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


	//FOR LATER -- Move this to AStarGraph and try to split up the 4 phases into 4 different methods... 
	//Omri, Fawzan and Vaughn
	public static AStarGraph fileToGraph(Scanner fileScanner)
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
			boolean isMatch = true; 
		    	for (int counter = 0; counter < nextLine.length() && isMatch; counter++) {
		        char letter = nextLine.charAt(counter);
				if (letter != 'O' && letter != 'X' && letter != 'S' && letter != 'T') {
					System.out.print("Error! Unkown characters detected.\n");
					System.out.print("Please make sure that the every line has either the character 'O', 'W', 'S' or 'T'.\n");
				    isMatch = false;
				} 
		   	}
			System.out.println("good");
			dataString = dataString + nextLine;
			rowSize++; //the input file i used had X's for some reason... 
		}
		
		
		
		
		AStarTile dataArray[][] = new AStarTile[rowSize][colSize];
		char curChar;
		
		//index0 - x
		//index1 - y
		int startingVertexCoord[] = new int[2];
		int targetVertexCoord[] = new int[2];
		
		
		//Populate data array
	    for(int rowCnt = 0; rowCnt < rowSize; rowCnt++)
	    {
	    	for(int colCnt = 0; colCnt < colSize; colCnt++)
	    	{
	    		curChar = dataString.charAt((rowCnt*(rowSize+2))+colCnt);
	    		if(curChar != 'W') //If the char isn't a wall vertex
	    		{
	    			//Mark the key of the vertex
	    			dataArray[rowCnt][colCnt] = new AStarTile(colCnt, rowCnt);
	    			
	    			//Keeps track of the coordinates of the start and target vertex
	    			if(curChar == 'S')
	    			{
	    				startingVertexCoord[0] = rowCnt; //x coord
	    				startingVertexCoord[1] = colCnt; //y coord
	    			}
	    			if(curChar == 'T')
	    			{
	    				targetVertexCoord[0] = rowCnt; //x coord
	    				targetVertexCoord[1] = colCnt; //y coord
	    			}
	    		}
	    		else
	    		{
	    			dataArray[rowCnt][colCnt] = null;
	    		}
	    	}
	    }
	    
	    
	    
	    AStarGraph g = new AStarGraph();
	    Vertex<AStarTile>[][] vertexRefernces = new Vertex[rowSize][colSize];
	    
	    //Loop through the array of ints and created the verticies
	    for(int rowCnt = 0; rowCnt < rowSize; rowCnt++)
	    {
	    	for(int colCnt = 0; colCnt < colSize; colCnt++)
	    	{
	    		if(dataArray[rowCnt][colCnt] != null) // If it's not a wall
	    		{
	    			vertexRefernces[rowCnt][colCnt] = g.addToVertexSet(dataArray[rowCnt][colCnt]); //Create a vertex!
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
	    		//Horizontal and vertical edges
	    		connectVerticies(vertexRefernces, rowCnt, colCnt, rowCnt-1, colCnt, 1);
	    		connectVerticies(vertexRefernces, rowCnt, colCnt, rowCnt+1, colCnt, 1);
	    		connectVerticies(vertexRefernces, rowCnt, colCnt, rowCnt, colCnt-1, 1);
	    		connectVerticies(vertexRefernces, rowCnt, colCnt, rowCnt, colCnt+1, 1);
	    		
	    		//Diagonal edges
	    		/*
	    		connectVerticies(vertexRefernces, rowCnt, colCnt, rowCnt-1, colCnt-1, Math.sqrt(2));
	    		connectVerticies(vertexRefernces, rowCnt, colCnt, rowCnt+1, colCnt-1, Math.sqrt(2));
	    		connectVerticies(vertexRefernces, rowCnt, colCnt, rowCnt-1, colCnt+1, Math.sqrt(2));
	    		connectVerticies(vertexRefernces, rowCnt, colCnt, rowCnt+1, colCnt+1, Math.sqrt(2));
	    		*/
	    	}
	    }
	    
	    return g; //Return to main! 
	}
	
	public static void connectVerticies(Vertex vertexRefernces[][], int vert0x, int vert0y, int vert1x, int vert1y, double weight)
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


