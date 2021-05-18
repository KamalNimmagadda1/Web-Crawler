package main;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;


public class Controller {
	private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
	private static final String FETCH_FILE = "/Users/kamalnimmagadda/eclipse-workspace/MyCrawlerProject/out/fetch_usatoday.csv";
	private static final String VISIT_FILE = "/Users/kamalnimmagadda/eclipse-workspace/MyCrawlerProject/out/visit_usatoday.csv";
	private static final String URL_FILE = "/Users/kamalnimmagadda/eclipse-workspace/MyCrawlerProject/out/urls_usatoday.csv";
	private static final String NEWS_URL = "https://www.usatoday.com";
	
	private static void writeOutput(StringBuilder output, String s) throws IOException {
		OutputStream os = new FileOutputStream(s);
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.print(output.toString().trim());
        writer.flush();
        writer.close();
	}

	public static void main(String[] args) throws Exception {
		String crawlStorageFolder = "/Users/kamalnimmagadda/eclipse-workspace/MyCrawlerProject/d";
		int numberOfCrawlers = 9;
		int maxPagesToFetch = 20000;
		int maxDepth = 16;
		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(crawlStorageFolder);
		config.setMaxPagesToFetch(maxPagesToFetch);
		config.setMaxDepthOfCrawling(maxDepth);
		config.setPolitenessDelay(100);
		config.setIncludeHttpsPages(true);
        config.setFollowRedirects(true);
        config.setIncludeBinaryContentInCrawling(true);
        config.setResumableCrawling(false);
		
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
		
		controller.addSeed(NEWS_URL);
		
		controller.start(MyCrawler.class, numberOfCrawlers); 
		
		StringBuilder out1 = new StringBuilder("URL,Status\n");
		StringBuilder out2 = new StringBuilder("URL,Size,Outgoing Links,Content Type\n");
		StringBuilder out3 = new StringBuilder("URL,Status\n");
		
		for (Object t : controller.getCrawlersLocalData()) {
			String[] tasks = (String[]) t;
			out1.append(tasks[0]);
			out2.append(tasks[1]);
			out3.append(tasks[2]);
		}
		
		LOGGER.info("Start writting CSV files");
		writeOutput(out1, FETCH_FILE);
		writeOutput(out2, VISIT_FILE);
		writeOutput(out3, URL_FILE);
	}

}
