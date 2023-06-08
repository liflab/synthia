package examples.parcel_shipment;
import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.random.RandomBoolean;

/**
 * @author Marc-Antoine Plourde
 */
public class Parcel implements Picker<Point>
{
	protected int m_id;
	protected RandomBoolean m_direction;
	protected RandomBoolean m_error;
	protected Point m_initialLocation;
	protected Point m_actualLocation;
	protected Point m_destination;
	protected int m_status;

	private Parcel(int id,RandomBoolean direction,RandomBoolean error,Point initial_location, Point actual_location,
			Point destination, int status)
	{
		m_id = id;
		m_direction = direction;
		m_initialLocation = initial_location;
		m_actualLocation = actual_location;
		m_destination = destination;
		m_error =error;
		m_status = status;
	}

	public Parcel(int id,RandomBoolean direction, RandomBoolean error, Point actual_location, Point destination)
	{
		m_id = id;
		m_direction = direction;
		m_error = error;
		m_initialLocation = actual_location;
		m_actualLocation = actual_location;
		m_destination = destination;
		m_status = 0;
	}

	public int getID()
	{
		return m_id;
	}

	public Point getActualLocation()
	{
		return m_actualLocation;
	}

	public Point getDestination()
	{
		return m_destination;
	}

	public int getStatus()
	{
		return m_status;
	}

	public boolean isArrived()
	{
		if ((m_actualLocation.getX() == m_destination.getX()) && (m_actualLocation.getY()
				== m_destination.getY()))
		{
			return true;
		}
		else return false;
	}

	private void moveLeft()
	{
		m_actualLocation =new Point(m_actualLocation.getX()-1, m_actualLocation.getY());
	}

	private void moveRight()
	{
		m_actualLocation =new Point(m_actualLocation.getX()+1, m_actualLocation.getY());
	}

	private void moveUp()
	{
		m_actualLocation =new Point(m_actualLocation.getX(), m_actualLocation.getY()+1);
	}

	private void moveDown()
	{
		m_actualLocation =new Point(m_actualLocation.getX(), m_actualLocation.getY()-1);
	}

	private void horizontalMove()
	{
		if (m_actualLocation.getX()>m_destination.getX())
		{
			moveLeft();
		}
		else
		{
			moveRight();
		}
	}

	private void verticalMove()
	{
		if(m_actualLocation.getY()>m_destination.getY())
		{
			moveDown();
		}
		else
		{
			moveUp();
		}
	}

	@Override
	public void reset()
	{
		m_actualLocation =new Point(m_initialLocation.getX(), m_initialLocation.getY());
		m_direction.reset();
		m_error.reset();
	}

	private void errorMove()
	{
		boolean error_move_vertical = m_direction.pick();
		boolean positive_move = m_direction.pick();

		if(error_move_vertical)
		{
			if (positive_move)
			{
				moveUp();
			}
			else
			{
				moveDown();
			}
		}
		else
		{
			if (positive_move)
			{
				moveRight();
			}
			else
			{
				moveLeft();
			}
		}
	}

	@Override
	public Point pick()
	{
		if (!isArrived())
		{
			boolean move_vertical = m_direction.pick();
			boolean is_error =m_error.pick();

			if (is_error)
			{
				errorMove();
			}
			else if ((move_vertical)&&(m_actualLocation.getY()==m_destination.getY()))
			{

				horizontalMove();
			}
			else if ((!move_vertical)&&(m_actualLocation.getX()==m_destination.getX()))
			{
				verticalMove();
			}
			else
			{
				if (move_vertical)
				{
					verticalMove();
				}
				else
				{
					horizontalMove();
				}
			}
		}
		return m_actualLocation;
	}

	@Override
	public Parcel duplicate(boolean with_state)
	{

		if (with_state)
		{
			return new Parcel(m_id,m_direction.duplicate(true),m_error.duplicate(true),
					new Point(m_initialLocation.getX(), m_initialLocation.getY()),
					new Point(m_actualLocation.getX(), m_actualLocation.getY()),
					new Point(m_destination.getX(),m_destination.getY()),m_status);
		}
		else
		{
			return new Parcel(m_id,m_direction.duplicate(false),m_error.duplicate(false),
					new Point(m_initialLocation.getX(), m_initialLocation.getY()),
					new Point(m_initialLocation.getX(), m_initialLocation.getY()),
					new Point(m_destination.getX(),m_destination.getY()),m_status);
		}

	}
}
