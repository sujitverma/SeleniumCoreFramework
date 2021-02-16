
package igt.test.automation.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

/**
 * This class is to handle multiple reading operations of content from Excel
 * workbook.
 *
 * @author 
 */
public class ExcelReader {

    /**
     * Path to the spreadsheet.
     */
    private String path;

    /**
     * Stream to read the spreadsheet.
     */
    private FileInputStream fis = null;

    /**
     * Access to the spreadsheet.
     */
    private XSSFWorkbook workbook = null;

    /**
     * Work sheet in the current file.
     */
    private XSSFSheet sheet = null;

    /**
     * Row within the current sheet.
     */
    private XSSFRow row = null;

    /**
     * Cell within the current sheet.
     */
    private XSSFCell cell = null;

    /**
     * Stream to write the spreadsheet.
     */
    private FileOutputStream fileOut = null;

    /** Constants */
    /**
     * unable to open spread sheet.
     */
    private static final String UNABLE_TO_OPEN = "Unable to open spreadsheet ";

    /**
     * unable to open spread sheet as the path is null.
     */
    private static final String NULL_PATH = "Unable to open spreadsheet as path is null";

    /**
     * " from workbook" constant.
     */
    private static final String FROM_WBOOK = "\" from workbook \"";

    /**
     * Log4j Logger.
     */
    private static final Logger LOG = LogManager.getLogger(ExcelReader.class);

    /**
     * ExcelReader(String path) constructor accepts only the path of the Excel
     * sheet.
     *
     * @param path
     *            The name of and path to the spreadsheet
     * @author 
     **/
    public ExcelReader(final String path) {
        if (path != null) {
            this.setPath(path);

            if (this.getPath() != null) {
                try {
                    setFis(new FileInputStream(this.getPath()));

                    if (this.getFis() != null) {
                        XSSFWorkbook xssfWB = new XSSFWorkbook(getFis());
                        setWorkbook(xssfWB);
                    }
                } catch (IOException ioe) {
                    LOG.warn(UNABLE_TO_OPEN +"{0}", ioe);
                } finally {
                    if (this.getFis() != null) {
                        try {
                            getFis().close();
                        } catch (IOException e) {
                            LOG.error(e);
                        }
                    }
                }
            }
        } else {
            LOG.warn(NULL_PATH);
        }
    }

    /**
     * ExcelReader(String path, String SheetName) constructor accepts the path
     * of the Excel sheet with its Name and the workSheetName.
     *
     * @param path
     *            - Name & path of the Excel spreadsheet
     * @param sheetName
     *            - Name of the workSheet
     * @author 
     **/

    public ExcelReader(final String path, final String sheetName) {
        if (path == null && sheetName == null) {
            return;
        }

        this.setPath(path);

        if (this.getPath() != null) {
            try {
                setFis(new FileInputStream(path));

                if (this.getFis() != null) {
                    XSSFWorkbook xssfWB = new XSSFWorkbook(getFis());

                    if (xssfWB != null) {
                        setWorkbook(xssfWB);

                        if (this.getWorkbook() != null) {
                            this.setSheet(getWorkbook().getSheet(sheetName));
                            if (this.getSheet() == null) {
                                LOG.warn("Unable to retrieve worksheet \""
                                        + sheetName + FROM_WBOOK + path + "\"");
                            }
                        }
                    }
                }
            } catch (FileNotFoundException fe) {
                LOG.warn(UNABLE_TO_OPEN +"{0}", fe);
            } catch (IOException ie) {
                LOG.warn(UNABLE_TO_OPEN + "{0}",ie);
            } finally {
                if (getFis() != null) {
                    try {
                        getFis().close();
                    } catch (IOException e) {
                        LOG.error(e);
                    }
                }
            }

        } else {
            LOG.warn(NULL_PATH);
        }
    }

