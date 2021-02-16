
package igt.test.automation.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.net.ssl.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * JsonReaderUtil which helps parse the JSON.
 * 
 * @author 
 *
 */
public final class JSONReaderUtil {

    /**
     * private constructor.
     */
    private JSONReaderUtil() {

    }

    /** Parse the error. */
    private static final String PARSE_ERROR = "Unable parse input Json";

    /**
     * A private variable for logging.
     */
    private static final Logger LOG = LogManager
            .getLogger(JSONReaderUtil.class);

    /**
     * reads the Json given and returns a Map collection.
     * 
     * @param jsonUrlPath
     *            url path to the json specified in String format
     * @return {@link Map}
     */
    public static Map<String, Object> parseJsonInUrlGivenAndReturnMap(
            final String jsonUrlPath) {
        Map<String, Object> dataMap = null;
        try {
            if (jsonUrlPath != null) {
                turnOffSSLChecking();
                dataMap = new ObjectMapper().readValue(new URL(jsonUrlPath),
                        new TypeReference<Map<String, Object>>() {
                        });

            }

        } catch (IOException e) {
            LOG.warn(PARSE_ERROR +"{0}", e);
        }

        return dataMap;
    }

    /**
     * reads the Json given and returns a Map collection.
     * 
     * @param jsonPath
     *            path to the json specified in String format
     * @return {@link Map}
     */
    public static Map<String, Object> parseJsonGivenAndReturnMap(
            final String jsonPath) {
        Map<String, Object> dataMap = null;
        try {
            if (jsonPath != null) {
                turnOffSSLChecking();
                dataMap = new ObjectMapper().readValue(new URL(jsonPath),
                        new TypeReference<Map<String, Object>>() {
                        });

            }

        } catch (IOException e) {

            LOG.warn(PARSE_ERROR +"{0}", e);

        }
        return dataMap;
    }

    /**
     * reads the Json URL given and returns a JSON response in the form of
     * String.
     * 
     * @return {@link String}
     * 
     *         This method opens the stream for JSON URL and with BufferedReader
     *         it will convert the JSON Response to String
     * @param jsonURL
     *            json url
     * 
     * @author Mohd Jeeshan
     */
    public static String parseJSONToString(final String jsonURL) {

        String jsonText = null;
        StringBuilder sb = new StringBuilder();
        int counter;
        if (jsonURL != null) {
            try (InputStream inputStream = new URL(jsonURL).openStream();
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(inputStream,
                                    StandardCharsets.UTF_8));) {
                while ((counter = bufferedReader.read()) != -1) {
                    sb.append((char) counter);

                }
                jsonText = sb.toString();
            } catch (MalformedURLException e) {
                LOG.warn("Unable to make connection {0}" , e);
            } catch (IOException e) {
                LOG.warn(PARSE_ERROR +"{0}", e);
            }
        }

        return jsonText;

    }

    /**
     * Turns of strict SSL certificate validation.
     */
    private static void turnOffSSLChecking() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }

                        public void checkClientTrusted(
                                final X509Certificate[] certs,
                                final String authType) {
                            // Nothing to implement
                        }

                        public void checkServerTrusted(
                                final X509Certificate[] certs,
                                final String authType) {
                            // Nothing to implement
                        }
                    } };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = (hostname, session) -> true;

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (Exception ignore) {
            LOG.error(ignore);
        }
    }

    /**
     * Helper method to convert json file into json String.
     * 
     * @param jsonPath json file path
     * @return String return the parsed json
     * 
     */

    public static String parseJSON(final String jsonPath) {

        Object object = "";
        try {
            JSONParser jsonParser = new JSONParser();
            object = jsonParser.parse(new FileReader(jsonPath));
        } catch (IOException e) {

            LOG.info("File not found in the given path " + jsonPath
                    + " and exception is :" + e);
        } catch (ParseException e) {

            LOG.info("Unable to parse the json file :{0}" , e);
        }
        return object.toString();
    }

    /**
     * Method to get the data from JSON.
     *
     * @param jsonFilePath  is the json file path
     * @param jsonArrayName is the name of parent key
     * @return {@link List}
     */
    public static List<Map<String, String>> getJSONDataUsingFeatureFile(final String jsonFilePath,
                                                                        final String jsonArrayName) {
        List<Map<String, String>> dataList = new ArrayList<>();
        Map<String, String> mDataMap;
        JsonParser jsonParser = new JsonParser();
        try {
            Optional<JsonObject> jsonObject = Optional
                .ofNullable(jsonParser.parse(new FileReader(new File(jsonFilePath))).getAsJsonObject());

            if (jsonObject.isPresent()) {
                JsonArray jsonArray = (JsonArray) jsonObject.get().get(jsonArrayName);
                for (JsonElement jsonElemet : jsonArray) {
                    mDataMap = new HashMap<>();
                    for (Map.Entry<String, JsonElement> entry : jsonElemet.getAsJsonObject().entrySet()) {
                        mDataMap.put(entry.getKey(), entry.getValue().toString().replaceAll("\"", ""));
                    }
                    dataList.add(mDataMap);
                }
            }
        } catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
            LOG.error(
                "This exception is raised when an attempt to parse / open the file denoted by a specified path name has failed "
                    + e);
        }

        return dataList;
    }

}
