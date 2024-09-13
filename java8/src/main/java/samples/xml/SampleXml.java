package samples.xml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

public class SampleXml {

  @Data
  @AllArgsConstructor
  static class FieldVo {
    private String name;
    private String iterName;
  }
  
  public static enum TestXmlString {
    XML_STR1,
    XML_STR2,
    XML_STR3,
    XML_STR4;
    
    String xmlstr;
    List<FieldVo> headerFields;
    List<FieldVo> bodyFields;
    
    TestXmlString() {
      String xmlstr = "";
      headerFields = Arrays.asList(
          new FieldVo("serviceName", null)
          , new FieldVo("useSystemCode", null)
          , new FieldVo("certServerId", null)
          , new FieldVo("transactionUniqueId", null)
          , new FieldVo("userDeptCode", null)
          , new FieldVo("userName", null)
          );
      
      int idx = this.ordinal()+1;
      String name = this.name();
      //log.trace(String.format("[%d] %s", idx, name));
      switch (idx) {
      case 1:
        xmlstr = "";
        xmlstr += "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
        xmlstr += "  <soap:Header>";
        xmlstr += "    <commonHeader xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/mpva/NaManMeritFamInfo/types\">";
        xmlstr += "      <serviceName>NaManMeritFamInfoService</serviceName>";
        xmlstr += "      <useSystemCode>ESBTEST</useSystemCode>";
        xmlstr += "      <certServerId>SVR1311000030</certServerId>";
        xmlstr += "      <transactionUniqueId>2018012217473952889982181</transactionUniqueId>";
        xmlstr += "      <userDeptCode></userDeptCode>";
        xmlstr += "      <userName>***</userName>";
        xmlstr += "    </commonHeader>";
        xmlstr += "  </soap:Header>";
        xmlstr += "  <soap:Body>";
        xmlstr += "    <getNaManMeritFamInfoResponse xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/mpva/NaManMeritFamInfo/types\">";
        xmlstr += "      <cnt>1</cnt>";
        xmlstr += "      <meritFamInfoBody>";
        xmlstr += "        <resName>홍길동</resName>";
        xmlstr += "        <resSecrNum>7102091234567</resSecrNum>";
        xmlstr += "        <relCd>A</relCd>";
        xmlstr += "        <authoriPsnYn>Y</authoriPsnYn>";
        xmlstr += "        <subjKbnCd>00029</subjKbnCd>";
        xmlstr += "        <wondClassCd>00020</wondClassCd>";
        xmlstr += "        <inquRsltCd>R</inquRsltCd>";
        xmlstr += "        <validDate>20240904125701</validDate>";
        xmlstr += "      </meritFamInfoBody>";
        xmlstr += "    </getNaManMeritFamInfoResponse>";
        xmlstr += "  </soap:Body>";
        xmlstr += "</soap:Envelope>";
        this.bodyFields = Arrays.asList(
            new FieldVo("cnt", null)
            , new FieldVo("resName", "meritFamInfoBody")
            , new FieldVo("resSecrNum", "meritFamInfoBody")
            , new FieldVo("relCd", "meritFamInfoBody")
            , new FieldVo("authoriPsnYn", "meritFamInfoBody")
            , new FieldVo("subjKbnCd", "meritFamInfoBody")
            , new FieldVo("wondClassCd", "meritFamInfoBody")
            , new FieldVo("inquRsltCd", "meritFamInfoBody")
            , new FieldVo("validDate", "meritFamInfoBody")
            );
        break;
      case 2:
        xmlstr = "";
        xmlstr += "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
        xmlstr += "  <soap:Header>";
        xmlstr += "    <commonHeader xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/nts/NtsGoodTaxpayInfo/types\">";
        xmlstr += "      <serviceName>NtsGoodTaxpayInfoService</serviceName>";
        xmlstr += "      <useSystemCode>ESBTEST</useSystemCode>";
        xmlstr += "      <certServerId>SVR1311000030</certServerId>";
        xmlstr += "      <transactionUniqueId>2018012216572071707533133</transactionUniqueId>";
        xmlstr += "      <userDeptCode>1380000</userDeptCode>";
        xmlstr += "      <userName>홍길동</userName>";
        xmlstr += "    </commonHeader>";
        xmlstr += "  </soap:Header>";
        xmlstr += "  <soap:Body>";
        xmlstr += "    <getNtsGoodTaxpayInfoResponse xmlns=\"http://ccais.mopas.go.kr/dh/nrw/infoservice/nts/NtsGoodTaxpayInfo/types\">";
        xmlstr += "      <trtRsltCd>14</trtRsltCd>";
        xmlstr += "      <trtRsltCntn>******************</trtRsltCntn>";
        xmlstr += "      <mdlTxprYn />";
        xmlstr += "    </getNtsGoodTaxpayInfoResponse>";
        xmlstr += "  </soap:Body>";
        xmlstr += "</soap:Envelope>";
        this.bodyFields = Arrays.asList(
            new FieldVo("trtRsltCd", null)
            , new FieldVo("trtRsltCntn", null)
            , new FieldVo("mdlTxprYn", null)
            );
        break;
      case 3:
        xmlstr = "";
        xmlstr += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        xmlstr += "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
        xmlstr += "  <soap:Header>";
        xmlstr += "    <commonHeader xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/swsdn/ReductionBscLivYn/types\">";
        xmlstr += "      <serviceName>ReductionBscLivYnService</serviceName>";
        xmlstr += "      <useSystemCode>ESBTEST</useSystemCode>";
        xmlstr += "      <certServerId>SVR1311000030</certServerId>";
        xmlstr += "      <transactionUniqueId>2010111020382700773722611</transactionUniqueId>";
        xmlstr += "      <userDeptCode>1234567</userDeptCode>";
        xmlstr += "      <userName>김공무</userName>";
        xmlstr += "    </commonHeader>";
        xmlstr += "  </soap:Header>";
        xmlstr += "  <soap:Body>";
        xmlstr += "    <getReductionBscLivYnResponse xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/swsdn/ReductionBscLivYn/types\">";
        xmlstr += "      <TGTR_NM>이름</TGTR_NM>";
        xmlstr += "      <TGTR_RRN>주민번호</TGTR_RRN>";
        xmlstr += "      <FCT_YN>사실여부</FCT_YN>";
        xmlstr += "      <BSLF01_YN>생계급여사실여부</BSLF01_YN>";
        xmlstr += "      <BSLF02_YN>의료급여사실여부</BSLF02_YN>";
        xmlstr += "      <BSLF03_YN>주거급여사실여부</BSLF03_YN>";
        xmlstr += "      <BSLF04_YN>교육급여사실여부</BSLF04_YN>";
        xmlstr += "    </getReductionBscLivYnResponse>";
        xmlstr += "  </soap:Body>";
        xmlstr += "</soap:Envelope>";
        this.bodyFields = Arrays.asList(
            new FieldVo("TGTR_NM", null)
            , new FieldVo("TGTR_RRN", null)
            , new FieldVo("FCT_YN", null)
            , new FieldVo("BSLF01_YN", null)
            , new FieldVo("BSLF02_YN", null)
            , new FieldVo("BSLF03_YN", null)
            , new FieldVo("BSLF04_YN", null)
            );
        break;
      case 4:
        xmlstr = "";
        xmlstr += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        xmlstr += "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
        xmlstr += "  <soap:Header>";
        xmlstr += "    <commonHeader xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/ctr/ReductionFarmMngYn/types\">";
        xmlstr += "      <serviceName>ReductionFarmMngYnService</serviceName>";
        xmlstr += "      <useSystemCode>ESBTEST</useSystemCode>";
        xmlstr += "      <certServerId>SVR1311000030</certServerId>";
        xmlstr += "      <transactionUniqueId>2010111020382700773722611</transactionUniqueId>";
        xmlstr += "      <userDeptCode>1234567</userDeptCode>";
        xmlstr += "      <userName>김공무</userName>";
        xmlstr += "    </commonHeader>";
        xmlstr += "  </soap:Header>";
        xmlstr += "  <soap:Body>";
        xmlstr += "    <getReductionFarmMngYnResponse xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/ctr/ReductionFarmMngYn/types\">";
        xmlstr += "      <AGRM_NM>경영주(농업인)이름</AGRM_NM>";
        xmlstr += "      <BZM_REL_DVCD>경영주와의관계</BZM_REL_DVCD>";
        xmlstr += "      <USE_YN>사용여부</USE_YN>";
        xmlstr += "      <REG_DT>등록일자</REG_DT>";
        xmlstr += "      <ADDR>주소</ADDR>";
        xmlstr += "      <DataList>";
        xmlstr += "        <AGRM_NM>경영주(농업인)이름</AGRM_NM>";
        xmlstr += "        <BZM_REL_DVCD>경영주와의관계</BZM_REL_DVCD>";
        xmlstr += "      </DataList>";
        xmlstr += "      <DataList>";
        xmlstr += "        <AGRM_NM>경영주(농업인)이름</AGRM_NM>";
        xmlstr += "        <BZM_REL_DVCD>경영주와의관계</BZM_REL_DVCD>";
        xmlstr += "      </DataList>";
        xmlstr += "      <DataList>";
        xmlstr += "        <AGRM_NM>경영주(농업인)이름</AGRM_NM>";
        xmlstr += "        <BZM_REL_DVCD>경영주와의관계</BZM_REL_DVCD>";
        xmlstr += "      </DataList>";
        xmlstr += "    </getReductionFarmMngYnResponse>";
        xmlstr += "  </soap:Body>";
        xmlstr += "</soap:Envelope>";
        this.bodyFields = Arrays.asList(
            new FieldVo("AGRM_NM", null)
            , new FieldVo("BZM_REL_DVCD", null)
            , new FieldVo("USE_YN", null)
            , new FieldVo("REG_DT", null)
            , new FieldVo("ADDR", null)
            , new FieldVo("AGRM_NM", "DataList")
            , new FieldVo("BZM_REL_DVCD", "DataList")
            );
        break;
      }
      this.xmlstr = xmlstr;
    }
    
