package beerSalesProfitStories;

/**
 * Program: ProfiStoriesDrive Class3
 * Class: CSC 201 Fall 2020
 * Author: Nathaniel Fink-Humes
 * Date: 12/7/2020
 */

import java.util.Scanner;
import java.io.*;

public class ProfitStoriesDrive {

	public static void main(String[] args)
	{
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Welcome to Beer Profit Stories.  Let's set up an On-Premise and Off-Premise account.  We will do the Off-Premise Account first.\n");
		OffPremise OffPremAccount = SetUpOffPremAccount();
		System.out.println("Now let's set up an On-Premise account to work with.");
		OnPremise OnPremAccount = setUpOnPremAccount();
		boolean Continue = true;
		char Selection;
		System.out.println("Now that we've set up the accounts lets go into the operations menu.\n\tRemember all comparison reports will be printed to an external file ProfitStores.txt for sharing with accounts.");
		while (Continue)
		{
			Selection = chooseAnAction();
			switch (Selection)
			{
				case 'A':
					offBreakEven(OffPremAccount);
					break;
				case 'B':
					onBreakEven(OnPremAccount);
					break;
				case 'C':
					offProductComparison(OffPremAccount);
					break;
				case 'D':
					onProductComparison(OnPremAccount);
					break;
				case 'E':
					offYearlySales(OffPremAccount);
					break;
				case 'F':
					onYearlySales(OnPremAccount);
					break;
				case 'G':
					OffPremAccount = adjustOffPremAcct(OffPremAccount);
					break;
				case 'H':
					OnPremAccount = adjustOnPremAcct(OnPremAccount);
					break;
				default:
					Continue = false;
			}
		}
		
		System.out.println("Thank you for using Beer Profit Stories.  Hopefully these will be useful for building your own sales story.");
		
		
	}
	
	//Choose whether or not to import information from a text document
	private static boolean importChoice()
	{
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Would you like to import account information from a file?  If so then please press Y, otherwise type any other character.  ");
		String Selection = keyboard.next();
		Selection = Selection.toUpperCase();
		char Choice = Selection.charAt(0);
		if (Choice == 'Y')
			return true;
		else
			return false;
	}
	
	
	//Set up an OffPremise object through either importing from a text file or manual input.
	private static OffPremise SetUpOffPremAccount()
	{
		Scanner keyboard = new Scanner(System.in);
		boolean ImportChoice = importChoice();
		if (ImportChoice)
		{
			try
			{
				Scanner inputStream = new Scanner(new File ("OffPremiseAccountInfo.txt"));
				String AccountName = inputStream.nextLine();
				String Address = inputStream.nextLine();
				String DecisionMaker = inputStream.nextLine();
				double Margin = inputStream.nextDouble();
				int PlanogramSize = inputStream.nextInt();
				inputStream.nextLine();
				OffPremise CurrentAccount = new OffPremise(AccountName, Margin, Address, DecisionMaker, PlanogramSize);
				Beer NewBeer = null;
				for (int i = 0; i < CurrentAccount.getPlanogram().length; i++)
				{
					String Producer = inputStream.nextLine();
					String Name = inputStream.nextLine();
					String SellingFormat = inputStream.nextLine();
					String CustomerPurchaseFormat = inputStream.nextLine();
					String ServingVessel = inputStream.nextLine();
					double Cost = inputStream.nextDouble();
					int ServingSize = inputStream.nextInt();
					NewBeer = new Beer(Name, Producer, Cost, SellingFormat, ServingSize, CustomerPurchaseFormat, ServingVessel);
					CurrentAccount.setPlanogramSlot(i, NewBeer);
					inputStream.nextLine();
				}
				inputStream.close();
				return CurrentAccount;
			}
			catch (FileNotFoundException e)
			{
				System.out.println("I'm sorry but there was no file to be found.  Please try running the program again after creating the file or simply input the values into the program by hand.");
				System.exit(0);
			}
			return new OffPremise();
		}
		else
		{
			System.out.println("What is the name of the account?");
			String AccountName = keyboard.nextLine();
			System.out.println("What is their address?");
			String Address = keyboard.nextLine();
			System.out.println("What is the buyer's name?");
			String DecisionMaker = keyboard.nextLine();
			System.out.print("What is their margin? Please enter the percent as a decimal.  ");
			double Margin = keyboard.nextDouble();
			System.out.print("How many beers do they have in their planogram?  ");
			int PlanogramSize = keyboard.nextInt();
			OffPremise CurrentAccount = new OffPremise(AccountName, Margin, Address, DecisionMaker, PlanogramSize);
			CurrentAccount.setPlanogram();
			return CurrentAccount;
		}
	}

