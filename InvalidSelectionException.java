package beerSalesProfitStories;

/**
 * Program: InvalidExceptionException
 * Class: CSC 201 Fall 2020
 * Author: Nathaniel Fink-Humes
 * Date: 12/7/2020
 */

public class InvalidSelectionException extends Exception {

	public InvalidSelectionException()
	{
		super ("That is not a valid selection");
	}
	
	public InvalidSelectionException(String message)
	{
		super(message);
	}
}
