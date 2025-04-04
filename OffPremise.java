package beerSalesProfitStories;

/**
 * Program: OffPremise Class
 * Class: CSC 201 Fall 2020
 * Author: Nathaniel Fink-Humes
 * Date: 12/7/2020
 */

import java.util.Scanner;

public class OffPremise extends Account implements ProfitComparison {
	
	private Beer[] Planogram;
	private double[] WeeklyROS;
	private double[] BeerPrices;
	
	public OffPremise ()
	{
		super ();
		Planogram = new Beer[6];
		WeeklyROS = new double[6];
		BeerPrices = new double[6];
	}
	
	public OffPremise(String Name, double Margin, String Address, String DecisionMaker, int NumberOfBeers)
	{
		super (Name, Margin, Address, DecisionMaker);
		Planogram = new Beer[NumberOfBeers];
		WeeklyROS = new double[NumberOfBeers];
		BeerPrices = new double[NumberOfBeers];
	}
	
	public void setPlanogram(Beer[] Planogram)
	{
		this.Planogram = Planogram;
		WeeklyROS = adjustROS();
		BeerPrices = calculateBeerPrices();
	}
	
	
	public void setPlanogram()
	{
		for (int i = 0; i < Planogram.length; i++)
		{
			populatePlanogramSlot(i);
		}
		WeeklyROS = adjustROS();
		BeerPrices = calculateBeerPrices();
	}

	public void setPlanogramSlot(int Slot)
	{
		Scanner keyboard = new Scanner(System.in);
		populatePlanogramSlot(Slot);
		System.out.print("What is its weekly rate of sale? ");
		WeeklyROS[Slot] = keyboard.nextDouble();
		BeerPrices = calculateBeerPrices();
	}
	
	public void setPlanogramSlot(int Slot, Beer Selection)
	{
		Planogram[Slot] = Selection;
		Scanner keyboard = new Scanner(System.in);
		System.out.print("What is " + Planogram[Slot].getName() +  "'s weekly rate of sale? ");
		WeeklyROS[Slot] = keyboard.nextDouble();
		BeerPrices[Slot] = calculateSingleBeerPrice(Slot); 
	}
	
	public void setWeeklyROS(double[] WeeklyROS)
	{
		if (this.WeeklyROS.length == WeeklyROS.length)
			this.WeeklyROS = WeeklyROS;
	}
	
	public void setSpecificWeeklyROS(int Slot, double ROS)
	{
		try
		{
			if (ROS < 0)
				throw new LessThanZeroException();
			WeeklyROS[Slot] = ROS;
		}
		catch (LessThanZeroException e)
		{
			System.out.println("I'm sorry, but you cannot have a negative Rate of Sale.");
		}
		catch (IndexOutOfBoundsException e)
		{
			System.out.println("It appears that slot doesn't exist, maybe try inputting a different slot.");
		}
	}
	
	public Beer[] getPlanogram()
	{
		return Planogram;
	}
	
	public Beer getSpecificPlanogramSlot(int Slot)
	{
		return Planogram[Slot];
	}
	
	public double[] getWeeklyROS()
	{
		return WeeklyROS;
	}
	
	public double getSpecificWeeklyROS(int Slot)
	{
		return WeeklyROS[Slot];
	}
	
	public double[] getBeerPrices()
	{
		return BeerPrices;
	}
	
	public double getSpecificBeerPrice(int Slot)
	{
		return BeerPrices[Slot];
	}
	
	public String toString()
	{
		String Display = super.toString() + " They work with a " + (Margin*100) + "% standard profit Margin.  Below are the products they carry.\n"
				+ "Product\t\t\t\tFormat\t\tPrice\n";
		for (int i = 0; i < Planogram.length; i++)
		{
			Display = Display + "  " + Planogram[i].getProducer() + " " + Planogram[i].getName() + "\t\t" + Planogram[i].getProductFormat().getCustomerPurchaseFormat() + "\t\t$" + BeerPrices[i] +"\n";
		}
		return Display;
	
	}
	
