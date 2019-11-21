package ca.uqac.lif.cep.synthia.random;

import ca.uqac.lif.synthia.Picker;

public class StringPatternProvider implements Picker<String>
{
	/*@ non_null @*/ protected Picker<?>[] m_providers;
	
	/*@ non_null @*/ protected String m_pattern;
	
	public StringPatternProvider(/*@ non_null @*/ String pattern, /*@ non_null @*/ Picker<?> ... providers)
	{
		super();
		m_pattern = pattern;
		m_providers = providers;
	}

	@Override
	public void reset() 
	{
		for (Picker<?> p : m_providers)
		{
			p.reset();
		}
	}

	@Override
	public String pick()
	{
		String[] parts = new String[m_providers.length];
		for (int i = 0; i < parts.length; i++)
		{
			parts[i] = m_providers[i].pick().toString();
		}
		String out = m_pattern;
		for (int i = 0; i < parts.length; i++)
		{
			out = out.replaceAll("\\{\\$" + i + "\\}", parts[i]);
		}
		return out;
	}

	@Override
	public StringPatternProvider duplicate(boolean with_state)
	{
		Picker<?>[] new_provs = new Picker<?>[m_providers.length];
		for (int i = 0; i < m_providers.length; i++)
		{
			new_provs[i] = m_providers[i].duplicate(with_state);
		}
		return new StringPatternProvider(m_pattern, new_provs);
	}
}
