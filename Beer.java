package beerSalesProfitStories;

/**
 * Program: Beer Class
 * Class: CSC 201 Fall 2020
 * Author: Nathaniel Fink-Humes
 * Date: 12/7/2020
 */

public class Beer {
	private ProductFormat PrFt;
	private double Cost;
	private String Name;
	private String Producer;
	
	public Beer(String Name, String Producer, double Cost, String SellingFormat, int ServingSize, String CustomerPurchaseFormat, String ServingVessel)
	{
		this.Name = Name;
		this.Producer = Producer;
		this.Cost = Cost;
		this.PrFt = new ProductFormat(SellingFormat, ServingSize, CustomerPurchaseFormat, ServingVessel);
	}
	
	public ProductFormat getProductFormat()
	{
		return PrFt;
	}
	
	public double getCost()
	{
		return Cost;
	}
	
	public String getName()
	{
		return Name;
	}
	
	public String getProducer()
	{
		return Producer;
	}
	
	//mutators
	
	public void setProductFormat(ProductFormat PrFt)
	{
		this.PrFt = PrFt;
	}
	
	public void setProductFormat(String SellingFormat, int ServingSize, String CustomerPurchaseFormat, String ServingVessel)
	{
		PrFt.setServingSize(ServingSize);
		PrFt.setSellingFormat(SellingFormat);
		PrFt.setCustomerPurchaseFormat(CustomerPurchaseFormat);
		PrFt.setServingVessel(ServingVessel);
	}
	
	public void setProducer(String Producer)
	{
		this.Producer = Producer;
	}
	
	public void setName(String Name)
	{
		this.Name = Name;
	}
	
	public void setCost(double Cost)
	{
		this.Cost = Cost;
	}
	
	public String toString()
	{
		return Producer + " " + Name + " costs $" + Cost + " and " + PrFt;
	}
}