	//Uses user input to set the WeeklyROS array and checks for exceptions as incorrect inputs.
	private double[] adjustROS()
	{
		double[] ROS = new double[Planogram.length];
		Scanner keyboard = new Scanner(System.in);
		double IndvROS;
		for (int i = 0; i < ROS.length; i++)
		{
			try
			{
				System.out.print("Please input the weekly rate of sale for " + Planogram[i].getProducer() + " " + Planogram[i].getName() +":  ");
				IndvROS = keyboard.nextInt();
				if (IndvROS < 0)
					throw new LessThanZeroException();
				ROS[i] = IndvROS;
			}
			catch (LessThanZeroException e)
			{
				System.out.println("How can you have a negative rate of sale? Let's try inputting that one again.");
				i--;
			}
			catch (NullPointerException e)
			{
				System.out.println("Seems we haven't finished building the planogram yet.  Let's do that first");
				i = ROS.length + 1;
			}
		}
		return ROS;
	}
	
	//sets the values of the BeerPrices array based on the value of the attribute Margin from the super class Account.
	private double[] calculateBeerPrices()
	{
		double[] Prices = new double[Planogram.length];
		double StraightPrice;
		double AdjustedPrice;
		for (int i = 0; i < Prices.length; i++)
		{
			StraightPrice = (Planogram[i].getCost() / Planogram[i].getProductFormat().getSellingUnits());
			StraightPrice = StraightPrice / (1 - Margin);
			if ((StraightPrice - (int)StraightPrice == 0))
			{
				AdjustedPrice = (int)StraightPrice;
				AdjustedPrice = AdjustedPrice - .01;
			}
			else
			{
				AdjustedPrice = (int)StraightPrice + 1;
				AdjustedPrice = AdjustedPrice - .01;
			}
			Prices[i] = AdjustedPrice;
		}
		return Prices;
	}
	
	//adjusts the value of a specific element in BeerPrices array based on value of Margin Attribute from Account Super Class.
	private double calculateSingleBeerPrice(int Slot)
	{
		double StraightPrice;
		double AdjustedPrice;
		StraightPrice = (Planogram[Slot].getCost() / Planogram[Slot].getProductFormat().getSellingUnits());
		StraightPrice = StraightPrice / (1 - Margin);
		if ((StraightPrice - (int)StraightPrice == 0))
			{
				AdjustedPrice = (int)StraightPrice;
				AdjustedPrice = AdjustedPrice - .01;
			}
		else
			{
				AdjustedPrice = (int)StraightPrice + 1;
				AdjustedPrice = AdjustedPrice - .01;
			}
		return AdjustedPrice;
	}

	//Uses user input to instantiate a new Beer object and then place it as an element in Planogram array.
	private void populatePlanogramSlot(int Slot)
	{
		Scanner keyboard = new Scanner(System.in);
		System.out.println("What brewery produced the beer in slot " + (Slot+1));
		String Producer = keyboard.nextLine();
		System.out.println("What is the name of the beer?");
		String Name = keyboard.nextLine();
		System.out.println("What format does the case come in?");
		String SellingFormat = keyboard.nextLine();
		System.out.println("How does a patron purchase the beer?");
		String CustomerPurchaseFormat = keyboard.nextLine();
		System.out.println("Is the beer in bottles or cans?");
		String ServingVessel = keyboard.nextLine();
		System.out.println("How much did the case cost?");
		double Cost = keyboard.nextDouble();
		System.out.println("How many ounces in each " + ServingVessel + "?");
		int ServingSize = keyboard.nextInt();
		Planogram[Slot] = new Beer(Name, Producer, Cost, SellingFormat, ServingSize, CustomerPurchaseFormat, ServingVessel);
		System.out.println();
		 	
	}
	
