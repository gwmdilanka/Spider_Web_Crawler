package spider;

import java.net.URI;
import java.util.List;


public interface Index {	
	public void storeLinks(URI uri, StringBuilder content);	
	public List<SearchResult> search(String queryStr);
	

}
