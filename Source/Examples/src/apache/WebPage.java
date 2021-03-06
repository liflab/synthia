package apache;

import apache.LogLine.Request;
import apache.LogLine.StatusCode;
import ca.uqac.lif.synthia.replay.Playback;
import ca.uqac.lif.synthia.util.Tick;

public class WebPage extends Playback<LogLine>
{
	public WebPage(String ip, int timezone, Tick t, String[] urls, int[] sizes)
	{
		super(generateLines(ip, timezone, t, urls, sizes));
	}

	protected static LogLine[] generateLines(String ip, int timezone, Tick t, String[] urls, int[] sizes)
	{
		LogLine[] lines = new LogLine[urls.length];
		for (int i = 0; i < urls.length; i++)
		{
			Request req = new Request(Request.Method.GET, urls[i]);
			LogLine line = new LogLine(ip, (long) t.pick(), timezone, req, StatusCode.OK, sizes[i]);
			lines[i] = line;
		}
		return lines;
	}
}
