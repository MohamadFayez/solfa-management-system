package com.truemega.solfa.view.utils;
/**   Added by Mohammed Fayez  */
import com.truemega.solfa.model.SolfaAppModuleImpl;
import com.truemega.solfa.model.util.ApplicationLogger;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.sql.SQLException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.jbo.ViewObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils implements Serializable {
    @SuppressWarnings("compatibility:281056328652477363")
    private static final long serialVersionUID = 8568143084402056575L;
    private RichInputText forBindBean;
    private long empCount;
    private final int FIRST_IGNORED_ROW_NUMBER = 1;
    private final String[] COLUMN_NAMES = new String[] { "STAFF_ID", "HAS-SOLFA", "SOLFA_AMOUNT" };
    private SolfaAppModuleImpl am =
        (SolfaAppModuleImpl) ADFUtil.getApplicationModuleForDataControl("SolfaAppModuleDataControl");

    public void insertExcelData(Row tempRow, Integer rowIndex) throws ParseException, SQLException {
        Integer skipRw = 1;

        DCIteratorBinding uploadIterator = ADFUtil.findIterator("SolfaEmployeesViewIterator");
        ViewObject vo = uploadIterator.getViewObject();
        if (rowIndex > skipRw) {
            ADFUtil.findOperation("CreateInsert").execute();
            oracle.jbo.Row row = vo.getCurrentRow();
            Cell staffIdCell = tempRow.getCell(0);
            String staffId = (int) Double.parseDouble(getCellValue(staffIdCell, rowIndex, 0)) + "";
            row.setAttribute("StaffId", staffId);
            Cell hasSolfaCell = tempRow.getCell(1);
            String hasSolfa = getCellValue(hasSolfaCell, rowIndex, 1);
            row.setAttribute("HasSolfa", hasSolfa);
            Cell amountCell = tempRow.getCell(2);
            String amount = (int) Double.parseDouble(getCellValue(amountCell, rowIndex, 2)) + "";
            row.setAttribute("SolfaAmount", amount);
        }

    }

    public void deleteEmpData() {
        // ViewObject uploadView = ADFUtil.findIterator("SolfaEmployeesViewIterator").getViewObject();
        ViewObject uploadView = am.getSolfaEmployeesView();
        ApplicationLogger.log("-----** ------ [  Before delete employee data ] -----** ------ ",
                              ApplicationLogger.INFO);
        ApplicationLogger.log("-----** ------ [  Row Count before deletting ....... ] -----** ------ " +
                              uploadView.getAllRowsInRange().length, ApplicationLogger.INFO);
        uploadView.executeQuery();
        System.out.println("[ Employee Row Count before deletting ....... ]    => " +
                           uploadView.getAllRowsInRange().length);
        this.setEmpCount(uploadView.getEstimatedRowCount());
        uploadView.setRangeSize(-1);
        for (oracle.jbo.Row r : uploadView.getAllRowsInRange()) {
            r.remove();
        }
        System.out.println("[ Employee Row Count after deletting ........ ]   => " +
                           uploadView.getAllRowsInRange().length);
        ApplicationLogger.log("-----** ------ [ After delete employee data ] -----** ------ " +
                              uploadView.getAllRowsInRange().length, ApplicationLogger.INFO);
        ApplicationLogger.log("-----** ------ [ Row Count after deletting ........ ] -----** ------   " +
                              uploadView.getAllRowsInRange().length + uploadView.getAllRowsInRange().length,
                              ApplicationLogger.INFO);
        this.setEmpCount(uploadView.getEstimatedRowCount());
    }


    public void readExcelFile(InputStream excelFile, String fileType, String page) throws IOException, ParseException,
                                                                                          SQLException {
        if (fileType == null)
            throw new RuntimeException("Kindly choose Excel file");
        //Use XSSFWorkbook for XLS file
        Workbook WorkBook = null;
        int sheetIndex = 0;
        try {

            if ("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equalsIgnoreCase(fileType) ||
                "application/xlsx".equalsIgnoreCase(fileType)) {

                    WorkBook = new XSSFWorkbook(excelFile);

            } else if ("application/vnd.ms-excel".equalsIgnoreCase(fileType) ||
                       "application/xls".equalsIgnoreCase(fileType)) {

                WorkBook = new HSSFWorkbook(excelFile);

            } else {
                throw new RuntimeException("File format not supported.-- Upload XLS or XLSX file");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error Reading File!!!");
        }
        Sheet sheet = WorkBook.getSheetAt(sheetIndex);
        //Iterate over excel rows
        Integer rowIndex = FIRST_IGNORED_ROW_NUMBER;
        deleteEmpData();
        Integer insertedRows = 0;

        for (Row row : sheet) {
            if (rowIndex <= FIRST_IGNORED_ROW_NUMBER) {
                rowIndex++;
                continue;
            }
            System.out.println("rownum : " + rowIndex);
//            System.out.println("row.getCell(0) : " + row.getCell(0));
//            System.out.println("row.getCell(0).getCellType() : " + row.getCell(0).getCellType());
            if (null != row.getCell(0)) {
                if (row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK && // if staff ID cells is blank Raise issue
                    row.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK) {
                    throw new RuntimeException("'Staff ID' is Mandatory Field Required at Row Index " + rowIndex);
                }

                if (row.getCell(1).getCellType() == Cell.CELL_TYPE_BLANK && // if amount cells is blank Raise issue
                    row.getCell(0).getCellType() != Cell.CELL_TYPE_BLANK) {
                    throw new RuntimeException("'Has Solfa' is Mandatory Field Required at Row Index " + rowIndex);
                }

                if (row.getCell(2).getCellType() == Cell.CELL_TYPE_BLANK && // if amount cells is blank Raise issue
                    row.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK) {
                    throw new RuntimeException("'Amount' is Mandatory Field Required at Row Index " + rowIndex);
                }

                if (row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK &&
                    row.getCell(1).getCellType() == Cell.CELL_TYPE_BLANK && row.getCell(2).getCellType() == Cell.CELL_TYPE_BLANK) { // if Two cells is blank Do not insert Row
                    rowIndex++;
                    continue;
                }
                insertExcelData(row, rowIndex);
                insertedRows++;
                rowIndex++;
            }
        }
        if (insertedRows == 0) {
            throw new RuntimeException("Excel has no data,You must insert at least one Row!");
        }
    }


    public String getCellValue(Cell cell, int cellRow, int cellColumn) {
        String cellValue = null;
        if (cell != null) {

            switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                cellValue = cell.getNumericCellValue() + "";
                break;
            case Cell.CELL_TYPE_STRING:
                cellValue = cell.getStringCellValue();
                break;
            }
        }

        if (cellValue == null || cellValue.isEmpty()) {
            ApplicationLogger.log("-----** ------ [ Mandatory Data Required at Row ] " + cellRow + " for column: " +
                                  COLUMN_NAMES[cellColumn] + "----- **-------", ApplicationLogger.INFO);
            throw new RuntimeException("Mandatory Data Required at Row: " + cellRow + " for column: " +
                                       COLUMN_NAMES[cellColumn]);
        }

        return cellValue;
    }


    public void setForBindBean(RichInputText forBindBean) {
        this.forBindBean = forBindBean;
    }

    public RichInputText getForBindBean() {
        return forBindBean;
    }

    public void setEmpCount(long empCount) {
        this.empCount = empCount;
    }

    public long getEmpCount() {
        return empCount;
    }


}
