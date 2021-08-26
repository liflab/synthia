package ca.uqac.lif.synthia.util;

import ca.uqac.lif.synthia.random.RandomFloat;

import java.util.ArrayList;
import java.util.List;

public class GetLists
{
	public List<String> getStringList()
	{
		List<String> list = new ArrayList<String>();
		list.add("portez");
		list.add("ce");
		list.add("wisky");
		list.add("au");
		list.add("vieux");
		list.add("juge");

		return list;
	}

	public List<String> getLongerStringList()
	{
		List<String> list = new ArrayList<String>();
		list.add("portez");
		list.add("ce");
		list.add("wisky");
		list.add("au");
		list.add("très");
		list.add("vieux");
		list.add("juge");

		return list;
	}

	public List<String> getShorterStringList()
	{
		List<String> list = new ArrayList<String>();
		list.add("portez");
		list.add("ce");
		list.add("wisky");
		list.add("au");
		list.add("juge");

		return list;
	}

	public List<String> getStringListLongerElement()
	{
		List<String> list = new ArrayList<String>();
		list.add("portez");
		list.add("ce");
		list.add("bourbon");
		list.add("au");
		list.add("vieux");
		list.add("juge");

		return list;
	}

	public List<String> getStringListShorterElement()
	{
		List<String> list = new ArrayList<String>();
		list.add("portez");
		list.add("ce");
		list.add("vin");
		list.add("au");
		list.add("vieux");
		list.add("juge");

		return list;
	}

	public List<Object> getComparableObjectList()
	{
		List<Object> list = new ArrayList<Object>();
		list.add(1);
		list.add(0.76936805);
		list.add("wisky");

		return list;
	}

	public List<Object> getListWithUncomparalbeObjects()
	{
		List<Object> list = new ArrayList<Object>();
		list.add(true);
		list.add(true);
		list.add("wisky ");

		return list;
	}

	public List<Object> getListWithUncomparalbeObjects2()
	{
		List<Object> list = new ArrayList<Object>();
		list.add(true);
		list.add(false);
		list.add("wisk");

		return list;
	}

	public List<Object> getUncomparableList()
	{
		List<Object> list = new ArrayList<Object>();
		list.add(true);
		list.add(false);
		list.add(true);

		return list;
	}

	public List<List<String>> getMultiLevelStringList()
	{
		List<List<String>> list = new ArrayList<List<String>>();
		list.add(getStringList());
		list.add(getStringList());
		list.add(getStringList());
		list.add(getStringList());
		list.add(getStringList());
		list.add(getStringList());

		return list;
	}

	public List<List<String>> getLongerMultiLevelStringList()
	{
		List<List<String>> list = new ArrayList<List<String>>();
		list.add(getStringList());
		list.add(getStringList());
		list.add(getStringList());
		list.add(getStringList());
		list.add(getStringList());
		list.add(getStringList());
		list.add(getStringList());
		return list;
	}

	public List<List<String>> getLongerMultiLevelStringList2()
	{
		List<List<String>> list = new ArrayList<List<String>>();
		list.add(getStringList());
		list.add(getStringList());
		list.add(getLongerStringList());
		list.add(getStringList());
		list.add(getStringList());
		list.add(getStringList());
		return list;
	}

	public List<List<String>> getShorterMultiLevelStringList()
	{
		List<List<String>> list = new ArrayList<List<String>>();
		list.add(getStringList());
		list.add(getStringList());
		list.add(getStringList());
		list.add(getStringList());
		list.add(getStringList());

		return list;
	}

	public List<List<String>> getShorterMultiLevelStringList2()
	{
		List<List<String>> list = new ArrayList<List<String>>();
		list.add(getStringList());
		list.add(getStringList());
		list.add(getStringList());
		list.add(getStringList());
		list.add(getShorterStringList());
		list.add(getStringList());
		return list;
	}

	public List<List<String>> getMultiLevelStringListLongerElement()
	{
		List<List<String>> list = new ArrayList<List<String>>();
		list.add(getStringList());
		list.add(getStringList());
		list.add(getStringListLongerElement());
		list.add(getStringList());
		list.add(getStringList());
		list.add(getStringList());

		return list;
	}

	public List<List<String>> getMultiLevelStringListShorterElement()
	{
		List<List<String>> list = new ArrayList<List<String>>();
		list.add(getStringList());
		list.add(getStringList());
		list.add(getStringList());
		list.add(getStringList());
		list.add(getStringListShorterElement());
		list.add(getStringList());

		return list;
	}

}
