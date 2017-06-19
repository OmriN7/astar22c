package astar22c;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

import astar22c.ArrayToGraph.TileType;


public class Driver
{
	//Global Vars
	public static Scanner userScanner = new Scanner(System.in);
	
	//Main
	public static void main(String[] args)
	{
		String userInput = ""; //String to hold user input
		String command = "";
		StringTokenizer st;
		
		Scanner fileScanner; //Scanner to scan through the input file
		char dataArray[][];
		AStarGraph<TileType> dataGraph = null;

		System.out.print("Welcome to the pathfinding program!\n");
		System.out.print("Type \"help\" for help\n");
		System.out.print("Please enter a filename...\n");
		
		while(!userInput.equals("exit"))
		{
			System.out.print("> ");
			userInput = userScanner.nextLine().toLowerCase().trim();
			st = new StringTokenizer(userInput," ");
			
			if(st.hasMoreTokens())
			{
				command = st.nextToken();
				switch(command)
				{
					case "help":
						//FAWZAN & OMRI
						System.out.print("\n");
						System.out.print("HELP   \tProvides Help information for commands.\n");
						System.out.print("EXIT   \tTerminate this program.\n");
						System.out.print("ADDEDGE\tAdd an edge to the current graph. (creates vertex if it doesn't exist)\n");
						System.out.print("       \tFORMAT: \"ADDEDGE {vert0 x-coord} {vert0 y-coord} {vert1 x-coord} {vert1 y-coord} {weight}\"\n");
						System.out.print("REMEDGE\tRemove an edge from the current graph.\n");
						System.out.print("       \tFORMAT: \"REMEDGE {vert0 x-coord} {vert0 y-coord} {vert1 x-coord} {vert1 y-coord}\"\n");
						System.out.print("UNDOREM\tUndo a previous REMEDGE command.\n");
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
					
					case "addedge":
						//FORMAT: ADDEDGE {vert0 x-coord} {vert0 y-coord} {vert1 x-coord} {vert1 y-coord} {weight}
						try
						{
							int vert0x = Integer.valueOf(st.nextToken());
							int vert0y = Integer.valueOf(st.nextToken());
							AStarTile<TileType> vert0 = new AStarTile<>(vert0x, vert0y, TileType.OPEN);
							
							int vert1x = Integer.valueOf(st.nextToken());
							int vert1y = Integer.valueOf(st.nextToken());
							AStarTile<TileType> vert1 = new AStarTile<>(vert1x, vert1y, TileType.OPEN);
							
							double weight = Double.valueOf(st.nextToken());
							
							dataGraph.addEdge(vert0, vert1, weight);
						}
						catch(Exception e)
						{
							System.out.print("Command prams are invalid... Please follow this format:\n");
							System.out.print("ADDEDGE {vert0 x-coord} {vert0 y-coord} {vert1 x-coord} {vert1 y-coord} {weight}\n");
						}
						break;
						
						case "remedge":
							//FORMAT: REMEDGE {vert0 x-coord} {vert0 y-coord} {vert1 x-coord} {vert1 y-coord}
							try
							{
								int vert0x = Integer.valueOf(st.nextToken());
								int vert0y = Integer.valueOf(st.nextToken());
								AStarTile<TileType> vert0 = new AStarTile<>(vert0x, vert0y, TileType.OPEN);
								
								int vert1x = Integer.valueOf(st.nextToken());
								int vert1y = Integer.valueOf(st.nextToken());
								AStarTile<TileType> vert1 = new AStarTile<>(vert1x, vert1y, TileType.OPEN);
								
								if(dataGraph.remove(vert0, vert1))
								{
									System.out.print("Edge removed succesfully.\n");
								}
								else
								{
									System.out.print("Failed to remove the edge.\n");
								}
							}
							catch(Exception e)
							{
								System.out.print("Command prams are invalid... Please follow this format:\n");
								System.out.print("REMEDGE {vert0 x-coord} {vert0 y-coord} {vert1 x-coord} {vert1 y-coord}\n");
							}
							break;
							
						case "undorem":
							if(dataGraph.undoRemoval())
							{
								System.out.print("Sucessfuly undone removal.\n");
							}
							else
							{
								System.out.print("Nothing to undo.\n");
							}
							break;
							
							
					default:
						fileScanner = openInputFile(userInput);
						if(fileScanner == null)
						{
							System.out.print("File not found...\n");
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
								
								String filename = System.getProperty("user.dir") + "\\" + userInput + ".txt";
								File file = new File(filename);
								
								try 
								{
									dataGraph.printToFile(new PrintWriter(file));
									System.out.print("Created: " + filename + "\n");
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
		}
		//END OF PROGRAM
	} // Written by Omri & Fawzan
	
	
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
	} // Written by Omri & Vaughn
}


