package igt.test.automation.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

/**
 * Simple CSV Parser library. 
 */
public class CSVReaderUtil {

    /** Path to the file. */
    private String filePath;

    /** Name of the file. */
    private String fileName;

    /** Log4j Logger. */
    private static final Logger LOG = LogManager.getLogger(CSVReaderUtil.class);

    /** "File :" constant. */
    private static final String FILE = "\"File :\"";

    /** " is not found at this " constant. */
    private static final String NOT_FOUND_AT_THIS = "\" is not found at this \"";

    /** "location :" constant. */
    private static final String LOCATION = "\" location : \"";

    /**
     * Constructor to set the file path and name.
     * 
     * @param filePath
     *            is the path of the file
     * 
     * @param fileName
     *            is the name of the file
     */
    public CSVReaderUtil(final String filePath, final String fileName) {
        setFilePath(filePath);
        setFileName(fileName);
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(final String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    /**
     * 
     * Method to get the data from CSV excluding headers.
     * 
     * @return List<String[]>
     */
    public List<String[]> getDataFromCSV() {
        List<String[]> data = null;
        try (FileReader fileReader = new FileReader(
                new File(getFilePath() + File.separator + getFileName()))) {
            CSVReader csvReader = new CSVReaderBuilder(fileReader)
                    .withSkipLines(1).build();
            data = csvReader.readAll();
        } catch (IOException e) {
            LOG.error(FILE + getFileName() + NOT_FOUND_AT_THIS + getFilePath()
                    + LOCATION + e);
        }
        return data;

    }

    /**
     * 
     * Method to get the data from CSV including headers.
     * 
     * @return List<String[]>
     */
    public List<String[]> getDataFromCSVWithHeaders() {
        List<String[]> data = null;
        try (FileReader fileReader = new FileReader(
                new File(getFilePath() + File.separator + getFileName()))) {
            CSVParser parser = new CSVParserBuilder().withSeparator(',')
                    .build();
            CSVReader csvReader = new CSVReaderBuilder(fileReader)
                    .withCSVParser(parser).build();
            data = csvReader.readAll();
        } catch (IOException e) {
            LOG.error(FILE + getFileName() + NOT_FOUND_AT_THIS + getFilePath()
                    + LOCATION + e);
        }
        return data;

    }

    /**
     * 
     * Method to get the data from Column based on Column name.
     * 
     * @param columnName
     *            is the column name from the CSV file
     * 
     * @return List<String>
     */
    public List<String> getRowDataWithColoumnName(final String columnName) {

        List<String> dataList = null;

        List<String> dataRows;
        try {
            Path path = Paths
                    .get(getFilePath() + File.separator + getFileName());
            dataRows = Files.readAllLines(path);
            List<String> columnHeader = Arrays
                    .asList(dataRows.get(0).split(","));
            int columnNumber = columnHeader.indexOf(columnName);

            dataList = dataRows.stream().skip(1)
                    .map(line -> Arrays.asList(line.split(",")))
                    .map(list -> list.get(columnNumber))
                    .filter(Objects::nonNull)
                    .filter(data -> data.trim().length() > 0)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            LOG.error(FILE + getFileName() + NOT_FOUND_AT_THIS + getFilePath()
                    + LOCATION + e);
        }
        return dataList;

    }
}
