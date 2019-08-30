package com.abdul.azure.keyvault.aad;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author abdul
 *
 */
public class AzureADTokenService {
	
 
	   
	
	
	public String getToken(String clientId, String clientSecret, String tenantId, String grantType,
			String scope) throws IOException {
		
		RestTemplate restTemplate = new RestTemplate() ;
		//String tokenAuthURI = "https://login.microsoftonline.com" + "/" + tenantId + "/oauth2/v2.0/token";
		String tokenAuthURI = "https://login.microsoftonline.com/{tenantId}/oauth2/v2.0/token";
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("tenantId",tenantId);
		
		URI  uri = UriComponentsBuilder.fromUriString(tokenAuthURI)
				                       .buildAndExpand(urlParams)
				                       .toUri();
		 
		 
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<String, String>();
        formData.add("grant_type", grantType);
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("scope", scope);
        
         
        
        HttpEntity<?> request = new HttpEntity<>(formData, headers);
		String response =  restTemplate.postForObject(uri, request,String.class);
		
		System.out.println("uri : " + uri);
		System.out.println("responsetoken : " + response);
	 
		return createBearerToken(response);
		
	}
	
	
	private String createBearerToken(String response) throws IOException {
	 
		
		  ObjectMapper mapper = new ObjectMapper();
		  JsonNode actualObj = mapper.readTree(response);
		  String access_token = actualObj.get("access_token").textValue();
		  String token_type = actualObj.get("token_type").textValue();
		  String token = token_type.trim() + " " + access_token;
		  return token;
		  
	}

}
