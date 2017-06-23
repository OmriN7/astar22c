package astar22c;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;

import astar22c.ArrayToGraph.TileType;

public class AStarGraph<E> extends Graph<AStarTile<E>>
{
	//Instance Variables
	private AStarTile<E> startingPointVertex = null;
	private AStarTile<E> targetVertex = null;
	private LinkedStack<Pair<Vertex<AStarTile<E>>, Double>> path = null;
	private LinkedStack<Pair<AStarTile<E>,AStarTile<E>>> removedEdgePairs = new LinkedStack<>();
	private LinkedStack<Double> removedcosts = new LinkedStack<>();

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
	public void findShortestPath()
	{
		Vertex<AStarTile<E>> curVertex = vertexSet.get(startingPointVertex);
		Vertex<AStarTile<E>> startVertex = curVertex;
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
        
        path = new LinkedStack<Pair<Vertex<AStarTile<E>>, Double>>();
        
        
        //Add the startingVertex
        curVertex.visit();
        
        //Go to target and mark possible steps
        while(curVertex != endVertex)
		{
        	//graphToTableString();
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

	        //Check for dead-end
	        if(curVertex == shortestPair.first)
	        {
	        	if(!path.isEmpty())
	        	{
	        		curVertex = path.pop().first;
	        	}
	        	else
	        	{
	        		System.out.print("The graph does not have a path from the start to the target.\n");
	        		return;
	        	}
	        }
	        else
	        {
	        	path.push(shortestPair);
		        curVertex.visit();
		        curVertex = shortestPair.first;
	        }
	        lowestScore = Double.MAX_VALUE;
		}

        //Empty the path
        while(!path.isEmpty())
        {
        	path.pop();
        }
        
        //Step back
        path.push(shortestPair);
        while(curVertex != startVertex)
        {
			pairs = curVertex.adjList.values();
       	 	pairIterator = pairs.iterator();
       	 
	        while(pairIterator.hasNext()) 
	        {
	        	curPair = pairIterator.next();
	        	tempEdge = (Vertex<AStarTile<E>>) curPair.first; // Vertex address
	        	
	        	if(tempEdge.isVisited())
	        	{
		        	edgeWeight = ((Double) curPair.second).doubleValue();
		        	distanceFromEdgeToTarget = Math.sqrt(Math.pow((tempEdge.getData().x - startingPointVertex.x),2) + Math.pow((tempEdge.getData().y - startingPointVertex.y),2));
		        	
		        	curScore = edgeWeight + distanceFromEdgeToTarget;
		        	
		        	if(curScore < lowestScore)
		        	{
		        		lowestScore = curScore;
		        		shortestPair = curPair;
		        	}
	        	}
	        }
	        //Check for dead-end
	        if(curVertex == shortestPair.first)
	        {
	        	curVertex = path.pop().first;
	        }
	        else
	        {
		        curVertex.unvisit();
		        curVertex = shortestPair.first;
		        path.push(shortestPair);
	        }
	        lowestScore = Double.MAX_VALUE;
	        
        }
	}// Written by Omri
	
	public String graphToTableString()
	{
		String returnValue = "";
		int widthSize = 0;
		int heightSize = 0;

		Iterator<Entry<AStarTile<E>, Vertex<AStarTile<E>>>> iter; 
		Vertex<AStarTile<E>> tempVert;

		iter = vertexSet.entrySet().iterator();
		while(iter.hasNext())
		{
			tempVert = iter.next().getValue();
			
			if(tempVert.data.x > widthSize)
			{
				widthSize = (int) tempVert.data.x;
			}

			if(tempVert.data.y > heightSize)
			{
				heightSize = (int) tempVert.data.y;
			}
		}
		
		widthSize = widthSize + 2;
		heightSize = heightSize + 1;
		
		char table[][] = new char[heightSize][widthSize];
		
		for(int h = 0; h < heightSize; h++)
		{
			for(int w = 0; w < (widthSize-1); w++)
			{
				table[h][w] = ' ';
			}
			table[h][(widthSize-1)] = '\n';
		}
		
		iter = vertexSet.entrySet().iterator();
		while(iter.hasNext())
		{
			tempVert = iter.next().getValue();
			int tempX = (int) tempVert.data.x;
			int tempY = (int) tempVert.data.y;
			TileType type = (TileType) tempVert.data.data;
			
			if(type == TileType.OPEN)
			{
				table[tempY][tempX] = 'O';
			}
			else if(type == TileType.START)
			{
				table[tempY][tempX] = 'S';
			}
			else if(type == TileType.TARGET)
			{
				table[tempY][tempX] = 'T';
			}
		}
		
		if(path != null)
		{
			while(!path.isEmpty())
			{
				AStarTile<E> v = path.pop().first.getData();
				table[(int)v.y][(int)v.x] = 'X';
			}
		}
		
		for(int h = 0; h < heightSize; h++)
		{
			for(int w = 0; w < widthSize; w++)
			{
				returnValue = returnValue + table[h][w];
			}
		}

		return returnValue;
	}// Written by Omri

	
	@Override
	public boolean remove(AStarTile<E> start, AStarTile<E> end)
	{
		double cost = vertexSet.get(start).adjList.get(end).second;
		boolean result = super.remove(start, end);
		if(result)
		{
			removedEdgePairs.push(new Pair<AStarTile<E>, AStarTile<E>>(start, end));
			removedcosts.push(cost);
		}
		return result;
	}// Written by Yang
	
	public boolean undoRemoval()
	{
		if(!removedEdgePairs.isEmpty() && !removedcosts.isEmpty())
		{
			Pair<AStarTile<E>,AStarTile<E>> removedEdgePair = removedEdgePairs.pop();
					
			super.addEdge(removedEdgePair.first, removedEdgePair.second, removedcosts.pop());
			return true;
		}
		else
		{
			return false;
		}
			
	}// Written by Yang & Omri
}
