package astar22c;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class AStarGraph<E> extends Graph
{
	//Instance Variables
	private Vertex<E> startingPointVertex = null;
	private Vertex<E> targetVertex = null;

	//Default constructor
	public AStarGraph()
	{
		super();
	}
	
	//Getters and Setters
	public Vertex<E> getStartingPointVertex() 
	{
		return startingPointVertex;
	}

	public void setStartingPointVertex(Vertex<E> startingPointVertex) 
	{
		this.startingPointVertex = startingPointVertex;
	}

	public Vertex<E> getTargetVertex() 
	{
		return targetVertex;
	}

	public void setTargetVertex(Vertex<E> targetVertex) 
	{
		this.targetVertex = targetVertex;
	}
	
	
	//Other Methods
	public void saveGraphToFile()
	{
		//Ask the user for a filename
		//Save it :P
	}
	
	public LinkedQueue findShortestPath()
	{
		Vertex curVertex = startingPointVertex;
		
		double lowestScore = Double.MAX_VALUE;
		double curScore;
		
		Pair<Vertex<E>, Double> curPair;
		Pair<Vertex<E>, Double> shortestPair = null;
		
		
		Collection pairs;
        Iterator<Pair<Vertex<E>, Double>> pairIterator;
		
        double edgeWeight;
        double distanceFromEdgeToTarget;
        Vertex<AStarTile> tempEdge;
        
        LinkedQueue path = new LinkedQueue();
        
        
        //Add the startingVertex
        curVertex.visit();
        path.enqueue(new Pair(curVertex, 0));
        System.out.print(curVertex.getData().toString() + "\n");
        
        //Add the remaining vertices
        while(curVertex != targetVertex)
		{
			pairs = curVertex.adjList.values();
       	 	pairIterator = pairs.iterator();
       	 
	        while(pairIterator.hasNext()) 
	        {
	        	curPair = pairIterator.next();
	        	tempEdge = (Vertex<AStarTile>) curPair.first; // Vertex address
	        	
	        	if(!tempEdge.isVisited())
	        	{
		        	edgeWeight = ((Double) curPair.second).doubleValue();
		        	distanceFromEdgeToTarget = Math.sqrt(Math.pow((tempEdge.getData().x - ((Vertex<AStarTile>) targetVertex).getData().x),2) + Math.pow((tempEdge.getData().y - ((Vertex<AStarTile>) targetVertex).getData().y),2));
		        	
		        	curScore = edgeWeight + distanceFromEdgeToTarget;
		        	
		        	if(curScore < lowestScore)
		        	{
		        		lowestScore = curScore;
		        		shortestPair = curPair;
		        	}
	        	}
	        }
	        
	        curVertex.visit();
	        curVertex = shortestPair.first;
	        path.enqueue(shortestPair);
	        lowestScore = Double.MAX_VALUE;
	        System.out.print(curVertex.getData().toString() + "\n");
		}
        
		return path;
	}
	
	
}
