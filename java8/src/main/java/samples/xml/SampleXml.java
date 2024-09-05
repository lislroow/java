package samples.xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SampleXml {
  
  public enum TestXmlString {
    XML_STR1,
    XML_STR2,
    XML_STR3,
    XML_STR4,
    XML_STR5;
    
    String xmlstr;
    
    TestXmlString() {
      String xmlstr = "";
      int ordinal = this.ordinal();
      String name = this.name();
      log.trace(String.format("[%d] %s", ordinal, name));
      switch (ordinal) {
      case 0:
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
        break;
      case 1:
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
        break;
      case 2:
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
        break;
      case 3:
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
        break;
      case 4:
        xmlstr = "";
        xmlstr += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        xmlstr += "<Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
        xmlstr += "  <Header>";
        xmlstr += "    <commonHeader xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/ctr/ReductionFarmMngYn/types\">";
        xmlstr += "      <serviceName>ReductionFarmMngYnService</serviceName>";
        xmlstr += "      <useSystemCode>ESBTEST</useSystemCode>";
        xmlstr += "      <certServerId>SVR1311000030</certServerId>";
        xmlstr += "      <transactionUniqueId>2010111020382700773722611</transactionUniqueId>";
        xmlstr += "      <userDeptCode>1234567</userDeptCode>";
        xmlstr += "      <userName>김공무</userName>";
        xmlstr += "    </commonHeader>";
        xmlstr += "  </Header>";
        xmlstr += "  <Body>";
        xmlstr += "    <getReductionFarmMngYn xmlns=\"http://ccais.mopas.go.kr/dh/rid/services/ctr/ReductionFarmMngYn/types\">";
        xmlstr += "      <jumin>주민등록번호</jumin>";
        xmlstr += "    </getReductionFarmMngYn>";
        xmlstr += "  </Body>";
        xmlstr += "</Envelope>";
        break;
      }
      this.xmlstr = xmlstr;
    }
    
    public String getXmlStr() {
      return this.xmlstr;
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
  
  public static void test1(TestXmlString xml) {
    String xmlstr = xml.getXmlStr();
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setNamespaceAware(false);
    DocumentBuilder builder = null;
    try {
      builder = factory.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    }
    Document document = null;
    try {
      document = builder.parse(new java.io.ByteArrayInputStream(xmlstr.getBytes("UTF-8")));
    } catch (SAXException | IOException e) {
      e.printStackTrace();
    }

    Element root = document.getDocumentElement();
    XPathFactory xpathFactory = XPathFactory.newInstance();
    XPath xpath = xpathFactory.newXPath();
    
    String serviceName = null;
    
    String pathstr = "";
    pathstr = "/Envelope/Header/commonHeader/*";
    NodeList headerNode = null;
    try {
      headerNode = (NodeList) xpath.compile(pathstr).evaluate(document, XPathConstants.NODESET);
    } catch (XPathExpressionException e) {
      e.printStackTrace();
    }
    int len = headerNode.getLength();
    log.debug(pathstr + " ["+len+"]");
    for (int i=0; i<len; i++) {
      Node node = headerNode.item(i);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        log.trace(node.getNodeName() + " : " + node.getTextContent());
      }
      if ("serviceName".equals(node.getNodeName())) {
        serviceName = node.getTextContent();
        serviceName = serviceName.substring(0, serviceName.lastIndexOf("Service"));
      }
    }
    
    log.info(String.format("serviceName: %s", serviceName));
    log.debug("\n---\n");
    
    pathstr = String.format("/Envelope/Body/%s%s%s/*", "get", serviceName, "Response");
    NodeList bodyNode = null;
    try {
      bodyNode = (NodeList) xpath.compile(pathstr).evaluate(document, XPathConstants.NODESET);
    } catch (XPathExpressionException e) {
      e.printStackTrace();
    }
    len = bodyNode.getLength();
    log.trace(pathstr + " ["+len+"]");
    for (int i=0; i<len; i++) {
      Node node = bodyNode.item(i);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        log.trace(node.getNodeName() + " : " + node.getTextContent());
      }
      String nodeName = node.getNodeName();
      int childrenLen = node.getChildNodes().getLength();
      boolean hasChildren = node.hasChildNodes();
      NodeList childrenNodes = node.getChildNodes();
      for (int j=0; j<childrenLen; j++) {
        if (childrenNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
          String name = childrenNodes.item(j).getNodeName();
          String text = childrenNodes.item(j).getTextContent();
          log.trace(String.format("\"%s\"=\"%s\"", name, text));
        }
      }
    }
    
    log.debug("\n---\n");
    
    xpath = xpathFactory.newXPath();
    List<String> pathList = new ArrayList<String>();
    extractPath(root, "", xpath, pathList);
    pathList.forEach(item -> log.trace(item));
    
    log.debug("\n---\n");
  }
  
  public static void test(TestXmlString xml) {
    String xmlstr = xml.getXmlStr();
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setNamespaceAware(false);
    DocumentBuilder builder = null;
    try {
      builder = factory.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    }
    Document document = null;
    try {
      document = builder.parse(new java.io.ByteArrayInputStream(xmlstr.getBytes("UTF-8")));
    } catch (SAXException | IOException e) {
      e.printStackTrace();
    }
    Element root = document.getDocumentElement();
    
    Map<String, Object> jsonMap = new HashMap<>();
    buildJsonMap(root, jsonMap);
    log.debug("jsonMap: {}", jsonMap);
    
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      String jsonstr = objectMapper.writeValueAsString(jsonMap);
      log.info("jsonstr: {}", jsonstr);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    
  }
  
  private static void buildJsonMap(Node node, Map<String, Object> currentMap) {
    NodeList children = node.getChildNodes();
    for (int i=0; i<children.getLength(); i++) {
      Node childNode = children.item(i);
      if (childNode.getNodeType() == Node.ELEMENT_NODE) {
        String name = node.getNodeName();
        // ns 제거
        if (name.indexOf(":") > 0) {
          name = name.substring(name.indexOf(":")+1, name.length());
        }
        String childName = name;
        if (currentMap.containsKey(childName)) {
          Object existingValue = currentMap.get(childName);
          if (existingValue instanceof List) {
            ((List<Object>)existingValue).add(buildChildNodeMap(childNode));
          } else {
            List<Object> newList = new ArrayList<>();
            newList.add(existingValue);
            newList.add(buildChildNodeMap(childNode));
            currentMap.put(childName, newList);
          }
        } else {
          currentMap.put(childName, buildChildNodeMap(childNode));
        }
      }
    }
  }
  
  private static Object buildChildNodeMap(Node node) {
    NodeList children = node.getChildNodes();
    if (children.getLength() == 1 && children.item(0).getNodeType() == Node.TEXT_NODE) {
      return children.item(0).getTextContent().trim();
    } else {
      Map<String, Object> childMap = new HashMap<>();
      buildJsonMap(node, childMap);
      return childMap;
    }
  }
  
  public static void main(String[] args) throws Exception {
    Arrays.asList(TestXmlString.values()).stream().forEach(item -> SampleXml.test(item));
  }
}
