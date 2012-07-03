/**
 * @(#)Budget.java
 *
 *
 * @author Borys H.N.
 * @version 1.00 2012/6/29
 */
import java.io.*; 
import java.math.MathContext;
import java.math.RoundingMode;
import java.math.BigDecimal;

public class Budget {
    //fields
    private BigDecimal budgetTotal;
    private int numberOfCategories;
    private String [] budgetCategories;
    private float [] budgetPercentages;
    private BigDecimal [] weightedCategories;
    private budgetSpan budgetDuration;    
    
    //how long do budgets last
    public enum budgetSpan {
        notSet(0), weekly(1), biweekly(2), monthly(3);
        
        private int code;
        
        private budgetSpan(int c) {
            code = c;
        }
        
        public int getCode() {
            return code;
    }
   
        }
    //constructors
    public Budget() {
        budgetTotal = new BigDecimal(0);
        numberOfCategories = 0;
        budgetCategories = new String[0];
        budgetPercentages = new float[0];
        weightedCategories = new BigDecimal[0];
    }

    /*
     * Budget(BigDecimal budgetTotal, int num, String [] categories, 
     *        float[] percentages, int span)
     * 	requires that...
     *		num > 0
     *		num == number of strings in array
     *		num == number of percentages in array
     */
    public Budget(BigDecimal total, int num, String [] categories, 
        float[] percentages, int span) {
    	
        budgetTotal = new BigDecimal(0);
        budgetDuration = budgetSpan.notSet;
        budgetCategories = new String[num];
        budgetPercentages = new float[num];
        weightedCategories = new BigDecimal[num];
        setBudgetTotal(total);
        
        if (setCategories(num, categories) && setBudgetPercentages(percentages) &&
            weighBudgetCategories()) {
            setBudgetDuration(span);
        } else { 
            System.out.println("Could not create a Budget object...");
    	}
    }

    //accessor methods
    public budgetSpan getBudgetDuration() {
        return budgetDuration;
    }
    
    public int getNumCategories() {
        return numberOfCategories;
    }

    public String [] getBudgetCategories() {
    	return budgetCategories;
    }
    
    public float [] getBudgetPercentages() {
        return budgetPercentages;
    }
    
    public BigDecimal [] getWeighedCategories() {
        return weightedCategories;
    }
    
    public BigDecimal getTotal() {
        return budgetTotal; 
    }
    
    public void printBudgetInformation() {
        if (numberOfCategories != 0) {
            System.out.println("Budget duration: " + budgetDuration);
            System.out.println("Budget total: $" + budgetTotal);
            System.out.println("# of categories: " + numberOfCategories);
            System.out.println("Categories & Percentages: ");
            for (int i = 0; i < numberOfCategories; i++) {
                System.out.println((i+1) + ":" + budgetCategories[i] + ", " + budgetPercentages[i] + 
                                   "%, $" + weightedCategories[i].round(new MathContext(5, RoundingMode.HALF_UP)));
            }
        } 
    }

    //mutator methods
    public void setBudgetValues(BigDecimal total, int num, String [] categories, 
                                 float[] percentages, int span) {
        weightedCategories = new BigDecimal[num];
        setBudgetTotal(total);
        if (setCategories(num, categories) && setBudgetPercentages(percentages)) {
            weighBudgetCategories();
            setBudgetDuration(span);
        } else { 
            System.out.println("Could not create a Budget object...");
    	}
    }
    
    private void setBudgetTotal(BigDecimal total) {
        budgetTotal = total;
    }
    
    private void setBudgetDuration(int duration) {
        switch (duration) {
            case 1:
                budgetDuration = budgetSpan.weekly;
                break;
            case 2: 
                budgetDuration = budgetSpan.biweekly;
                break;
            case 3:
                budgetDuration = budgetSpan.monthly;
                break;
            default:
                budgetDuration = budgetSpan.notSet;
                break;
        }
            
    }

 /* setCategories(int num, String [] categories)
  * 	requires that...
  *		num > 0
  *		num == number of strings in array
  **/
 private boolean setCategories(int num, String [] categories) {
    try {
        if (num > 0 && num == categories.length) {
            numberOfCategories = num;
            budgetCategories = new String[numberOfCategories];

            for (int i = 0; i < numberOfCategories; i++) {
                budgetCategories[i] = categories[i];
            }
        } else {
            throw new IOException("Input error...");
        }
    } catch (IOException e) {
            System.out.println("Caught IOException: " + e.getMessage());
            return false;
        }
    return true;
 }

  /* setBudgetPercentages(float [] percentages)
      requires that...
          numberOfCategories > 0
          numberOfCategories == number of percentages in array
          percentages must add to 100
  */
  private boolean setBudgetPercentages(float [] percentages) {
      try {
          if (numberOfCategories > 0 && numberOfCategories == percentages.length) {
		float sumOfPercentages = 0;
		for (int i = 0; i < numberOfCategories; i++) {
                  sumOfPercentages += percentages[i];
              }
		if (sumOfPercentages == 100) {
                  budgetPercentages = new float[5];
                  for (int i = 0; i < numberOfCategories; i++) {
			 budgetPercentages[i] = percentages[i];
                  }
		}
          } else {
              throw new IOException("input error...");
          }
	} catch (IOException e) {
		System.out.println("Caught IOException: " + e.getMessage());
                return false;
	}
      return true;
  }
  
  /* weighBudgetCategories()
   *  requires that...
   *      budgetTotal > 0
   *      numberOfCategories > 0
   *      numberOfCategories == budgetCategories.length
   *      numberOfCategories == budgetPercentages.length
   */
  private boolean weighBudgetCategories() {
      if (numberOfCategories > 0) {
          for (int i = 0; i < numberOfCategories; i++) {
              BigDecimal nextPercentage = new BigDecimal(budgetPercentages[i]/100.0f);
              BigDecimal weighedOut = nextPercentage.multiply(budgetTotal);
              weightedCategories[i] = weighedOut; 
          }
      } else {
          return false;
      }
    return true;
  }
}