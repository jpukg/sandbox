package org.example.main.test;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class TestCombineTwoDocumentObject {

	public static void main(String[] args) throws Exception {
		DocumentBuilderFactory dbFactory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.newDocument();
		Element el = doc.createElement("test");
		doc.appendChild(el);

		String xml = "<foo bar=\"1\">Hi <baz>there</baz></foo>";
		Document doc2 = dBuilder.parse(new ByteArrayInputStream(xml.getBytes()));

		Node node = doc.importNode(doc2.getDocumentElement(), true);
		el.appendChild(node);
		
		System.out.println(toString(doc, true, true));

	}

	public static String toString(Node subject, boolean omitXmlDeclaration, boolean indent) throws Exception {
		Transformer t = TransformerFactory.newInstance().newTransformer();
		t.setOutputProperty(OutputKeys.INDENT, indent ? "yes" : "no");
		t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, omitXmlDeclaration ? "yes" : "no");

		StringWriter writer = new StringWriter();
		t.transform(new DOMSource(subject), new StreamResult(writer));
		return writer.toString();
	}
}
