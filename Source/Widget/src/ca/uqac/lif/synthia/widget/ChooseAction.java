package ca.uqac.lif.synthia.widget;

import java.awt.event.ActionEvent;
import java.util.Set;

import ca.uqac.lif.synthia.Picker;
import ca.uqac.lif.synthia.Reactive;

public class ChooseAction implements Reactive<Set<Object>,ActionEvent>
{
	/**
	 * The set of objects on which actions can be made.
	 */
	Set<Object> m_contents;
	
	@Override
	public void reset()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public ActionEvent pick()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChooseAction duplicate(boolean with_state)
	{
		return new ChooseAction();
	}

	@Override
	public void tell(Set<Object> objects)
	{
		m_contents = objects;
	}
}
