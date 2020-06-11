package covidTracker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class LowNewDeaths {

	@Test
	public void lowNewDeathCases() {

		RestAssured.baseURI = "https://covid-19.dataflowkit.com/v1";

		Response response = RestAssured.given().contentType(ContentType.JSON).get();

		JsonPath jsonPath = response.jsonPath();

		List<String> NewDeathList = jsonPath.getList("'New Deaths_text'");

		List<Integer> NewDeathListInt = new ArrayList<Integer>();

		for (int i = 0; i < NewDeathList.size() - 1; i++) {
			if (NewDeathList.get(i) != "") {
				NewDeathListInt.add(Integer.parseInt(NewDeathList.get(i).replaceAll("\\D", "")));
			}
		}

		Collections.sort(NewDeathListInt);
//		System.out.println(NewDeathListInt);

		for (int i = 1; i < NewDeathList.size() - 1; i++) {
			for (int j = 0; j <= 4; j++) {
				if (NewDeathList.get(i) != "") {
					if (NewDeathList.get(i).replaceAll("\\D", "").equals(String.valueOf(NewDeathListInt.get(j)))) {

						List<String> Country_Name = jsonPath.getList("Country_text");
						System.out.println("Country Name is: " + Country_Name.get(i));
						System.out.println("Death Count is: " + String.valueOf(NewDeathListInt.get(j)));
						System.out.println("***************************************************");
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
