package ca.uqac.lif.synthia.tree;

import ca.uqac.lif.synthia.Picker;

public class IntegerNodePicker extends NodePicker<Integer>
{
	/**
	 * Creates a new instance of the picker.
	 * @param label_picker The picker for the nodes' labels
	 */
	public IntegerNodePicker(Picker<Integer> label_picker)
	{
		super(label_picker);
	}

	@Override
	public Node<Integer> pick()
	{
		return new Node<Integer>(m_labelPicker.pick());
	}

	@Override
	public IntegerNodePicker duplicate(boolean with_state)
	{
		return new IntegerNodePicker(m_labelPicker.duplicate(with_state));
	}
}