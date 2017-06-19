package astar22c;

import java.util.*;

import astar22c.ArrayToGraph.TileType;

import java.text.*;


//------------------------------------------------------
public class GraphTester
{
	   // -------  main --------------
	   public static void main(String[] args)
	   {
	      // build graph
	      Graph<String> myGraph1 = new Graph<String>();
	      myGraph1.addEdge("A", "B", 0);
	      myGraph1.addEdge("A", "C", 0);
	      myGraph1.addEdge("A", "D", 0);
	      myGraph1.addEdge("B", "E", 0);   myGraph1.addEdge("B", "F", 0);
	      myGraph1.addEdge("C", "G", 0);
	      myGraph1.addEdge("D", "H", 0);   myGraph1.addEdge("D", "I", 0);
	      myGraph1.addEdge("F", "J", 0);
	      myGraph1.addEdge("G", "K", 0);   myGraph1.addEdge("G", "L", 0);
	      myGraph1.addEdge("H", "M", 0);   myGraph1.addEdge("H", "N", 0);
	      myGraph1.addEdge("I", "N", 0);
	      
	      myGraph1.showAdjTable();
	      
	      AStarGraph<TileType> newGraph = new  AStarGraph<>();
	      char[][] chars = {
	    		  {'W','O','O','O','O','W','W'},
	    		  {'O','O','O','W','O','O','W'},
	    		  {'O','S','O','W','O','T','O'},
	    		  {'W','O','O','W','O','O','O'},
	    		  {'W','O','O','O','O','O','W'}};
	      
	      ArrayToGraph arrayToGraphConstructor = new ArrayToGraph();
	      newGraph = arrayToGraphConstructor.twoDArrayToGraph(chars);
	      
	      newGraph.showAdjTable();
	      
	      newGraph.addEdge(new AStarTile<TileType>(2,1,TileType.OPEN), new AStarTile<TileType>(4,1,TileType.OPEN), 2.0);
	      
	      newGraph.showAdjTable();
	      
	      newGraph.remove(new AStarTile<TileType>(2,1,TileType.OPEN), new AStarTile<TileType>(4,1,TileType.OPEN));
	      
	      newGraph.showAdjTable();
	      


	   }

}
