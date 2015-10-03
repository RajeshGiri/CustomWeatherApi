import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.rm.rest.WeatherReportRest;


public class WeatherTests {
	private WeatherReportRest weatherReportRest;
	private JSONParser parser = new JSONParser();
	private JSONObject jsonObj;

	@Before
	public void setUp() throws Exception {
		weatherReportRest = new WeatherReportRest();

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void verifyCity() {
		List<String> inputCities = Arrays.asList(new String[]{"sydney", "melbourne", "wollongong"});
		List<String> expectedCities = Arrays.asList(new String[]{"Sydney","Melbourne","Wollongong"});
		String actualCityName;
		int i =0;
		for (String city: inputCities){

			Response jsonResponse = weatherReportRest.report(city);

			try{
				Object obj = parser.parse(jsonResponse.getEntity().toString());

				jsonObj = (JSONObject)obj;
				actualCityName = (String) jsonObj.get("name");
				Assert.assertEquals("Verify that the city name is as expected ", expectedCities.get(i), actualCityName);
				i = i + 1;

			}catch(ParseException pe){
				System.out.println("position: " + pe.getPosition());
				System.out.println(pe);
			}
		}
	}
}
