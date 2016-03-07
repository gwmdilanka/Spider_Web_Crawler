package spider;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jodd.jerry.Jerry;
import jodd.jerry.JerryFunction;
import jodd.lagarto.dom.LagartoDOMBuilder;




import jodd.lagarto.dom.Node;

import org.springframework.stereotype.Component;

@Component
public class HttpParserImpl implements HttpParser{
	public List<URI> parseLinks(final String content, final String host)
	{
		
		final List<URI> urls = new ArrayList <URI> ();
		
		//parse HTML content using Jerry HTML Parsers	
		
		Jerry.JerryParser jerryParser = new Jerry.JerryParser();
		
		LagartoDOMBuilder domBuilder = (LagartoDOMBuilder) jerryParser.getDOMBuilder();
		
		domBuilder.enableHtmlMode();
		domBuilder.getConfig().setImpliedEndTags(true);
		
		try
		{
			Jerry doc = jerryParser.parse(content);
						
			doc.html();		
			
			Jerry links = doc.find("a[href]");
			
			links.each(new JerryFunction() {
				
				@Override
				public boolean onNode(Jerry node, int index) {
					
					try{
						String href = node.attr("href").trim();
						if(href.startsWith("/")){
							href = "http://"+host+href;
						}							
						if(href.startsWith("http")){
							System.out.println(href);
							URI uri = new URI(href);
							urls.add(uri);
						}
								
					}
					catch(URISyntaxException e){
						e.printStackTrace();
					 }
					return true;
				}				
			});			
		} finally {
			
		}		
		return Collections.unmodifiableList(urls);
	}	
}

