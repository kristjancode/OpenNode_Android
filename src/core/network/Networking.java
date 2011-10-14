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
	
	public String httpRequest(String server, int port, String method, String destination, String data)
	{
		String response = null;
		
		try
		{
			String urlString = "http://" + server + ":" + port + "/" + destination;
			URL url = new URL(urlString);			
			HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
			httpUrlConnection.setRequestMethod(method);		
			httpUrlConnection.setConnectTimeout(10000);
			
			if (data.length() > 0)
			{
				httpUrlConnection.setDoOutput(true);
				httpUrlConnection.getOutputStream().write(data.getBytes());
			}
			
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
