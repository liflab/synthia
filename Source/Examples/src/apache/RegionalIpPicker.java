package apache;

import ca.uqac.lif.synthia.IndexPicker;
import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.util.Choice;

/**
 * A picker that generates IP addresses, with different probabilities
 * associated to different regions. For the purpose of the simulation:
 * <ul>
 * <li>addresses of the form <tt>11.x.x.x</tt> are considered to be in the USA
 * and have a 1/2 probability of being generated</li>
 * <li>addresses of the form <tt>10.x.x.x</tt> are considered to be in Canada
 * and have a 1/3 probability of being generated</li>
 * <li>addresses in the range form <tt>20.x.x.x</tt>-<tt>60.x.x.x</tt> are
 * considered to be in Europe and have a 1/6 probability of being generated</li>
 * </ul>
 */
public class RegionalIpPicker implements Picker<String>
{
	/*@ non_null @*/ Choice<IpRegion> m_regionPicker;
	
	public RegionalIpPicker(/*@ non_null @*/ Picker<Float> picker, /*@ non_null @*/ IndexPicker i_picker)
	{
		super();
		m_regionPicker = new Choice<IpRegion>(picker);
		m_regionPicker.add(new CanadaRegion(i_picker), 1/3);
		m_regionPicker.add(new UsRegion(i_picker), 1/2);
		m_regionPicker.add(new EuropeRegion(i_picker), 1/6);
	}
	
	protected RegionalIpPicker(/*@ non_null @*/ Choice<IpRegion> r_picker)
	{
		super();
		m_regionPicker = r_picker;
	}
	
	@Override
	public String pick()
	{
		IpRegion r = m_regionPicker.pick();
		return r.pick();
	}
	
	@Override
	public void reset()
	{
		m_regionPicker.reset();
	}

	@Override
	public Picker<String> duplicate(boolean with_state)
	{
		return new RegionalIpPicker(m_regionPicker.duplicate(with_state));
	}
	
	protected static abstract class IpRegion implements Picker<String>
	{
		/*@ non_null @*/ protected IndexPicker m_picker;
		
		public IpRegion(/*@ non_null @*/ IndexPicker picker)
		{
			super();
			m_picker = picker;
		}
		
		@Override
		public void reset()
		{
			m_picker.reset();
		}
	}
	
	protected static class CanadaRegion extends IpRegion
	{
		public CanadaRegion(/*@ non_null @*/ IndexPicker picker)
		{
			super(picker);
		}
		
		@Override
		/*@ non_null @*/ public String pick()
		{
			m_picker.setInterval(256);
			return "10." + m_picker.pick() + "." + m_picker.pick() + "." + m_picker.pick();
		}

		@Override
		public CanadaRegion duplicate(boolean with_state)
		{
			return new CanadaRegion(m_picker.duplicate(with_state));
		}
	}
	
	protected static class UsRegion extends IpRegion
	{
		public UsRegion(/*@ non_null @*/ IndexPicker picker)
		{
			super(picker);
		}
		
		@Override
		/*@ non_null @*/ public String pick()
		{
			m_picker.setInterval(256);
			return "11." + m_picker.pick() + "." + m_picker.pick() + "." + m_picker.pick();
		}
		
		@Override
		public UsRegion duplicate(boolean with_state)
		{
			return new UsRegion(m_picker.duplicate(with_state));
		}
	}
	
	protected static class EuropeRegion extends IpRegion
	{
		public EuropeRegion(/*@ non_null @*/ IndexPicker picker)
		{
			super(picker);
		}
		
		@Override
		/*@ non_null @*/ public String pick()
		{
			m_picker.setInterval(40);
			String prefix = (m_picker.pick() + 20) + ".";
			m_picker.setInterval(256);
			return prefix + m_picker.pick() + "." + m_picker.pick() + "." + m_picker.pick();
		}
		
		@Override
		public EuropeRegion duplicate(boolean with_state)
		{
			return new EuropeRegion(m_picker.duplicate(with_state));
		}
	}
}
