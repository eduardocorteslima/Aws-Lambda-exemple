package example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;

public class SnsEvent implements RequestHandler<SNSEvent, String> {

	public String handleRequest(SNSEvent input, Context context) {
		String output="nada";
		try {
			URL url = new URL("http://localhost:8080/add");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			OutputStream os = conn.getOutputStream();
			os.write(input.getRecords().get(0).getSNS().getMessage().getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return output;
	}

}
