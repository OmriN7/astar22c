package astar22c;

public class AStarVertex<E> extends Vertex
{
	//Instance Variables
	public float x;
	public float y;
	
	//Constructors
	AStarVertex(E data, int x, int y)
	{
		super(data);
		this.y = y;
		this.x = x;
	}
	
	AStarVertex() { this(null, 0, 0); }
	
	//Getters and Setters
	public float getX() { return x; }
	public float getY() { return y; }
	
	public void setX(float x){ this.x = x; }
	public void setY(float y){ this.y = y; }
	
	//VAUGHN 
	//Make a "toString" method here...
}