	//Set up an OnPremise object through either importing from a text file or user input
	private static OnPremise setUpOnPremAccount()
	{
		Scanner keyboard = new Scanner(System.in);
		boolean ImportChoice = importChoice();
		if (ImportChoice)
		{
			try
			{
				Scanner inputStream = new Scanner(new File ("OnPremiseAccountInfo.txt"));
				String AccountName = inputStream.nextLine();
				String Address = inputStream.nextLine();
				String DecisionMaker = inputStream.nextLine();
				double Margin = inputStream.nextDouble();
				int PlanogramSize = inputStream.nextInt();
				inputStream.nextLine();
				OnPremise CurrentAccount = new OnPremise(AccountName, Margin, Address, DecisionMaker, PlanogramSize);
				Beer NewBeer = null;
				double[] ROS = new double [CurrentAccount.getTaps().length];
				for (int i = 0; i < CurrentAccount.getTaps().length; i++)
				{
					String Producer = inputStream.nextLine();
					String Name = inputStream.nextLine();
					String SellingFormat = inputStream.nextLine();
					String CustomerPurchaseFormat = inputStream.nextLine();
					String ServingVessel = inputStream.nextLine();
					double Cost = inputStream.nextDouble();
					int ServingSize = inputStream.nextInt();
					ROS[i] = inputStream.nextDouble();
					NewBeer = new Beer(Name, Producer, Cost, SellingFormat, ServingSize, CustomerPurchaseFormat, ServingVessel);
					CurrentAccount.setTap(NewBeer, i);
					inputStream.nextLine();
				}
				inputStream.close();
				CurrentAccount.setRateOfSale(ROS);
				return CurrentAccount;
			}
			catch (FileNotFoundException e)
			{
				System.out.println("I'm sorry but there was no file to be found.  Please try running the program again after creating the file or simply input the values into the program by hand.");
				System.exit(0);
			}
			return new OnPremise();
		}
		else
		{
			System.out.println("What is the name of the account?");
			String AccountName = keyboard.nextLine();
			System.out.println("What is their address?");
			String Address = keyboard.nextLine();
			System.out.println("What is the buyer's name?");
			String DecisionMaker = keyboard.nextLine();
			System.out.print("What is their usual margin? Please enter the percent as a decimal.  ");
			double Margin = keyboard.nextDouble();
			System.out.print("How many beers do they have on tap?  ");
			int PlanogramSize = keyboard.nextInt();
			OnPremise CurrentAccount = new OnPremise(AccountName, Margin, Address, DecisionMaker, PlanogramSize);
			CurrentAccount.setTaps();
			CurrentAccount.setAllRatesOfSale();
			return CurrentAccount;
		}
	}
	
