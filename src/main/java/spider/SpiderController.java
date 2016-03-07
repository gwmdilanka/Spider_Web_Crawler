package spider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class SpiderController {

//	@Bean
//	public Spider getSpider()
//	{
//		return new Spider(getHttpClient(), getHttpParser());
//	}
//	
//	@Bean
//	public HttpClient getHttpClient()
//	{
//		return new HttpClientImpl();
//	}
//	
//	@Bean
//	public HttpParser getHttpParser()
//	{
//		return new HttpParserImpl();
//	}
	
	public static void main(String[] args)
	{
		SpringApplication.run(SpiderController.class, args);
		// bootstrap spring by scanning for components linked to the SpiderController
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpiderController.class);

		// use our context to get access to the Spider component
		Spider webCrawler = (Spider) ctx.getBean(Spider.class);
		
		// the Spider components @Autowire annotation will automatically load the HttpClient and HttpParser @Components.
		webCrawler.startCrawl();
	}
}
