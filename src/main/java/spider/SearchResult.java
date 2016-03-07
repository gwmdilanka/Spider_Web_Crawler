package spider;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class SearchResult {
	
	@NotNull
	@NotBlank
	private String resultUrl;
	
	@NotNull
	@NotBlank
	private String resultContent;
	
	@NotNull
	@NotBlank
	private float hitValue;
	
	public String getResultUrl() {
		return resultUrl;
	}

	public void setResultUrl(String resultUrl) {
		this.resultUrl = resultUrl;
	}
	
	public String getResultContent() {
		return resultContent;
	}

	public void setResultContent(String resultContent) {
		this.resultContent = resultContent;
	}

	public float getHitValue() {
		return hitValue;
	}

	public void setHitValue(float hitValue) {
		this.hitValue = hitValue;
	}

	public SearchResult(){
		
	}
	
	public SearchResult(String resultUrl, float hitValue,String resultContent){
		super();
		this.resultUrl = resultUrl;
		this.hitValue = hitValue;
		this.resultContent = resultContent;
	}
	
	@Override
	public String toString()
	{
		return "SearchResult [resultUrl=\"" + resultUrl + resultContent+"\"" + "]";
	}
}
