package beerSalesProfitStories;

/**
 * Program: LessThanZeroException
 * Class: CSC 201 Fall 2020
 * Author: Nathaniel Fink-Humes
 * Date: 12/7/2020
 */

public class LessThanZeroException extends Exception {

	public LessThanZeroException()
	{
		super ("This value cannot be less than zero.");
	}
	
	public LessThanZeroException(String message)
	{
		super (message);
	}
}
