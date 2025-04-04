package beerSalesProfitStories;
/**
 * Program: Account Class
 * Class: CSC 201 Fall 2020
 * Author: Nathaniel Fink-Humes
 * Date: 12/7/2020
 */
public class Account {
	private String Name;
	protected double Margin;
	private String Address;
	private String DecisionMaker; //this is the person who can buy new beers/control the selection
	
	public Account()
	{
		Name = "Not Set";
		Margin = 0.0;
		Address = "Unknon";
		DecisionMaker = "Eh?";
	}
	
	public Account(String Name, double Margin, String Address, String DecisionMaker)
	{
		this.Name = Name;
		this.Margin = Margin;
		this.Address = Address;
		this.DecisionMaker = DecisionMaker;
	}
	
	//accessors
	public String getName()
	{
		return Name;
	}
	
	public double getMargin()
	{
		return Margin;
	}
	
	public String getAddress()
	{
		return Address;
	}
	
	public String getDecisionMaker()
	{
		return DecisionMaker;
	}
	
	//mutators
	public void setName(String Name)
	{
		this.Name = Name;
	}
	
	public void setMargin(double Margin)
	{
		this.Margin = Margin;
	}
	
	public void setAddress(String Address)
	{
		this.Address = Address;
	}
	
	public void setDecisionMaker(String DecisionMaker)
	{
		this.DecisionMaker = DecisionMaker;
	}
	
	public String toString()
	{
		return Name + " is located at " + Address + ".  The buyer's name is " + DecisionMaker + ".";
	}
}
