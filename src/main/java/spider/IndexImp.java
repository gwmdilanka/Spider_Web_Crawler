package spider;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.index.IndexWriterConfig;
import org.springframework.stereotype.Component;

import java.io.File;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.QueryBuilder;
import org.apache.lucene.util.Version;


@Component
public class IndexImp implements Index {

	public void storeLinks(URI uri,StringBuilder content) {
               
        try {
            CharArraySet stopwords = charArrayStopWord();

            EnglishAnalyzer analyzer = new EnglishAnalyzer(stopwords);

            IndexWriterConfig conf = new IndexWriterConfig(Version.LATEST,
                    analyzer);
            SimpleFSDirectory simpleFSDirectory = new SimpleFSDirectory(
                    new File(LuceneIndexConstants.LUCENE_DATABASE));

            IndexWriter writer = new IndexWriter(simpleFSDirectory, conf);

            Document doc = new Document();
            //Removing script, style and html tags    		
    		String htmlStr = StrFilter(content.toString());    		
            
            doc.add(new TextField("content", htmlStr, Store.YES));
            doc.add(new StoredField("uri", uri.toString()));          

            writer.addDocument(doc);
            writer.close();

        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (LockObtainFailedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	//Removing unnecessary tags
	public   static   String StrFilter(String htmlstr)   throws   PatternSyntaxException  
	 {     
		//Removing script tags
		String regExScript="<script[^>]*?>[\\s\\S]*?<\\/script>";  
		Pattern   pScript   =   Pattern.compile(regExScript, Pattern.CASE_INSENSITIVE);     
		Matcher   mScript   =   pScript.matcher(htmlstr);
		htmlstr =  mScript.replaceAll("");	
		
		//Removing Style tags
		String regExStyle="<style[^>]*?>[\\s\\S]*?<\\/style>"; 
		Pattern   pStyle   =   Pattern.compile(regExStyle, Pattern.CASE_INSENSITIVE);     
		Matcher   mStyle  =   pStyle.matcher(htmlstr);
		htmlstr =  mStyle.replaceAll("");
		
		//Removing HTML tags
		String regExHTML="<[^>]+>"; 
		Pattern   pHTML   =   Pattern.compile(regExHTML, Pattern.CASE_INSENSITIVE);     
		Matcher   mHTML  =   pHTML.matcher(htmlstr);
		htmlstr =  mHTML.replaceAll("");
		
		return   htmlstr.trim();		   
   }
	
	
	public List<SearchResult> search(String queryStr) {
		
		List<SearchResult> searchResults = new ArrayList<SearchResult>();
		
		CharArraySet stopwords = charArrayStopWord();

		Analyzer analyzer = new EnglishAnalyzer(stopwords);

		try {
			QueryBuilder builder = new QueryBuilder(analyzer);
			Query q = builder.createPhraseQuery("content", queryStr);

			int hitsPerPage = 10;
			SimpleFSDirectory simpleFSDirectory = new SimpleFSDirectory(
					new File(LuceneIndexConstants.LUCENE_DATABASE));
			IndexReader reader = DirectoryReader.open(simpleFSDirectory);

			IndexSearcher searcher = new IndexSearcher(reader);

			TopScoreDocCollector collector = TopScoreDocCollector.create(
					hitsPerPage, true);

			searcher.search(q, collector);

			ScoreDoc[] hits = collector.topDocs().scoreDocs;			
			System.out.println("Found " + hits.length + " hits.");
			for (int i = 0; i < hits.length; ++i) {
				ScoreDoc hit = hits[i];
				int docId = hit.doc;
				Document d = searcher.doc(docId);
				searchResults.add(new SearchResult(d.get("uri"), hit.score,d.get("content")));
				System.err.println(searchResults.get(i).getResultUrl());
				System.err.println(searchResults.get(i).getResultContent());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return searchResults;
	}
	
	private static CharArraySet charArrayStopWord()
	{
		CharArraySet stopwords = new CharArraySet(new ArrayList(), true);
		try
		{
			BufferedReader sw = new BufferedReader(new FileReader(
					LuceneIndexConstants.ENGLISH_STOP));
	
			while (true) {
				String temp = sw.readLine();
				if (temp != null) {
					stopwords.add(temp);
				} else
					break;
			}
			sw.close();		
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		return stopwords;
	}
}
