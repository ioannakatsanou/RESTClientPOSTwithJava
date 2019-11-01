
import com.google.gson.Gson;

import com.google.gson.JsonElement;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.nio.charset.StandardCharsets;

import java.util.Base64;
import java.util.HashMap;
import java.util.Optional;

public class RESTClientPOST {

	public static void main(String[] args) {
		try {

			String username = "username";
			String password = "password";

			URL url = new URL("http://localhost:8080/RESTExample/post");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			String encoded = Base64.getEncoder()
					.encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8)); // Java 8

			// Setting Authorization to Basic
			conn.setRequestProperty("Authorization", "Basic " + encoded);

			String emplId = "1111";
			String email = "test@test.com";
			String phone = "123456";

			Employee empl = new Employee();
			empl.setEmplId(emplId);
			empl.setEmail(email);
			empl.setPhone(phone);

			Gson gson = new Gson();
			String input = gson.toJson(empl);
			StringBuilder strBu = new StringBuilder(input);
			strBu.insert(0, "{\n" + "\"EMPLOYEES\": {\n" + "\"LIST\": [");
			strBu.append("]\n" + "}\n" + "}");

			input = strBu.toString();
			System.out.println("input = " + input);

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;

			StringBuilder strBuilder = new StringBuilder();
			while ((output = br.readLine()) != null) {
				strBuilder.append(output);
			}

			String response = strBuilder.toString().trim();

			Response resp = new Response(response);
			System.out.println("RESPONSE = " + resp.getResponse());
			System.out.println("ERROR_MESSAGE = " + resp.getErrorMessage());

			conn.disconnect();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
