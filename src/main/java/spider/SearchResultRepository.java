package spider;


import java.util.List;






import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SearchResultRepository {
	
	private Index index;
	
	@Autowired
	public SearchResultRepository(Index index){
		this.index = index;
	}
	
	public List<SearchResult> search(String queryStr){
		return index.search(queryStr);
	}
}
