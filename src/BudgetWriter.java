/**
 *
 * @author Borys H.N.
 */
import java.io.*;  
import jxl.*; 
import jxl.write.*; 
import jxl.write.Number;
import java.util.Scanner;
import java.util.Date;
import java.util.Calendar;
import java.util.Locale;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class BudgetWriter {
    //fields
    private Budget budget;
    private WritableWorkbook workbook;
    private WritableSheet budgetSheet;
    private String budgetName;
    private WritableCellFormat wcFormat;
    private WorkbookSettings wbSettings; 
    private CellView cv; 
    
    //constructors
    public BudgetWriter(Budget aBudget) {
        budget = aBudget;
        
        setupBudgetSheet();
        writeAndCloseBudgetSheet();
    }
    
    //accessors
    
    private void setupBudgetSheet() {
        getBudgetName();
        setupFormatAndSettings();
        
        //set spreadsheet 
        try {
            workbook = Workbook.createWorkbook(new File(budgetName + ".xls"), wbSettings);
        } catch (IOException e) {
            System.out.println("Caught IOException: " + e.getMessage());
        }
        workbook.createSheet("Budget Report", 0);
        budgetSheet = workbook.getSheet(0);
        
        setLabels();
        setDate();
        setSpan();
        setTotal();
        setCategoryCells();
        setPercentageCells();
        setWeighedOutCells();
    }
    
    private void setupFormatAndSettings() {
        try {
            wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"))
                    ;
            WritableFont wfont = new WritableFont(WritableFont.TIMES, 10);
            wcFormat = new WritableCellFormat(wfont);
            wcFormat.setWrap(true); 
            
            cv = new CellView();
            cv.setFormat(wcFormat);
            cv.setAutosize(true);
        } catch (WriteException e) {
            System.out.println("Caught WriteException: " + e.getMessage());
        }
    }
    
    private void writeAndCloseBudgetSheet() {
        try {
            workbook.write();
            try {
                workbook.close();
            } catch (WriteException e) {
                System.out.println("Caught WriteException: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Caught IOException: " + e.getMessage());
        }
    }
    
    //mutators
    
    private void getBudgetName() {
        Scanner s = new Scanner(System.in);
        char correct = 'n';
        
        do {
            System.out.print("Enter name of budget: ");
            budgetName = s.next().toString();
            System.out.println(budgetName);
            System.out.println("Is this correct? y/n");
            System.out.print(">: ");
            correct = s.next().toLowerCase().charAt(0);
        } while (correct != 'y');
    }
    
    private void setLabels() {
        Label categories = new Label(0, 2, "Categories", wcFormat);
        Label percentages = new Label(1, 2, "Percentages", wcFormat);
        Label weighedOut = new Label(2, 2, "Weighed Out", wcFormat);
        try { 
            budgetSheet.addCell(categories);
            budgetSheet.addCell(percentages);
            budgetSheet.addCell(weighedOut); 
        } catch (WriteException e) { 
            System.out.println("Caught WriteException: " + e.getMessage());
        }
    }
    
    private void setDate() {
        Date now = Calendar.getInstance().getTime(); 
        DateFormat customDateFormat = new DateFormat ("MMM/dd/yyyy"); 
        WritableCellFormat dateFormat = new WritableCellFormat (customDateFormat);
        String time = now.toString();
        //DateTime dateCell = new DateTime(0, 0, time, dateFormat); 
        Label l = new Label(0, 0, time, dateFormat);
        try {
            budgetSheet.addCell(l); 
        } catch (WriteException e) {
            System.out.println("Caught WriteException: " + e.getMessage());
        }
    }
    
    private void setTotal() {
        BigDecimal total = budget.getTotal();
        String temp = new String("$" + total.round(new MathContext(5, RoundingMode.HALF_UP))); 
        Label l = new Label(0, 1, temp);
        try {
            budgetSheet.addCell(l);
        } catch (WriteException e) {
            System.out.println("Caught WriteExceptin: " + e.getMessage());
        }
    }
    
    private void setSpan() {
        String span = new String(); 
        switch (budget.getBudgetDuration().getCode()) {
            case 1:
                span = new String("weekly");
                break;
            case 2: 
                span = new String("bi-weekly");
                break;
            case 3:
                span = new String("monthly");
                break;
            default:
                span = new String("not-set"); 
                break;
        } 
        Label l = new Label(1, 1, span);
        try {
            budgetSheet.addCell(l);
        } catch (WriteException e) {
            System.out.println("Caught WriteExceptin: " + e.getMessage());
        }
    }
    
    private void setCategoryCells() { 
        String [] categories = budget.getBudgetCategories();
        int index = budget.getNumCategories();
        int indexOffset = 3;
        
        for (int i = 0; i < index; i++) {
            try {
                StringBuffer buf = new StringBuffer();
                buf.append(categories[i]);
                Label l = new Label(0, i+indexOffset, buf.toString(), wcFormat);
                budgetSheet.addCell(l); 
            } catch (WriteException e) {
                System.out.println("Caught WriteException: " + e.getMessage());
            }
        }
    }
    
    private void setPercentageCells() { 
        float [] percentages = budget.getBudgetPercentages();
        int index = budget.getNumCategories();
        int indexOffset = 3;
        for (int i = 0; i < index; i++) {
            try {
                //String temp = new String(percentages[i] + "%");
                Number n = new Number(1, i+indexOffset, percentages[i]);
                //Formula f = new Formula(1, i+indexOffset, temp);
                budgetSheet.addCell(n); 
            } catch (WriteException e) { 
                System.out.println("Caught WriteException: " + e.getMessage());
            }
        }
    }
    
    private void setWeighedOutCells() {   
        BigDecimal [] weighedCategories = budget.getWeighedCategories();
        int index = budget.getNumCategories();
        int indexOffset = 3;
        Number percentage; 
        for (int i = 0; i < index; i++) {
            try { 
                //float temp = weighedCategories[i].round(new MathContext(5, RoundingMode.HALF_UP));
                float temp = weighedCategories[i].floatValue();
                Number n = new Number(2, i+indexOffset, temp); 
                budgetSheet.addCell(n); 
            } catch (WriteException e) { 
                System.out.println("Caught WriteException: " + e.getMessage());
            }
        }
    }
}
