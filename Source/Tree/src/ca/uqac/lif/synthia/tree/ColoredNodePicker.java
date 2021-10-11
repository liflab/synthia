package ca.uqac.lif.synthia.tree;

import ca.uqac.lif.synthia.Picker;

public class ColoredNodePicker implements Picker<Node<String>>
{
	/**
	 * A picker to select the color for a node.
	 */
	protected Picker<String> m_colorPicker;

	/**
	 * Creates a new colored node picker.
	 * @param color_picker A picker to select the color for a node
	 */
	public ColoredNodePicker(Picker<String> color_picker)
	{
		super();
		m_colorPicker = color_picker;
	}
	
	@Override
	public void reset() 
	{
		m_colorPicker.reset();
	}

	@Override
	public Node<String> pick() 
	{
		return new Node<String>(m_colorPicker.pick());
	}

	@Override
	public ColoredNodePicker duplicate(boolean with_state) 
	{
		return new ColoredNodePicker(m_colorPicker.duplicate(with_state));
	}
}
