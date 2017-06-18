package astar22c;

//Omri & Yang

public class AStarTile<E>
{
	//Instance Variables
	public float x;
	public float y;
	
	E data;
	
	//Constructors
	AStarTile(float x, float y, E inData)
	{
		this.x = x;
		this.y = y;
		this.data = inData;
	}
	
	//Getters and Setters
	public float getX() { return x; }
	public float getY() { return y; }
	
	public void setX(float x){ this.x = x; }
	public void setY(float y){ this.y = y; }

	@Override
	public String toString() 
	{
		return "AStarTile [x=" + x + ", y=" + y + "]";
	}

}