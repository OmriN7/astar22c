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
						//Convert to graph
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
	
	//
	public static char[][] dataToArray(Scanner fileScanner)
	{
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
	
	public enum TileType
	{
		OPEN, WALL, START, TARGET
	}
	
	public PathFindingGraph<TileData> twoDArrayToGraph(char[][] chars)
	{
		PathFindingGraph<TileData> myGraph = new PathFindingGraph<>();
		boolean visited[][] = new boolean[chars.length][chars.clone()[0].length];
		
		for(int x=0; x<chars.length; x++)
			for(int y=0; y<chars[0].length; y++)
				DFS(chars, myGraph, visited,x, y);
		
		return myGraph;
	}
	
	private TileData DFS(char[][] chars, PathFindingGraph<TileData> graph, 
			boolean[][] visited, int x, int y)
	{
		if(x<0 || x>chars.length || y<0 || y>chars[0].length)
			return null;
		if(visited[x][y])
			return null;
		visited[x][y] = true;
		TileData newData = new TileData(checkType(chars[x][y]), x, y);
		
		TileData tempData = DFS(chars, graph, visited,x+1, y);
		if(tempData != null)
			graph.addEdge(newData, tempData, 1.0);
		
		tempData = DFS(chars, graph, visited,x-1, y);
		if(tempData != null)
			graph.addEdge(newData, tempData, 1.0);
		
		tempData = DFS(chars, graph, visited,x, y+1);
		if(tempData != null)
			graph.addEdge(newData, tempData, 1.0);
		
		tempData = DFS(chars, graph, visited,x, y-1);
		if(tempData != null)
			graph.addEdge(newData, tempData, 1.0);
		
		
		return newData;
			
	}
	
	private TileType checkType(char c)
	{
		switch( c )
		{
		case 'O':
			return TileType.OPEN;
		case 'W':
			return TileType.WALL;
		case 'S':
			return TileType.START;
		case 'T':
			return TileType.TARGET;
		default:
			return null;
		}
	}
	
	public class TileData
	{
		public TileType type;
		public int x, y;
		
		public TileData(TileType t, int x, int y)
		{
			type = t;
			this.x = x;
			this.y = y;
		}
	}
}	