	//Switch command to choose what the user would like to do.
	private static char chooseAnAction()
	{
		Scanner keyboard = new Scanner(System.in);
		String Selection;
		char Choice;
		System.out.println("Please type in the corresponding character below to the action you would like to take:\n"
				+ "\t 'A' - Calculate the Break Even Rate of Sale for a specific beer at Special Pricing in your Off-Premise Account.\n"
				+ "\t 'B' - Calculate the Break Even Rate of Sale for if a specific tap is put on a Sale Price at your On-Premise Account.\n"
				+ "\t 'C' - Compare a new beer to one currently in the Planogram for your Off-Premise Account.\n"
				+ "\t 'D' - Compare a new beer to on currently on Tap at your On-Premise Account.\n"
				+ "\t 'E' - Calculte Yearly Profit for a specific beer in the Planogram at your Off-Premise Account.\n"
				+ "\t 'F' - Calculate Yearly Profit for a specific beer on tap at your On-Premise Account.\n"
				+ "\t 'G' - Make any changes to your Off-Premise Account.\n"
				+ "\t 'H' - Make any changes to your On-Premise Account.\n"
				+ "\t 'N' or any other unspecified character to conclude operations.");
		Selection = keyboard.next();
		Selection = Selection.trim();
		Selection = Selection.toUpperCase();
		Choice = Selection.charAt(0);
		return Choice;		
	}
	
	
	//run discountBreakEven() for an OffPremise object.  Write results to text file.
	private static void offBreakEven(OffPremise CurrentAccount)
	{
		Scanner keyboard = new Scanner(System.in);
		System.out.println("What is the sale price of the beer on discount?  ");
		double SalePrice = keyboard.nextDouble();
		PrintWriter outputStream = null;
		try 
		{
			outputStream = new PrintWriter(new FileOutputStream("ProfitStores.txt", true));
		}
		catch (FileNotFoundException e)
		{
			System.out.println("We were unable to find the ProfitStores.txt file.  Please make sure it has been created and is blank before running this program.");
			System.exit(0);
		}
		outputStream.println("\n" + CurrentAccount.Info() + "\n" + CurrentAccount.discountBreakEven(SalePrice, 0.0));
		outputStream.close();
		System.out.println("The analysis has been printed to the file.\n");
	}
	
	
	//run discountBreakEven for an OnPremise object.  Write results to text file.
	private static void onBreakEven(OnPremise CurrentAccount)
	{
		Scanner keyboard = new Scanner(System.in);
		System.out.println("What is the sale price of the beer on discount?  ");
		double SalePrice = keyboard.nextDouble();
		System.out.println("What is the regular price of the beer on discount?  ");
		double RegularPrice = keyboard.nextDouble();
		PrintWriter outputStream = null;
		try 
		{
			outputStream = new PrintWriter(new FileOutputStream("ProfitStores.txt", true));
		}
		catch (FileNotFoundException e)
		{
			System.out.println("We were unable to find the ProfitStores.txt file.  Please make sure it has been created and is blank before running this program.");
			System.exit(0);
		}
		outputStream.println("\n" + CurrentAccount.Info() + "\n" + CurrentAccount.discountBreakEven(SalePrice, RegularPrice));
		outputStream.close();
		System.out.println("The analysis has been printed to the file.\n");
	}
	
	//run salesOverYear() for an OffPremise object and write results to text file.
	private static void offYearlySales(OffPremise CurrentAccount)
	{
		Scanner keyboard = new Scanner(System.in);
		double SalePrice;
		double SalePercent;
		
		System.out.print("What is the percent of the beer you would like to look at that is sold on Sale per week?  ");
		SalePercent = keyboard.nextDouble();
		System.out.print("What is the sale price of that beer?  ");
		SalePrice = keyboard.nextDouble();
		PrintWriter outputStream = null;
		try 
		{
			outputStream = new PrintWriter(new FileOutputStream("ProfitStores.txt", true));
		}
		catch (FileNotFoundException e)
		{
			System.out.println("We were unable to find the ProfitStores.txt file.  Please make sure it has been created and is blank before running this program.");
			System.exit(0);
		}
		outputStream.println("\n" + CurrentAccount.Info() + "\n" + CurrentAccount.salesOverYear(0.0, SalePercent, SalePrice, 0.0));
		outputStream.close();
		System.out.println("The analysis has been printed to the file.\n");
	}
	
