package ca.uqac.lif.synthia.relative;



import java.util.ArrayList;
import java.util.List;

/**
 * Class for test use. Returns the list of all the substrings of a string.
 */
public class SubStrings
{
	protected String m_string;

	protected List<String> m_subStrings;

	public SubStrings(String s)
	{
		m_string = s;
		m_subStrings = new ArrayList<String>();
		fillList();
	}

	protected void fillList()
	{
		m_subStrings.add("");
		for (int start_index = 0; start_index < m_string.length(); start_index++)
		{
			for (int end_index = start_index + 1; end_index <= m_string.length(); end_index++)
			{
				m_subStrings.add(m_string.substring(start_index, end_index));
			}
		}
	}

	public List<String> getSubStrings()
	{
		return m_subStrings;
	}

	public void print()
	{
		for (String m_subString : m_subStrings)
		{
			System.out.println(m_subString);
		}
	}

	protected void resetList()
	{
		m_subStrings.clear();
	}

	public void newString(String s)
	{
		m_string = s;
		resetList();
		fillList();
	}


}
