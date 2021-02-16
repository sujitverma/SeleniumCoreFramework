
package igt.test.automation.util;

import com.google.gson.*;
import com.jayway.jsonpath.JsonPath;

import igt.test.automation.selenium.constants.IGlobalConstants;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Re-usable utility methods to deal the data.
 *
 * @author 
 */
public final class DataUtil {
    /**
     * FIS object.
     */
    private static FileInputStream fileInputStream = null;

    /**
     * private constructor.
     */
    private DataUtil() {

    }

    /**
     * EnvironmentData.xlsx constant.
     */
    private static final String ENV_DATA = "EnvironmentData.xlsx";

    /**
     * GlobalData constant.
     */
    private static final String GLOBAL_DATA = "GlobalData";

    /**
     * The Log4j logger.
     */
    private static final Logger LOG = LogManager
        .getLogger(JSONReaderUtil.class);

    /**
     * method to read the JSON data from the specified web url for the json key
     * specified.
     *
     * @param jsonUrl url to access the JSON
     * @param keyName key for the JSON element you are interested
     * @return {@link Object}
     */
    @SuppressWarnings("unchecked")
    public static Object readJSONData(final String jsonUrl,
                                      final String keyName) {

        // reads the initial Map and returns the Complete JSON as
        // Map<String,Object>
        Map<String, Object> jsonMap = JSONReaderUtil
            .parseJsonInUrlGivenAndReturnMap(jsonUrl);

        String[] keyArray = StringUtils.split(keyName, ".");

        List<String> keyList = Arrays.asList(keyArray);

        int keydepth = keyList.size();
        Map<String, Object> dataMap = jsonMap;
        if (dataMap != null) {
            for (int i = 0; i < keydepth - 1; i++) {
                dataMap = (Map<String, Object>) dataMap.get(keyList.get(i));
            }

            // reads the readMap and fetches the Page you are interested as a
            // Map<String,String> so that the complete page elements are
            // returned
            // as Key,Value pair

            // returns the resultant Map which contains the text you are
            // interested
            return dataMap.get(keyList.get(keydepth - 1));

        }
        return new HashMap<>();
    }

    /**
     * method to read the JSON data from the specified file.
     *
     * @param jsonPath path of the json file
     * @param keyName  json key
     * @return {@link Object}
     */
    @SuppressWarnings("unchecked")
    public static Object readJSONDataFromFile(final String jsonPath,
                                              final String keyName) {

        // reads the initial Map and returns the Complete JSON as
        // Map<String,Object>
        Map<String, Object> jsonMap = JSONReaderUtil
            .parseJsonGivenAndReturnMap(jsonPath);

        String[] keyArray = StringUtils.split(keyName, ".");

        List<String> keyList = Arrays.asList(keyArray);

        int keydepth = keyList.size();
        Map<String, Object> dataMap = jsonMap;
        for (int i = 0; i < keydepth - 1; i++) {
            dataMap = (Map<String, Object>) dataMap.get(keyList.get(i));
        }

        // reads the readMap and fetches the Page you are interested as a
        // Map<String,String> so that the complete page elements are returned
        // as Key,Value pair

        // returns the resultant Map which contains the text you are interested
        return dataMap.get(keyList.get(keydepth - 1));
    }

    /**
     * loads the specific column data from EnvironmentData.xlsx according to the
     * environment Name passed.
     *
     * @param envName name of the environment you want to get
     * @return HashMap<String, String>
     */
    public static HashMap<String, String> loadEnvironmentData(
        final String envName) {

        if (envName == null) {
            LOG.info("No environment is selected to run: ");
        }
        int colNum = getColumnNumberFromSpreadSheet(ENV_DATA, "Data", envName);
        return loadRowHeadersValuesForAColumnFromSpreadSheet(ENV_DATA, "Data",
            colNum);

    }

    /**
     * returns the column number from spreadsheet for the specified input.
     *
     * @param excelWorkBookName name of the spreadsheet
     * @param workSheetName     name of the worksheet
     * @param columnHeader      header of the column
     * @return int
     * @author 
     */
    public static int getColumnNumberFromSpreadSheet(
        final String excelWorkBookName, final String workSheetName,
        final String columnHeader) {
        int colNumber = 0;
        final String datatablepath = SystemUtils.getUserDir()
            + IGlobalConstants.GLOBAL_DATA_FOLDER + excelWorkBookName;
        ExcelReader datatable = new ExcelReader(datatablepath, workSheetName);

        if (columnHeader != null) {

            int colCount = datatable.getColumnCount();

            for (int colNum = 0; colNum < colCount; colNum++) {
                String cv = datatable.getCellValue(1, colNum);
                if (cv != null && columnHeader.equals(cv)) {
                    colNumber = colNum;
                    break;
                }

            }

            return colNumber;

        } else {
            LOG.warn("Null columnHeader when searching in file:" + datatablepath
                + " Sheet: " + workSheetName);
            throw new IllegalArgumentException(
                "Null columnHeader when searching in file:" + datatablepath
                    + " Sheet: " + workSheetName);
        }
    }

