package parserImpl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;







import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import clientImp.HttpClientImplGoogle;
import spider.HttpClientImpl;
import spider.HttpParser;


@Component
public class HttpParserImplJSoup implements HttpParser{
	public List<URI> parseLinks(final String content, final String host)
	{
		
		final List<URI> urls = new ArrayList <URI> ();
		
		//parse HTML content using JSoup
		URI uri = null;
		
		try{
			Document d1 = Jsoup.parse(content);
			Elements links = d1.select("a[href]");
			for (Element link : links)
			{
				String href = link.attr("href").trim();
			try{
				
				if(href.startsWith("/")){
					href = "http://"+host+href;
				}							
				if(href.startsWith("http")){
					System.out.println(href);
					uri = new URI(href);
					urls.add(uri);
				}
			}catch(URISyntaxException e){
					e.printStackTrace();
			}			

			}
		}catch (Exception e)
		{
			e.printStackTrace();			
		}
		return Collections.unmodifiableList(urls);
	}
	
	public static void main(String [] args){
		HttpClientImpl clientimp = new HttpClientImpl();
		
		StringBuilder response = new StringBuilder();
		try {
			clientimp.get(new URI("http://www.unitec.ac.nz"), response);
			new HttpParserImplJSoup().parseLinks(response.toString(), "http://www.unitec.ac.nz");
					
		} catch (URISyntaxException e) {			
			e.printStackTrace();
		}
	}
}
	
