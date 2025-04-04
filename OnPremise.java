package beerSalesProfitStories;

/**
 * Program: OnPremise Class
 * Class: CSC 201 Fall 2020
 * Author: Nathaniel Fink-Humes
 * Date: 12/7/2020
 */

import java.util.Scanner;

public class OnPremise extends Account implements ProfitComparison {
	
	private Beer[] Taps;
	private double[] RateOfSale;
	
	public OnPremise ()
	{
		super ();
		Taps = new Beer[6];
		RateOfSale = new double[6];
	}
	
	public OnPremise(String Name, double Margin, String Address, String DecisionMaker, int NumberOfTaps)
	{
		super (Name, Margin, Address, DecisionMaker);
		Taps = new Beer[NumberOfTaps];
		RateOfSale = new double[NumberOfTaps];
	}
	
	public void setTaps(Beer[] Taps)
	{
		this.Taps = Taps;
		double[] ROS = new double[Taps.length];
		RateOfSale = ROS;
	}
	
	public void setTaps()
	{
		for (int i = 0; i < Taps.length; i++)
		{
			populateTap(i);
		}
	}
	
	public void setTap(Beer OnDraft, int TapNumber)
	{
		try
		{
			Taps[TapNumber] = OnDraft;
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			System.out.println("This bar doesn't have a tap of this number.");
		}
	}
	
	//use user input to put a new beer object in a specific array element of Taps
	private void populateTap(int TapNumber)
	{
		Scanner keyboard = new Scanner(System.in);
		System.out.println("What brewery produced the beer on tap " + (TapNumber+1));
		String Producer = keyboard.nextLine();
		System.out.println("What is the name of the beer?");
		String Name = keyboard.nextLine();
		System.out.println("What type of keg is the beer in?");
		String SellingFormat = keyboard.nextLine();
		System.out.println("How does a patron purchase the beer?");
		String CustomerPurchaseFormat = keyboard.nextLine();
		System.out.println("What kind of glass does the beer come in?");
		String ServingVessel = keyboard.nextLine();
		System.out.println("How much did the keg cost?");
		double Cost = keyboard.nextDouble();
		System.out.println("How many ounces does your " + ServingVessel + " hold?");
		int ServingSize = keyboard.nextInt();
		Taps[TapNumber] = new Beer(Name, Producer, Cost, SellingFormat, ServingSize, CustomerPurchaseFormat, ServingVessel);
		System.out.println();
	}
	
	public void setRateOfSale(double[] ROS)
	{
		RateOfSale = ROS;
	}
	
	//use user input to put input a value for a specific element of RateOfSale array.
	public void setRateOfSale()
	{
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Which tap number would you like to set the Rate of Sale of? ");
		int TapNumber = keyboard.nextInt() - 1;
		try
		{
			RateOfSale[TapNumber] = enterRateOfSale(TapNumber);
		}
		catch (NullPointerException e)
		{
			System.out.println("There is currently no beer assigned to tap " + (TapNumber+1) + ".");
		}
		catch (IndexOutOfBoundsException e)
		{
			System.out.println("I'm sorry, but our records show there is not a tap numbered " + (TapNumber+1) + ".");
		}
		catch (LessThanZeroException e)
		{
			System.out.println("It doesn't seem possible to have a negative Rate of Sale, please try again.");
		}
	}
	
	public void setRateOfSale(int TapNumber)
	{
		try
		{
			enterRateOfSale(TapNumber);
		}
		catch (LessThanZeroException e)
		{
			System.out.println("I'm sorry, but it doesn't seem like you can have a Rate of Sale less than zero.  Let's try that again.");
			setRateOfSale(TapNumber);
		}
	}
	
	//Use user input to set the value of each element of RateOfSale array
	public void setAllRatesOfSale()
	{
		for (int i = 0; i < Taps.length; i++)
		{
			try
			{
				RateOfSale[i] = enterRateOfSale(i);
			}
			catch (NullPointerException e)
			{
				System.out.println("There is currently no beer assigned to tap " + (i+1) + ".");
			}
			catch (IndexOutOfBoundsException e)
			{
				System.out.println("I'm sorry, but our records show there is not a tap numbered " + (i+1) + ".");
			}
			catch (LessThanZeroException e)
			{
				System.out.println("It doesn't seem possible to have a negative Rate of Sale, please try again.");
				i--;
			}
		}
	}
	
	//private method to take user input for a specific RateOfSale array index.
	private double enterRateOfSale(int TapNumber) throws NullPointerException, LessThanZeroException, IndexOutOfBoundsException
	{
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Please input the current Rate of Sale for " + Taps[TapNumber].getProducer() + " " + Taps[TapNumber].getName()+ ".");
		double RateOfSale = keyboard.nextDouble();
		;
		if (RateOfSale < 0)
			throw new LessThanZeroException();
		return RateOfSale;
	}
	
