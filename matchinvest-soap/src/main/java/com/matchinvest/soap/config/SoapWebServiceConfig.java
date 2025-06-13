package com.matchinvest.soap.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.*;

@EnableWs
@Configuration
public class SoapWebServiceConfig {

  @Bean
  public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext ctx) {
    MessageDispatcherServlet servlet = new MessageDispatcherServlet();
    servlet.setApplicationContext(ctx);
    servlet.setTransformWsdlLocations(true);
    return new ServletRegistrationBean<>(servlet, "/soap/*");
  }

  @Bean(name = "certification")
  public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema certSchema) {
    DefaultWsdl11Definition def = new DefaultWsdl11Definition();
    def.setPortTypeName("CertificationPort");
    def.setLocationUri("/soap");
    def.setTargetNamespace("http://matchinvest.com/soap/certification");
    def.setSchema(certSchema);
    return def;
  }

  @Bean
  public XsdSchema certificationSchema() {
    return new SimpleXsdSchema(new ClassPathResource("xsd/certification.xsd"));
  }
}
