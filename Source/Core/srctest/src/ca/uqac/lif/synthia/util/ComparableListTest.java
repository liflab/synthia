package ca.uqac.lif.synthia.util;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ComparableListTest
{
	@Test
	public void equalStringLists()
	{
		GetLists gl = new GetLists();
		ComparableList<String> reference_string_list = new ComparableList<String>(gl.getStringList());

		int anwser = reference_string_list.compareTo((ArrayList<String>) gl.getStringList());

		Assertions.assertEquals(0, anwser);
	}

	// reference is shorter than the second one
	@Test
	public void shorterStringLists()
	{
		GetLists gl = new GetLists();
		ComparableList<String> reference_string_list = new ComparableList<String>(gl.getStringList());

		int anwser = reference_string_list.compareTo((ArrayList<String>) gl.getLongerStringList());

		Assertions.assertEquals(-1, anwser);
	}

	// reference is longer than the second one
	@Test
	public void longerStringLists()
	{
		GetLists gl = new GetLists();
		ComparableList<String> reference_string_list = new ComparableList<String>(gl.getStringList());

		int anwser = reference_string_list.compareTo((ArrayList<String>) gl.getShorterStringList());

		Assertions.assertTrue(anwser>0);
	}

	// reference contains an element shorter than the second one
	@Test
	public void shorterElementStringLists()
	{
		GetLists gl = new GetLists();
		ComparableList<String> reference_string_list = new ComparableList<String>(gl.getStringList());

		int anwser = reference_string_list
				.compareTo((ArrayList<String>) gl.getStringListLongerElement());

		Assertions.assertTrue( anwser>0);
	}

	// reference contains an element longer than the second one
	@Test
	public void longerElementStringLists()
	{
		GetLists gl = new GetLists();
		ComparableList<String> reference_string_list = new ComparableList<String>(gl.getStringList());

		int anwser = reference_string_list
				.compareTo((ArrayList<String>) gl.getStringListShorterElement());

		Assertions.assertTrue(0< anwser);
	}

	// one list contains only uncomparable elements
	@Test
	public void uncomparableList()
	{
		GetLists gl = new GetLists();
		ComparableList<Object> reference_list = new ComparableList<Object>(gl.getComparableObjectList());

		int anwser = reference_list.compareTo((ArrayList<Object>) gl.getUncomparableList()) ;

		Assertions.assertEquals(0, anwser);
	}


	@Test
	public void equalComparableLists()
	{
		GetLists gl = new GetLists();
		ComparableList<Object> reference_list = new ComparableList<Object>(gl.getComparableObjectList());

		int anwser = reference_list.compareTo((ArrayList<Object>) gl.getComparableObjectList()) ;

		Assertions.assertEquals(0, anwser);
	}

	@Test
	public void comparableListswithUncomparableElements()
	{
		GetLists gl = new GetLists();
		ComparableList<Object> reference_list = new ComparableList<Object>(gl.getComparableObjectList());

		int anwser = reference_list.compareTo((ArrayList<Object>) gl.getListWithUncomparalbeObjects()) ;

		Assertions.assertTrue(0>anwser);
	}

	@Test
	public void comparableListswithUncomparableElements2()
	{
		GetLists gl = new GetLists();
		ComparableList<Object> reference_list = new ComparableList<Object>(gl.getComparableObjectList());

		int anwser = reference_list.compareTo((ArrayList<Object>) gl.getListWithUncomparalbeObjects2()) ;

		Assertions.assertTrue(anwser>0);
	}

	@Test
	public void equalMultiLevelStringLists()
	{
		GetLists gl = new GetLists();
		ComparableList<List<String>> reference_list = new ComparableList<List<String>>
				(gl.getMultiLevelStringList());

		int anwser = reference_list.compareTo((ArrayList<List<String>>) gl.getMultiLevelStringList()) ;

		Assertions.assertEquals(0, anwser);
	}

	@Test
	public void shorterLevelStringList()
	{
		GetLists gl = new GetLists();
		ComparableList<List<String>> reference_list = new ComparableList<List<String>>
				(gl.getMultiLevelStringList());

		int anwser = reference_list.compareTo((ArrayList<List<String>>)
				gl.getLongerMultiLevelStringList()) ;

		Assertions.assertTrue(0> anwser);
	}

	@Test
	public void shorterLevelStringList2()
	{
		GetLists gl = new GetLists();
		ComparableList<List<String>> reference_list = new ComparableList<List<String>>
				(gl.getMultiLevelStringList());

		int anwser = reference_list.compareTo((ArrayList<List<String>>)
				gl.getLongerMultiLevelStringList2()) ;

		Assertions.assertTrue(0> anwser);
	}

	@Test
	public void longerLevelStringList()
	{
		GetLists gl = new GetLists();
		ComparableList<List<String>> reference_list = new ComparableList<List<String>>
				(gl.getMultiLevelStringList());

		int anwser = reference_list.compareTo((ArrayList<List<String>>)
				gl.getShorterMultiLevelStringList()) ;

		Assertions.assertTrue(anwser>0);
	}

	@Test
	public void longerMulitlevelStringList2()
	{
		GetLists gl = new GetLists();
		ComparableList<List<String>> reference_list = new ComparableList<List<String>>
				(gl.getMultiLevelStringList());

		int anwser = reference_list.compareTo((ArrayList<List<String>>)
				gl.getShorterMultiLevelStringList2()) ;

		Assertions.assertTrue(anwser>0);
	}

	@Test
	public void multilevelStringLongerElement()
	{
		GetLists gl = new GetLists();
		ComparableList<List<String>> reference_list = new ComparableList<List<String>>
				(gl.getMultiLevelStringList());

		int anwser = reference_list.compareTo((ArrayList<List<String>>)
				gl.getMultiLevelStringListShorterElement()) ;

		Assertions.assertTrue(anwser>0);
	}

	//shorter string element but greater value
	@Test
	public void multilevelStringShorterElement()
	{
		GetLists gl = new GetLists();
		ComparableList<List<String>> reference_list = new ComparableList<List<String>>
				(gl.getMultiLevelStringList());

		int anwser = reference_list.compareTo((ArrayList<List<String>>)
				gl.getMultiLevelStringListLongerElement()) ;

		Assertions.assertTrue(anwser>0);
	}
}
