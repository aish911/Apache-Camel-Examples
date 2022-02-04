/**
 * 
 */
package com.pt.application;

import org.springframework.context.annotation.Configuration;

/**
 * @author Dell
 *
 */

import javax.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.metrics.MetricsFeature;
import org.apache.cxf.metrics.MetricsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.pt.service.HelloPortImpl;

@Configuration
public class WebServiceConfig {

    @Autowired
    private Bus bus;

    @Autowired
    private MetricsProvider metricsProvider;

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, new HelloPortImpl(), null, null, new MetricsFeature[]{
            new MetricsFeature(metricsProvider)
        });
        endpoint.publish("/Hello");
        return endpoint;
    }
}