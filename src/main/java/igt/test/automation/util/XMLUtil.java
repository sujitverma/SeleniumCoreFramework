
package igt.test.automation.util;

import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.*;
import java.util.*;

/**
 * This class is to handle all the Read and Write Operations on XML file.
 *
 * @author 
 **/

public class XMLUtil {

    /**
     * Path to the file.
     */
    private String filePath;

    /**
     * Name of the file.
     */
    private String fileName;

    /**
     * XML Document.
     */
    private static Document doc;

    /**
     * Constants.
     */
    public static final String UNABLE_TO_RETRIEVE = "Unable to retrieve node for tag: ";

    /**
     * Log4j Logger.
     */
    private static final Logger LOG = LogManager.getLogger(SeleniumUtil.class);

    /**
     * Initialiser.
     *
     * @param filePath path to the file
     * @param fileName name of the file
     * @throws ParserConfigurationException if the parse can't be configured
     * @throws IOException                  if the document can't be read
     * @throws SAXException                 if the document can't be processed
     */
    public XMLUtil(final String filePath, final String fileName)
        throws ParserConfigurationException, IOException, SAXException {
        this.setFilePath(filePath);
        this.setFileName(fileName);
        DocumentBuilderFactory docFactory = DocumentBuilderFactory
            .newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document aDoc = docBuilder.parse(filePath + fileName);
        XMLUtil.setDocument(aDoc);
    }

    /**
     * Overloaded Constructor in case of Fetching response directly rather than from
     * a File.
     *
     * @param filePath path to the file which can be used to set the location where
     *                 we the store the response
     * @param fileName name of the file which can be used to set the file name where
     *                 we the store the response
     * @param response response object
     * @throws IOException                  IOException
     * @throws SAXException                 SAXException
     * @throws ParserConfigurationException ParserConfigurationException
     * @author 
     */
    public XMLUtil(final String filePath, final String fileName, final Response response)
        throws SAXException, IOException, ParserConfigurationException {
        this.setFilePath(filePath);
        this.setFileName(fileName);
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        docFactory.setNamespaceAware(true);
        DocumentBuilder builder = docFactory.newDocumentBuilder();
        Document document = builder.parse(new java.io.ByteArrayInputStream(response.asString().getBytes()));
        XMLUtil.setDocument(document);

    }

    /**
     * This method uses DOM Parser to Read Tag Attribute Value in a Specified
     * Node.
     *
     * @param tagName   - Root Element ex: <JourneySegment
     * @param childNode - childNode in the xml for which the value needs to be fetched
     * @return the text from the node
     * @throws Throwable
     */
    public String xmlReadChildNodeValue(final String tagName,
                                        final String childNode) {
        String returnValue = null;

        // Get the tagNode element by tag name directly
        Node tagNode = getDocument().getElementsByTagName(tagName).item(0);

        returnValue = traverseChildNodes(childNode, tagNode);
        return returnValue;
    }

    /**
     * This method uses DOM Parser to Read Tag Attribute Value in a Specified
     * Node.
     *
     * @param tagName   - Root Element ex: <JourneySegment
     * @param childNode - childNode in the xml for which the value needs to be
     *                  modified
     * @param nodeValue - value to modify for the mentioned childNode
     * @throws Throwable
     * @throws TransformerException if something goes wrong
     */
    public void xmlModifyChildNodeValue(final String tagName,
                                        final String childNode, final String nodeValue)
        throws TransformerException {

        // Get the tagNode element by tag name directly
        Node tagNode = getDocument().getElementsByTagName(tagName).item(0);

        // loop the tagName child node
        NodeList list = tagNode.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (childNode.equals(node.getNodeName())) {
                node.setTextContent(nodeValue);
                break;
            }
        }