	//Do some arithmatic to show how much of a specific product must be sold at a Sale price in order to make the same profit. Write a String which is a table with relevent infomration.
	public String discountBreakEven(double DiscountSellingPrice, double SellingPrice)
	{
		int Slot = beerSlotSelector();
		SellingPrice = BeerPrices[Slot];
		
		double[] ProfitPerUnit = new double[2];
		ProfitPerUnit[0] = SellingPrice - (Planogram[Slot].getCost() / Planogram[Slot].getProductFormat().getSellingUnits());
		ProfitPerUnit[1] = DiscountSellingPrice - (Planogram[Slot].getCost() / Planogram[Slot].getProductFormat().getSellingUnits());
		double EffectiveProfitMargin[] = new double[2];
		EffectiveProfitMargin[0] = ProfitPerUnit[0] / SellingPrice;
		EffectiveProfitMargin[1] = ProfitPerUnit[1] / DiscountSellingPrice;
		double ProfitPerWeek[] = new double[2];
		ProfitPerWeek[0] = ProfitPerUnit[0] * WeeklyROS[Slot];
		double GrossPerWeek[] = new double[2];
		GrossPerWeek[0] = SellingPrice * WeeklyROS[Slot];
		
		//Assuming 4 weeks per month
		double ProfitPerMonth[] = new double[2];
		ProfitPerMonth[0] = ProfitPerWeek[0]*4;
		double GrossPerMonth[] = new double[2];
		GrossPerMonth[0] = GrossPerWeek[0]*4;
		double MonthlyROS[] = new double[2];
		MonthlyROS[0] = WeeklyROS[Slot]*4;
		
		double BreakEvenWeeklyROS = ProfitPerWeek[0] / ProfitPerUnit[1];
		if ((BreakEvenWeeklyROS - (int)BreakEvenWeeklyROS) != 0)
			BreakEvenWeeklyROS = (int)BreakEvenWeeklyROS + 1.0;
		ProfitPerWeek[1] = ProfitPerUnit[1]*BreakEvenWeeklyROS;
		GrossPerWeek[1] = DiscountSellingPrice*BreakEvenWeeklyROS;
		ProfitPerMonth[1] = ProfitPerWeek[1] * 4;
		GrossPerMonth[1] = GrossPerWeek[1] * 4;
		MonthlyROS[1] = BreakEvenWeeklyROS * 4;
		double ROSPercentIncrease = (MonthlyROS[1] - MonthlyROS[0]) / MonthlyROS[0];
		
		return ("Let's see how much more of " + Planogram[Slot].getProducer() + " " + Planogram[Slot].getName() + " you will need to sell at $" + DiscountSellingPrice + " to break even on the discounted price.\n" + 
				"\tPrice to Consumer:\t\t$" + SellingPrice + "\t\t$" + DiscountSellingPrice + "\n" + 
				"\tCost Per Unit:\t\t\t$" + ((int)((Planogram[Slot].getCost() / Planogram[Slot].getProductFormat().getSellingUnits())*100)/100.0) + "\t\t$" + ((int)((Planogram[Slot].getCost() / Planogram[Slot].getProductFormat().getSellingUnits())*100)/100.0) + "\n" +
				"\tProfit Per Unit:\t\t$" + ((int)(ProfitPerUnit[0]*100)/100.0) + "\t\t$" + ((int)(ProfitPerUnit[1]*100)/100.0) + "\n" +
				"\tProfit Per Case:\t\t$" + ((int)((ProfitPerUnit[0] * Planogram[Slot].getProductFormat().getSellingUnits())*100)/100.0) + "\t\t$" + ((int)((ProfitPerUnit[1] * Planogram[Slot].getProductFormat().getSellingUnits())*100)/100.0) + "\n" +
				"\tMargin Percentage:\t\t" + ((int)(EffectiveProfitMargin[0]*10000)/100.0) + "%\t\t" + ((int)(EffectiveProfitMargin[1]*10000)/100.0) + "%\n" +
				"\tRate Of Sale per Week:\t" + ((int)(WeeklyROS[Slot]*100)/100.0) + "\t\t\t" + ((int)(BreakEvenWeeklyROS*100)/100.0) + "\n" +
				"\tGross Sales per Week:\t$" + ((int)(GrossPerWeek[0]*100)/100.0) + "\t\t$" + ((int)(GrossPerWeek[1]*100)/100.0) + "\n" + 
				"\tProfit Per Week:\t\t$" + ((int)(ProfitPerWeek[0]*100)/100.0) + "\t\t$" + ((int)(ProfitPerWeek[1]*100)/100.0) + "\n" +
				"\tMontly Rate of Sale:\t" + ((int)(MonthlyROS[0]*1000)/1000.0) + "\t\t" + ((int)(MonthlyROS[1]*1000)/1000.0) + "\n" +
				"\tGross Sales Per Month:\t$" + GrossPerMonth[0] + "\t\t$" + GrossPerMonth[1] + "\n" +
				"\nAs the above table shows, you only need to sell an additional " + (int)(MonthlyROS[1] - MonthlyROS[0]) + " " + Planogram[Slot].getProductFormat().getCustomerPurchaseFormat() + " per month. After that it's a pure increase in revenue.\n" +
				"\tThat's only a " + ((int)(ROSPercentIncrease * 10000)/100) + "% increase in volume.");
		
	}
	
