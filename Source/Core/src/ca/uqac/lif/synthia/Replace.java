package ca.uqac.lif.synthia;

public class Replace<T> extends Mutator<T>
{
	protected Picker<? extends T> m_replacement;
	
	public Replace(Picker<? extends T> picker, Picker<? extends T> replacement)
	{
		super(picker);
		m_replacement = replacement;
	}
	
	@Override
	public void reset()
	{
		super.reset();
		m_replacement.reset();
	}

	@Override
	public T pick()
	{
		return m_replacement.pick();
	}

	@Override
	public Replace<T> duplicate(boolean with_state)
	{
		return new Replace<T>(m_picker.duplicate(with_state), m_replacement.duplicate(with_state));
	}
}
