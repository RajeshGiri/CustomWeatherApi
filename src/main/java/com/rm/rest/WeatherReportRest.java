package com.rm.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
/**
 * @author RajeshGiri
 *
 */
@Path("/weather")
public class WeatherReportRest {

	private static final String API_KEY = "41002906586925e66d7d2dda5ca23a91";

	@GET
	@Path("report/{city}")
	public Response report ( @PathParam("city") String city){
		String output = null;
		try {		
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet getRequest = new HttpGet("http://api.openweathermap.org/data/2.5/weather?q="+city);
			getRequest.addHeader("accept", "application/json");

			HttpResponse response = httpClient.execute(getRequest);
			if (response.getStatusLine().getStatusCode() != 200){
				throw new RuntimeException("Failed : HTTP error code :" + response.getStatusLine().getStatusCode());

			}
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			System.out.println("Output from Server ....for  "+getRequest.getURI()+ "\n");
			while ((output = br.readLine()) != null){
				System.out.println(output);
				return Response.status(200).entity(output).build();
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.status(200).entity(output).build();

	}

}