    /**
     * This method returns the String value of the cell content which matches
     * for the specified row number and column number.
     *
     * @param row
     *            - Row Number
     * @param col
     *            - column Number
     * @return String Note: Row number and column numbers start from index 0.
     * @author 
     **/
    public String getCellValue(final int row, final int col) {
        Row rows = getSheet().getRow(row - 1);
        if (rows == null) {
            LOG.warn("Attempt to read data from cell in undefined row " + "["
                    + row + "," + col + "] on sheet \""
                    + getSheet().getSheetName() + "\"");
            return "";
        } else {
            Cell aCell;
            try {
                aCell = rows.getCell(col);
            } catch (IllegalArgumentException ex) {
                LOG.warn("Attempt to read data from undefined cell " + "[" + row
                        + "," + col + "] on sheet \""
                        + getSheet().getSheetName() + "\"");
                throw ex;
            }
            if (aCell == null) {
                // Treat cells that don't exist as empty because Excel generally
                // only creates the
                // cells that have values or styles
                return "";
            } else if (aCell.getCellType() == CellType.STRING) {
                return aCell.getStringCellValue();
            } else if (aCell.getCellType() == CellType.NUMERIC) {
                return new java.text.DecimalFormat("0")
                        .format(aCell.getNumericCellValue());
            } else {
                return aCell.getStringCellValue();
            }
        }
    }

    /**
     * This overloaded method returns the String value of the cell content which
     * matches for the specified SheetName, column number and row number.
     *
     * @param sheetName
     *            - String value of the workSheetName
     * @param row
     *            - Row Number
     * @param col
     *            - column Number
     * @return String Note: column numbers start from index 0.
     * @author 
     **/
    public String getCellValue(final String sheetName, final int row,
            final int col) {
        setSheet(getWorkbook().getSheet(sheetName));
        if (getSheet() != null) {
            Row rows = getSheet().getRow(row - 1);
            Cell aCell = rows.getCell(col);

            if (aCell == null) {
                return "";
            } else if (aCell.getCellType() == CellType.STRING) {
                return aCell.getStringCellValue();
            } else if (aCell.getCellType() == CellType.NUMERIC) {
                return new java.text.DecimalFormat("0")
                        .format(aCell.getNumericCellValue());
            } else {
                return aCell.getStringCellValue();
            }
        } else {
            LOG.warn("Unable to retrieve sheet \"" + sheetName + FROM_WBOOK
                    + getPath() + "\"");
            return null;
        }
    }

