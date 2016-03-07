package spider;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class SearchController {

	private SearchResultRepository searchResultRepository;
	
	@Autowired
	public SearchController(SearchResultRepository searchResultRepository){
		super();
		this.searchResultRepository = searchResultRepository;
	}
	
	@RequestMapping (method = RequestMethod.GET, value = "/search")
	public String search(){
		
		return "search";
	}
	
	@RequestMapping (method = RequestMethod.POST, value = "/search")
	public String searchByKeyword(@RequestParam("queryStr") String queryStr, Map<String, Object> model){
		List<SearchResult> searchResults = searchResultRepository.search(queryStr);
		model.put("searchResults", searchResults);
		
		return "search";
	}
}
