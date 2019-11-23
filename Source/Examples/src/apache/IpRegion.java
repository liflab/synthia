package apache;

import ca.uqac.lif.synthia.IndexPicker;

public abstract class IpRegion
{
	/*@ non_null @*/ protected IndexPicker m_picker;
	
	/*@ non_null @*/ public abstract String getAddress();
	
	public IpRegion(/*@ non_null @*/ IndexPicker picker)
	{
		super();
		m_picker = picker;
	}
	
	public class CanadaRegion extends IpRegion
	{
		public CanadaRegion(/*@ non_null @*/ IndexPicker picker)
		{
			super(picker);
		}
		
		@Override
		/*@ non_null @*/ public String getAddress()
		{
			m_picker.setRange(256);
			return "10." + m_picker.pick() + "." + m_picker.pick() + "." + m_picker.pick();
		}
	}
	
	public class UsRegion extends IpRegion
	{
		public UsRegion(/*@ non_null @*/ IndexPicker picker)
		{
			super(picker);
		}
		
		@Override
		/*@ non_null @*/ public String getAddress()
		{
			m_picker.setRange(256);
			return "11." + m_picker.pick() + "." + m_picker.pick() + "." + m_picker.pick();
		}
	}
	
	public class EuropeRegion extends IpRegion
	{
		public EuropeRegion(/*@ non_null @*/ IndexPicker picker)
		{
			super(picker);
		}
		
		@Override
		/*@ non_null @*/ public String getAddress()
		{
			m_picker.setRange(40);
			String prefix = (m_picker.pick() + 20) + ".";
			m_picker.setRange(256);
			return prefix + m_picker.pick() + "." + m_picker.pick() + "." + m_picker.pick();
		}
	}
}
