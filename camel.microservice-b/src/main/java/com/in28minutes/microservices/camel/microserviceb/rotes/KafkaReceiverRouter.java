package com.in28minutes.microservices.camel.microserviceb.rotes;

import java.math.BigDecimal;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.in28minutes.microservices.camel.microserviceb.CurrencyExchange;

//@Component
public class KafkaReceiverRouter extends RouteBuilder{

	
	@Override
	public void configure() throws Exception {
		
		from("kafka:myKafkaTopic")
		.to("log:received-from-kafka");
		
	}

}