    public String getXmlStr() {
      return this.xmlstr;
    }
    
    public List<FieldVo> getHeaderFields() {
      return this.headerFields;
    }
    
    public List<FieldVo> getBodyFields() {
      return this.bodyFields;
    }
  }
  
  
  public static void extractPath(Node node, String currPath, XPath xpath, List<String> pathList) {
    String nodeName = node.getNodeName();
    if (node.getNodeType() == Node.ELEMENT_NODE) {
      currPath += "/" + nodeName;
    }
    NodeList children = null;
    try {
      children = (NodeList) xpath.evaluate("*", node, XPathConstants.NODESET);
    } catch (XPathExpressionException e) {
      e.printStackTrace();
    }
    
    if (children == null) {
      return;
    }
    
    if (children.getLength() == 0 && node.getTextContent().trim().length() > 0) {
      pathList.add(currPath);
    } else {
      int len = children.getLength();
      for (int i=0; i<len; i++) {
        extractPath(children.item(i), currPath, xpath, pathList);
      }
    }
  }
  
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
  
  public static Map<String, Object> parseXml(SampleXml.TestXmlString xml) {
    Map<String, Object> resultMap = new HashMap<>();
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
      resultMap.put("header", map);
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
      resultMap.put("body", map);
    }
    
    return resultMap;
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
      Map<String, Object> resultMap = parseXml(xml);
      //System.out.println(resultMap);
      map.merge(time, 1, Integer::sum);
      if (map.size() > phase + 1) {
        System.out.println("Phase: " + phase + ", Throughput: " + map.get(phase));
        phase++;
      }
    }
  }
}
