package com.abdul.azure.keyvault.aad;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class AzureKeyVaultService {

	private  String resourceURI;
	private  String token;
	private  String apiversion="2016-10-01";
	
	AzureKeyVaultService(String resourceURI,String token) throws IOException{
		
		this.resourceURI=resourceURI;
		this.token=token;
		 
		
	}
	
	
	
	public String getSecret(String secret) throws IOException {
	
		RestTemplate restTemplate = new RestTemplate() ;
		String urlString =  this.resourceURI + "/secrets/{secret}";
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("secret",secret);
		
		URI  uri = UriComponentsBuilder.fromUriString(urlString)
				                       .queryParam("api-version", this.apiversion)
				                       .buildAndExpand(urlParams)
				                       .toUri();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", this.token);
		HttpEntity<String> request = new HttpEntity<String>(headers);
		
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);

		System.out.println("Data received:" + response.getBody());
	  	return response.getBody();
		
		
	}
	
	
	
	
	
}
