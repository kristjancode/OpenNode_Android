package core.network;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Networking 
{
	public Networking()
	{
		
	}
	
	public String httpRequest(String server, int port, String destination)
	{
		String response = null;
		String urlString = "http://" + server + ":" + port + "/" + destination;
		
		try
		{
			URL url = new URL(urlString);
			HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
			httpUrlConnection.setRequestMethod("GET");
			httpUrlConnection.setConnectTimeout(10000);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
			StringBuffer stringBuffer = new StringBuffer();
			
			for (String line = null; (line = bufferedReader.readLine()) != null; )
			{
				stringBuffer.append(line);
				stringBuffer.append('\n');
			}
			
			int length = stringBuffer.length();
			
			if (length > 0)
			{
				stringBuffer.delete(length - 1, length);
				response = stringBuffer.toString();
			}
		}
		catch (Exception exception)
		{
			
		}
		
		return response;
	}
}