	public Beer[] getTaps()
	{
		return Taps;
	}
	
	public Beer getSpecificTap(int TapNumber)
	{
		return Taps[TapNumber];
	}
	
	public double[] getRateOfSale()
	{
		return RateOfSale;
	}
	
	public double getSpecificRateOfSale(int TapNumber)
	{
		return RateOfSale[TapNumber];
	}
	
	//Do some arithmatic to show how much of a specific product must be sold at a Sale price in order to make the same profit. Write a String which is a table with relevent infomration.
	public String discountBreakEven (double DiscountSellingPrice, double SellingPrice)
	{
		int TapNumber = tapSelector();
		
		double ServingsPerPeriod;
		double CostPerServing;
		double CostPerOunce;
		double[] ProfitPerServing = new double[2];
		double[] ProfitPerOunce = new double [2];
		double[] MaxProfitPerKeg = new double [2];
		double[] RealProfitPerKeg = new double [2];
		double[] MarginPercent = new double[2];
		int NumberOfAdditionalServings;
		double BreakEvenServingsROS;
		double BreakEvenKegsROS;
		double ROSIncreasePercent;
		
		ServingsPerPeriod = Taps[TapNumber].getProductFormat().getSellingUnits() * RateOfSale[TapNumber];
		CostPerServing = Taps[TapNumber].getCost() / Taps[TapNumber].getProductFormat().getSellingUnits();
		CostPerOunce = Taps[TapNumber].getCost() / Taps[TapNumber].getProductFormat().getOunces();
		ProfitPerServing[0] = SellingPrice - CostPerServing;
		ProfitPerServing[1] = DiscountSellingPrice - CostPerServing;
		ProfitPerOunce[0] = (SellingPrice/Taps[TapNumber].getProductFormat().getServingSize()) - CostPerOunce;
		ProfitPerOunce[1] = (DiscountSellingPrice/Taps[TapNumber].getProductFormat().getServingSize()) - CostPerOunce;
		MarginPercent[0] = (SellingPrice - CostPerServing) / SellingPrice;
		MarginPercent[1] = (DiscountSellingPrice - CostPerServing) / DiscountSellingPrice;
		MaxProfitPerKeg[0] = ProfitPerOunce[0] * Taps[TapNumber].getProductFormat().getOunces();
		MaxProfitPerKeg[1] = ProfitPerOunce[1] * Taps[TapNumber].getProductFormat().getOunces();
		RealProfitPerKeg[0] = ProfitPerServing[0]*(int)(Taps[TapNumber].getProductFormat().getSellingUnits()*.94);  //Avg about 6% loss for draft beer systems.
		RealProfitPerKeg[1] = ProfitPerServing[1]*(int)(Taps[TapNumber].getProductFormat().getSellingUnits()*.94);
		
		BreakEvenServingsROS = ServingsPerPeriod * ProfitPerServing[0];
		BreakEvenServingsROS = BreakEvenServingsROS / ProfitPerServing[1];
		
		NumberOfAdditionalServings = (int)(BreakEvenServingsROS - ServingsPerPeriod);
		BreakEvenKegsROS = BreakEvenServingsROS / Taps[TapNumber].getProductFormat().getSellingUnits();
		
		ROSIncreasePercent = NumberOfAdditionalServings / ServingsPerPeriod;
		
		return ("Break even analysis for " + Taps[TapNumber].getProducer() + " " + Taps[TapNumber].getName() + " at $" + Taps[TapNumber].getCost() + " per keg.\n" +
				"\t\t\t\t\tNormal Price\t\t\t\tDiscounted Price\n" +
				"\t\t\t\t\t\t$" + SellingPrice + "\t\t\t\t$" + DiscountSellingPrice + "\n" +
				"Ounces Per Keg:\t\t\t" + Taps[TapNumber].getProductFormat().getOunces() + "\t\t\t" + Taps[TapNumber].getProductFormat().getOunces() + "\n" +
				"Ounces Per Serving:\t\t" + Taps[TapNumber].getProductFormat().getServingSize() + "\t\t\t\t" + Taps[TapNumber].getProductFormat().getServingSize() + "\n" +
				"Cost Per Serving:\t\t$" + ((int)(CostPerServing*100000)/100000.0) + "\t\t\t$" + ((int)(CostPerServing*100000)/100000.0) +  "\n" +
				"Cost Per Ounce:\t\t\t$" + ((int)(CostPerOunce*100000)/100000.0) + "\t\t\t$" + ((int)(CostPerOunce*100000)/100000.0) + "\n" + 
				"Profit Per Serving:\t\t$" + ((int)(ProfitPerServing[0]*1000)/1000.0) + "\t\t\t\t$" + ((int)(ProfitPerServing[1]*1000)/1000.0) + "\n" + 
				"Profit Per Ounce:\t\t$" + ((int)(ProfitPerOunce[0]*100000)/100000.0) + "\t\t\t$" + ((int)(ProfitPerOunce[1]*100000)/100000.0) + "\n" +
				"Current Margin:\t\t\t" + ((int)(MarginPercent[0]*10000)/100.0) + "%\t\t\t\t" + ((int)(MarginPercent[1]*10000)/100.0) + "%\n" + 
				"Max Profit Per Keg:\t\t$" + ((int)(MaxProfitPerKeg[0]*100)/100.0) + "\t\t\t\t$" + ((int)(MaxProfitPerKeg[1]*100)/100.0) + "\n" +
				"Realistic Profit per Keg:\t$" + ((int)(RealProfitPerKeg[0]*100)/100.0) + "\t\t\t$" + ((int)(RealProfitPerKeg[1]*100)/100.0) + "\n" +
				"\nYou are currently selling " + (int)ServingsPerPeriod + " " + Taps[TapNumber].getProductFormat().getServingVessel()+ "s per month.  You would need to sell " + (int)BreakEvenServingsROS + " to make the same amount, everything more would be straight profit." +
				"\nThat's only a " + ((int)(ROSIncreasePercent*10000)/100.0) + "% or  " + NumberOfAdditionalServings + " additional " + Taps[TapNumber].getProductFormat().getServingVessel() + "s.");	
	}
	
