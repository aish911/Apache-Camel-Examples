package com.in28minutes.microservices.camel.microservicea.routes.patterns;

import java.util.ArrayList;
import java.util.Map;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangeProperties;
import org.apache.camel.Headers;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.in28minutes.microservices.camel.microservicea.CurrencyExchange;
import com.sun.tools.javac.util.List;

@Component
public class EipPatternsRouter extends RouteBuilder{

	// pipeline pattern is default
	//using multicast we can have multiple endpoints.
	//split pattern
	// aggregate integration pattern
	//routing slip pattern

	@Autowired
	SplitterComponent splitter;
	
	@Autowired
	DynamicRouterBean dynamicRouterBean;
	
	@Override
	public void configure() throws Exception {
		
		/*from("timer:multicast?period=10000")
		.multicast()
		.to("log:something1", "log:something2", "log:something3");*/
		
		/*from("file:files/csv")
		.unmarshal()
		.csv()
		.split(body())
		.to("activemq:split-queue");*/
		
		//Message,Message2,Message3  => send every message as separate msgs.
		/*from("file:files/csv")
		.convertBodyTo(String.class)
		//.split(body(),",")
		.split(method(splitter))
		.to("activemq:split-queue");*/
		
		//Aggregate
		//Messages => Aggregate => Endpoint
		//to , 3 messages at a time
		from("file:files/aggregate")
		.unmarshal().json(JsonLibrary.Jackson, CurrencyExchange.class)
		.aggregate(simple("${body.to}"),new ArrayListAggregationStrategy())
		.completionSize(3)
		//.completionTimeout(HIGHEST)
		.to("log:aggregate-json");
		
		String routingSlip = "direct:endpoint1,direct:endpoint2,direct:endpoint3";
		//routing slip
		/*from("timer:routingSlip?period=10000")
		.transform().constant("My message is Hardcoded")
		.routingSlip(simple(routingSlip));*/
		//.to("log:something1", "log:something2", "log:something3");
		
		
		//Dynamic routing pattern
		from("timer:dynamicRouting?period=10000")
		.transform().constant("My message is hardcoded")
		.dynamicRouter(method(dynamicRouterBean));
		
		from("direct:endpoint1")
		.to("log:directendpoint1");
		
		from("direct:endpoint2")
		.to("log:directendpoint2");
		
		from("direct:endpoint3")
		.to("log:directendpoint3");
		
	}

}

@Component
class SplitterComponent{
	public List<String> splitInput(String body){
		return List.of("ABC","DEF","GHI","JKL");
	}
}

@Component
class DynamicRouterBean {
	
	Logger logger = LoggerFactory.getLogger(DynamicRouterBean.class);
	int invocations = 0;
	public String decideTheNectEndpoint(@ExchangeProperties Map<String, String> properties,
			@Headers Map<String, String> headers,
			@Body String body) {
		
		logger.info("DynamicRouterBean {} {} {}", properties, headers, body);
		invocations++;
		
		if(invocations%3==0)
			return "direct:endpoint1";
		if(invocations%3==1)
			return "direct:endpoint2,direct:endpoint3";
		
		return null;
		
	}
}

class ArrayListAggregationStrategy implements AggregationStrategy {

	//1,2,3
	//null,1
	//result,2
	//result,3
	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		Object newBody = newExchange.getIn().getBody();
        ArrayList<Object> list = null;
        if (oldExchange == null) {
            list = new ArrayList<Object>();
            list.add(newBody);
            newExchange.getIn().setBody(list);
            return newExchange;
        } else {
            list = oldExchange.getIn().getBody(ArrayList.class);
            list.add(newBody);
            return oldExchange;
        }
	}

}
