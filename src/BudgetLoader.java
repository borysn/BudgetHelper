/**
 *
 * @author Borys H.N.
 */
import java.io.*;
import java.nio.file.Files;
import jxl.*; 
import jxl.write.*; 
import jxl.write.Number;
import jxl.read.biff.BiffException;
import java.util.Scanner;
import java.util.Date;
import java.util.Locale;
import java.math.BigDecimal; 
import java.math.MathContext;
import java.math.RoundingMode;

public class BudgetLoader {
    //fields
    private Budget budget;
    private String filePath;
    private Workbook workbook;
    private WorkbookSettings wbSettings;
    private Sheet budgetSheet;
    
    //constructor
    public BudgetLoader() {
        setWBSettings();
        getBudgetPath();
        FileInputStream fileStream = null;
        try {
            fileStream = new FileInputStream(new File(filePath));
        } catch (FileNotFoundException e) {
            System.out.println("Caught FileNotFoundException: " + e.getStackTrace());
        }
        setBudgetSheet(fileStream);
        try {
            buildBudget();
        } catch (NullPointerException e) { 
            System.out.println("Caught NullPointerException: " + e.getMessage());
        }
        
       // try { 
            workbook.close();
        //} catch (IOException e) {
            //System.out.println(e.getStackTrace());
        //}
    }
    
    //accessor methods
    public Budget getBudget() {
        return budget;
    }
    
    public void setWBSettings() {
        wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale ("en", "EN")); 
    }
    
    public void setBudgetSheet(InputStream fileInputStream) {
        try {
            workbook = Workbook.getWorkbook(fileInputStream, wbSettings); 
            budgetSheet = workbook.getSheet(0); //only one sheet
        } catch (BiffException e) {
            System.out.println("Caught BiffException: " + e.getStackTrace());
        } catch (IOException e) {
            System.out.println("Caught IOException: " + e.getStackTrace());
        } catch (NullPointerException e) {
            System.out.println("Caught NullPointerException: " + e.getStackTrace());
        }
    }
    
    public void buildBudget() {
        int numCategories = getNumCategories();
        BigDecimal total = new BigDecimal(0);
        int duration = 0; 
        
        String [] categories = new String[numCategories];
        float [] percentages = new float[numCategories];
        BigDecimal [] weighedOut = new BigDecimal[numCategories];
        
        int indexOffset = 3; //offset for labels
        
        //fill categories
        for (int i = 0; i < numCategories; i++) {
            Cell tempCell = budgetSheet.getCell(0, i+indexOffset);
            String contents = tempCell.getContents(); 
            categories[i] = contents;
        }
        
        //fill percentages
        for (int i = 0; i < numCategories; i++) {
            Cell tempCell = budgetSheet.getCell(1, i+indexOffset);
            String contents = tempCell.getContents();
            percentages[i] = Float.parseFloat(contents);
        }
        
        //fill category weights
        for (int i = 0; i < numCategories; i++) {
            Cell tempCell = budgetSheet.getCell(2, i+indexOffset);
            String contents = tempCell.getContents();
            weighedOut[i] = new BigDecimal(contents);
        }
        
        //get budget total
        Cell tempCell = budgetSheet.getCell(0, 1);
        String temp = tempCell.getContents();
        temp = temp.substring(1, temp.length()); //remove dollar sign
        total = new BigDecimal(temp);
        
        //get budget span
        duration  = getBudgetDuration(); 
        
        //setup budget 
        budget = new Budget();
        budget.setBudgetValues(total, numCategories, categories, percentages, duration);
    }
    
    public int getNumCategories() {
        int numCategories = budgetSheet.getRows() - 3;
        return numCategories;
    }
    
    public int getBudgetDuration() {
        Cell tempCell = budgetSheet.getCell(1, 1);
        String temp = tempCell.getContents();
        switch (temp) {
            case "weekly":
                return 1;
            case "bi-weekly": 
                return 2;
            case "monthly":
                return 3;
            default:
                return 0;
        }
    }
    
    //mutator methods
    public void getBudgetPath() {
        File file;
        do {
            Scanner s = new Scanner(System.in);
        
            System.out.print("Enter file path: ");
            filePath = s.nextLine().toString();
            
            file = new File(filePath);
            filePath = file.getAbsolutePath();
        } while (file.exists() == false);
    }
}
