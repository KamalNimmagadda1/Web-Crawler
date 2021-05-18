# Web-Crawler

## 1. Objective
In this assignment, you will work with a simple web crawler to measure aspects of a crawl, study the
characteristics of the crawl, download web pages from the crawl and gather webpage metadata, all
from pre-selected news websites.

## 2. Preliminaries
To begin we will make use of an existing open source Java web crawler called crawler4j. This crawler
is built upon the open source crawler4j library which is located on github. For complete details on
downloading and compiling see
Also see the document “Instructions for Installing Eclipse and Crawler4j” located on the Assignments
web page for help.
Note: You can use any IDE of your choice. But we have provided installation instructions for Eclipse
IDE only

## 3. Crawling
Your task is to configure and compile the crawler and then have it crawl a news website. In the interest
of distributing the load evenly and not overloading the news servers, we have pre-assigned the news
sites to be crawled according to your USC ID number, given in the table below.
The maximum pages to fetch can be set in crawler4j and it should be set to 20,000 to ensure a
reasonable execution time for this exercise. Also, maximum depth should be set to 16 to ensure that
we limit the crawling.

## 4. Collecting Statistics
Your primary task is to enhance the crawler so it collects information about:
1. the URLs it attempts to fetch, a two column spreadsheet, column 1 containing the URL and
column 2 containing the HTTP/HTTPS status code received; name the file fetch_NewsSite.csv
(where the name “NewsSite” is replaced by the news website name in the table above that you
are crawling). The number of rows should be no more than 20,000 as that is our pre-set limit.
Column names for this file can be URL and Status
2. the files it successfully downloads, a four column spreadsheet, column 1 containing the
URLs successfully downloaded, column 2 containing the size of the downloaded file (in
Bytes, or you can choose your own preferred unit (bytes,kb,mb)), column 3 containing the
number of outlinks found, and column 4 containing the resulting content-type; name the file
visit_NewsSite.csv; clearly the number of rows will be less than the number of rows in
fetch_NewsSite.csv
3. all of the URLs (including repeats) that were discovered and processed in some way; a two
column spreadsheet where column 1 contains the encountered URL and column two an
indicator of whether the URL a. resides in the website (OK), or b. points outside of the website
(N_OK). (A file points out of the website if its URL does not start with the initial host/domain
name, e.g. when crawling USA Today news website all inside URLs must start with
.) Name the file urls_NewsSite.csv. This file will be much larger than
fetch_*.csv and visit_*.csv.\
For example for New York Times- the URL and the
URL are both considered as residing in the same website
whereas the following URL is not considered to be in the same website,
http://store.nytimes.com/ \
Based on the information recorded by the crawler in the output files above, you are to collate the
following statistics for a crawl of your designated news website:\
● Fetch statistics:\
o # fetches attempted:\
The total number of URLs that the crawler attempted to fetch. This is usually equal to the
MAXPAGES setting if the crawler reached that limit; less if the website is smaller than that.
o # fetches succeeded:\
The number of URLs that were successfully downloaded in their entirety, i.e. returning a
HTTP status code of 2XX.\
o # fetches failed or aborted:\
The number of fetches that failed for whatever reason, including, but not limited to: HTTP
3
redirections (3XX), client errors (4XX), server errors (5XX) and other network-related
errors.1\
● Outgoing URLs: statistics about URLs extracted from visited HTML pages\
o Total URLs extracted:\
The grand total number of URLs extracted (including repeats) from all visited pages\
o # unique URLs extracted:\
The number of unique URLs encountered by the crawler\
o # unique URLs within your news website:\
The number of unique URLs encountered that are associated with the news website,
i.e. the URL begins with the given root URL of the news website, but the remainder of the
URL is distinct\
o # unique URLs outside the news website:\
The number of unique URLs encountered that were not from the news website.\
● Status codes: number of times various HTTP status codes were encountered during crawling,
including (but not limited to): 200, 301, 401, 402, 404, etc.\
● File sizes: statistics about file sizes of visited URLs – the number of files in each size range
(See Appendix A).\
o 1KB = 1024B; 1MB = 1024KB\
● Content Type: a list of the different content-types encountered\
These statistics should be collated and submitted as a plain text file whose name is
CrawlReport_NewsSite.txt, following the format given in Appendix A at the end of this document.
Make sure you understand the crawler code and required output before you commence collating
these statistics.