	//Do some arithmatic to show how profitable a specific Beer in Taps array is over the course of a year.
	public String salesOverYear(double LossPercent, double PercentSoldOnSpecial, double SpecialPricing, double RegularPricing)
	{
		int TapNumber = tapSelector();
		double ProfitPerServingRegular = RegularPricing - (Taps[TapNumber].getCost() / Taps[TapNumber].getProductFormat().getSellingUnits());
		double ProfitPerServingSpecial = SpecialPricing - (Taps[TapNumber].getCost() / Taps[TapNumber].getProductFormat().getSellingUnits());
		double MaxProfitPerKeg = (ProfitPerServingRegular*Taps[TapNumber].getProductFormat().getSellingUnits())*(1-PercentSoldOnSpecial) + (ProfitPerServingSpecial*Taps[TapNumber].getProductFormat().getSellingUnits())*(PercentSoldOnSpecial);
		double ExpectedProfitPerKeg = (ProfitPerServingRegular*(int)(Taps[TapNumber].getProductFormat().getSellingUnits()*(1-LossPercent)))*(1-PercentSoldOnSpecial) + (ProfitPerServingSpecial*(int)(Taps[TapNumber].getProductFormat().getSellingUnits()*(1-LossPercent)))*(PercentSoldOnSpecial);
		double ExpectedProfitPerMonth = ExpectedProfitPerKeg*RateOfSale[TapNumber];
		double ExpectedProfitPerQuarter = ExpectedProfitPerMonth*3;
		double ExpectedProfitPerYear = ExpectedProfitPerMonth*12;
		
		return ("Taking into account the amount of beer you sell on special as well as your average loss you should make $" + ((int)(ExpectedProfitPerKeg*100)/100.0) + " per " + Taps[TapNumber].getProductFormat().getSellingFormat() + " of " + Taps[TapNumber].getProducer() + " " + Taps[TapNumber].getName() + ".\n" + 
				"That is a profit of $" + ((int)(ExpectedProfitPerMonth*100)/100.0) + " per month, $" + ((int)(ExpectedProfitPerQuarter*100)/100.0) + " per quarter, and $" + ((int)(ExpectedProfitPerYear*100)/100.0) + " per year.");			
	}
	
