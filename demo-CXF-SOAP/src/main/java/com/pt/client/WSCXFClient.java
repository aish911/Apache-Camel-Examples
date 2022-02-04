/**
 * 
 */
package com.pt.client;

/**
 * @author Dell
 *
 */

import java.io.StringReader;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.Service.Mode;

import org.apache.cxf.staxutils.StaxUtils;

public class WSCXFClient {

	/**
	 * @param args
	 */
	 public static void main(String[] args) throws Exception {
	        String address = "http://localhost:8080/services/Hello";
	        String request = "<q0:sayHello xmlns:q0=\"http://com.pt.service/\"><myname>Elan</myname></q0:sayHello>";

	        StreamSource source = new StreamSource(new StringReader(request));
	        Service service = Service.create(new URL(address + "?wsdl"), 
	                                         new QName("http://com.pt.service/" , "HelloService"));
	        Dispatch<Source> disp = service.createDispatch(new QName("http://com.pt.service/" , "HelloPort"),
	                                                       Source.class, Mode.PAYLOAD);
	        
	        Source result = disp.invoke(source);
	        String resultAsString = StaxUtils.toString(result);
	        System.out.println(resultAsString);
	       
	    }

}