	//run SalesOverYear() for an OnPremise object and write results to text file
	private static void onYearlySales(OnPremise CurrentAccount)
	{
		Scanner keyboard = new Scanner(System.in);
		double SalePrice;
		double SalePercent;
		double RegularPrice;
		double LossPercent;
		System.out.print("What percent of your beer sales occur during happy hour?  ");
		SalePercent = keyboard.nextDouble();
		System.out.print("What is the happy hour price of the beer we are examining?  ");
		SalePrice = keyboard.nextDouble();
		System.out.print("What is the beers price outside of happy hour?  ");
		RegularPrice = keyboard.nextDouble();
		System.out.print("What is your average percent loss per keg?  ");
		LossPercent = keyboard.nextDouble();
		PrintWriter outputStream = null;
		try 
		{
			outputStream = new PrintWriter(new FileOutputStream("ProfitStores.txt", true));
		}
		catch (FileNotFoundException e)
		{
			System.out.println("We were unable to find the ProfitStores.txt file.  Please make sure it has been created and is blank before running this program.");
			System.exit(0);
		}
		outputStream.println("\n" + CurrentAccount.Info() + "\n" + CurrentAccount.salesOverYear(LossPercent, SalePercent, SalePrice, RegularPrice));
		outputStream.close();
		System.out.println("The analysis has been printed to the file.\n");
	}
	
	//run productComparison() for a OffPremise object and write results to a text file.
	private static void offProductComparison(OffPremise CurrentAccount)
	{
		Scanner keyboard = new Scanner(System.in);
		System.out.println("First lets get some information about the beer not in the planogram");
		Beer NewBeer = generateBeer();
		System.out.print("How many cases should an account like " + CurrentAccount.getName() + " expect to sell per week?  ");
		double NewROS = keyboard.nextDouble();
		System.out.println("How much should a customer expect to pay for this beer?");
		double NewPrice = keyboard.nextDouble();
		PrintWriter outputStream = null;
		try 
		{
			outputStream = new PrintWriter(new FileOutputStream("ProfitStores.txt", true));
		}
		catch (FileNotFoundException e)
		{
			System.out.println("We were unable to find the ProfitStores.txt file.  Please make sure it has been created and is blank before running this program.");
			System.exit(0);
		}
		outputStream.println("\n" + CurrentAccount.Info() + "\n" + CurrentAccount.productComparison(NewBeer, NewROS, 0.0, NewPrice));
		outputStream.close();
		System.out.println("The analysis has been printed to the file.\n");
	}
	
	//run productComparison() for an OnPremise object and write results to a text file.
	private static void onProductComparison(OnPremise CurrentAccount)
	{
		Scanner keyboard = new Scanner(System.in);
		System.out.println("First lets get some information about the beer not on tap");
		Beer NewBeer = generateBeer();
		System.out.print("How many kegs should an account like" + CurrentAccount.getName() + " expect to sell per month?  ");
		double NewROS = keyboard.nextDouble();
		System.out.print("How much does the beer currently on tap sell for?  ");
		double OldPrice = keyboard.nextDouble();
		System.out.print("How much should the new beer sell for on draft?  ");
		double NewPrice = keyboard.nextDouble();
		PrintWriter outputStream = null;
		try 
		{
			outputStream = new PrintWriter(new FileOutputStream("ProfitStores.txt", true));
		}
		catch (FileNotFoundException e)
		{
			System.out.println("We were unable to find the ProfitStores.txt file.  Please make sure it has been created and is blank before running this program.");
			System.exit(0);
		}
		outputStream.println("\n" + CurrentAccount.Info() + "\n" + CurrentAccount.productComparison(NewBeer, NewROS, OldPrice, NewPrice));
		outputStream.close();
		System.out.println("The analysis has been printed to the file.\n");
	}
	
