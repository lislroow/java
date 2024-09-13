package samples.xml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import lombok.AllArgsConstructor;
import lombok.Data;
import samples.xml.SampleXml.FieldVo;
import samples.xml.SampleXml.TestXmlString;

public class SampleXml2 {
  
  public static Map<String, Object> toMap(Node node, List<SampleXml.FieldVo> fields) {
    Map<String, Object> level1Map = new HashMap<>();
    {
      NodeList level0 = node.getChildNodes();
      for (int i=0; i<level0.getLength(); i++) {
        Node level1 = level0.item(i);
        if (level1.getNodeType() == Node.ELEMENT_NODE) {
          String level1Name = level1.getNodeName();
          // debug
          //System.out.println(">> "+level1Name + ", " + level1.getChildNodes().getLength());
          if (level1.getChildNodes().getLength() > 1) {
            // list 일 경우 length > 1
            List<Map<String, Object>> level1List = (ArrayList) level1Map.getOrDefault(level1Name, new ArrayList<>());
            if (level1List.size() == 0) {
              level1Map.put(level1Name, level1List);
            }
            Map<String, Object> level2Map = new HashMap<>();
            for (int j=0; j<level1.getChildNodes().getLength(); j++) {
              Node level2 = level1.getChildNodes().item(j);
              if (level2.getNodeType() != Node.ELEMENT_NODE) {
                continue;
              }
              String level2Name = level2.getNodeName();
              fields.stream()
                .filter(item -> (item.getIterName()+"/"+item.getName()).equals(level1Name+"/"+level2Name))
                .findFirst()
                .ifPresent(item -> level2Map.put(level2Name, level2.getTextContent()));
            }
            level1List.add(level2Map);
          } else {
            // item 일 경우 length == 1
            fields.stream()
              .filter(item -> item.getName().equals(level1Name))
              .findFirst()
              .ifPresent(item -> level1Map.put(level1Name, level1.getTextContent()));
          }
        }
      }
    }
    //System.out.println(level1Map);
    return level1Map;
  }
  
  public static void parseXml(SampleXml.TestXmlString xml) {
    String xmlstr = xml.getXmlStr();
    String serviceName = null;
    {
      serviceName = "ReductionFarmMngYn";
      String pathstr = "/Envelope/Header/commonHeader";
      Node node = null;
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setNamespaceAware(false);
      DocumentBuilder builder = null;
      Document document = null;
      try {
        builder = factory.newDocumentBuilder();
        document = builder.parse(new java.io.ByteArrayInputStream(xmlstr.getBytes("UTF-8")));
      } catch (Exception e) {
        e.printStackTrace();
      }
      
      Map<String, Object> map = new HashMap<>();
      XPathFactory xpathFactory = XPathFactory.newInstance();
      XPath xpath = xpathFactory.newXPath();
      try {
        node = (Node) xpath.compile(pathstr).evaluate(document, XPathConstants.NODE);
      } catch (XPathExpressionException e) {
        e.printStackTrace();
      }
      map = toMap(node, xml.getHeaderFields());
      serviceName = map.get("serviceName").toString().replace("Service", "");
    }
    
    {
      String pathstr = "/Envelope/Body/get"+serviceName+"Response";
      Node node = null;
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setNamespaceAware(false);
      DocumentBuilder builder = null;
      Document document = null;
      try {
        builder = factory.newDocumentBuilder();
        document = builder.parse(new java.io.ByteArrayInputStream(xmlstr.getBytes("UTF-8")));
      } catch (Exception e) {
        e.printStackTrace();
      }

      Map<String, Object> map = new HashMap<>();
      XPathFactory xpathFactory = XPathFactory.newInstance();
      XPath xpath = xpathFactory.newXPath();
      try {
        node = (Node) xpath.compile(pathstr).evaluate(document, XPathConstants.NODE);
      } catch (XPathExpressionException e) {
        e.printStackTrace();
      }
      map = toMap(node, xml.getBodyFields());
    }
  }
  
  // -XX:+PrintCompilation
  public static void main(String[] args) throws Exception {
    // performance
    ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      System.out.println("--- summary ---");
      map.forEach((k, v) -> System.out.println("Phase: " + k + ", Throughput: " + v));
    }));
    int phase = 0;
    long start = System.currentTimeMillis();
    for (int i=0; i<=100000; i++) {
      int time = (int) ((System.currentTimeMillis() - start) / 1000);
      SampleXml.TestXmlString xml = SampleXml.TestXmlString.values()[i%SampleXml.TestXmlString.values().length];
      parseXml(xml);
      map.merge(time, 1, Integer::sum);
      if (map.size() > phase + 1) {
        System.out.println("Phase: " + phase + ", Throughput: " + map.get(phase));
        phase++;
      }
    }
  }
}
