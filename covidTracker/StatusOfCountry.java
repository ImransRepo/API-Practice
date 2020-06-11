package covidTracker;

import java.util.List;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class StatusOfCountry {

	@Test
	public void getCountryStatus() {

		RestAssured.baseURI = "https://covid-19.dataflowkit.com/v1";

		Response response = RestAssured.given().accept(ContentType.JSON).get();

		JsonPath jsonPath = response.jsonPath();

		List<String> CountryList = jsonPath.getList("Country_text");
		List<String> NewCaseCount = jsonPath.getList("'New Cases_text'");
		List<String> NewDeaths = jsonPath.getList("'New Deaths_text'");
		List<String> TotalCases = jsonPath.getList("'Total Cases_text'");
		List<String> TotalRecovered = jsonPath.getList("'Total Recovered_text'");

		for (int i = 1; i < CountryList.size() - 1; i++) {

			if (CountryList.get(i).equalsIgnoreCase("India")) {
				System.out.println("Country Name = " + CountryList.get(i));
				System.out.println("New Cases Count = " + NewCaseCount.get(i));
				System.out.println("New Deaths Count = " + NewDeaths.get(i));
				System.out.println("Total Cases Count = " + TotalCases.get(i));
				System.out.println("Total Recovered Count = " + TotalRecovered.get(i));

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