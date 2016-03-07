package clientImp;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Component;

import parserImpl.HttpParserImplJSoup;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

import spider.HttpClient;
import spider.HttpClientImpl;

@Component
public class HttpClientImplSonatype implements HttpClient {
	@Override
	public boolean get(URI request, StringBuilder messageResponse)
	{
		
		try {
			AsyncHttpClient client = new AsyncHttpClient();
			Response response = client.prepareGet(request.toString()).execute().get();			
			
			messageResponse.append(response.getResponseBody());		
            System.out.println("----------------------------------------");
            System.out.println(response);
            
            client.close();
            return true;
            
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;		
	}
	
	
}
