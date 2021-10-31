/*
    Synthia, a data structure generator
    Copyright (C) 2019-2020 Laboratoire d'informatique formelle
    Université du Québec à Chicoutimi, Canada

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package examples.apache;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.sequence.MarkovChain;
import ca.uqac.lif.synthia.util.Freeze;
import examples.apache.LogLine.Request;
import examples.apache.LogLine.StatusCode;
import examples.apache.LogLine.Request.Method;

/**
 * A picker producing instances of visitors.
 * @ingroup Examples
 */
public class VisitorPicker implements Picker<Picker<LogLine>>
{
	protected Picker<Float> m_floatSource;
	
	protected Picker<String> m_ip;
	
	protected MarkovChain<String> m_map;
	
	protected Picker<Long> m_timestamp;
	
	public VisitorPicker(Picker<Long> timestamp, Picker<String> ip, MarkovChain<String> map)
	{
		super();
		m_ip = ip;
		m_map = map;
		m_timestamp = timestamp;
	}
	
	@Override
	public Picker<LogLine> pick()
	{
		MarkovChain<String> map_dup = m_map.duplicate(false);
		return new LogLinePicker(m_timestamp, new Freeze<String>(m_ip), map_dup);
	}

	@Override
	public Picker<Picker<LogLine>> duplicate(boolean with_state)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reset()
	{
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Picker that produces log lines.
	 */
	protected static class LogLinePicker implements Picker<LogLine>
	{
		/**
		 * The picker for the timestamp of the log line.
		 */
		protected Picker<Long> m_timestamp;
		
		/**
		 * The picker for the IP address of the log line.
		 */
		protected Picker<String> m_ip;
		
		/**
		 * The picker for the URL of the log line.
		 */
		protected Picker<String> m_url;
		
		/**
		 * Creates a new log line picker.
		 * @param timestamp The picker for the timestamp of the log line
		 * @param ip The picker for the IP address of the log line
		 * @param url The picker for the URL of the log line
		 */
		public LogLinePicker(Picker<Long> timestamp, Picker<String> ip, Picker<String> url)
		{
			super();
			m_timestamp = timestamp;
			m_ip = ip;
			m_url = url;
		}

		@Override
		public void reset()
		{
			m_timestamp.reset();
			m_ip.reset();
			m_url.reset();
		}

		@Override
		public LogLine pick()
		{
			return new LogLine(m_ip.pick(), m_timestamp.pick(), 0, new Request(Method.GET, m_url.pick()), StatusCode.OK, 1000);
		}

		@Override
		public LogLinePicker duplicate(boolean with_state)
		{
			return new LogLinePicker(m_timestamp.duplicate(with_state), m_ip.duplicate(with_state), m_url.duplicate(with_state));
		}
	}
}
