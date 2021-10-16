package html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse {
    public static void main(String[] args) throws Exception {
        int counter = 1;
        List<String> pages = new ArrayList<>();
        pages.add("https://www.sql.ru/forum/job-offers");
        Document doc = Jsoup.connect(pages.get(0)).get();
        Elements page = doc.select("#content-wrapper-forum > table:nth-child(7) > tbody > tr > td:nth-child(1) > a");
        for (Element pg : page) {
            pages.add(pg.attr("href"));
        }
        for (int i = 0; i < 5; i++) {
        Document doc1 = Jsoup.connect(pages.get(i)).get();
            System.out.println("Страница: " + counter);
           Elements row = doc1.select(".postslisttopic");
            for (Element td : row) {
                Element parent = td.parent();
                System.out.println(parent.child(5).text());
            }
            counter++;
        }

    }
}