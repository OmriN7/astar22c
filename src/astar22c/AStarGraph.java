package astar22c;

import java.util.Collection;
import java.util.Iterator;

public class AStarGraph<E> extends Graph<AStarTile<E>>
{
	//Instance Variables
	private AStarTile<E> startingPointVertex = null;
	private AStarTile<E> targetVertex = null;

	//Default constructor
	public AStarGraph()
	{
		super();
	}
	
	//Getters and Setters
	public AStarTile<E> getStartingPointVertex() 
	{
		return startingPointVertex;
	}

	public void setStartingPointVertex(AStarTile<E> newTile) 
	{
		this.startingPointVertex = newTile;
	}

	public AStarTile<E> getTargetVertex() 
	{
		return targetVertex;
	}

	public void setTargetVertex(AStarTile<E> targetVertex) 
	{
		this.targetVertex = targetVertex;
	}
	
	
	//Other Methods
	public void saveGraphToFile()
	{
		//Ask the user for a filename
		//Save it :P
	}
	
	public LinkedQueue<Pair<Vertex<AStarTile<E>>, Double>> findShortestPath()
	{
		Vertex<AStarTile<E>> curVertex = vertexSet.get(startingPointVertex);
		Vertex<AStarTile<E>> endVertex = vertexSet.get(targetVertex);
		
		double lowestScore = Double.MAX_VALUE;
		double curScore;
		
		Pair<Vertex<AStarTile<E>>, Double> curPair;
		Pair<Vertex<AStarTile<E>>, Double> shortestPair = null;
		
		
		Collection<Pair<Vertex<AStarTile<E>>, Double>> pairs;
        Iterator<Pair<Vertex<AStarTile<E>>, Double>> pairIterator;
		
        double edgeWeight;
        double distanceFromEdgeToTarget;
        Vertex<AStarTile<E>> tempEdge;
        
        LinkedQueue<Pair<Vertex<AStarTile<E>>, Double>> path = new LinkedQueue<Pair<Vertex<AStarTile<E>>, Double>>();
        
        
        //Add the startingVertex
        curVertex.visit();
        path.enqueue(new Pair<Vertex<AStarTile<E>>, Double>(curVertex, 0.0));
        System.out.print(curVertex.getData().toString() + "\n");
        
        //Add the remaining vertices
        while(curVertex != endVertex)
		{
			pairs = curVertex.adjList.values();
       	 	pairIterator = pairs.iterator();
       	 
	        while(pairIterator.hasNext()) 
	        {
	        	curPair = pairIterator.next();
	        	tempEdge = (Vertex<AStarTile<E>>) curPair.first; // Vertex address
	        	
	        	if(!tempEdge.isVisited())
	        	{
		        	edgeWeight = ((Double) curPair.second).doubleValue();
		        	distanceFromEdgeToTarget = Math.sqrt(Math.pow((tempEdge.getData().x - targetVertex.x),2) + Math.pow((tempEdge.getData().y - targetVertex.y),2));
		        	
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
