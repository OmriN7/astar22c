package astar22c;

//Omri & Yang
public class AStarTile
{
	//Instance Variables
	public float x;
	public float y;
	
	//Constructors
	AStarTile(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	AStarTile() { this(0, 0); }
	
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