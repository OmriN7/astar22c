package astar22c;

public class Driver 
{
	public static void main(String[] args)
	{
		System.out.printf("Hello worl!\n");
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
