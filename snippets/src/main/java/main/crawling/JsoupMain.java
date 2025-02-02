package main.crawling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsoupMain {
  
  public static void main(String[] args) {
    wordExtract();
  }
  
  
  private static void crawling() {
    Document doc = null;
    try {
      doc = Jsoup.connect("https://en.wikipedia.org/").get();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    Elements sel = doc.select("#mp-tfa > p");
    log.info("{}", sel.text());
  }
  
  private static void wordExtract() {
    String article = """
        James Joyce (2 February 1882 – 13 January 1941) was an Irish novelist, poet and literary critic. He contributed to the modernist avant-garde movement and is regarded as one of the most influential and important writers of the 20th century. He is best known for his short story collection Dubliners, and for his novels A Portrait of the Artist as a Young Man, Ulysses and Finnegans Wake. Together with Virginia Woolf and Dorothy Richardson, he is credited with the development of the stream of consciousness technique in which the same weight is given to both the internal world of the mind and the external world of events and circumstances as factors shaping the actions and views of fictional characters. His fictional universe is firmly rooted in Dublin and reflects his family life and the events and friends and enemies from his school and college days. In this, he became both one of the most cosmopolitan and local of all the prominent English-language modernists. (Full article...)
        """;
    List<String> words = new ArrayList<>();
    words.addAll(Stream.of(article.split(" "))
        .filter(word -> !word.matches(".*\\d.*"))
        .filter(word -> !word.matches(".+-.+"))
        .filter(word -> word.length() >= 5)
        .map(word -> word.replaceAll("[^a-zA-Z]", ""))
        .map(word -> word.toLowerCase())
        .filter(word -> !word.isEmpty())
        .distinct()
        .collect(Collectors.toList()));
    words.addAll(Stream.of(article.split(" "))
        .filter(word -> word.matches(".+-.+"))
        .map(word -> word.toLowerCase())
        .collect(Collectors.toList()));
    words.forEach(System.out::println);
    System.out.println(words.size());
  }
}
