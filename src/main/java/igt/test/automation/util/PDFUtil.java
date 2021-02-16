package igt.test.automation.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Utility library to handle PDF operations such as extracting text from PDF,
 * getting the total page numbers, etc.
 */
public class PDFUtil {
    /** Path to the file. */
    private String filePath;

    /** Name of the file. */
    private String fileName;

    /** Log4j Logger. */
    private static final Logger LOG = LogManager.getLogger(PDFUtil.class);

    /** Reference of the PDDocument. */
    private PDDocument pdDocument;

    /** Reference of the URL. */
    private URL url;

    /** Reference of BufferedInputStream. */
    private BufferedInputStream bufferedInputStream;

    /** Reference of PDFParser. */
    private PDFParser pdfParser;

    /** Reference of COSDocument. */
    private COSDocument cosDocument;

    /** Reference of PDFTextStripper. */
    private PDFTextStripper pdfTextStripper;

    /** Reference of file. */
    private File file;

    /**
     * Constructor for the PDFUtil class.
     * 
     * @param filePath
     *            is the file location of the pdf file
     * @param fileName
     *            is the name of the pdf file
     */

    public PDFUtil(final String filePath, final String fileName) {
        this.setFilePath(filePath);
        this.setFileName(fileName);
        try {
            this.setFile(
                    new File(getFilePath() + File.separator + getFileName()));
            this.setPdfParser(
                    new PDFParser(new RandomAccessFile(getFile(), "r")));
            getPdfParser().parse();
            this.setCosDocument(getPdfParser().getDocument());
            this.setPdfTextStripper(new PDFTextStripper());
            this.setPdDocument(new PDDocument(getCosDocument()));
        } catch (IOException e) {

            LOG.error("Unable to find the file :" + getFileName()
                    + " at the location " + getFilePath() + " " + e);

        }

    }

    /**
     * Constructor for the PDFUtil class.
     * 
     * @param url
     *            is the url for pdf e.g.
     *            http://www.xyz.com/download/pdfguide.pdf
     * @throws IOException
     */

    public PDFUtil(final String url) throws IOException{
        try {
            this.setUrl(new URL(url));
            this.setBufferedInputStream(
                    new BufferedInputStream(getUrl().openStream()));
            this.setPdfParser(
                    new PDFParser(new RandomAccessBufferedFileInputStream(
                            getBufferedInputStream())));
            getPdfParser().parse();
            this.setCosDocument(getPdfParser().getDocument());
            this.setPdfTextStripper(new PDFTextStripper());
            this.setPdDocument(new PDDocument(getCosDocument()));
        } catch (IOException malEx) {
            LOG.error("Given URL  {} is an invalid URL", url, malEx);
            throw malEx;
        }

    }

    public File getFile() {
        return file;
    }

    public void setFile(final File file) {
        this.file = file;
    }

    public PDFTextStripper getPdfTextStripper() {
        return pdfTextStripper;
    }

    public void setPdfTextStripper(final PDFTextStripper pdfTextStripper) {
        this.pdfTextStripper = pdfTextStripper;
    }

    public PDFParser getPdfParser() {
        return pdfParser;
    }

    public void setPdfParser(final PDFParser pdfParser) {
        this.pdfParser = pdfParser;
    }

    public COSDocument getCosDocument() {
        return cosDocument;
    }

    public void setCosDocument(final COSDocument cosDocument) {
        this.cosDocument = cosDocument;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(final URL url) {
        this.url = url;
    }

    public BufferedInputStream getBufferedInputStream() {
        return bufferedInputStream;
    }

    public void setBufferedInputStream(
            final BufferedInputStream bufferedInputStream) {
        this.bufferedInputStream = bufferedInputStream;
    }

    public PDDocument getPdDocument() {
        return pdDocument;
    }

    public void setPdDocument(final PDDocument pdDocument) {
        this.pdDocument = pdDocument;
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
     * Method to get the Total number of pages of a PDF file.
     * 
     * @return int total number of pages
     */
    public int getTotalNumberOfPages() {
        return getPdDocument().getNumberOfPages();

    }

    /**
     * Method to get the pdf data.
     * 
     * @param startPage
     *            is the start page of the pdf
     *
     * @param endPage
     *            is the end page of pdf
     * @return String data from the pdf file
     */

    public String getPDFData(final int startPage, final int endPage) {
        getPdfTextStripper().setStartPage(startPage);
        getPdfTextStripper().setEndPage(endPage);
        String pdfText = "";
        try {
            pdfText = getPdfTextStripper().getText(getPdDocument());
        } catch (IOException e) {
            LOG.error("Getting the Exception : {0}", e);
        }
        return pdfText;
    }

}
