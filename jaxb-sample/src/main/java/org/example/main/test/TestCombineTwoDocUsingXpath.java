package org.example.main.test;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.example.utils.DOMUtility;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TestCombineTwoDocUsingXpath {

  static String inputXml ="<employee><name>Raj</name><location></location></employee>";

  public static void main(String[] args) throws Exception {

    Document document = DOMUtility.stringToDom(inputXml);
    
    setTextValue(document, "/employee/location", "<data><latitude>10</latitude><longitude>11</longitude></data>");

    System.out.println(DOMUtility.domToString(document));

  }

  public static void setTextValue(Document document, String pXPathQuery, String xml) throws Exception {
    XPath aXPath = XPathFactory.newInstance().newXPath();
    NodeList aNodeList = null;
    try {
      XPathExpression aXPathExpression = aXPath.compile(pXPathQuery);
      aNodeList = (NodeList) aXPathExpression.evaluate(document, XPathConstants.NODESET);

      Document newXmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes()));
      for (int i = 0; i < aNodeList.getLength(); i++) {
        Node node = aNodeList.item(i);
        Node copyNode = document.importNode(newXmlDocument.getDocumentElement(), true);
        node.appendChild(copyNode);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
