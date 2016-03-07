package parserImpl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.springframework.stereotype.Component;

import spider.HttpClientImpl;
import spider.HttpParser;


@Component
public class HttpParserImplHTMLParser implements HttpParser{
	public List<URI> parseLinks(final String content, final String host)
	{
		//parse HTML content using HTMLParser
		final List<URI> urls = new ArrayList <URI> ();
		
		final Parser htmlParser = Parser.createParser(content, null);
		try {
	        NodeList tagNodeList = htmlParser.extractAllNodesThatMatch(new NodeClassFilter(LinkTag.class));
			
	        for (int j = 0; j < tagNodeList.size(); j++) {
	            LinkTag loopLink = (LinkTag) tagNodeList.elementAt(j);
	            String href = loopLink.getLink().trim(); //Returns the url as a string, to which this link points.
	            
	            if(href.startsWith("/")){
					href = "http://"+host+href;
				}							
				if(href.startsWith("http")){
					System.out.println(href);
					URI uri = new URI(href);					
					urls.add(uri);
				}
	           
	        }
	    }catch (URISyntaxException e) {			
			e.printStackTrace();
		}catch (org.htmlparser.util.ParserException e) {			
			e.printStackTrace();
		}		
		return Collections.unmodifiableList(urls);
	}	
	public static void main(String [] args){
		HttpClientImpl clientimp = new HttpClientImpl();
		StringBuilder response = new StringBuilder();
		try {
			clientimp.get(new URI("http://www.unitec.ac.nz"), response);
			new HttpParserImplHTMLParser().parseLinks(response.toString(), "http://www.unitec.ac.nz");		
			
		} catch (URISyntaxException e) {			
			e.printStackTrace();
		}
	}
}
