package astar22c;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import astar22c.ArrayToGraph.TileType;


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
		AStarGraph<TileType> dataGraph = null;

		System.out.print("Welcome to the pathfinding program!\n");
		System.out.print("Type \"help\" for help\n");
		System.out.print("Please enter a filename...\n");
		
		while(!userInput.equals("exit"))
		{
			userInput = userScanner.nextLine().toLowerCase();
			switch(userInput)
			{
				case "help":
					//FAWZAN & OMRI
					System.out.print("\n");
					System.out.print("HELP   \tProvides Help information for commands.\n");
					System.out.print("EXIT   \tTerminate this program.\n");
					System.out.print("ADDEDGE\t(Under development) Add an edge to the current graph.\n");
					System.out.print("       \tFORMAT: \"ADDEDGE {vert0 x-coord} {vert0 y-coord} {vert1 x-coord} {vert1 y-coord} {weight}\"\n");
					System.out.print("REMEDGE\t(Under development) Remove an edge from the current graph.\n");
					System.out.print("       \tFORMAT: \"REMEDGE {vert0 x-coord} {vert0 y-coord} {vert1 x-coord} {vert1 y-coord} {weight}\"\n");
					System.out.print("UNDOREM\t(Under development) Undo a previous REMEDGE command.\n");
					System.out.print("DISPDFT\t(Under development) Display graph using Depth-First Traversel.\n");
					System.out.print("DISPBST\t(Under development) Display graph using Breadth-First Traversel.\n");
					System.out.print("DISPAL \tDisplay graph using Adjacency list.\n");
					//System.out.print("FPATH  \t(Under development) Find the shortest path using A*.\n"); add this later!
					//System.out.print("DISPT  \t(Under development) Display the graph as a table.\n"); add this later!
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
					
				case "dispal":
					if(dataGraph != null)
					{
						dataGraph.showAdjTable();
					}
					else
					{
						System.out.print("No graph in memory!\n");
					}
					break; 
				
				default:
					fileScanner = openInputFile(userInput);
					if(fileScanner == null)
					{
						System.out.print("File not found...\n\n");
					}
					else
					{
						System.out.print("\n");
						dataGraph = fileToGraph(fileScanner);

						if(dataGraph != null)
						{
							System.out.print("Graph recieved:\n");
							System.out.print("\n");
							System.out.print(dataGraph.graphToTableString());
							System.out.print("\n");
							
							dataGraph.findShortestPath();
							
							System.out.print("Solved Graph:\n");
							System.out.print("\n");
							System.out.print(dataGraph.graphToTableString());
							

							System.out.print("Please give me a filename for the output file: ");
							userInput = userScanner.nextLine();
							
							String filename = System.getProperty("user.dir") + "\\" + userInput;
							File file = new File(filename);
							
							try 
							{
								dataGraph.printToFile(new PrintWriter(file));
								System.out.print("Sucessfully created: " + filename);
							} 
							catch (FileNotFoundException e) 
							{
								System.out.print("Failed to create the output file...");
								e.printStackTrace();
							}
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
	public static AStarGraph<TileType> fileToGraph(Scanner fileScanner)
	{
		String nextLine = fileScanner.nextLine();
		String dataString = nextLine;
		int rowSize = 1;
		int colSize = nextLine.length();
			

		System.out.print("Scanning file's format...\n");
		while(fileScanner.hasNext())
		{
			nextLine = fileScanner.nextLine();

			if(nextLine.length() != colSize)
			{
	    		System.out.print("Error! the file given has a formatting error.\n");
	    		System.out.print("Please make sure that the every line has the same amount of characters!\n");
				return null;
			}

			char letter;
		    for(int i = 0; i < nextLine.length(); i++) 
		    {
		        letter = nextLine.charAt(i);
				if (letter != 'O' && letter != 'W' && letter != 'S' && letter != 'T') 
				{
					System.out.print("Error! Unkown characters detected.\n");
					System.out.print("Please make sure that the every line has either the character 'O', 'W', 'S' or 'T'.\n");
					return null;
				} 
		   	}
		    
			dataString = dataString + nextLine;
			rowSize++;
		}
		System.out.print("File's format is OK!\n");
		
		
		char[][] charArr = new char[rowSize][colSize];
		int index = 0;
		for(int rowCnt = 0; rowCnt < rowSize; rowCnt++)
		{
			for(int colCnt = 0; colCnt < colSize; colCnt++)
			{
				charArr[rowCnt][colCnt] = dataString.charAt(index);
				index++;
			}
		}
		
	    ArrayToGraph arrayToGraphConstructor = new ArrayToGraph();
	    return arrayToGraphConstructor.twoDArrayToGraph(charArr);
	}
	/*
		
	 // Loop based AStarGraph construction, replaced with recursion based method above	
	
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
	    				startingVertexCoord[0] = rowCnt;// x coord
	    				startingVertexCoord[1] = colCnt;// y coord
	    			}
	    			if(curChar == 'T')
	    			{
	    				targetVertexCoord[0] = rowCnt;// x coord
	    				targetVertexCoord[1] = colCnt;// y coord
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
	    
	    //Loop through the array of AStarTiles and created the verticies
	    for(int rowCnt = 0; rowCnt < rowSize; rowCnt++)
	    {
	    	for(int colCnt = 0; colCnt < colSize; colCnt++)
	    	{
	    		if(dataArray[rowCnt][colCnt] != null)//  If it's not a wall
	    		{
	    			vertexRefernces[rowCnt][colCnt] = g.addToVertexSet(dataArray[rowCnt][colCnt]);// Create a vertex!
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
	    		//connectVerticies(vertexRefernces, rowCnt, colCnt, rowCnt-1, colCnt-1, Math.sqrt(2));
	    		//connectVerticies(vertexRefernces, rowCnt, colCnt, rowCnt+1, colCnt-1, Math.sqrt(2));
	    		//connectVerticies(vertexRefernces, rowCnt, colCnt, rowCnt-1, colCnt+1, Math.sqrt(2));
	    		//connectVerticies(vertexRefernces, rowCnt, colCnt, rowCnt+1, colCnt+1, Math.sqrt(2));
	    	}
	    }
	    
	    return g; //Return to main! 
	}
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
	*/
}


