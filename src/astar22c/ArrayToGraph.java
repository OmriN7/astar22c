/**
 * Written by Yang Guo
 * A class that converts a 2D array to a graph
 */

package astar22c;

@SuppressWarnings("unchecked")
public class ArrayToGraph
{
	public enum TileType
	{
	 	OPEN, START, TARGET
	}
	 	
	public AStarGraph<TileType> twoDArrayToGraph(char[][] chars)
	{
		AStarGraph<TileType> myGraph = new AStarGraph<>();
		AStarTile<TileType> data[][] = new AStarTile[chars.length][chars[0].length];
	 		
	 	for(int y=0; y<chars.length; y++)
	 		for(int x=0; x<chars[0].length; x++)
	 			connectTile(chars, myGraph, data,x, y);
	 		
	 	return myGraph;
	 }
	 	
	private AStarTile<TileType> connectTile(char[][] chars, AStarGraph<TileType> graph, 
			 AStarTile<TileType>[][] data, int x, int y)
	 {
		//Check that the data isn't outside of the table we've defined
	 	if(x<0 || x>=chars[0].length || y<0 || y>=chars.length)
	 		return null;
	 	//if the AStarTile exist, return this tile
	 	if(data[y][x] != null)
	 		return data[y][x];
	 	
	 	
	 	TileType type = checkType(chars[y][x]);
	 	AStarTile<TileType> newTile;
	 	
	 	if(type != null) //If the tile isn't a wall
	 	{
	 		//Create a AStarTile
	 		newTile = new AStarTile<TileType>(x, y, type);
	 		data[y][x] = newTile;
	 	}
	 	else //Otherwise do nothing
	 	{
	 		return null;
	 	}
	 	
	 	if(type == TileType.START)
	 		graph.setStartingPointVertex(newTile);
	 	if(type == TileType.TARGET)
	 		graph.setTargetVertex(newTile);
	 	
	 		
	 	AStarTile<TileType> tempData = connectTile(chars, graph, data,x+1, y);
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
	 	/*
	 	tempData = connectTile(chars, graph, data,x+1, y+1);
	 	if(tempData != null)
	 		graph.addEdge(newTile, tempData, Math.sqrt(2));
	 		
	 	tempData = connectTile(chars, graph, data,x-1, y-1);
	 	if(tempData != null)
	 		graph.addEdge(newTile, tempData, Math.sqrt(2));
	 		
	 	tempData = connectTile(chars, graph, data,x-1, y+1);
	 	if(tempData != null)
			graph.addEdge(newTile, tempData, Math.sqrt(2));
	 		
	 	tempData = connectTile(chars, graph, data,x+1, y-1);
	 	if(tempData != null)
	 		graph.addEdge(newTile, tempData, Math.sqrt(2));
		*/
	 			
	 	return newTile;	
	 }
	 	
	 private static TileType checkType(char c)
	{
	 	switch( c )
	 	{
	 	case 'O':
	 		return TileType.OPEN;
	 	case 'W':
	 		return null;
	 	case 'S':
	 		return TileType.START;
	 	case 'T':
			return TileType.TARGET;
	 	default:
	 		return null;
	 	}
	}
	 
}
