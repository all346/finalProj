package model;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class APIConnector {
	public String apiUrl;
	
	public APIConnector(String url) {
		this.apiUrl = url;
	}
	
	public JSONObject connectAPI(String query) {
		//Establishes connection and makes the call
		//Should return a JSON object
		try {
			URL url = new URL(this.apiUrl + query);
			System.out.println(url);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();

			//Error
			if(connection.getResponseCode() != 200) {
				throw new RuntimeException("Error " + connection.getResponseCode());
			}else {
//				System.out.println(connection.getContent());
				StringBuilder dataString = new StringBuilder();
				Scanner scanner = new Scanner(url.openStream());
				
			for(int i = 0; i < 5; i++) {
				if(scanner.hasNext()) {
					dataString.append(scanner.nextLine());
				}
			}
			scanner.close();
			
			JSONParser parser = new JSONParser();
			
			return (JSONObject)parser.parse(String.valueOf(dataString));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public JSONArray authConnectApi(String imgUrl, String headers) {
		try {
			URL callUrl = new URL(this.apiUrl + imgUrl);
//			System.out.println(callUrl);
			HttpURLConnection connection  = (HttpURLConnection)callUrl.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Authorization","Basic YWNjXzY4YTQ1MDc1NmY1NWE5Mjo3NTIxZDk5MWYzYTE1MzNlY2E4MzRiNDQ2ZjViNGM5Yg==");
			connection.connect();
			
			if(connection.getResponseCode() != 200) {
				throw new RuntimeException("Error " + connection.getResponseCode());
			}else {
				InputStream is = connection.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				
				int numCharsRead;
		        char[] charArray = new char[1024];
		        StringBuffer sb = new StringBuffer();
		        while ((numCharsRead = isr.read(charArray)) > 0) {
		            sb.append(charArray, 0, numCharsRead);
		        }
		        
		        String result = sb.toString();
		        JSONParser parser = new JSONParser();
//				System.out.println(result);
				try {
					JSONObject json = (JSONObject)parser.parse(result);
					json = (JSONObject) json.get("result");
					JSONArray tags = (JSONArray)json.get("tags");
//					System.out.println(json);
					return tags;
				} catch (ParseException e) {
					e.printStackTrace();
					return null;
				}
		        
		        
			}
		}catch(Exception e) {
			System.out.println("Caught");
			e.printStackTrace();
		}
		
		return null;
	}
	
	
}
