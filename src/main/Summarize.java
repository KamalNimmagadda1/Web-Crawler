package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.hc.core5.http.impl.EnglishReasonPhraseCatalog;

public class Summarize {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		final String FETCH_FILE = "/Users/kamalnimmagadda/eclipse-workspace/MyCrawlerProject/out/fetch_usatoday.csv";
		final String VISIT_FILE = "/Users/kamalnimmagadda/eclipse-workspace/MyCrawlerProject/out/visit_usatoday.csv";
		final String URL_FILE = "/Users/kamalnimmagadda/eclipse-workspace/MyCrawlerProject/out/urls_usatoday.csv";
		final String REPORT = "/Users/kamalnimmagadda/eclipse-workspace/MyCrawlerProject/out/CrawlReport_usatoday.txt";
		
		boolean firstLine = true;
		
		BufferedReader in = new BufferedReader(new FileReader(URL_FILE));
        Set<String> urls = new HashSet<>();
        String line;
        int URLs = 0;
        int UniqueURLs = 0;
        int UniqueURLsIn = 0;
        int UniqueURLsOut = 0;
        
        while((line = in.readLine()) != null) {
        	if(firstLine) {
        		firstLine = false;
        		continue;
        	}
        	URLs += 1;
        	String[] temp = line.split(",");
        	String url = temp[0].trim();
        	if(!urls.contains(url)) {
        		urls.add(url);
        		UniqueURLs += 1;
        		
        		String indicator = temp[1].trim();
            	if(indicator.equals("OK")) {
            		UniqueURLsIn += 1;
            	}
            	else {
            		UniqueURLsOut += 1;
            	}
        	}
        }
        in.close();
        
        in = new BufferedReader(new FileReader(FETCH_FILE));
        Map<String, Integer> stateCodeMap = new HashMap<>();
        int fetchAttempted = 0;
        int fetchSucceeded = 0;
        int fetchFailed = 0;
        firstLine = true;
        while((line = in.readLine()) != null) {
        	if(firstLine) {
        		firstLine = false;
        		continue;
        	}
        	fetchAttempted += 1;
            String[] temp = line.split(",");
            String stateCode = temp[1].trim();
            if(stateCode.charAt(0) == '2') {
            	fetchSucceeded += 1;
            }
            else {
            	fetchFailed += 1;
            }
            stateCodeMap.put(stateCode, stateCodeMap.getOrDefault(stateCode, 0) + 1);
        }
        in.close();
        
        in = new BufferedReader(new FileReader(VISIT_FILE));
        Map<String, Integer> contentTypeMap = new HashMap<>();
        int[] sizes = new int[5];
        firstLine = true;
        while((line = in.readLine()) != null) {
        	if(firstLine) {
        		firstLine = false;
        		continue;
        	}
            String[] temp = line.split(",");
            int size = Integer.valueOf(temp[1].trim());
            if(size < 1) {
            	sizes[0] += 1;
            }
            else if(size >= 1 && size < 10) {
            	sizes[1] += 1;
            }
            else if(size >= 10 && size < 100) {
            	sizes[2] += 1;
            }
            else if(size >= 100 && size < 1024) {
            	sizes[3] += 1;
            }
            else {
            	sizes[4] += 1;
            }
            String contentType = temp[3].trim();
            contentTypeMap.put(contentType, contentTypeMap.getOrDefault(contentType, 0) + 1);
        }
        in.close();
        
        BufferedWriter bw = new BufferedWriter(new FileWriter(REPORT, true));
		bw.write("Name: Kamala Sai Theja Nimmagadda\n");
		bw.write("USC ID: 6986899979\n");
		bw.write("News site crawled: usatoday.com\n");
		bw.write("Number of threads: 9\n");
		bw.write("\n");
		
		bw.write("Fetch Statistics\n");
		bw.write("==============\n");
		bw.write("# fetches attempted: " + fetchAttempted + "\n");
		bw.write("# fetches succeeded: " + fetchSucceeded + "\n");
		bw.write("# fetches failed or aborted: " + fetchFailed + "\n");
		bw.write("\n");
		
		bw.write("Outgoing URLs:\n");
		bw.write("==============\n");
		bw.write("Total URLs extracted: " + URLs + "\n");
		bw.write("# unique URLs extracted: " + UniqueURLs + "\n");
		bw.write("# unique URLs within News Site: " + UniqueURLsIn + "\n");
		bw.write("# unique URLs outside News Site: " + UniqueURLsOut + "\n");
		bw.write("\n");
		
		bw.write("Status Codes:\n");
		bw.write("=============\n");
		SortedSet<String> keySet = new TreeSet<>(stateCodeMap.keySet());
		for(String stateCode: keySet) {
			String status = EnglishReasonPhraseCatalog.INSTANCE.getReason(Integer.parseInt(stateCode), Locale.ENGLISH);
			bw.write(stateCode + ' ' + status + ": " + stateCodeMap.get(stateCode) + "\n");
		}
		bw.write("\n");
		
		bw.write("File Sizes:\n");
		bw.write("===========\n");
		bw.write("< 1KB: " + sizes[0] + "\n");
		bw.write("1KB ~ <10KB: " + sizes[1] + "\n");
		bw.write("10KB ~ <100KB: " + sizes[2] + "\n");
		bw.write("100KB ~ <1MB: " + sizes[3] + "\n");
		bw.write(">= 1MB: " + sizes[4] + "\n");
		bw.write("\n");
		
		bw.write("Content Types:\n");
		bw.write("==============\n");
		for(String contentType: contentTypeMap.keySet()) {
			bw.write(contentType + ": " + contentTypeMap.get(contentType) + "\n");
		}
		bw.write("\n");
		
		bw.close();
	}

}
