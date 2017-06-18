/**
 * Written by Yang Guo
 * A class that converts a 2D array to a graph
 */

package astar22c;

public class ArrayToGraph 
{
	public enum TileType
	{
	 	OPEN, WALL, START, TARGET
	}
	 	
	public static AStarGraph<AStarTile> twoDArrayToGraph(char[][] chars)
	{
		AStarGraph<AStarTile> myGraph = new AStarGraph<>();
	 	AStarTile data[][] = new AStarTile[chars.length][chars[0].length];
	 		
	 	for(int x=0; x<chars.length; x++)
	 		for(int y=0; y<chars[0].length; y++)
	 			connectTile(chars, myGraph, data,x, y);
	 		
	 	return myGraph;
	 }
	 	
	@SuppressWarnings("unchecked")
	private static AStarTile connectTile(char[][] chars, AStarGraph<AStarTile> graph, 
			 AStarTile[][] data, int x, int y)
	 {
	 	if(x<0 || x>=chars.length || y<0 || y>=chars[0].length)
	 		return null;
	 	if(data[x][y] != null)
	 		return data[x][y];
	 	
	 	TileType newType = checkType(chars[x][y]);
	 	AStarTile newTile;
	 	if(newType != null && newType != TileType.WALL)
	 	{
	 		newTile = new AStarTile(x, y);
	 		data[x][y] = newTile;
	 	}
	 	else
	 		return null;
	 		
	 	AStarTile tempData = connectTile(chars, graph, data,x+1, y);
	 	if(tempData != null)
	 		graph.addEdge(newTile, tempData, 1.0);
	 		
	 	tempData = connectTile(chars, graph, data,x-1, y);
	 	if(tempData != null)
	 		graph.addEdge(newTile, tempData, 1.0);
	 		
	 	tempData = connectTile(chars, graph, data,x, y+1);
	 	if(tempData != null)
			graph.addEdge(newTile, tempData, 1.0);
	 		
	 	tempData = connectTile(chars, graph, data,x, y-1);
	 	if(tempData != null)
	 		graph.addEdge(newTile, tempData, 1.0);
	 	
//connection with diagonal tiles, commented out for now
	 	
//	 	tempData = connectTile(chars, graph, data,x+1, y+1);
//	 	if(tempData != null)
//	 		graph.addEdge(newTile, tempData, 1.0);
//	 		
//	 	tempData = connectTile(chars, graph, data,x-1, y-1);
//	 	if(tempData != null)
//	 		graph.addEdge(newTile, tempData, 1.0);
//	 		
//	 	tempData = connectTile(chars, graph, data,x-1, y+1);
//	 	if(tempData != null)
//			graph.addEdge(newTile, tempData, 1.0);
//	 		
//	 	tempData = connectTile(chars, graph, data,x+1, y-1);
//	 	if(tempData != null)
//	 		graph.addEdge(newTile, tempData, 1.0);
	 	
	 			
	 	return newTile;	
	 }
	 	
	 private static TileType checkType(char c)
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
	 
	 	
//	 public static class AStarTile
//	 {
//	 	public TileType type;
//		public int x, y;
//	 		
//	 	public TileData(TileType t, int x, int y)
//	 	{
//	 		type = t;
//	 		this.x = x;
//	 		this.y = y;
//	 	}
//	 	
//	 	public String toString()
//	 	{
//	 		switch(type)
//	 		{
//	 		case OPEN:
//	 			return "OPEN["+x+","+y+"]";
//	 		case START:
//	 			return "START["+x+","+y+"]";
//	 		case TARGET:
//	 			return "TARGET["+x+","+y+"]";
//	 		default:
//	 			return null;
//	 		}
//	 	}
//	 }
}
