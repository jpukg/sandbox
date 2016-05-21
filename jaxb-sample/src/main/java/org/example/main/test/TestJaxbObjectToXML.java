package org.example.main.test;

import org.example.model.Response;
import org.example.utils.DOMUtility;

public class TestJaxbObjectToXML {

  public static void main(String[] args) throws Exception {
    Response response = new Response();
    response.setReturnUrl("https://www.google.com");
    System.out.println(DOMUtility.convertObjectToXML(response));
  }

}