	//compare profitability of a specific Beer object in Taps array with a new one supplied by the user.
	public String productComparison(Beer NewProduct, double ExpectedROS, double OldProductSellingPrice, double NewProductSellingPrice)
	{
		int Slot = beerSlotSelector();
		OldProductSellingPrice = BeerPrices[Slot];
		
		double CostPerSellingUnit[] = new double[2];
		CostPerSellingUnit[0] = Planogram[Slot].getCost() / Planogram[Slot].getProductFormat().getSellingUnits();
		CostPerSellingUnit[1] = NewProduct.getCost() / NewProduct.getProductFormat().getSellingUnits();
		double ProfitPerSellingUnit[] = new double[2];
		ProfitPerSellingUnit[0] = OldProductSellingPrice - CostPerSellingUnit[0];
		ProfitPerSellingUnit[1] = NewProductSellingPrice - CostPerSellingUnit[1];
		double EffectiveMargin[] = new double[2];
		EffectiveMargin[0] = ProfitPerSellingUnit[0]/OldProductSellingPrice;
		EffectiveMargin[1] = ProfitPerSellingUnit[1]/NewProductSellingPrice;
		double UnitsPerCase[] = new double[2];
		UnitsPerCase[0] = Planogram[Slot].getProductFormat().getSellingUnits();
		UnitsPerCase[1] = NewProduct.getProductFormat().getSellingUnits();
		double ProfitPerCase[] = new double[2];
		for (int i = 0; i < ProfitPerCase.length; i ++)
			ProfitPerCase[i] = ProfitPerSellingUnit[i] * UnitsPerCase[i];
		double GrossPerCase[] = new double[2];
		GrossPerCase[0] = OldProductSellingPrice * UnitsPerCase[0];
		GrossPerCase[1] = NewProductSellingPrice * UnitsPerCase[1];
		double ProfitPerWeek[] = new double[2];
		double GrossPerWeek[] = new double[2];
		ProfitPerWeek[0] = ProfitPerCase[0] * WeeklyROS[Slot];
		ProfitPerWeek[1] = ProfitPerCase[1] * ExpectedROS;
		GrossPerWeek[0] = GrossPerCase[0] * WeeklyROS[Slot];
		GrossPerWeek[1] = GrossPerCase[1] * ExpectedROS;
		double ProfitPerMonth[] = new double[2];
		double GrossPerMonth[] = new double[2];
		for (int i = 0; i < ProfitPerMonth.length; i++)
		{
			ProfitPerMonth[i] = ProfitPerWeek[i] * 4;
			GrossPerMonth[i] = GrossPerWeek[i] * 4;
		}
		
		return ("Let's compare " + Planogram[Slot].getProducer() + " " + Planogram[Slot].getName() + " and " + NewProduct.getProducer() + " " +NewProduct.getName() + "\n" +
				"\tProducer:\t\t\t" + Planogram[Slot].getProducer() +"\t\t" + NewProduct.getProducer() + "\n" +
				"\tName:\t\t\t\t" + Planogram[Slot].getName() + "\t" + NewProduct.getName() + "\n" +
				"\tSelling Price:\t\t$" + OldProductSellingPrice + "\t\t$" + NewProductSellingPrice + "\n" +
				"\tCost Per Case:\t\t$" + Planogram[Slot].getCost() +"\t\t$" + NewProduct.getCost() + "\n" +
				"\tPacks per Case:\t\tt" + UnitsPerCase[0] + "\t\t" + UnitsPerCase[1] +"\n" +
				"\tCost per Pack:\t\t$" + ((int)(CostPerSellingUnit[0]*100)/100.0) + "\t\t$" + ((int)(CostPerSellingUnit[1]*100)/100.0) + "\n" +
				"\tProfit per Pack:\t$" + ((int)(ProfitPerSellingUnit[0]*100)/100.0) +"\t\t$" + ((int)(ProfitPerSellingUnit[1]*100)/100.0) + "\n" +
				"\tEffective Margin:\t" + ((int)(EffectiveMargin[0]*10000)/100.0) + "%\t\t" + ((int)(EffectiveMargin[1]*10000)/100.0) + "%\n" +
				"\tWeekly Rate of Sale: " + WeeklyROS[Slot] + "\t\t" + ExpectedROS + "\n" +
				"\tGross Per Week:\t\t$" + ((int)(GrossPerWeek[0]*100)/100.0) + "\t\t$" + ((int)(GrossPerWeek[1]*100)/100.0) + "\n" +
				"\tProfit per Week:\t$" + ((int)(ProfitPerWeek[0]*100)/100.0) + "\t\t$" + ((int)(ProfitPerWeek[1]*100)/100.0) + "\n" +
				"\tGross per Month:\t$" + ((int)(GrossPerMonth[0]*100)/100.0) +"\t\t$" + ((int)(GrossPerMonth[1]*100)/100.0) + "\n" +
				"\tProfit per Month:\t$" + ((int)(ProfitPerMonth[0]*100)/100.0) + "\t\t$" + ((int)(ProfitPerMonth[1]*100)/100.0));		 
	}
	
