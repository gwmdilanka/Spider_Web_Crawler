package clientImp;

import java.io.IOException;
import java.net.URI;

import org.springframework.stereotype.Component;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

import spider.HttpClient;

@Component
public class HttpClientImplGoogle implements HttpClient{
	@Override
	public boolean get(URI request, StringBuilder messageResponse)
	{
		final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
		
		try {
			
			GenericUrl url = new GenericUrl(request.toString());
			HttpRequest httpRequest = HTTP_TRANSPORT.createRequestFactory().buildGetRequest(url);
			HttpResponse response = httpRequest.execute();
			if (response.getStatusCode() == 200){
			messageResponse.append(response.parseAsString());
			return true;
			}
	       
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        
		return false;		
	}
}
