package com.abdul.azure.keyvault.aad;

import java.io.IOException;
 /**
  * 
  * @author abdul
  *
  */

public class App {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		System.setProperty("http.proxyHost", "example-proxy-server");
		System.setProperty("http.proxyPort", "8080");
		System.setProperty("https.proxyHost", "example-proxy-server");
		System.setProperty("https.proxyPort", "8080"); 
		System.setProperty("http.proxyUser", "proxyuser");
                System.setProperty("http.proxyPassword", "proxypassword ");
		System.setProperty("https.proxyUser", "proxyuser");
		
		String tenantId = "";
		String clientId = "";
		String clientSecret = "";
		String grantType = "client_credentials";
		String scope   = "https://vault.azure.net/.default";
		String resourceURI = "https://samplekeyvaultstore.vault.azure.net";
		 
		String token =   new AzureADTokenService().getToken(clientId, clientSecret, tenantId, grantType, scope);
		String data =    new AzureKeyVaultService(resourceURI,token).getSecret("spring-db-password");
		System.out.println("Data ::" + data);
	}

	 
	
	
	 
	
	

}
