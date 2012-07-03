/**
 * @(#)BudgetHelperTest.java
 *
 *
 * @author Borys H.N.
 * @version 1.00 2012/6/29
 */
import java.math.BigDecimal;

public class BudgetHelperTest {
    public static void main(String [] args) {
    	System.out.println("***********************");
    	System.out.println("* Budget Helper v 0.1 *");
    	System.out.println("* Borys H.N.          *");
    	System.out.println("* June 28th 2012      *");
    	System.out.println("***********************\n");

        /*
	//First the Budget class test
	System.out.println("[Budget class test]");
	
        //create a new budget
        Budget aBudget = new Budget();
        
        //create some values used in a budget
	int numCategories = 5;
        BigDecimal budgetTotal = new BigDecimal(850); //$850 monthly
        int duration = 3; //monthly budget
	String [] categories = {"One", "Two", "Three", "Four", "Five"};
        float [] percentages = {25, 25, 15, 15, 20};
        
        //set budget values
        aBudget.setBudgetValues(budgetTotal, numCategories, categories, percentages, duration);
        
        System.out.println("numberOfCategories = " + aBudget.getNumCategories());
	System.out.print("budgetCategories = ");
		
        for (String s : aBudget.getBudgetCategories()) {
            System.out.print(s + " ");
	}
        System.out.println();
        aBudget.printBudgetInformation();
	System.out.println();
	//test complete
                
        //Second the BudgetPlanner class
        System.out.println("[BudgetPlanner class test]");
        BudgetPlanner planner = new BudgetPlanner();
        //test complete
        */
        
        //the third test is of the BudgetHelper class
        System.out.println("[BudgetHelper class test]");
        BudgetHelper budgetHelper = new BudgetHelper();
    }
}