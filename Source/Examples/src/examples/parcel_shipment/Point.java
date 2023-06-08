package examples.parcel_shipment;

public class Point
{
	protected int m_x;
	protected int m_y;


	public Point(int x, int y)
	{
		this.m_x = x;
		this.m_y = y;
	}

	public int getX()
	{
		return m_x;
	}

	public int getY()
	{
		return m_y;
	}
}
