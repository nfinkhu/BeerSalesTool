package beerSalesProfitStories;

/**
 * Program: ProductFormat Class
 * Class: CSC 201 Fall 2020
 * Author: Nathaniel Fink-Humes
 * Date: 12/7/2020
 */

public class ProductFormat {

	private boolean Keg;
	private double Ounces;
	private String SellingFormat;
	private int ServingSize; //in Ounces
	private String ServingVessel;
	private String CustomerPurchaseFormat;
	private double SellingUnits;
	
	public ProductFormat (String SellingFormat, int ServingSize, String CustomerPurchaseFormat, String ServingVessel)
	{
		this.ServingSize = ServingSize;
		try 
		{
			formatBuilder(SellingFormat);
			this.CustomerPurchaseFormat = CustomerPurchaseFormat;
			this.ServingVessel = ServingVessel;
		}
		catch (InvalidProductFormatException e)
		{
			System.out.println(e);
			Keg = false;
			Ounces = 0.0;
			SellingFormat = "N/A";
			this.ServingSize = 0;
			this.ServingVessel = "N/A";
			this.CustomerPurchaseFormat = "N/A";
			this.SellingFormat = "N/A";		
		}	
	}
	
	private void formatBuilder(String SellingFormat) throws InvalidProductFormatException
	{
		String checkFormat = SellingFormat.toUpperCase();
		switch (checkFormat)
		{
		case "HALF BARREL":
		case "HALF":
		case "15.5":
		case "15.5 GALLON":
		case "15.5 G":
		case "HALF KEG":
		case "1/2":	
			Keg = true;
			Ounces = (128*15.5);
			this.SellingFormat = SellingFormat;
			SellingUnits = Ounces/ServingSize;
			break;
		case "SIXTEL":
		case "1/6":
		case "5.3":
		case "PONY":
		case "PONY KEG":
			Keg = true;
			Ounces = (128*15.5)/3;
			this.SellingFormat = SellingFormat;
			SellingUnits = Ounces/ServingSize;
			break;
		case "2/12":
		case "TWELVE PACK":
		case "TWELVE PACKS":
			Keg = false;
			Ounces = 12*24.0;
			this.SellingFormat = SellingFormat;
			SellingUnits = 2;
			break;
		case "4/6":
		case "SIX-PACK":
		case "SIX PACK":
		case "SIX-PACKS":
		case "SIX PACKS":
		case "6 PACKS":
		case "6-PACKS":
		case "6 PACK":
		case "6-PACK":	
			Keg = false;
			if (ServingSize == 16)
				Ounces = 16*24.0;
			else
				Ounces = 12*24.0;
			this.SellingFormat = SellingFormat;
			SellingUnits = 4;
			break;
		case "6/4":
		case "FOUR-PACK":
		case "FOUR PACK":
		case "FOUR-PACKS":
		case "FOUR PACKS":
		case "4 PACK":
		case "4-PACK":
		case "4 PACKS":
		case "4-PACKS":
			Keg = false;
			if (ServingSize == 16)
				Ounces = 16*24.0;
			else
				Ounces = 12*24.0;
			this.SellingFormat = SellingFormat;
			SellingUnits = 6;
			break;
		case "LOOSE":
		case "LOOSE 24":
		case "24":
		case "SINGLES":
		case "LOOSE CASE":
		case "BAR CASE":
			Keg = false;
			if (ServingSize == 16)
				Ounces = 16*24.0;
			else
				Ounces = 12*24.0;
			this.SellingFormat = SellingFormat;
			SellingUnits = 24;
			break;
		default:
			throw new InvalidProductFormatException();
		}	
	}
	
	//accessors
	public boolean getKeg()
	{
		return Keg;
	}
	
	public double getOunces()
	{
		return Ounces;
	}
	
	public String getSellingFormat()
	{
		return SellingFormat;
	}
	
	public int getServingSize()
	{
		return ServingSize;
	}
	
	public String getServingVessel()
	{
		return ServingVessel;
	}
	
	public String getCustomerPurchaseFormat()
	{
		return CustomerPurchaseFormat;
	}
	
	public double getSellingUnits()
	{
		return SellingUnits;
	}
	
	//mutators
	public void setSellingFormat(String SellingFormat)
	{
		try 
		{
			formatBuilder(SellingFormat);
		}
		catch (InvalidProductFormatException e)
		{
			System.out.println(e);
			Keg = false;
			Ounces = 0.0;
			SellingFormat = "N/A";
			ServingSize = 0;
			ServingVessel = "N/A";
			CustomerPurchaseFormat = "N/A";
			this.SellingFormat = "N/A";		
		}
	}
	
	public void setServingSize(int ServingSize)
	{
		this.ServingSize = ServingSize;
		if (Keg)
			SellingUnits = Ounces/ServingSize;
	}
	
	public void setServingVessel(String ServingVessel)
	{
		this.ServingVessel = ServingVessel;
	}
	
	public void setCustomerPurchaseFormat(String CustomerPurchaseFormat)
	{
		this.CustomerPurchaseFormat = CustomerPurchaseFormat;
	}
	
	public void setSellingUnits(double SellingUnits)
	{
		if (!Keg)
			this.SellingUnits = SellingUnits;
	}
	
	public String toString()
	{
		if (Keg)
			return " is a " + SellingFormat + " which contains " + Ounces + " ounces that are served in " + ServingVessel + " of " + ServingSize + " ounces.  There are total of " + (int)SellingUnits + " per keg.";
		else
			return " is a case of " + SellingFormat + " format of " + ServingVessel + " which customers purchase as " + CustomerPurchaseFormat + ".  There are " + SellingUnits + " per case.";
	}
}

