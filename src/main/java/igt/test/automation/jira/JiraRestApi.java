package igt.test.automation.jira;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class JiraRestApi {
	
	
	
	public static void updateJiraTestStatus(final String geturl, final String puturl, final String issueId, final String projectId, 
			final String cycleId, final String userName, final String password, final int status, final String updateStatus) {
		if(updateStatus.equalsIgnoreCase("No")) {
			return;
		}
		String testId = getJiraCycleOrderId(geturl, issueId, projectId, cycleId, userName, password);
		executeJiraTest(testId, status, userName, password, puturl);
	}
	
	
	/**
     * Perform a GET request to the <code>url</code> with the
     * <code>headers</code>.
     * 
     * @param url
     *            - the REST api that you want to access
     * @param issueId
     * @param projectId
     * @param cycleId
     * @param userName
     * @param password
     *
     * @return orderId
     *    test case orderid based on cycleid.
     * 
     * @author Sujit
     */
	public static String getJiraCycleOrderId(final String url, final String issueId, final String projectId, 
			final String cycleId, final String userName, final String password){
		JsonPath jPath;
		Map<String, String> parametersMap = new HashMap<>();
		parametersMap.put("issueId", issueId);
		parametersMap.put("projectId", projectId);
		parametersMap.put("cycleId", cycleId);
		Response resp = given().auth()
				  .preemptive().basic(userName, password).queryParams(parametersMap).get(url);
		jPath = resp.jsonPath();
		String testId = jPath.getList("executions.id").get(0).toString();
		System.out.println("Id: "+ testId);
		return testId;
	}
	
	
	/**
     * Perform a put request to update the jira test execution status
     * 
     * @param testId
     * @param status
     * @param url
     * @param userName
     * @param password
     *
     * @author Sujit
     */
	public static void executeJiraTest(final String testId, final int status, final String userName, final String password, String url){
		JSONObject requestParams = new JSONObject();
		requestParams.put("status", status);
		requestParams.put("comment", "aut");
		Response response = given().auth()
				  .preemptive().basic(userName, password).header("Content-Type","application/json").body(requestParams.toJSONString()).put(url+testId+"/execute");
		 int statusCode = response.getStatusCode();
		 System.out.println("The status code recieved: " + statusCode);	
	}
}
