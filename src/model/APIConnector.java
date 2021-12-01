package model;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;


//
//@class -> Takes in a query and will establish a connection with the API in order to allow for REST calls 
//@methods -> Creates Connection Between API's and also creates a JSONArray of the response received from API Calls to bring to frontend
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
//			System.out.println(url);
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
	
	public JSONArray callLocalImage(String uploadID) {
		try {
			URL endpoint = new URL("https://api.imagga.com/v2/tags?image_upload_id=" + uploadID);
			HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Authorization","Basic YWNjXzY4YTQ1MDc1NmY1NWE5Mjo3NTIxZDk5MWYzYTE1MzNlY2E4MzRiNDQ2ZjViNGM5Yg==");
			
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
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public String connectLocalImage(String imagePath) {
		String uploadPoint = "https://api.imagga.com/v2/uploads";
		String crlf = "\r\n";
	    String twoHyphens = "--";
	    
		try {
			URL callUrl = new URL(uploadPoint);
			HttpURLConnection connection  = (HttpURLConnection)callUrl.openConnection();
			connection.setRequestMethod("POST");
	        connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Authorization","Basic YWNjXzY4YTQ1MDc1NmY1NWE5Mjo3NTIxZDk5MWYzYTE1MzNlY2E4MzRiNDQ2ZjViNGM5Yg==");
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			connection.setRequestProperty("Cache-Control", "no-cache");
		    connection.setRequestProperty(
		        "Content-Type", "multipart/form-data;boundary=" + "Image Upload");

		    DataOutputStream request = new DataOutputStream(connection.getOutputStream());
		    
		    
		    request.writeBytes(twoHyphens + "Image Upload" + crlf);
		    request.writeBytes("Content-Disposition: form-data; name=\"image\";filename=\"" + imagePath + "\"" + crlf);
		    request.writeBytes(crlf);

		    InputStream inputStream = new FileInputStream(imagePath);
		    int bytesRead;
		    byte[] dataBuffer = new byte[1024];
		    while ((bytesRead = inputStream.read(dataBuffer)) != -1) {
		      request.write(dataBuffer, 0, bytesRead);
		    }

		    request.writeBytes(crlf);
		    request.writeBytes(twoHyphens + "Image Upload" + twoHyphens + crlf);
		    request.flush();
		    request.close();

		    InputStream responseStream = new BufferedInputStream(connection.getInputStream());

		    BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));

		    String line = "";
		    StringBuilder stringBuilder = new StringBuilder();

		    while ((line = responseStreamReader.readLine()) != null) {
		        stringBuilder.append(line).append("\n");
		    }
		    responseStreamReader.close();

		    String response = stringBuilder.toString();

		    responseStream.close();
		    connection.disconnect();
		    return response;
		    
		}catch(Exception e) {
			e.printStackTrace();
			return "";
		}
		
	}
	
	public JSONArray authConnectApi(String imgUrl) {
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
