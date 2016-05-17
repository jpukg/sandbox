package example.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XpathTest {

	public static void main(String[] args) throws Exception {

		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader("<root><node1>22</node1></root>"));

		Document doc = db.parse(is);

		// Evaluate XPath against Document itself
		XPath xPath = XPathFactory.newInstance().newXPath();
		NodeList nodes = (NodeList) xPath.evaluate("/root/node1", doc.getDocumentElement(), XPathConstants.NODESET);

		nodes.item(0).getFirstChild().setTextContent("<jj><ss>");

		for (int i = 0; i < nodes.getLength(); ++i) {
			Element e = (Element) nodes.item(i);
			System.out.println(e.getTextContent());
		}

		System.out.println(toString(doc, false, true));

		String xml = "<foo>bar</foo>";
		Node node = parse(xml);

		System.out.println(toString(node, false, false));

	}

	public static String toString(Node subject, boolean omitXmlDeclaration, boolean indent) throws Exception {
		Transformer t = TransformerFactory.newInstance().newTransformer();
		t.setOutputProperty(OutputKeys.INDENT, indent ? "yes" : "no");
		t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, omitXmlDeclaration ? "yes" : "no");

		StringWriter writer = new StringWriter();
		t.transform(new DOMSource(subject), new StreamResult(writer));
		return writer.toString();
	}

	public static Document parse(String subject) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		return factory.newDocumentBuilder().parse(new InputSource(new StringReader(subject)));
	}

}
