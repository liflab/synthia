package ca.uqac.lif.synthia.map;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.random.RandomInteger;

import java.util.ArrayList;
import java.util.List;

public class AreaMapPicker implements Picker<AreaMap>
{
	protected Picker<Integer> m_widthPicker;
	protected Picker<Integer> m_heightPicker;
	protected Picker<Integer> m_hubsNbPicker;
	protected RandomInteger	m_xPicker;
	protected RandomInteger m_yPicker;


	public AreaMapPicker(Picker<Integer> width_picker, Picker<Integer> height_picker,
			Picker<Integer> hubs_nb_picker, RandomInteger x_picker, RandomInteger y_picker)
	{
		this.m_widthPicker = width_picker;
		this.m_heightPicker = height_picker;
		this.m_hubsNbPicker = hubs_nb_picker;
		this.m_xPicker = x_picker;
		this.m_yPicker = y_picker;
	}

	@Override public void reset()
	{
		m_heightPicker.reset();
		m_hubsNbPicker.reset();
		m_widthPicker.reset();
		m_xPicker.reset();
		m_yPicker.reset();
	}

	@Override public AreaMap pick()
	{
		int width = m_widthPicker.pick();
		int height = m_heightPicker.pick();
		int hubs_nb = m_hubsNbPicker.pick();

		m_xPicker.setInterval(0,width);
		m_yPicker.setInterval(0,height);


		return new AreaMap(width,height,generateHubList(hubs_nb));
	}

	private List<Point> generateHubList(int hub_nb)
	{
		List<Point> hubs = new ArrayList<>();
		for (int i = 0; i < hub_nb; i++)
		{
			hubs.add(new Point(m_xPicker.pick(),m_yPicker.pick()));
		}

		return hubs;
	}

	@Override public Picker<AreaMap> duplicate(boolean with_state)
	{
		return new AreaMapPicker(m_widthPicker.duplicate(with_state),
														 m_heightPicker.duplicate(with_state),
														 m_hubsNbPicker.duplicate(with_state),
														 m_xPicker.duplicate(with_state),
														 m_yPicker.duplicate(with_state));
	}

	public void setSeed(int x_seed, int y_seed)
	{
		m_xPicker.setSeed(x_seed);
		m_yPicker.setSeed(y_seed);
	}
	
}
