package itunesSearchTest;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.AssertJUnit;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;

public class SearchAPITest {
	
	static String baseUri = "https://itunes.apple.com/search?";
	String searchUrl = "";

/*	@BeforeClass
	public static void init(){

		RestAssured.baseURI = baseUri;
	}*/
	
	
	@Parameters("Term")
	@Test
	public void searchValidTerm(String term) throws ClientProtocolException, IOException
	{
		if(term == null || (term.trim().equals("")))
		{
			Assert.fail("Term is a mandatory parameter. Please provide term parameter");
		}	
		else
		{
			 searchUrl = baseUri +"term";
		}
		
		 	HttpResponse response = httpSearchRequest(searchUrl); 
		 	int actualResponse = response.getStatusLine().getStatusCode();
		 	int expectedResponse = 200;	 	
		 	AssertJUnit.assertEquals(actualResponse, expectedResponse);		
	}
	
	@Test
	@Parameters({"Term", "Country", "Media", "Limit" })
	public void searchvalidTermCountryMediaLimit(String term, String country, String media, String limit) throws ClientProtocolException, IOException
	{
		if(term == null || (term.trim().equals("")))
		{
			Assert.fail("Term is a mandatory parameter. Please provide term parameter");
		}	
		else
		{
			 searchUrl = baseUri +"term="+term;
		}
		if(country != null)
		{
			 searchUrl = searchUrl+"&country=" + country;
		}
		if(media != null)
		{
			searchUrl = searchUrl+"&media=" + media;
		}
		if(limit != null)
		{
			if(Integer.parseInt(limit) > 0 || Integer.parseInt(limit)<=200)
			{
				searchUrl = searchUrl + "&limit=" + limit;
			}
			else
			{
				Assert.fail("Limit should be between 1 and 200");
			}
			
			HttpResponse response = httpSearchRequest(searchUrl); 
		 	int actualResponse = response.getStatusLine().getStatusCode();
		 	int expectedResponse = 200;	 	
		 	Assert.assertEquals(actualResponse, expectedResponse);	
		}
		
		
		
	}
	
	
	@Test
	@Parameters({"TermInvalid", "CountryInvalid", "MediaInvalid", "LimitInvalid" })
	public void searchInvalidTermCountryMediaLimit(String term, String country, String media, String limit) throws ClientProtocolException, IOException{
		
		searchUrl = baseUri +"term="+term;
		searchUrl = searchUrl+"&country=" + country;
		searchUrl = searchUrl+"&media=" + media;
		searchUrl = searchUrl + "&limit=" + limit;
		
		HttpResponse response = httpSearchRequest(searchUrl); 
	 	int actualResponse = response.getStatusLine().getStatusCode();
	 	int expectedResponse = 400;	 	
	 	Assert.assertEquals(actualResponse, expectedResponse);		
	}
	
	@Test
	public void searchEmptyParameters() throws ClientProtocolException, IOException
	{
		searchUrl = baseUri;
		HttpResponse response = httpSearchRequest(searchUrl); 
		/*if(!response.containsHeader("resultCount:0"))
		{
			Assert.fail("Result count should be empty");
		}*/
	 	int actualResponse = response.getStatusLine().getStatusCode();
	 	int expectedResponse = 200;	 	
	 	Assert.assertEquals(actualResponse, expectedResponse);
	}
	
	private HttpResponse httpSearchRequest(String searchUrl) throws ClientProtocolException, IOException {
		
		HttpUriRequest searchRequest = new HttpGet(searchUrl);
		HttpResponse searchResponse = HttpClientBuilder.create().build().execute(searchRequest);
		
		return searchResponse;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
