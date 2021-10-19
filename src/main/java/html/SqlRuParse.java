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
        String url = "https://www.sql.ru/forum/job-offers";
        for (int i = 1; i < 6; i++) {
            Document doc = Jsoup.connect(url + "/" + i).get();
            System.out.println("Страница: " + i);
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Element parent = td.parent();
                String vacancy = parent.child(1).child(0).attr("href") + System.lineSeparator() + parent.child(1).text() + System.lineSeparator() + parent.child(5).text() + System.lineSeparator();
                System.out.println(vacancy);
            }
        }

    }
}