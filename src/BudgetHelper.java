/**
 * @(#)BudgetHelper.java
 *
 *
 * @author Borys H.N.
 * @version 1.00 2012/6/29
 */
import java.util.Scanner;

public class BudgetHelper {
    private BudgetPlanner budgetPlanner;
    private BudgetWriter budgetWriter;
    private BudgetLoader budgetLoader; 
    
    public BudgetHelper() {
        boolean noExitCode = true;
        int menuLevel = 1;
        int input = 0;
        do {
            printMenu(menuLevel);
            input = getMenuOption();
            menuLevel = navigateMenu(menuLevel, input);
            if (menuLevel == 0) { 
                noExitCode = false;
            }
        } while (noExitCode == true);
    }
    
    //accessor methods
    public void printMenu(int menuLevel) {
        switch (menuLevel) {
            default:
            case 1:
                System.out.println("--------Menu 1--------");
                System.out.println("1. Create a new budget");
                System.out.println("2. Load an old budget");
                System.out.println("3. Exit");
                break;
            case 2:
                System.out.println("--------Menu 2--------");
                System.out.println("1. View budget");
                System.out.println("2. Edit budget");
                System.out.println("3. Save budget");
                System.out.println("4. Return");
                break;
        }
    }
    
    public int getMenuOption() {
        int input = 0;
        Scanner s = new Scanner(System.in);
        System.out.print(":> ");
        input = s.nextInt();
        return input;
    }
    
    public int navigateMenu(int menuLevel, int input) {
        int newMenuLevel = menuLevel;
        
        switch (menuLevel) {
            default:
                //do nothing to navigate if it is unclear
                break;
            //this is menu level 1
            case 1: 
                if (input == 1) { //create a new budget
                    budgetPlanner = new BudgetPlanner();
                    newMenuLevel = 2;
                } else if (input == 2) { //load an old budget
                    budgetLoader = new BudgetLoader();
                    budgetPlanner = new BudgetPlanner(budgetLoader.getBudget());
                    newMenuLevel = 2;
                } else if (input == 3) { //exit
                    newMenuLevel = 0; //exit code
                }
                break;
            //this s menu level 2
            case 2:
                if (input == 1) { //view budget
                    budgetPlanner.showBudget();
                    Scanner s = new Scanner(System.in);
                    System.out.println("(press enter to continue...)");
                    s.nextLine();
                } else if (input == 2) { //edit budget
                    budgetPlanner.setBudgetValues();
                } else if (input == 3) { //save budgdet
                    budgetWriter = new BudgetWriter(budgetPlanner.getBudget());
                } else if (input == 4) { //return
                    newMenuLevel = 1;
                }
                break;
        }
        
        return newMenuLevel;
    }
}