	//Change specific attributes for an OffPremise object.
	private static OffPremise adjustOffPremAcct(OffPremise CurrentAccount)
	{
		Scanner keyboard = new Scanner(System.in);
		System.out.println("What would you like to adjust about your Off-Premise account?  ");
		char Selection = adjustmentAction(keyboard);
		int Slot = 0;
		switch (Selection)
		{
		case 'A':
			System.out.println("What is the name of the account?");
			String AccountName = keyboard.nextLine();
			System.out.println("What is their address?");
			String Address = keyboard.nextLine();
			System.out.println("What is the buyer's name?");
			String DecisionMaker = keyboard.nextLine();
			System.out.print("What is their margin? Please enter the percent as a decimal.  ");
			double Margin = keyboard.nextDouble();
			CurrentAccount.setAddress(Address);
			CurrentAccount.setName(AccountName);
			CurrentAccount.setDecisionMaker(DecisionMaker);
			CurrentAccount.setMargin(Margin);
			break;
		case 'B':
			System.out.print("Which beer would you like to change in the Planogram?  ");
			try
			{
			Slot = keyboard.nextInt() - 1;
			if ((Slot < 0)  || (Slot >= CurrentAccount.getPlanogram().length))
					throw new InvalidSelectionException();
			CurrentAccount.setPlanogramSlot(Slot);
			}
			catch (InvalidSelectionException e)
			{
				System.out.println("I'm sorry, but there is no beer in the Planogram slot " + (Slot+1) + " try running the adjust program again.");
			}
			break;
		case 'C':
			CurrentAccount.setPlanogram();
			break;
		case 'D':
			System.out.print("Which slot in the planogram would you like to adjust the weekly Rate of Sale of?  ");
			try
			{
			Slot = keyboard.nextInt() - 1;
			if ((Slot < 0)  || (Slot >= CurrentAccount.getPlanogram().length))
				throw new InvalidSelectionException();
			System.out.print("What is the new weekly Rate of Sale for " + CurrentAccount.getSpecificPlanogramSlot(Slot).getProducer() + " " + CurrentAccount.getSpecificPlanogramSlot(Slot).getName() + "?  ");
			double ROS = keyboard.nextDouble();
			CurrentAccount.setSpecificWeeklyROS(Slot, ROS);
			}
			catch (InvalidSelectionException e)
			{
				System.out.println("I'm sorry, but there is no beer in the Planogram slot " + (Slot+1) + " try running the adjust program again.");
			}
			break;
		case 'E':
			double[] ROSArray = new double[CurrentAccount.getWeeklyROS().length];
			for (int i = 0; i < ROSArray.length; i++)
			{
				try
				{
					System.out.print("What is the current weekly Rate of Sale for " + CurrentAccount.getSpecificPlanogramSlot(i).getProducer() + " " + CurrentAccount.getSpecificPlanogramSlot(i).getName() +"?  ");
					ROSArray[i] = keyboard.nextDouble();
					if (ROSArray[i] < 0)
						throw new LessThanZeroException("I'm sorry, but I don't believe you can have a negative Rate of Sale.  Let's try that one again.");
				}
				catch (LessThanZeroException e)
				{
					System.out.println(e);
					i--;
				}
			}
			CurrentAccount.setWeeklyROS(ROSArray);
			break;
		default:
			break;
		}
		System.out.println("Thank you for making adjustments to your Off-Premise Account.");
		return CurrentAccount;
	}
	
