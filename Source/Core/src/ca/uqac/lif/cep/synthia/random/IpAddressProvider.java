package ca.uqac.lif.cep.synthia.random;

import ca.uqac.lif.synthia.Picker;

public class IpAddressProvider implements Picker<String>
{
	protected Picker<Integer> m_source;
	
	public IpAddressProvider(Picker<Integer> src)
	{
		super();
		m_source = src;
	}
	
	@Override
	/*@ nullable @*/ public String pick()
	{
		int b1 = m_source.pick();
		int b2 = m_source.pick();
		int b3 = m_source.pick();
		int b4 = m_source.pick();
		return b1 + "." + b2 + "." + b3 + "." + b4;
	}
	
	@Override
	public void reset()
	{
		// Nothing to do
	}
	
	@Override
	public IpAddressProvider duplicate(boolean with_state)
	{
		return new IpAddressProvider(m_source);
	}
}
