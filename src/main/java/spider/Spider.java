package spider;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Spider {
	
	private final Log log = LogFactory.getLog(getClass());
	private HttpClient client;
	private HttpParser parser;
	private Index index;
	
	private Set<URI> alreadyVisited = new HashSet<URI>();
	
	@Autowired
	public Spider(HttpClient client, HttpParser parser, Index index)
	{
		this.client = client;
		this.parser = parser;
		this.index = index;
	}
	
	public void startCrawl()
	{
		try {
			crawl(new URI("http://www.unitec.ac.nz/"),5); //start with 2 or 3 first
		} catch (URISyntaxException e) {
			//e.printStackTrace();
			log.error("Error "+ e);
		}
	}
	
	
	public void crawl(URI uri, int depth)
	{		
		log.info(depth +"/" +uri);
		
		//web crawler algorithm
		
		if(depth <=0)
		{
			return;
		}
		
		System.err.println(uri);
		StringBuilder messageRespond = new StringBuilder();
		
		if(client.get(uri,messageRespond))
		{
			
			//pass to lucene to do indexing
			index.storeLinks(uri, messageRespond);
			
			//Save the content of the URL
			List<URI> uris = parser.parseLinks(messageRespond.toString(), uri.getHost());
			
			//Index page content in lucene
			for(URI request:uris)
			{
				if(!alreadyVisited.contains(request))
				{
					alreadyVisited.add(request);
					System.out.println(request);
					crawl(request, depth -1);
				}
			}			
		}		
	}
}