	//change specific attributes for an OnPremise object.
	private static OnPremise adjustOnPremAcct(OnPremise CurrentAccount)
	{
			Scanner keyboard = new Scanner(System.in);
			System.out.println("What would you like to adjust about your On-Premise account?  ");
			char Selection = adjustmentAction(keyboard);
			int Slot = 0;
			switch (Selection)
			{
			case 'A':
				System.out.println("What is the name of the account?");
				String AccountName = keyboard.nextLine();
				System.out.println("What is their address?");
				String Address = keyboard.nextLine();
				System.out.println("What is the buyer's name?");
				String DecisionMaker = keyboard.nextLine();
				System.out.print("What is their margin? Please enter the percent as a decimal.  ");
				double Margin = keyboard.nextDouble();
				CurrentAccount.setAddress(Address);
				CurrentAccount.setName(AccountName);
				CurrentAccount.setDecisionMaker(DecisionMaker);
				CurrentAccount.setMargin(Margin);
				break;
			case 'B':
				System.out.print("Which beer would you like to change on Tap?  ");
				try
				{
				Slot = keyboard.nextInt() - 1;
				if ((Slot < 0)  || (Slot >= CurrentAccount.getTaps().length))
						throw new InvalidSelectionException();
				Beer NewBeer = generateBeer();
				CurrentAccount.setTap(NewBeer, Slot);
				CurrentAccount.setRateOfSale(Slot);
				}
				catch (InvalidSelectionException e)
				{
					System.out.println("I'm sorry, but there is no beer on tap number " + (Slot+1) + " try running the adjust program again.");
				}
				break;
			case 'C':
				CurrentAccount.setTaps();
				break;
			case 'D':
				CurrentAccount.setRateOfSale();
				break;
			case 'E':
				double[] ROSArray = new double[CurrentAccount.getTaps().length];
				for (int i = 0; i < ROSArray.length; i++)
				{
					try
					{
						System.out.print("What is the current monthly Rate of Sale for " + CurrentAccount.getSpecificTap(i).getProducer() + " " + CurrentAccount.getSpecificTap(i).getName() +"?  ");
						ROSArray[i] = keyboard.nextDouble();
						if (ROSArray[i] < 0)
							throw new LessThanZeroException("I'm sorry, but I don't believe you can have a negative Rate of Sale.  Let's try that one again.");
					}
					catch (LessThanZeroException e)
					{
						System.out.println(e);
						i--;
					}
				}
				CurrentAccount.setRateOfSale(ROSArray);
				break;
			default:
				break;
			}
			System.out.println("Thank you for making adjustments to your On-Premise Account.");
			return CurrentAccount;
			
	}
	
	//switch command which is used to decide what attributes to change for either an OnPremise or OffPremise object.
	private static char adjustmentAction(Scanner keyboard)
	{
		System.out.println("Please type in the corresponding character below to the action you would like to take:\n"
				+ "\t 'A' - Change Account Information.\n"
				+ "\t 'B' - Change a specific beer.\n"
				+ "\t 'C' - Change all available beers.\n"
				+ "\t 'D' - Change a specific beer's Rate of Sale.\n"
				+ "\t 'E' - Change all beers' Rate Of Sale\n"
				+ "\t 'N' or any other unspecified character to return to the main menu.");
		String Selection = keyboard.next();
		Selection = Selection.trim();
		Selection = Selection.toUpperCase();
		char Choice = Selection.charAt(0);
		return Choice;
	}
	
	//Use user input to instantiate a new Beer object then return it to another method.
	private static Beer generateBeer()
	{
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Who makes this beer?");
		String Producer = keyboard.nextLine();
		System.out.println("What is this beer called?");
		String Name = keyboard.nextLine();
		System.out.println("What format does the beer come to the account as?");
		String SellingFormat = keyboard.nextLine();
		System.out.println("In what format does the customer purchase a beer?");
		String CustomerPurchaseFormat = keyboard.nextLine();
		System.out.println("How is the beer consumed, i.e. pint glass, bottle, can?");
		String ServingVessel = keyboard.nextLine();
		System.out.println("How much per keg or case of the beer?");
		double Cost = keyboard.nextDouble();
		System.out.println("How many ounces per serving of the beer?");
		int ServingSize = keyboard.nextInt();
		Beer NewBeer = new Beer(Name, Producer, Cost, SellingFormat, ServingSize, CustomerPurchaseFormat, ServingVessel);
		return NewBeer;
	}
	
	
}