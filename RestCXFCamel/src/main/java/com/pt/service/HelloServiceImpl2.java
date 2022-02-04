package com.pt.service;

import javax.ws.rs.Path;

@Path("/sayHello2")
public class HelloServiceImpl2 implements HelloService  {

	@Override
	public String sayHello(String a) {
		// TODO Auto-generated method stub
		return "Hello2 "+ a +"Springboot cxf";
	}

}
