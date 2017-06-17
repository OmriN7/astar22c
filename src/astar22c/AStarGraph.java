package astar22c;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class AStarGraph<E> extends Graph
{
	//Instance Variables
	private AStarVertex<E> startingPointVertex = null;
	private AStarVertex<E> targetVertex = null;

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
		Vertex curVertex = startingPointVertex;
		
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
	        	
	        	if(!tempEdge.isVisited())
	        	{
		        	tempWeight = ((Double) curPair.second).doubleValue(); // Weight to that vertex
		        	
		        	double a = tempEdge.x-targetVertex.x;
		        	double b = tempEdge.y-targetVertex.y;
		        	
		        	double q = tempEdge.x;
		        	double w = tempEdge.y;
		        	
		        	curScore = tempWeight + Math.sqrt(Math.pow(a,2) + Math.pow(b,2));
		        	
		        	if(curScore < lowestScore)
		        	{
		        		lowestScore = curScore;
		        		shorterPair = curPair;
		        	}
	        	}
	        }
	        
	        curVertex.visit();
	        curVertex = shorterPair.first;
	        path.enqueue(shorterPair);
	        lowestScore = Double.MAX_VALUE;
		}
        
		return path;
	}
	
	
}