    /**
     * Loads the row headers for a column from the spreadsheet.
     *
     * @param excelWorkBookName name of the spreadsheet
     * @param workSheetName     name of the worksheet
     * @param columnNum         column's number
     * @return {@link HashMap}
     * @author 
     */
    public static HashMap<String, String> loadRowHeadersValuesForAColumnFromSpreadSheet(
        final String excelWorkBookName, final String workSheetName,
        final int columnNum) {

        ExcelReader datatable = new ExcelReader(SystemUtils.getUserDir()
            + IGlobalConstants.GLOBAL_DATA_FOLDER + excelWorkBookName,
            workSheetName);

        String rowheader;
        String rowvalue;
        int rowCount = datatable.getRowCount();

        HashMap<String, String> map = new HashMap<>();

        for (int rowNum = 2; rowNum <= rowCount; rowNum++) {
            rowheader = datatable.getCellValue(rowNum, 0);
            rowvalue = datatable.getCellValue(rowNum, columnNum);
            map.put(rowheader, rowvalue);
        }

        return map;
    }

    /**
     * Reads the data from spreadsheet and returns the data as a dynamic Object
     * array.
     *
     * @param excelWorkBookName - excel sheets name placed in the "Data" folder
     * @param workSheetName     - work sheets name
     * @return Object[][]
     * @author 
     */
    public static Object[][] getDataFromSpreadSheet(
        final String excelWorkBookName, final String workSheetName) {
        ExcelReader datatable = new ExcelReader(SystemUtils.getUserDir()
            + IGlobalConstants.GLOBAL_DATA_FOLDER + excelWorkBookName,
            workSheetName);

        int rowCount = datatable.getRowCount();
        int colCount = datatable.getColumnCount();
        Object[][] data = new Object[rowCount - 1][colCount];
        for (int rowNum = 2; rowNum <= rowCount; rowNum++) {
            // loop all the available row values
            for (int colNum = 0; colNum < colCount; colNum++) {
                // while returning the data[][] you should not send the header
                // values
                data[rowNum - 2][colNum] = datatable.getCellValue(rowNum,
                    colNum);
            }
        }

        return data;
    }

    /**
     * reads the data from spreadsheet and returns the data as hashmap.
     *
     * @param excelWorkBookName - spreadsheet name
     * @param workSheetName     - worksheet name
     * @return HashMap<String, String>
     */
    public static HashMap<String, String> loadColumnHeadersValuesFromSpreadSheet(
        final String excelWorkBookName, final String workSheetName) {

        ExcelReader datatable = new ExcelReader(SystemUtils.getUserDir()
            + IGlobalConstants.GLOBAL_DATA_FOLDER + excelWorkBookName,
            workSheetName);

        String columnheader;
        String columnvalue;
        int colCount = datatable.getColumnCount();

        HashMap<String, String> map = new HashMap<>();

        for (int colNum = 0; colNum < colCount; colNum++) {
            columnheader = datatable.getCellValue(1, colNum);
            columnvalue = datatable.getCellValue(2, colNum);
            map.put(columnheader, columnvalue);
        }

        return map;
    }

