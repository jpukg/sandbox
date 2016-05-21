package org.example.main.test;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.example.jaxb.Request;
import org.example.jaxb.Request.Addendum;
import org.w3c.dom.Document;

public class TestJaxbObjectToXML1 {

	public static void main(String[] args) throws Exception {
		
		// Create the JAXBContext
		JAXBContext jc = JAXBContext.newInstance(Request.class);

		// Create the Object
		Request request = new Request();
		Addendum Addendum = new Addendum();
		Addendum.setKey("key");
		Addendum.setType("key");
		Addendum.setName1("john");

		Addendum Addendum1 = new Addendum();
		Addendum1.setKey("person");
		Addendum1.setType("person1");
		Addendum1.setName("john1");

		request.getAddendum().add(Addendum1);
		request.getAddendum().add(Addendum);

		// Create the Document
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.newDocument();

		// Marshal the Object to a Document
		Marshaller marshaller = jc.createMarshaller();
		marshaller.marshal(request, document);

		// Output the Document
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t = tf.newTransformer();
		DOMSource source = new DOMSource(document);
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		t.transform(source, result);

		System.out.println(writer.toString());

	}

}