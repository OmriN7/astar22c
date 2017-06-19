package astar22c;

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
		return data + "[x=" + x + ", y=" + y + "]";
	}
	
	public boolean equals(Object anObject)
	{
        if (this == anObject)
        {
            return true;
        }
        if (anObject instanceof AStarTile<?>)
        {
        	AStarTile<E> anotherTile = (AStarTile<E>)anObject;
    		
    		return x == anotherTile.x &&
    				y == anotherTile.y &&
    				data == anotherTile.data;
        }
        return false;
    }//Yang
	
	//Override a hashCode method so the Graph.vertexSet knows how to hash AStarTile
    public int hashCode()
    {
        int h = ((int)(x) & 0xffff) | ((int)(y) << 16);
        return h;
    }//Yang
    
} // Written by Omri & Yang