	//Do some arithmatic to show how profitable a specific Beer in Taps array is over the course of a year.
	public String salesOverYear(double LossPercent, double PercentSoldOnSpecial, double SpecialPricing, double RegularPricing)
	{
		int Slot = beerSlotSelector();
		RegularPricing = BeerPrices[Slot];
		double[] UnitProfit = new double[2];
		UnitProfit[0] = RegularPricing - (Planogram[Slot].getCost()/Planogram[Slot].getProductFormat().getSellingUnits());
		UnitProfit[1] = SpecialPricing - (Planogram[Slot].getCost()/Planogram[Slot].getProductFormat().getSellingUnits());
		double[] EffectiveMargin = new double[2];
		EffectiveMargin[0] = UnitProfit[0] / RegularPricing;
		EffectiveMargin[1] = UnitProfit[1] / SpecialPricing;
		double[] ProfitPerCase = new double[2];
		for (int i = 0; i < ProfitPerCase.length; i++)
			ProfitPerCase[i] = UnitProfit[i] * Planogram[Slot].getProductFormat().getSellingUnits();
		double ProfitPerWeek = UnitProfit[0]*((1-PercentSoldOnSpecial)*WeeklyROS[Slot]) + UnitProfit[1]*(PercentSoldOnSpecial*WeeklyROS[Slot]);
		double ProfitPerMonth = ProfitPerWeek * 4;
		double ProfitPerQuarter = ProfitPerWeek * 13;
		double ProfitPerYear = ProfitPerWeek*52;
		
		double GrossPerWeek = WeeklyROS[Slot] * (Planogram[Slot].getCost()/Planogram[Slot].getProductFormat().getSellingUnits());
		double GrossPerMonth = GrossPerWeek * 4;
		double GrossPerQuarter = GrossPerWeek * 13;
		double GrossPerYear = GrossPerWeek * 52;
		
		return ("Given your percent of beer sold on discount vs sold at regular the regular price you can expect the following revenue stream for " + Planogram[Slot].getProducer() + " " + Planogram[Slot].getName() +":\n" +
				"\t\t\t\tGross\t\tProfit\n" +
				"\tWeekly:\t\t$" + ((int)(GrossPerWeek*100)/100.0) + "\t\t$" + ((int)(ProfitPerWeek*100)/100.0) + "\n" +
				"\tMonthly:\t$" + ((int)(GrossPerMonth*100)/100.0) +"\t\t$" + ((int)(ProfitPerMonth*100)/100.0) + "\n" + 
				"\tQuarterly:\t$" + ((int)(GrossPerQuarter*100)/100.0) + "\t\t$" + ((int)(ProfitPerQuarter*100)/100.0) + "\n" +
				"\tYearly:\t\t$" + ((int)(GrossPerYear*100)/100.0) + "\t$" + ((int)(ProfitPerYear*100)/100.0));
	}

	//Uses user input to identify the index of which Beer the user wants to work with from the Planogram Array in other methods.
	private int beerSlotSelector()
	{
		int Slot;
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Please select a which slot in the beer Planogram you would like to work with:  ");
		try
		{
			Slot = keyboard.nextInt() - 1;
			if ((Slot < 0) || (Slot >= Planogram.length))
				throw new InvalidSelectionException();
			return Slot;
		}
		catch (InvalidSelectionException e)
		{
			System.out.println("That doesn't represent any current beers in the planogram.  Let's try that one again.");
			return beerSlotSelector();
		}
	}
	
	//returns the toString() from Account super class.
	public String Info()
	{
		return super.toString();
	}
}
