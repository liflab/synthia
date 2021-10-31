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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Representation of a line of Apache's access log. This class contains all
 * the fields that Apache displays in its standard log format (IP address,
 * URL, timestamp, return code, file size).
 * @ingroup Examples
 */
public class LogLine
{
	/**
	 * The possible status codes associated to each request
	 */
	public enum StatusCode {OK, NOT_FOUND, INTERNAL_SERVER_ERROR}
	
	/**
	 * A formatter for dates
	 */
	protected static final transient DateFormat m_dateFormat = new SimpleDateFormat("d-MMM-yyyy HH:mm:ss z");

	/**
	 * The IP address of an HTTP request
	 */
	protected String m_ipAddress;

	/**
	 * The date the request was made
	 */
	protected long m_timestamp;

	/**
	 * The GMT offset of the timestamp
	 */
	protected int m_timeZone;

	/**
	 * The HTTP request made
	 */
	protected Request m_request;

	/**
	 * The size of the object requested
	 */
	protected int m_size;
	
	/**
	 * The status code that the server sends back to the client
	 */
	protected StatusCode m_statusCode;
	
	public LogLine(String ip, long timestamp, int timezone, Request req, StatusCode code, int size)
	{
		super();
		m_ipAddress = ip;
		m_timestamp = timestamp;
		m_timeZone = timezone;
		m_request = req;
		m_statusCode = code;
		m_size = size;
	}

	@Override
	public String toString()
	{
		StringBuilder out = new StringBuilder();
		out.append(m_ipAddress);
		out.append(" - - ");
		out.append("[").append(formatDate(m_timestamp, m_timeZone)).append("]");
		out.append(" \"").append(m_request.toString()).append("\" ");
		out.append(getCode(m_statusCode)).append(" ").append(m_size);
		return out.toString();
	}

	protected static String formatDate(long timestamp, long timezone)
	{
		Date d = new Date(timestamp);
		return m_dateFormat.format(d) + " " + timezone;
	}

	protected static int getCode(StatusCode code)
	{
		switch (code)
		{
		case OK:
			return 200;
		case NOT_FOUND:
			return 404;
		case INTERNAL_SERVER_ERROR:
			return 500;
		default:
			return 500;
		}
	}
	
	/**
	 * Simple representation of an HTTP request
	 */
	public static class Request
	{
		public enum Method {GET}
		
		protected String m_url;
		
		protected Method m_method;
		
		public Request(Method m, String url)
		{
			super();
			m_method = m;
			m_url = url;
		}
		
		@Override
		public String toString()
		{
			return m_method + " " + m_url + " HTTP/2";
		}
	}
}