    /**
     * This overloaded method returns the String value of the cell content which
     * matches for the specified SheetName, row number and column number.
     * <p>
     * This will gets a String, Numeric or Boolean from the cell as an Object
     * return type.
     *
     * @param sheetName
     *            - String value of the workSheetName
     * @param row
     *            - Row Number
     * @param col
     *            - column Number
     * @return Object (as a String, Double or Boolean)
     **/
    public Object getCellValueByFormat(final String sheetName, final int row,
            final int col) {
        if (sheetName != null) {
            if (getWorkbook() != null) {
                XSSFSheet eSheet = getWorkbook().getSheet(sheetName);
                if (eSheet != null) {
                    setSheet(eSheet);
                    if (getSheet() != null) {
                        Row rows = getSheet().getRow(row - 1);
                        Cell aCell = rows.getCell(col);
                        if (aCell == null) {
                            return "";
                        } else if (aCell.getCellType() == CellType.STRING) {
                            return aCell.getStringCellValue();
                        } else if (aCell.getCellType() == CellType.NUMERIC) {
                            return (Double) aCell.getNumericCellValue();
                        } else if (aCell.getCellType() == CellType.BOOLEAN) {
                            return Boolean.valueOf(aCell.getBooleanCellValue());
                        } else {
                            return aCell.getStringCellValue();
                        }
                    }
                } else {
                    LOG.warn("Unable to retrieve sheet \"" + sheetName
                            + FROM_WBOOK + getPath() + "\"");
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * This method returns the number of usedRows present in a worksheet.
     *
     * @return integer
     * @author 
     **/

    public int getRowCount() {
        if (getSheet() != null) {
            int rowCount = getSheet().getLastRowNum();
            return rowCount + 1;
        } else {
            LOG.warn("Sheet not found");
            return 0;
        }
    }

    /**
     * This is a overloaded method which returns the number of usedRows present
     * in the specified workSheet.
     *
     * @param sheetName
     *            String value of the workSheetName
     * @return integer
     * @author 
     **/
    public int getRowCount(final String sheetName) {
        setSheet(getWorkbook().getSheet(sheetName));
        if (getSheet() != null) {
            return getSheet().getLastRowNum() + 1;
        } else {
            LOG.warn("Sheet not found {0}",sheetName);
            return 0;
        }
    }

    /**
     * This method returns the number of columns present in a particular
     * worksheet for the specified rownumber.
     *
     * @return integer
     * @author 
     **/
    public int getColumnCount() {
        if (getSheet() != null) {
            Row rowNum = getSheet().getRow(0);
            return rowNum.getLastCellNum();
        } else {
            LOG.warn("Sheet not found");
            return 0;
        }
    }

    /**
     * This overloaded method returns the number of columns present in a
     * particular worksheet for the specified worksheet.
     *
     * @param sheetName
     *            String value of the workSheetName
     * @return integer
     * @author 
     **/

    // returns number of columns in a sheet
    public int getColumnCount(final String sheetName) {
        setSheet(getWorkbook().getSheet(sheetName));
        if (getSheet() != null) {
            Row rowNum = getSheet().getRow(0);
            return rowNum.getLastCellNum();
        } else {
            LOG.warn("Sheet not found {0}",sheetName);
            return 0;
        }
    }

    /**
     * This method is to set String value in Excel file.
     *
     * @param sheetName
     *            is workSheet Name in which value need to be set
     * @param colName
     *            is to find particular column to set value
     * @param rowNum
     *            is to find particular cell to set value
     * @param data
     *            is the string to set
     * @return Boolean whether data added or not
     */
    public boolean setCellData(final String sheetName, final String colName,
            final int rowNum, final String data) {
        try {
            if (getPath() != null) {
                setFis(new FileInputStream(getPath()));

                if (getFis() != null) {
                    setWorkbook(new XSSFWorkbook(getFis()));
                    if (getWorkbook() != null) {
                        if (rowNum <= 0) {
                            return false;
                        }

                        int index = getWorkbook().getSheetIndex(sheetName);
                        int colNum = -1;
                        if (index == -1) {
                            return false;
                        }

                        setSheet(getWorkbook().getSheetAt(index));

                        setRow(getSheet().getRow(0));
                        for (int i = 0; i < getRow().getLastCellNum(); i++) {
                            if (getRow().getCell(i).getStringCellValue().trim()
                                    .equals(colName)) {
                                colNum = i;
                            }
                        }

                        if (colNum == -1) {
                            return false;
                        }

                        getSheet().autoSizeColumn(colNum);
                        setRow(getSheet().getRow(rowNum - 1));

                        if (getRow() == null) {
                            setRow(getSheet().createRow(rowNum - 1));
                        }

                        setCell(getRow().getCell(colNum));

                        if (getCell() == null) {
                            setCell(getRow().createCell(colNum));
                        }

                        getCell().setCellValue(data);

                        setFileOut(new FileOutputStream(getPath()));

                        getWorkbook().write(getFileOut());

                    }
                }
            }
        } catch (IOException e) {
            LOG.error(e);

        } finally {
            if (getFis() != null) {
                try {
                    getFis().close();
                } catch (IOException e) {
                    LOG.error(e);
                }
            }

            if (getFileOut() != null) {
                try {
                    getFileOut().close();
                } catch (IOException e) {
                    LOG.error(e);
                }
            }
        }

        return true;
    }

    /**
     * Getter.
     *
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * Setter.
     *
     * @param path
     *            the path to set
     */
    public void setPath(final String path) {
        this.path = path;
    }

    /**
     * Getter.
     *
     * @return the fis
     */
    public FileInputStream getFis() {
        return fis;
    }

    /**
     * Setter.
     *
     * @param fis
     *            the fis to set
     */
    public void setFis(final FileInputStream fis) {
        this.fis = fis;
    }

    /**
     * Getter.
     *
     * @return the workbook
     */
    public XSSFWorkbook getWorkbook() {
        return workbook;
    }

    /**
     * Setter.
     *
     * @param workbook
     *            the workbook to set
     */
    public void setWorkbook(final XSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    /**
     * Getter.
     *
     * @return the sheet
     */
    public XSSFSheet getSheet() {
        return sheet;
    }

    /**
     * Setter.
     *
     * @param sheet
     *            the sheet to set
     */
    public void setSheet(final XSSFSheet sheet) {
        this.sheet = sheet;
    }

    /**
     * Getter.
     *
     * @return the row
     */
    public XSSFRow getRow() {
        return row;
    }

    /**
     * Setter.
     *
     * @param row
     *            the row to set
     */
    public void setRow(final XSSFRow row) {
        this.row = row;
    }

    /**
     * Getter.
     *
     * @return the cell
     */
    public XSSFCell getCell() {
        return cell;
    }

    /**
     * Setter.
     *
     * @param cell
     *            the cell to set
     */
    public void setCell(final XSSFCell cell) {
        this.cell = cell;
    }

    /**
     * Getter.
     *
     * @return the fileOut
     */
    public FileOutputStream getFileOut() {
        return fileOut;
    }

    /**
     * Setter.
     *
     * @param fileOut
     *            the fileOut to set
     */
    public void setFileOut(final FileOutputStream fileOut) {
        this.fileOut = fileOut;
    }

    /**
     * getRowData - reads the data from spreadsheet for the matching rowNumber
     * specified and returns the data as a Map collection with the rowHeaders of
     * the work-sheet as key and the corresponding row as value.
     *
     * @param rownumber
     *            - rowN Number for which the values needs to be mapped to
     *            Column Headers
     * @return {@link Map}
     * @author 
     */
    public Map<String, String> getRowData(final int rownumber) {
        String columnheader;
        String columnvalue;
        int colCount = this.getColumnCount();

        HashMap<String, String> map = new HashMap<>();

        for (int colNum = 0; colNum < colCount; colNum++) {
            columnheader = this.getCellValue(1, colNum);
            columnvalue = this.getCellValue(rownumber, colNum);
            map.put(columnheader, columnvalue);
        }

        return map;
    }

    /**
     * This is an utility method to return the whole data from a spreadsheet
     * into a List<Map<String, String>>.
     *
     * @param filepath
     *            is the excel workbook path e.g.
     *            src/test/resources/data/Test.xlsx
     * @param sheetName
     *            is the sheet name in the workbook
     * @return {@link List}
     */
    public static List<Map<String, String>> getDataFromExcel(
            final String filepath, final String sheetName) {
        List<Map<String, String>> dataList = new ArrayList<>();
        try (Workbook workbook = WorkbookFactory.create(new File(filepath))) {

            Sheet sheet = workbook.getSheet(sheetName);

            Optional<Row> headerRow = Optional.ofNullable(sheet.getRow(0));
            if (headerRow.isPresent()) {
                for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {

                    Row currentRow = sheet.getRow(i);
                    Map<String, String> dataMap = new HashMap<>();

                    for (int j = 0; j < currentRow
                            .getPhysicalNumberOfCells(); j++) {
                        Cell currentCell = currentRow.getCell(j);
                        if (currentCell.getCellType() == CellType.STRING) {
                            dataMap.put(
                                    headerRow.get().getCell(j)
                                            .getStringCellValue(),
                                    currentCell.getStringCellValue());
                        }
                    }

                    dataList.add(dataMap);
                }
            }

        } catch (Exception e) {
            LOG.info("There is error in reading the excel :{0}" , e);
        }

        return dataList;
    }

}
