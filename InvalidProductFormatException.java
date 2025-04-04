package beerSalesProfitStories;

/**
 * Program: InvalidProductFormatException
 * Class: CSC 201 Fall 2020
 * Author: Nathaniel Fink-Humes
 * Date: 12/7/2020
 */

public class InvalidProductFormatException extends Exception {

	public InvalidProductFormatException()
	{
		super ("That is not a valid product format.");
	}
	
	public InvalidProductFormatException(String message)
	{
		super (message);
	}
}
