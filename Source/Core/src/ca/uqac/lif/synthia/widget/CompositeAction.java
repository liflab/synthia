package ca.uqac.lif.synthia.widget;

import java.util.ArrayList;

public class CompositeAction extends ArrayList<GuiAction> implements GuiAction
{
	/**
	 * Dummy UID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void doAction()
	{
		for (GuiAction a : this)
		{
			a.doAction();
		}
	}

}