        // write the content into xml file
        transformAndCreateXMLFile();

    }

    /**
     * helper method that writes to the XML file.
     *
     * @throws TransformerFactoryConfigurationError TransformerFactoryConfigurationError
     * @throws TransformerException TransformerException
     */
    protected void transformAndCreateXMLFile()
        throws TransformerFactoryConfigurationError, TransformerException {
        TransformerFactory transformerFactory = TransformerFactory
            .newInstance();
        transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING,
                                      true);

        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(getDocument());
        StreamResult result = new StreamResult(
            new File(getFilePath() + getFileName()));
        transformer.transform(source, result);
    }

    /**
     * This method uses DOM Parser to Read Tag Attribute Value in a Specified
     * Node [Index bases].
     *
     * @param tagName   - Root Element ex: <JourneySegment
     * @param childNode - childNode in the xml for which the value needs to be
     *                  modified
     * @param index     - index value
     * @return String;
     * @throws Throwable
     * @throws TransformerException if something goes wrong
     */
    public static String xmlReadChildNodeValueBasedOnIndex(final String tagName,
                                                           final String childNode, final int index) {

        // Get the tagNode element by tag name directly
        Node tagNode = getDocument().getElementsByTagName(tagName).item(index);
        // loop the tagName child node
        return traverseChildNodes(childNode, tagNode);
    }

    /**
     * helper method.
     *
     * @param childNode the childNode you want to traverse
     * @param tagNode   the node you are interested in.
     * @return String
     */
    protected static String traverseChildNodes(final String childNode,
                                               final Node tagNode) {
        NodeList list = tagNode.getChildNodes();
        String retText = null;
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (childNode.equals(node.getNodeName())) {
                retText = node.getTextContent();
            }
        }
        return retText;
    }

    /**
     * Read child node value based on parent node attribute.
     *
     * @param parentNodeName           is parent node in xml
     * @param parentNodeAttributeName  is parent node attribute
     * @param parentNodeAttributeValue is parent node attribute value
     * @param childNodeName            is child node in xml
     * @return child node value
     */
    public String xmlReadChildNodeValueBasedOnParentNodeAttribute(
        final String parentNodeName, final String parentNodeAttributeName,
        final String parentNodeAttributeValue, final String childNodeName) {
        String childNodeValue = "";
        NodeList parentNodeList;
        Node parentNode;
        Attr parentNodeAttribute;
        String parentNodeAttrValue;
        NodeList childNodeList;
        Node childNode;

        parentNodeList = getDocument().getElementsByTagName(parentNodeName);

        for (int i = 0; i < parentNodeList.getLength(); i++) {
            parentNode = parentNodeList.item(i);

            if (parentNode.hasAttributes()) {
                parentNodeAttribute = (Attr) parentNode.getAttributes()
                    .getNamedItem(parentNodeAttributeName);

                if (parentNodeAttribute != null) {
                    parentNodeAttrValue = parentNodeAttribute.getNodeValue();

                    if (StringUtils.equalsIgnoreCase(parentNodeAttrValue,
                                                     parentNodeAttributeValue)) {
                        childNodeList = parentNode.getChildNodes();

                        for (int j = 0; j < childNodeList.getLength(); j++) {
                            childNode = childNodeList.item(j);
                            if (StringUtils.equalsIgnoreCase(
                                childNode.getNodeName(), childNodeName)) {
                                childNodeValue = childNode.getTextContent();
                                break;
                            }
                        }
                    }
                }
            }
        }

        return childNodeValue;
    }

    /**
     * This method uses DOM Parser to Read Tag Attribute Value in a Specified
     * Node [Index bases].
     *
     * @param tagName   - Root Element ex: <JourneySegment
     * @param childNode - childNode in the xml for which the value needs to be
     *                  modified
     * @param nodeValue - value to modify for the mentioned childNode
     * @param index     - index value
     * @throws Throwable
     * @throws TransformerException if something goes wrong
     */
    public void xmlModifyChildNodeValueBasedOnIndex(final String tagName,
                                                    final String childNode, final String nodeValue, final int index)
        throws TransformerException {
        // Get the tagNode element by tag name directly
        Node tagNode = getDocument().getElementsByTagName(tagName).item(index);

        // loop the tagName child node
        NodeList list = tagNode.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (childNode.equals(node.getNodeName())) {
                node.setTextContent(nodeValue);
                break;
            }
        }

        transformAndCreateXMLFile();
    }

    /**
     * This method uses DOM Parser to Read Tag Attribute Value in a Specified
     * Node.
     *
     * @param tagName   - Root Element ex: <JourneySegment
     * @param attribute - any attribute in the xml for which the value needs to be
     *                  fetched
     * @return the text from the node
     * @throws NullPointerException has null pointer due to an non-found tag
     */
    public String xmlReadTagAttributeValue(final String tagName,
                                           final String attribute) {
        // Get the tagNode element by tag name directly
        Node tagNode = getDocument().getElementsByTagName(tagName).item(0);

        // Read tagNode attribute
        NamedNodeMap attr = tagNode.getAttributes();
        Node nodeAttr = attr.getNamedItem(attribute);
        if (nodeAttr == null) {
            return null;
        }
        return nodeAttr.getTextContent();
    }

    /**
     * This method uses DOM Parser to read tag attribute value.
     *
     * @param tagName   - Root Element ex: <JourneySegment
     * @param attribute - an attribute in the xml for which the value needs to be
     *                  fetched
     * @param tagNum    - which tag in the list of tags to get the text content of the
     *                  attribute value
     * @return a String of the text value
     */
    public String xmlReadTagAttributeValue(final String tagName,
                                           final String attribute, final int tagNum) {
        // Get the tagNode element by tag name directly
        Node tagNode = getDocument().getElementsByTagName(tagName).item(tagNum);

        // Read tagNode attribute
        NamedNodeMap attr = tagNode.getAttributes();
        Node nodeAttr = attr.getNamedItem(attribute);
        return nodeAttr.getTextContent();
    }

    /**
     * Set content of the tagNode element by tag name directly.
     *
     * @param tagName name of the tag
     * @param value   the value to set the content to
     * @throws TransformerException when something goes wrong
     */
    public void xmlModifyNodeValue(final String tagName, final String value)
        throws TransformerException {

        NodeList carriers = getDocument().getElementsByTagName(tagName);

        for (int i = 0; i < carriers.getLength(); i++) {
            Node carrier = carriers.item(i);
            carrier.setTextContent(value);
        }

        transformAndCreateXMLFile();

    }

    /**
     * This method uses DOM Parser to Read Tag Attribute Value in a Specified
     * Node.
     *
     * @param tagName        - Root Element ex: <JourneySegment
     * @param attribute      - any attribute in the xml for which the value needs to be
     *                       modified
     * @param attributeValue - value to modify for the mentioned Attribute
     * @throws Throwable
     * @throws TransformerException if the transformation fails
     */
    public void xmlModifyTagAttributeValue(final String tagName,
                                           final String attribute, final String attributeValue)
        throws TransformerException {

        // Get the tagNode element by tag name directly
        Node tagNode = getDocument().getElementsByTagName(tagName).item(0);

        if (tagNode != null) {
            // update tagNode attribute
            NamedNodeMap attr = tagNode.getAttributes();
            Node nodeAttr = attr.getNamedItem(attribute);
            nodeAttr.setTextContent(attributeValue);

            // write the content into xml file
            transformAndCreateXMLFile();
        } else {
            LOG.warn(UNABLE_TO_RETRIEVE + "{}: ", tagName);
        }
    }

    /**
     * This method uses DOM Parser to Read Tag Attribute Value in a specific
     * mode and modify the value.
     *
     * @param tagName        - Root Element ex: <JourneySegment
     * @param attribute      - any attribute in the xml for which the value needs to be
     *                       modified
     * @param attributeValue - value to modify for the mentioned Attribute
     * @param tagInfile      - which of the tags in the file to modify
     * @throws Throwable
     * @throws TransformerException if the transformation fails
     */
    public void xmlModifyTagAttributeValue(final String tagName,
                                           final String attribute, final String attributeValue,
                                           final int tagInfile) throws TransformerException {

        // Get the tagNode element by tag name directly
        Node tagNode = getDocument().getElementsByTagName(tagName)
            .item(tagInfile);

        if (tagNode != null) {
            // update tagNode attribute
            NamedNodeMap attr = tagNode.getAttributes();
            Node nodeAttr = attr.getNamedItem(attribute);
            nodeAttr.setTextContent(attributeValue);

            transformAndCreateXMLFile();
        } else {
            LOG.warn(UNABLE_TO_RETRIEVE + "{}", tagName);
        }
    }

    /**
     * This method uses DOM Parser to get the Tag Attribute Value by a specific
     * attribute and attribute value.
     *
     * @param tagName           - Tag Element to find eg <JourneySegment
     * @param getAttribute      - an attribute in the Tag for which to get the specific tag we
     *                          are looking for
     * @param getAttributeValue - the attribute value in the Tag for which we get the specific
     *                          tag
     * @param setAttribute      - the attribute in the Tag we have which requires to changing
     * @param setAttributeValue - the attribute value to set in the Tag
     * @throws TransformerException if the transformation fails
     */
    public void xmlModifyTagAttributeValueByAttributeValue(final String tagName,
                                                           final String getAttribute, final String getAttributeValue,
                                                           final String setAttribute, final String setAttributeValue)
        throws TransformerException {

        NodeList tagNodeList = getDocument().getElementsByTagName(tagName);

        if (tagNodeList != null) {
            for (int i = 0; i < tagNodeList.getLength(); i++) {
                Node tagNode = getDocument().getElementsByTagName(tagName)
                    .item(i);
                if (tagNode != null) {
                    // update tagNode attribute
                    NamedNodeMap attr = tagNode.getAttributes();
                    Node nodeAttr = attr.getNamedItem(getAttribute);
                    if (nodeAttr.getTextContent().equals(getAttributeValue)) {
                        nodeAttr = attr.getNamedItem(setAttribute);
                        nodeAttr.setTextContent(setAttributeValue);

                        transformAndCreateXMLFile();
                    }
                }
            }
        } else {
            LOG.warn(UNABLE_TO_RETRIEVE + "{} ", tagName);
        }
    }

    /**
     * Generic updateXMLFileWithString() method to replace text string in given
     * xml file.
     *
     * @param existingText Existing Text
     * @param newText      New Text
     * @throws IOException                  Exception
     * @throws ParserConfigurationException if the parser is misconfigured
     * @throws SAXException                 when parsing fails
     */
    public void updateXMLFileWithString(final String existingText,
                                        final String newText) throws IOException {
        BufferedReader br = new BufferedReader(
            new FileReader(getFilePath() + getFileName()));
        String line = null;
        StringBuilder sb = new StringBuilder();

        try {
            while ((line = br.readLine()) != null) {
                if (line.indexOf(existingText) != -1) {
                    line = line.replaceAll(existingText, newText);
                }
                sb.append(line);
                sb.append(System.getProperty("line.separator"));
            }
        } finally {
            br.close();
        }

        BufferedWriter bw = new BufferedWriter(
            new FileWriter(getFilePath() + getFileName()));

        try {
            bw.write(sb.toString());
        } finally {
            bw.close();
        }
    }

    /**
     * Getter.
     *
     * @return the filePath
     */
    String getFilePath() {
        return filePath;
    }

    /**
     * Setter.
     *
     * @param filePath the filePath to set
     */
    void setFilePath(final String filePath) {
        this.filePath = filePath;
    }

    /**
     * Getter.
     *
     * @return the fileName
     */
    String getFileName() {
        return fileName;
    }

    /**
     * Setter.
     *
     * @param fileName the fileName to set
     */
    void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    /**
     * Getter.
     *
     * @return the doc
     */
    public static Document getDocument() {
        return doc;
    }

    /**
     * Setter.
     *
     * @param doc the doc to set
     */
    static void setDocument(final Document doc) {
        XMLUtil.doc = doc;
    }

    // <API <JourneyDetail attribute

    //

    /**
     * Gets the first tag, the first child node and the attribute for the
     * parameterized name.
     *
     * @param tagName      the owner Tag name.
     * @param childTagName the name for the child tag.
     * @param attribute    the name for the attibute
     * @return the attribute value or if not found null.
     */
    public String xmlReadTagAttributeValue(final String tagName,
                                           final String childTagName, final String attribute) {
        // Get the tagNode element by tag name directly
        NodeList tagNodeList = getDocument().getElementsByTagName(tagName);
        Node node = tagNodeList.item(0);
        NodeList childNodesList = node.getChildNodes();

        // loop the tagName child node
        for (int i = 0; i < childNodesList.getLength(); i++) {
            Node childnode = childNodesList.item(i);
            if (childTagName.equals(childnode.getNodeName())) {
                NamedNodeMap attr = childnode.getAttributes();
                Node nodeAttr = attr.getNamedItem(attribute);
                return nodeAttr.getNodeValue();
            }
        }
        return null;
    }

    /**
     * Method to check if XML tag exist.
     *
     * @param tagName - Root Element
     * @return boolean
     */
    public boolean isXmlTagExist(final String tagName) {
        // Get the tagNode element by tag name directly
        NodeList tagNodeList = getDocument().getElementsByTagName(tagName);
        return tagNodeList.getLength() > 0 ? true : false;
    }

    /**
     * Method to check if XML tag node exist.
     *
     * @param tagName   - Root Element
     * @param childNode - Child Node
     * @return boolean
     */
    public boolean isXmlTagNodeExist(final String tagName,
                                     final String childNode) {
        NodeList tagNodeList = getDocument().getElementsByTagName(tagName);

        if (tagNodeList.getLength() > 0) {
            Node tagNode = tagNodeList.item(0);
            NodeList list = tagNode.getChildNodes();
            if (list.getLength() > 0) {
                for (int i = 0; i < list.getLength(); i++) {
                    Node node = list.item(i);
                    if (childNode.equals(node.getNodeName())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * method to get the child nodes of first child.
     *
     * @return NodeList which have all the childNodes
     */
    public NodeList getChildNodesOfFirstChild() {
        getDocument().getDocumentElement().normalize();
        Node node = getDocument().getFirstChild();
        return node.getChildNodes();
    }

    /**
     * method to get the data from environment.xml and storing the values in
     * HashMap.
     *
     * @param envName is the property name you want to load.
     * @return HashMap<String, String>
     */
    public Map<String, String> getDataFromXML(final String envName) {

        Map<String, String> map = new HashMap<>();
        NodeList nL = getChildNodesOfFirstChild();
        for (int i = 0; i < nL.getLength(); i++) {
            NodeList nodeList = nL.item(i).getChildNodes();
            if (nL.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nL.item(i);
                NamedNodeMap nodeMap = element.getAttributes();
                for (int j = 0; j < nodeMap.getLength(); j++) {
                    if (nodeMap.item(j).getTextContent().equals(envName)) {
                        for (int k = 0; k < nodeList.getLength(); k++) {
                            map.put(nodeList.item(k).getNodeName(),
                                    nodeList.item(k).getTextContent());
                        }
                    }
                }
            }
        }
        return map;

    }

    /**
     * Setting up Namespace context to utilise xpath expressions for complex result
     * values this is for those Soap Request who have namespaces defined and we
     * cannot call xpath expressions directly to call those methods.
     *
     * @param xpathStr xpath expression as a string to evaluate the expressions
     * @param uri3     uri3
     * @param uri2     uri2
     * @param uri1     uri1
     * @return String
     * @throws XPathExpressionException     XPathExpressionException
     * @throws IOException                  IOException
     * @throws SAXException                 SAXException
     * @throws ParserConfigurationException ParserConfigurationException
     * @author 
     */
    public String setupNameSpaceAndFetchMatchingStringValue(final String xpathStr, final String uri1, final String uri2,
                                                            final String uri3)
        throws XPathExpressionException {
        NamespaceContext ctx = new NamespaceContext() {
            public String getNamespaceURI(final String prefix) {
                String uri;
                if ("name1".equals(prefix)) {
                    uri = uri1;
                } else if ("name2".equals(prefix)) {
                    uri = uri2;
                } else if ("name3".equals(prefix)) {
                    uri = uri3;
                } else {
                    uri = null;
                }
                return uri;
            }

            // Dummy implementation - not used!
            @SuppressWarnings({"unchecked", "rawtypes"})
            public Iterator getPrefixes(final String val) {
                return null;
            }

            // Dummy implemenation - not used!
            public String getPrefix(final String uri) {
                return null;
            }
        };
        XPathFactory xpathFact = XPathFactory.newInstance();
        XPath xpath = xpathFact.newXPath();
        xpath.setNamespaceContext(ctx);
        return xpath.evaluate(xpathStr, getDocument());
    }

    /**
     * Method to Get the root node of the element.
     *
     * @return String
     * @author 
     */
    public String getRootNodeName() {
        Element root = getDocument().getDocumentElement();
        return root.getNodeName();
    }

    /**
     * Method to Read Comments from the Nodes.
     *
     * @param xpathexpr xpathExpression as a string which contains location from
     *                  where comments needs to be fetched for e.g.
     *                  //inventory/comment()
     * @return String
     * @throws XPathExpressionException XPathExpressionException
     * @author 
     */

    public String readingCommentNode(final String xpathexpr) throws XPathExpressionException {
        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();
        XPathExpression expr = xpath.compile(xpathexpr);
        Object result = expr.evaluate(getDocument(), XPathConstants.STRING);
        return (String) result;
    }

    /**
     * Method to count specific no. of expressions.
     *
     * @param xpathxpr xpathExpression as a string which contains the details of no
     *                 of nodes with specific value to be counted for e.g.
     *                 count(//book[author='Shrey'])
     * @return Double
     * @throws XPathExpressionException XPathExpressionException
     * @author 
     */

    public Double countingSpecificNumbersExpression(final String xpathxpr) throws XPathExpressionException {
        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();
        XPathExpression expr = xpath.compile(xpathxpr);
        Object result = expr.evaluate(getDocument(), XPathConstants.NUMBER);
        return (Double) result;
    }

    /**
     * Method to fetch Specific Child node as per the xpath defined.
     *
     * @param xpathxpr xpathExpression as a string which contains the details of no
     *                 of nodes with specific value to be counted for e.g.
     *                 //book[starts-with(author,'Neal')] or
     *                 //book[contains(author,'Niven')]
     * @return String
     * @throws XPathExpressionException XPathExpressionException
     * @author 
     */
    public String getSpecificXpathChildNode(final String xpathxpr) throws XPathExpressionException {
        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();
        XPathExpression expr = xpath.compile(xpathxpr);
        Object result = expr.evaluate(getDocument(), XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        String childnode = null;
        for (int i = 0; i < nodes.getLength(); i++) {
            // node <title> is on first index
            childnode = nodes.item(i).getChildNodes().item(1).getTextContent();
        }
        return childnode;
    }

    /**
     * Method to fetch all the child values pertaining to specific xpath expression.
     *
     * @param xpathStr This is the xpath expression which is used to fetch specific
     *                 results from the entire Document
     * @param uri3     uri3
     * @param uri2     uri2
     * @param uri1     uri1
     * @return List<String>
     * @throws XPathExpressionException XPathExpressionException
     * @author 
     */

    public List<String> setupNameSpaceFetchChildValues(final String xpathStr, final String uri1, final String uri2,
                                                       final String uri3)
        throws XPathExpressionException {
        NamespaceContext ctx = new NamespaceContext() {
            public String getNamespaceURI(final String prefix) {
                String uri;
                if ("name1".equals(prefix)) {
                    uri = uri1;
                } else if ("name2".equals(prefix)) {
                    uri = uri2;
                } else if ("name3".equals(prefix)) {
                    uri = uri3;
                } else {
                    uri = null;
                }
                return uri;
            }

            // Dummy implementation - not used!
            @SuppressWarnings({"unchecked", "rawtypes"})
            public Iterator getPrefixes(final String val) {
                return null;
            }

            // Dummy implemenation - not used!
            public String getPrefix(final String uri) {
                return null;
            }
        };
        ArrayList<String> names = new ArrayList<>();
        XPathFactory xpathFact = XPathFactory.newInstance();
        XPath xpath = xpathFact.newXPath();
        xpath.setNamespaceContext(ctx);
        XPathExpression expr = xpath.compile(xpathStr);
        Object result2 = expr.evaluate(getDocument(), XPathConstants.NODESET);
        NodeList nodes = (NodeList) result2;
        for (int i = 0; i < nodes.getLength(); i++) {
            names.add(nodes.item(i).getTextContent());
        }
        return names;

    }

}
