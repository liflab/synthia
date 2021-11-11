package examples.parcel_shipment;

import ca.uqac.lif.synthia.Picker;

import ca.uqac.lif.synthia.random.RandomBoolean;
import ca.uqac.lif.synthia.random.RandomInteger;


public class ParcelPicker implements Picker<Parcel>
{
	protected RandomInteger m_position; // internal picker

	protected RandomBoolean m_directionParcel;  // picker to create picker generation
	protected RandomBoolean m_errorParcel;      // picker to create picker generation

	public ParcelPicker(RandomInteger position, RandomBoolean direction_parcel, RandomBoolean error_parcel)
	{
		m_position = position;

		m_directionParcel = direction_parcel;
		m_errorParcel = error_parcel;
	}

	public ParcelPicker(int radius, RandomBoolean direction_parcel, float error_rate)
	{
		radius = Math.abs(radius);
		m_position = new RandomInteger(Math.negateExact(radius),radius);



		m_directionParcel = direction_parcel;
		m_errorParcel = new RandomBoolean(1 - error_rate); // the prob of getting true is 1 - error rate
	}

	@Override public void reset()
	{
		m_position.reset();
		m_errorParcel.reset();
		m_directionParcel.reset();
	}

	public void setParcelArea(int radius)
	{
		radius = Math.abs(radius);

		m_position.setInterval(Math.negateExact(radius),radius);
	}

	public void setErrorRate(float error_rate)
	{
		error_rate = Math.abs(error_rate);
		m_errorParcel.setTrueProbability(1-error_rate);
	}


	@Override public Parcel pick()
	{

		Point p1 =new Point(m_position.pick(), m_position.pick());
		Point p2 =new Point(m_position.pick(), m_position.pick());

		while ((p1.getX()==p2.getX()) && (p1.getY()==p2.getY()))
		{
			p2=new Point(m_position.pick(), m_position.pick());
		}


		return new Parcel(0, m_directionParcel, m_errorParcel, p1, p2);
	}

	@Override public ParcelPicker duplicate(boolean with_state)
	{

		return new ParcelPicker(m_position.duplicate(with_state), m_directionParcel.duplicate(with_state),
				m_errorParcel.duplicate(with_state));
	}
}
