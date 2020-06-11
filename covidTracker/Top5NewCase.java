package covidTracker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Top5NewCase {

	@Test
	public void getTrackerDetails() {

		RestAssured.baseURI = "https://covid-19.dataflowkit.com/v1";

		Response response = RestAssured.given().accept(ContentType.JSON).get();

		JsonPath jsonPath = response.jsonPath();

		List<String> newCaseList = jsonPath.getList("'New Cases_text'");

		List<Integer> IntNewCase = new ArrayList<Integer>();

		System.out.println(newCaseList.size());

		for (int i = 0; i < newCaseList.size() - 1; i++) {
			if (newCaseList.get(i) != "") {
				IntNewCase.add(Integer.parseInt(newCaseList.get(i).replaceAll("\\D", "")));

			}
		}

		Collections.sort(IntNewCase);
		Collections.reverse(IntNewCase);

		System.out.println(IntNewCase);

		for (int i = 0; i < newCaseList.size() - 1; i++) {
			for (int j = 1; j <= 5; j++) {
				if (newCaseList.get(i) != "") {
					if (newCaseList.get(i).replaceAll("\\D", "").contains(String.valueOf(IntNewCase.get(j)))) {

						List<Object> list = jsonPath.getList("Country_text");

						System.out.println("Country Name = " + list.get(i));
						System.out.println("New Cases = " + String.valueOf(IntNewCase.get(j)));

						System.out.println("***************************************");
						break;
					}
				}
			}
		}

		int statusCode = response.getStatusCode();
		if (statusCode == 200) {
			System.out.println("Status Code Matched");
		} else {
			System.out.println("Incorrect Status Code");
		}

		long time = response.getTime();
		if (time < 600) {
			System.out.println("Response Time is correct");
		} else {
			System.out.println("Response Time is too long");
		}

		String contentType = response.contentType();
		if (contentType.equalsIgnoreCase("application/json")) {
			System.out.println("Response Type is JSON");
		} else {
			System.out.println("Incorrect Response Type");
		}

	}
}
