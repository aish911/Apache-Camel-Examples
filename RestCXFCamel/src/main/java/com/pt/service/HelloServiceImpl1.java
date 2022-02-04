package com.pt.service;

import javax.ws.rs.Path;

@Path("/sayHello1")
public class HelloServiceImpl1 implements HelloService {

	@Override
	public String sayHello(String a) {
		// TODO Auto-generated method stub
		return "Hello1 "+ a +"Springboot cxf";
	}

}