	//compare profitability of a specific Beer object in Taps array with a new one supplied by the user.
	public String productComparison(Beer NewProduct, double ExpectedROS, double OldProductSellingPrice, double NewProductSellingPrice)
	{
		double NewProductProfitPerServing = NewProductSellingPrice - (NewProduct.getCost() / NewProduct.getProductFormat().getSellingUnits());
		double NewProductProfitMargin = NewProductProfitPerServing / NewProductSellingPrice;
		double NewProductProfitPerKeg = NewProductProfitPerServing * (int)NewProduct.getProductFormat().getSellingUnits();
		double NewProductProfitPerMonth = NewProductProfitPerKeg * ExpectedROS;
		int TapNumber = tapSelector();
		
		double OldProductProfitPerServing = OldProductSellingPrice - (Taps[TapNumber].getCost() / Taps[TapNumber].getProductFormat().getSellingUnits());
		double OldProductProfitMargin = OldProductProfitPerServing / OldProductSellingPrice;
		double OldProductProfitPerKeg = OldProductProfitPerServing * (int)Taps[TapNumber].getProductFormat().getSellingUnits();
		double OldProductProfitPerMonth = OldProductProfitPerKeg * RateOfSale[TapNumber];
		
		return ("\t\t\t\t\tLet's compare these two kegs.\n" +
				"Producer:\t\t\t" + Taps[TapNumber].getProducer() + "\t\t" + NewProduct.getProducer() + "\n" +
				"Name:\t\t\t\t" + Taps[TapNumber].getName() + "\t\t" + NewProduct.getName() + "\n" +
				"Cost Per Keg$:\t\t" + Taps[TapNumber].getCost() + "\t\t\t\t" + NewProduct.getCost() + "\n" +
				"Keg Format:\t\t" + Taps[TapNumber].getProductFormat().getSellingFormat() + "\t\t\t\t" + NewProduct.getProductFormat().getSellingFormat() + "\n" +
				"Ounces per Keg:\t\t" + ((int)(Taps[TapNumber].getProductFormat().getOunces()*100)/100.0) + "\t\t\t" + ((int)(NewProduct.getProductFormat().getOunces()*100)/100.0) + "\n" +
				"Serving Size:\t\t" + Taps[TapNumber].getProductFormat().getServingSize() + "\t\t\t" + NewProduct.getProductFormat().getServingSize() + "\n" +
				"Servings Per Keg:\t" + (int)Taps[TapNumber].getProductFormat().getSellingUnits() + "\t\t\t" + (int)NewProduct.getProductFormat().getSellingUnits() + "\n" +
				"Cost Per Serving:\t" + ((int)((Taps[TapNumber].getCost() / Taps[TapNumber].getProductFormat().getSellingUnits())*1000)/1000.0) + "\t\t\t" + ((int)((NewProduct.getCost() / NewProduct.getProductFormat().getSellingUnits())*1000)/1000.0) + "\n" +
				"Selling Price:\t\t$" + OldProductSellingPrice + "\t\t\t$" + NewProductSellingPrice + "\n" +
				"Profit Per Serving:\t$" + ((int)(OldProductProfitPerServing*1000)/1000.0) + "\t\t\t$" + ((int)(NewProductProfitPerServing*1000)/1000.0) + "\n" + 
				"Profit Per Keg:\t\t$" + ((int)(OldProductProfitPerKeg*100)/100.0) + "\t\t\t$" + ((int)(NewProductProfitPerKeg*100)/100.0) + "\n" + 
				"Profit Margin:\t\t" + ((int)(OldProductProfitMargin*10000)/10000.0) + "%\t\t\t" + ((int)(NewProductProfitMargin*10000)/10000.0) + "%\n" + 
				"Monthly Rate of Sale: " + RateOfSale[TapNumber] + "\t\t\t" + ExpectedROS + "\n" + 
				"Monthly Profit:\t\t$" + ((int)(OldProductProfitPerMonth*100)/100.0) + "\t\t\t$" + ((int)(NewProductProfitPerMonth*100)/100.0));
		
	}
	
	//Used to identify which element in Taps arry which the user wants to work with in other methods.
	private int tapSelector()
	{
		Scanner keyboard = new Scanner(System.in);
		try
		{
			System.out.print("Please input which tap number you would like to work with, starting with 1 as the first tap:  ");
			int TapNumber = keyboard.nextInt()-1;
			if ((TapNumber<0) || (TapNumber>Taps.length))
				throw new InvalidSelectionException("That is not a valid tap number.");
			return TapNumber;
		}
		catch (InvalidSelectionException e)
		{
			System.out.println(e);
			return tapSelector();
		}
	}
	
	//returns string with infomration about the account, what beers they have on Tap stored in the Taps array
	public String toString()
	{
		String Display = super.toString() + " They have the following draft list:\n";
		try 
		{
			for (int i = 0; i < Taps.length; i++)
			{
				Display = Display + "\tTap " + (i+1) + "\t\t" + Taps[i].getProducer() + " " + Taps[i].getName() + "\n";
			}
		}
		catch (NullPointerException e)
		{
			Display = super.toString();
		}
		return Display;
	}
	
	
	//returns the toString() of the super class Account.
	public String Info()
	{
		return super.toString();
	}

}
