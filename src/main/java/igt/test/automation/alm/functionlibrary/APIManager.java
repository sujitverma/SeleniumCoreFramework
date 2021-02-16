
package igt.test.automation.alm.functionlibrary;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Class to have function related to API structure(Json/XML) conversions.
 * 
 * @author 
 *
 */
public final class APIManager {
    /** The Log4j logger. */
    private static final Logger LOG = LogManager
            .getLogger(APIManager.class);
    
    /** private constructor. */
    private APIManager() {
        
    }
    
    /**
     * Perform to convert Java object to Json String using java
     * <code>obj</code>.
     * 
     * @param obj
     *            - {@link Object} Java object.
     * @return Json formatted String.
     * 
     * @author 
     */
    public static String convertToJson(final Object obj) {
        String jsonData = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            jsonData = mapper.writeValueAsString(obj);
        } catch (Exception e) {
            LOG.error("Exception while converting java object to Json string:- " + e);
        }
        return jsonData;
    }

    /**
     * Perform to convert Json String to Java object using <code>jsonData</code>
     * and java <code>classType</code>.
     * 
     * @param jsonData
     *            - Json string request or response
     * @param classType
     *            - function convert it into mention class object
     * @return {@link Object} Java Object of class mention in function parameter
     *         if structure matches.
     * 
     * @author 
     */
    public static Object convertFromJson(final String jsonData,
            final Class<?> classType) {
        Object objFromJson = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            objFromJson = mapper.readValue(jsonData, classType);
        } catch (Exception e) {
            LOG.error("Exception while converting Json string to java object:- " + e);
        }
        return objFromJson;
    }
}
