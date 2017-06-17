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

	public void setStartingPointVertex(AStarVertex<E> startingPointVertex) 
	{
		this.startingPointVertex = startingPointVertex;
	}

	public Vertex<E> getTargetVertex() 
	{
		return targetVertex;
	}

	public void setTargetVertex(AStarVertex<E> targetVertex) 
	{
		this.targetVertex = targetVertex;
	}
	
	//Other Methods
	public AStarVertex<E> addVertex(E data, float x, float y)
	{
		AStarVertex v = new AStarVertex<E>(data,x ,y);
		v.setY(x);
		v.setY(y);

		vertexSet.put(data, v);
		
		return v;
		
		
	}
	public LinkedQueue findShortestPath()
	{
		AStarVertex curVertex = (AStarVertex)startingPointVertex;
		
		double lowestScore = Double.MAX_VALUE;
		double curScore;
		
		Pair<Vertex<E>, Double> curPair;
		Pair<Vertex<E>, Double> shorterPair = null;
		
		
		Collection pairs;
        Iterator<Pair<Vertex<E>, Double>> pairIterator;
		
        double tempWeight;
        AStarVertex<Integer> tempEdge;
        
        LinkedQueue path = new LinkedQueue();
        
        while(curVertex != targetVertex)
		{
			pairs = curVertex.adjList.values();
       	 	pairIterator = pairs.iterator();
       	 
	        while(pairIterator.hasNext()) 
	        {
	        	curPair = pairIterator.next();

	        	tempEdge = (AStarVertex<Integer>) curPair.first; // Vertex address
	        	tempWeight = ((Double) curPair.second).doubleValue(); // Weight to that vertex
	        	
	        	curScore = tempWeight + Math.sqrt(Math.pow(tempEdge.x,2) + Math.pow(tempEdge.y,2));
	        	
	        	if(curScore < lowestScore)
	        	{
	        		lowestScore = curScore;
	        		curPair = shorterPair;
	        	}	
	        }
	        
	        path.enqueue(shorterPair);
	        lowestScore = Double.MAX_VALUE;
		}
        
		return path;
	}
	
	
}
