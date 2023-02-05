package org.restassured;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ExcelDataDrivenTest {

    public ArrayList<String> getData(String testCaseName) throws IOException {
        // Accepts a file input stream argument
        String os = System.getProperty("os.name");
        String excelFilePath;
        if(os.contains("Mac")) {
            excelFilePath = System.getProperty("user.dir") + "//src//main//java//org//restassured//ExcelTestData.xlsx";
        } else {
            excelFilePath = System.getProperty("user.dir") + "\\src\\main\\java\\org//restassured\\ExcelTestData.xlsx";
        }

        FileInputStream fis = new FileInputStream(excelFilePath);

        // Object created to use Excel file
        XSSFWorkbook workBook = new XSSFWorkbook(fis);

        // Arraylist to store data from file
        ArrayList<String> a=new ArrayList<String>();

        // Loop to count and select the sheets in a workBook
        int sheetCount = workBook.getNumberOfSheets();
        for(int i=0; i<sheetCount;i++) {
            //System.out.println(workBook.getSheetName(i));
            if(workBook.getSheetName(i).equalsIgnoreCase("Sheet1")) {
                XSSFSheet sheets =workBook.getSheetAt(i);
                Iterator<Row> rows=sheets.iterator();
                Row firstRow = rows.next();
                Iterator<Cell> ce=firstRow.cellIterator();
                int k=0;
                int column=0;
                while(ce.hasNext()){
                    Cell value = ce.next();
                    if(value.getStringCellValue().equalsIgnoreCase("Testcases")){
                        column =k;
                        //System.out.println(column + ": " + value.getStringCellValue());
                    }
                    k++;
                }
                //System.out.println(column);
                // once the column is identified then scan the entire testcase column to
                // identify purchase testcase row
                while(rows.hasNext()){
                    Row r=rows.next();
                    if(r.getCell(column) != null){
                        if(r.getCell(column).getStringCellValue().equalsIgnoreCase(testCaseName)){
                            Iterator<Cell> cv =r.cellIterator();
                            while(cv.hasNext()){
                                Cell cellValue = cv.next();
                                if(cellValue.getCellType()== CellType.STRING){
                                    a.add(cellValue.getStringCellValue());
                                    //System.out.println(cellValue);
                                }else {
                                    String intToString = NumberToTextConverter.toText(cellValue.getNumericCellValue());

                                    a.add(intToString);
                                }



                            }

                        }
                    }

                }
            }
            //return a;
        }
        workBook.close();
        return a;

    }

    public ArrayList<String> getTestCaseData(String sheetName, String testCaseName) throws IOException {

        ArrayList<String> testDataList = new ArrayList<String>();
        FileInputStream file = new FileInputStream(new File(System.getProperty("user.dir") + "//src//main//java//org//restassured//ExcelTestData.xlsx"));
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);

        int numberOfSheets = xssfWorkbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            if (xssfWorkbook.getSheetName(i).equals(sheetName)) {
                XSSFSheet sheet = xssfWorkbook.getSheetAt(i);
                //System.out.println("The wanted Sheet found--> " + xssfWorkbook.getSheetName(i));
                Iterator<Row> rowIterat = sheet.rowIterator();
                Row firstRow = rowIterat.next();
                Iterator<Cell> cellIterat = firstRow.cellIterator();
                int count = 0;
                int column = 0;
                while (cellIterat.hasNext()) {
                    Cell value = cellIterat.next();
                    if (value.getStringCellValue().equals("Test Cases")) {
                        column = count;
                    }
                    count++;
                }
                //System.out.println("The desired Column is found --> " + column);

                while (rowIterat.hasNext()) {
                    Row row = rowIterat.next();

                    if (row.getCell(column).getStringCellValue().equalsIgnoreCase(testCaseName)) {
                        //System.out.println("Test Case has been found --> " + row.getCell(column).getStringCellValue());

                        row.getCell(column).getStringCellValue();
                        Iterator<Cell> cellIterat2 = row.cellIterator();
                        while (cellIterat2.hasNext()) {
                            Cell value2 = cellIterat2.next();

                            if (value2.getCellType() == CellType.STRING) {
                                //System.out.println("Cell Value ->" + value2.getStringCellValue());
                                testDataList.add(value2.getStringCellValue());
                            } else {
                                testDataList.add(NumberToTextConverter.toText((value2.getNumericCellValue())));

                            }
                        }
                    }
                }
            }
        }
        return testDataList;
    }

}
