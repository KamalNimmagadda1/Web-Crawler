package main;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class MyCrawler extends WebCrawler{
	private static final Logger LOGGER = LoggerFactory.getLogger(MyCrawler.class);
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(" + "css|js|json|webmanifest|ttf|wav|avi|mov|mpeg|mpg|ram|m4v|wma|wmv|mid|txt|mp2|mp3|mp4|zip|rar|gz|exe|ico))$");
	
	private String task1 = "";
	private String task2 = "";
	private String task3 = "";
	private Set<String> urlSet = new HashSet<>();
	
	@Override
	public Object getMyLocalData() {
		return new String[]{task1, task2, task3};
	}
	
	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String href = url.getURL().toLowerCase().replaceAll(",", "_");
	    boolean startWith = false;
        if(href.startsWith("http://www.usatoday.com") || href.startsWith("https://www.usatoday.com")) {
        	startWith = true;
        	task3 += href + ",OK\n";
        }
        else {
        	task3 += href + ",N_OK\n";
        }
	    boolean notFound = !urlSet.contains(href);
	    return !FILTERS.matcher(href).matches() && startWith && notFound;
	}
	
	@Override
    protected void handlePageStatusCode(WebURL webUrl, int statusCode, String statusDescription) {
		String url = webUrl.getURL().toLowerCase().replaceAll(",", "_");
		LOGGER.info(webUrl + " - " + statusCode);
		task1 += url + "," + statusCode + "\n";
        urlSet.add(url);
    }
	
	@Override
	public void visit(Page page) {
		String url = page.getWebURL().getURL().toLowerCase().replaceAll(",", "_");
		int size = page.getContentData().length / 1024;
		int numOfOutLink = 0;
		String contentType = page.getContentType().split(";")[0];
		boolean isCorrectType = contentType.contains("html") | contentType.contains("image") |
                contentType.contains("doc") | contentType.contains("pdf");
		
		if(page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlData = (HtmlParseData) page.getParseData();
			Set<WebURL> links = htmlData.getOutgoingUrls();
			numOfOutLink += links.size();
		}
		LOGGER.info("URL: " + url);
		LOGGER.info("Content-type: " + contentType);
		
		if(isCorrectType) {
			task2 += url + "," + size + "," + numOfOutLink + "," + contentType + "\n";
		}
	}

}