    /**
     * reads the properties file and returns the data as Properties.
     *
     * @param envPropertyFilePath file path
     * @return Properties {@link Properties}
     */
    public static Properties getPropertyName(final String envPropertyFilePath) {
        File file = new File(envPropertyFilePath);
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            LOG.error(e);
        }
        Properties prop = new Properties();
        try {
            prop.load(fileInputStream);
        } catch (IOException e) {
            LOG.error(e);
        }
        return prop;

    }

    /**
     * This method will read the data from excel and store the data in the map
     * and returns the value according to the key given.
     *
     * @param parameter param you want to get
     * @param colName   Column Name
     * @return String
     * @author Mohd Jeeshan
     */
    public static String loadGlobalData(final String parameter,
                                        final String colName) {

        if (colName == null) {
            LOG.info("No Coloumn is selected to load the data: ");
        }
        int colNum = getColumnNumberFromSpreadSheet(ENV_DATA, GLOBAL_DATA,
            colName);
        HashMap<String, String> map = loadRowHeadersValuesForAColumnFromSpreadSheet(
            ENV_DATA, GLOBAL_DATA, colNum);
        Set<String> keySet = map.keySet();
        Iterator<String> keySetIterator = keySet.iterator();
        while (keySetIterator.hasNext()) {

            String key = keySetIterator.next();
            if (key.equals(parameter)) {
                return map.get(key);
            }
        }

        return "";
    }

    /**
     * This method will read json path from the JSON string and returns the
     * value according to the path given.
     *
     * @param jsonURL   the url path for json
     * @param jsonXPath the json path e.g: a.b
     * @return Object
     * @author Mohd Jeeshan
     */
    public static Object readJSONAndReturnValue(final String jsonURL,
                                                final String jsonXPath) {
        String jsonFile = JSONReaderUtil.parseJSONToString(jsonURL);
        return JsonPath.parse(jsonFile).read(jsonXPath);

    }

    /**
     * This method will read an excel work sheet data and return the contents as
     * Map.
     *
     * @param worksheetPath - path to the worksheet you want to read data from.
     * @param worksheetName - worksheet's name.
     * @return Object[][] - a multi dimensional object array whose elements are
     * Map<String, String>
     * @author Mohd Jeeshan
     * @author 
     * @author Justin.Smith
     */
    public static Object[][] getMapDataFromSpreadSheet(
        final String worksheetPath, final String worksheetName) {

        ExcelReader datatable = new ExcelReader(worksheetPath, worksheetName);

        Map<String, String> datamap;

        int rowCount = datatable.getRowCount();
        int colCount = datatable.getColumnCount();
        Object[][] data = new Object[rowCount - 1][1];
        for (int row = 0; row < rowCount - 1; row++) {
            datamap = new HashMap<>();
            for (int col = 0; col < colCount; col++) {
                datamap.put(datatable.getCellValue(1, col),
                    datatable.getCellValue(row + 2, col));
            }
            data[row][0] = datamap;
        }
        return data;
    }

    /**
     * This method will read an JSON array data and return the contents as map.
     *
     * @param jsonPath      is the location of the JSON test data file.
     * @param jsonArrayName is the name of JSON array root element.
     * @return Object[][] - is multi-dimensional object array whose elements are
     * Map<String,String>
     * @author Mohd.Jeeshan
     */

    public static Object[][] getMapDataFromJSON(final String jsonPath,
                                                final String jsonArrayName) {
        Map<String, String> datamap;
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = null;
        Object[][] data = null;
        if (jsonArrayName != null) {
            try {
                jsonObject = jsonParser
                    .parse(new FileReader(new File(jsonPath)))
                    .getAsJsonObject();
                if (jsonObject != null) {
                    JsonArray jsonArray = (JsonArray) jsonObject
                        .get(jsonArrayName);
                    data = new Object[jsonArray.size()][1];
                    int rowNumber = 0;
                    for (JsonElement jsonElemet : jsonArray) {
                        datamap = new HashMap<>();
                        for (Map.Entry<String, JsonElement> entry : jsonElemet
                            .getAsJsonObject().entrySet()) {
                            datamap.put(entry.getKey(), entry.getValue()
                                .toString().replaceAll("\"", ""));

                        }
                        data[rowNumber][0] = datamap;
                        rowNumber++;
                    }

                }
            } catch (FileNotFoundException e) {
                LOG.error("File Not found at the location :" + jsonPath
                    + " Exception is :" + e);

            }
        }
        return data;

    }

    /**
     * Method to convert excel test data sheet to json array and writes to the
     * json file.
     *
     * @param excelWorkbookPath is the excel workbook path i.e. src/test/resources/data/
     * @param workbookName      is the workbookName sitting in the workbook path
     * @param destinationPath   is the destination path where you need to store the json file
     *                          e.g. src/test/resources/data/
     * @author Mohd.Jeeshan
     **/

    public static void convertExcelToJsonArray(final String excelWorkbookPath,
                                               final String workbookName, final String destinationPath) {
        Workbook wb = null;
        try (FileInputStream fileInputStream = new FileInputStream(
            new File(excelWorkbookPath + workbookName))) {
            wb = WorkbookFactory.create(fileInputStream);
            if (wb != null) {
                for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                    JsonObject jsonSheetObject = new JsonObject();
                    JsonArray jsonSheetArray = new JsonArray();
                    List<String> columnNames = new ArrayList<>();
                    Sheet sheet = wb.getSheetAt(i);
                    Iterator<Row> sheetIterator = sheet.iterator();
                    while (sheetIterator.hasNext()) {
                        Row row = sheetIterator.next();
                        JsonObject jsonObject = new JsonObject();
                        if (row.getRowNum() != 0) {
                            for (int j = 0; j < columnNames.size(); j++) {

                                if (row.getCell(j) != null) {
                                    if (row.getCell(j)
                                        .getCellType() == CellType.STRING) {
                                        jsonObject.addProperty(
                                            columnNames.get(j),
                                            row.getCell(j)
                                                .getStringCellValue());
                                    } else if (row.getCell(j)
                                        .getCellType() == CellType.NUMERIC) {
                                        jsonObject.addProperty(
                                            columnNames.get(j),
                                            row.getCell(j)
                                                .getNumericCellValue());
                                    } else if (row.getCell(j)
                                        .getCellType() == CellType.BOOLEAN) {
                                        jsonObject.addProperty(
                                            columnNames.get(j),
                                            row.getCell(j)
                                                .getBooleanCellValue());
                                    } else if (row.getCell(j)
                                        .getCellType() == CellType.BLANK) {
                                        jsonObject.addProperty(
                                            columnNames.get(j), "");
                                    }
                                } else {
                                    jsonObject.addProperty(columnNames.get(j),
                                        "");
                                }
                            }
                            jsonSheetArray.add(jsonObject);
                        } else {
                            // Adding the columns name to the list.
                            for (int headerRow = 0; headerRow < row
                                .getPhysicalNumberOfCells(); headerRow++) {
                                columnNames.add(row.getCell(headerRow)
                                    .getStringCellValue());
                            }
                        }
                    }
                    jsonSheetObject.add(wb.getSheetName(i), jsonSheetArray);

                    // Writing the data to json file.
                    try (BufferedWriter br = new BufferedWriter(
                        new FileWriter(new File(destinationPath
                            + wb.getSheetName(i) + "TestData.json")))) {
                        // pretty printing for json data
                        Gson gson = new GsonBuilder().setPrettyPrinting()
                            .create();
                        br.write(gson.toJson(jsonSheetObject));
                        br.close();
                    }

                }
            }
        } catch (FileNotFoundException e) {
            LOG.error("File not found at the location " + excelWorkbookPath
                + workbookName + e);
        } catch (IOException e) {
            LOG.error("Getting the IO exception {0}", e);
        } finally {
            if (wb != null) {
                try {
                    wb.close();
                } catch (IOException e) {
                    LOG.error("Getting the IO exception {0}", e);

                }
            }
        }
    }

    /**
     * Method to check whether a file exists in the given location.
     *
     * @param filePath is the path location of the file
     * @param fileName is the name of the file
     * @return boolean true if the file present at the location
     * @author Mohd.Jeeshan
     */
    public static boolean checkFileExists(final String filePath,
                                          final String fileName) {
        boolean isExist = false;
        Path source = Paths.get(filePath);
        try {
            List<Path> list = Files.walk(source).filter(Files::isRegularFile)
                .collect(Collectors.toList());
            isExist = list.stream().anyMatch(
                file -> file.getFileName().toString().equals(fileName));
        } catch (IOException e) {
            LOG.error("Exception occured while checking existence of the file:"
                + e);
        }

        return isExist;
    }

    /**
     * reads the properties file and returns the data as Properties.
     *
     * @param envPropertyFilePath file path
     * @return Properties {@link Properties}
     */
    public static List<String> readPropertyFileKey(
        final String envPropertyFilePath) {
        Properties prop = getPropertyName(envPropertyFilePath);
        return new ArrayList<>(prop.stringPropertyNames());

    }

    /**
     * This method will read json value from the JSON string.
     *
     * @param jsonString is the jsonString
     * @param jsonXPath  the json path e.g: a.b
     * @return Object
     * @author Mohd Jeeshan
     */
    public static Object readJsonValueFromJsonString(final String jsonString,
                                                     final String jsonXPath) {
        return JsonPath.parse(jsonString).read(jsonXPath);

    }

    /**
     * method to read the JSON data from the specified web url for the json key
     * specified.
     *
     * @param jsonUrl is the {@link String} json url
     * @param keyName is the {@link String} json xpath {@link String}
     * @return {@link Object}
     */
    public static Object readJSONDataFromUrlRelaxHttps(final String jsonUrl,
                                                       final String keyName) {

        Response response = RestAssured.given().relaxedHTTPSValidation().when()
            .get(jsonUrl).then().extract().response();
        return DataUtil.readJsonValueFromJsonString(response.asString(),
            keyName);
    }

    /**
     * Method to combine two data providers and returns single instance of
     * {@link Object}.
     *
     * @param object1 is first Object[][]
     * @param object2 is second Object[][]
     * @return {@link Object}
     * @author Mohd.Jeeshan
     */
    @SuppressWarnings("unchecked")
    public static Object[][] getCombinedDataProviderData(final Object[][] object1,
                                                         final Object[][] object2) {
        Map<String, String> dataMap = new HashedMap<>();
        dataMap.putAll((Map<String, String>) object1[0][0]);
        dataMap.putAll((Map<String, String>) object2[0][0]);
        Object[][] object = new Object[1][];
        object[0] = new Object[]{dataMap};
        return object;
    }
}
