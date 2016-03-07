package parserImpl;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import spider.HttpParser;

@Component
public class HttpParserImplMozilla implements HttpParser{
	public List<URI> parseLinks(final String content, final String host)
	{
		final List<URI> urls = new ArrayList <URI> ();
		//MozillaParser.init(null , "C:\\dapper\\mozilla\\dist\\bin"); 
		//Document domDocument =new MozillaParser().parse(content.toString()); 
      
		
		return Collections.unmodifiableList(urls);
	}

}
