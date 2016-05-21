package org.example.utils;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.ServiceException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DOMUtility {
  private DOMUtility() {

  }

  public static Document createEmptyDocument() throws Exception {
    Document aDocument = null;
    try {
      aDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
    } catch (ParserConfigurationException e) {
    }
    return aDocument;
  }

  public static <T> String convertObjectToXML(T object) {
    try {
      StringWriter stringWriter = new StringWriter();
      JAXBContext context = JAXBContext.newInstance(object.getClass());
      Marshaller marshaller = context.createMarshaller();
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
      marshaller.marshal(object, stringWriter);
      return stringWriter.toString();
    } catch (JAXBException e) {
      System.err.println(String.format("Exception while marshalling: %s", e.getMessage()));
    }
    return null;
  }

  public static String domToString(Document pDocument) throws Exception {
    DOMSource aDOMSource = new DOMSource(pDocument);
    StringWriter aStringWriter = new StringWriter();
    TransformerFactory aTransformerFactory = TransformerFactory.newInstance();
    try {
      Transformer aTransformer = aTransformerFactory.newTransformer();
      aTransformer.setOutputProperty("omit-xml-declaration", "yes");
      aTransformer.transform(aDOMSource, new StreamResult(aStringWriter));
    } catch (TransformerConfigurationException e) {
    } catch (TransformerException e) {
    }
    String returnString = aStringWriter.toString();
    return returnString;
  }

  public static void setTextNodeValue(Object pObj, String pXPathString, String pValue) throws Exception {
    XPath aXPath = XPathFactory.newInstance().newXPath();
    NodeList aNodeList = null;
    try {
      XPathExpression aXPathExpression = aXPath.compile(pXPathString);
      aNodeList = (NodeList) aXPathExpression.evaluate(pObj, XPathConstants.NODESET);
      aNodeList.item(0).getFirstChild().setNodeValue(pValue);
    } catch (XPathExpressionException e) {
    }
  }

  /**
   * 
   * @param pObj
   * @param pXPathString
   * @return
   * @throws ServiceException
   * @throws Exception
   */
  public static String findTextNodeValue(Object pObj, String pXPathString) throws ServiceException {
    XPath aXPath = XPathFactory.newInstance().newXPath();
    NodeList aNodeList = null;
    String aTextValue = null;
    try {
      XPathExpression aXPathExpression = aXPath.compile(pXPathString);
      aNodeList = (NodeList) aXPathExpression.evaluate(pObj, XPathConstants.NODESET);

      if (aNodeList.getLength() != 0) {
        if (!(aNodeList.item(0).hasChildNodes())) {
          return null;
        }
        if (aNodeList.item(0).getFirstChild().getNodeValue() == null) {
          aTextValue = null;
        } else {
          aTextValue = aNodeList.item(0).getFirstChild().getNodeValue().trim();
        }
      }
    } catch (XPathExpressionException e) {
      throw new ServiceException("XPath exception occured ", e);
    }
    return aTextValue;
  }

  /**
   * 
   * @param pXmlSource
   * @return
   * @throws Exception
   */
  public static Document stringToDom(String pXmlSource) {
    DocumentBuilderFactory aDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
    pXmlSource.replace("&", "&amp;");

    aDocumentBuilderFactory.setNamespaceAware(false);
    DocumentBuilder aDocumentBuilder = null;
    Document aDocument = null;
    try {
      aDocumentBuilder = aDocumentBuilderFactory.newDocumentBuilder();
      aDocument = aDocumentBuilder.parse(new InputSource(new StringReader(pXmlSource)));
      Reader reader = new StringReader(pXmlSource);
      InputSource inputSource = new InputSource(reader);
      inputSource.setEncoding("UTF-8");
      aDocument = aDocumentBuilder.parse(inputSource);
    } catch (SAXException e) {
    } catch (IOException e) {
    } catch (ParserConfigurationException e) {
    }
    return aDocument;
  }

  public static String findNodeString(Object pObj, String pXPathString) throws Exception {
    XPath aXPath = XPathFactory.newInstance().newXPath();
    NodeList aNodeList = null;
    String aNodeString = null;
    try {
      XPathExpression aXPathExpression = aXPath.compile(pXPathString);
      aNodeList = (NodeList) aXPathExpression.evaluate(pObj, XPathConstants.NODESET);
      if (aNodeList != null)
        aNodeString = nodeToString(aNodeList.item(0));
    } catch (XPathExpressionException e) {
    }
    return aNodeString;
  }

  public static String nodeToString(Node pNode) throws Exception {
    DOMSource aDOMSource = new DOMSource(pNode);
    StringWriter aStringWriter = new StringWriter();
    TransformerFactory aTransformerFactory = TransformerFactory.newInstance();
    try {
      Transformer aTransformer = aTransformerFactory.newTransformer();
      aTransformer.setOutputProperty("omit-xml-declaration", "yes");
      aTransformer.transform(aDOMSource, new StreamResult(aStringWriter));
    } catch (TransformerConfigurationException e) {
    } catch (TransformerException e) {
    }
    String returnString = aStringWriter.toString();
    return returnString;
  }
}
