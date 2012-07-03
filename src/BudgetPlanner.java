/**
 * @(#)BudgetPlanner.java
 *
 *
 * @author Borys H.N.
 * @version 1.00 2012/6/29
 */
import java.math.BigDecimal;
import java.io.*;
import java.util.Scanner;

public class BudgetPlanner {
    //fields
    private Budget budget;

    //constructors
    public BudgetPlanner() {
    	setBudgetValues();
    }

    public BudgetPlanner(Budget aBudget) {
    	setBudget(aBudget);
    }

    //accessor methods
    
    public Budget getBudget() {
        return budget;
    }
    
    public void showBudget() {
        budget.printBudgetInformation();
    }

    //mutator methods
    
    /*
     * setBudgetValues()
     *  requires that...
     *      numbet of categories == length of categories array == length of percentages array
     *      all budget percentages add up to 100
     */
    public void setBudgetValues() {
	int numCategories = 0;
        BigDecimal budgetTotal = new BigDecimal(0);
        String [] categories = new String[0];
    	float [] percentages = new float[0];
        int span = 0;         
        
        //these vars will be used for checks
        char correct = 'n'; //to see if budget info is correct...probably needs a better name
        float sumCheck = 0; //this is the percentages sumcheck... ||
	
        //enter information untill it is found correct
        do {
            Scanner s = new Scanner(System.in);
            
            //budget total
            System.out.print("Budget Total: ");
            budgetTotal = s.nextBigDecimal(); 
            
            //Budget duration
            System.out.println("Enter number of budget duration: ");
            System.out.print("1. weekly, 2. biweekly, 3. monthly: ");
            span = s.nextInt();
            
            //# of categories
            System.out.print("Enter the number of Categories: ");
            numCategories = s.nextInt();
                        
            categories = new String[numCategories];
            percentages = new float[numCategories];
            
            //set categories and percentages
            for (int i = 0; i < numCategories; i++) {
                System.out.print("Category #" + (i+1) + ": ");
                categories[i] = s.next().toString();
                System.out.print("Percantage: ");
                percentages[i] = s.nextFloat();
                sumCheck += percentages[i];
            } 
            
            //sum check, budget values must add up to 100
            if (sumCheck == 100.0) {
                System.out.println("\n[Budget Information]");
                        
                budget = new Budget(budgetTotal, numCategories, categories, percentages, span);
                budget.printBudgetInformation();
            
                System.out.print("Is this correct? y/n: ");
                String input = s.next();
                input.toLowerCase();
                correct = input.charAt(0);
            } else {
                System.out.println("Sum of percentages != 100");
            }

         } while(numCategories == 0 || numCategories != categories.length ||
                 numCategories != percentages.length || correct == 'n');
	}

    public void setBudget(Budget aBudget) {
	budget = aBudget;
    }
}