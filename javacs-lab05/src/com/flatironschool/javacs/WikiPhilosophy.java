package com.flatironschool.javacs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Deque;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import org.jsoup.select.Elements;

public class WikiPhilosophy {
	
    final static WikiFetcher wf = new WikiFetcher();
	
    /**
     * Tests a conjecture about Wikipedia and Philosophy.
     * 
     * https://en.wikipedia.org/wiki/Wikipedia:Getting_to_Philosophy
     * 
     * 1. Clicking on the first non-parenthesized, non-italicized link
     * 2. Ignoring external links, links to the current page, or red links
     * 3. Stopping when reaching "Philosophy", a page with no links or a page
     *    that does not exist, or when a loop occurs
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
		
        // some example code to get you started
        final String FINAL_URL = "https://en.wikipedia.org/wiki/Philosophy";
        Deque<String> url_stack = new LinkedList<String>();
        String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";

        while(! url.equals(FINAL_URL)) {
            System.out.println("Fetching from url: " + url);
            url_stack.push(url);
            Elements paragraphs = wf.fetchWikipedia(url);
            Element firstPara = paragraphs.get(0);

            Elements links = firstPara.select("a");

            Element selected_link = null; 
            for(Element link: links) {
                String link_url = link.attr("href");
                if(link_url.charAt(0) != '#') {
                    System.out.println("Breaking!");
                    selected_link = link;
                    break;
                }
            }

            if (selected_link == null) {
                System.out.println("There is no path to philosophy.");
            }

            url = selected_link.attr("abs:href");
        }

        System.out.println("Found philosophy. ");
        while(! url_stack.isEmpty()) {
            System.out.println(url_stack.pop());
        }
        /*
        System.out.println("The first link is: " + first_link);
        Iterable<Node> iter = new WikiNodeIterable(firstPara);
        for (Node node: iter) {
            if (node instanceof TextNode) {
                System.out.print(node);
            }
        }
        */
    }
}
