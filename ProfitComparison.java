package beerSalesProfitStories;

/**
 * Program: ProfitComparison Interface
 * Class: CSC 201 Fall 2020
 * Author: Nathaniel Fink-Humes
 * Date: 12/7/2020
 */

public interface ProfitComparison {
	
	public String discountBreakEven(double DiscountSellingPrice, double SellingPrice);
	
	public String productComparison(Beer NewProduct, double ExpectedROS, double OldProductSellingPrice, double NewProductSellingPrice);
	
	public String salesOverYear(double LossPercent, double PercentSoldOnSpecial, double SpecialPricing, double RegularPricing);

}
