package com.fortiumtech.scottdavies;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.security.xwss.callback.SpringPlainTextPasswordValidationCallbackHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@EnableWebSecurity
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

	@Lazy
	@Autowired
	private AuthenticationManager authenticationManager;

	@Bean
	public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(
			ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean<>(servlet, "/ws/*");
	}

	@Bean
	public SpringPlainTextPasswordValidationCallbackHandler callbackHandler() {
		SpringPlainTextPasswordValidationCallbackHandler callbackHandler = new SpringPlainTextPasswordValidationCallbackHandler();
		try {
			callbackHandler.setAuthenticationManager(authenticationManager);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return callbackHandler;
	}

	@Bean
	public XwsSecurityInterceptor securityInterceptor() {

		XwsSecurityInterceptor securityInterceptor = new XwsSecurityInterceptor();
		securityInterceptor.setCallbackHandler(callbackHandler());
		securityInterceptor.setPolicyConfiguration(new ClassPathResource("securityPolicy.xml"));
		return securityInterceptor;
	}

	@Override
	public void addInterceptors(List<EndpointInterceptor> interceptors) {
		interceptors.add(securityInterceptor());
	}


	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user")
				.password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("password"))
				.authorities("ROLE_USER");

	}

	@Bean(name = "countries")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema countriesSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("CountriesPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://spring.io/guides/gs-producing-web-service");
		wsdl11Definition.setSchema(countriesSchema);
		return wsdl11Definition;
	}

	@Bean(name = "countriesToo")
	public DefaultWsdl11Definition defaultWsdl11DefinitionToo(XsdSchema countriesSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("CountriesPortToo");
		wsdl11Definition.setLocationUri("/ws/unsecured");
		wsdl11Definition.setTargetNamespace("http://spring.io/guides/gs-producing-web-service");
		wsdl11Definition.setSchema(countriesSchema);
		return wsdl11Definition;
	}

	@Bean
	public XsdSchema countriesSchema() {
		return new SimpleXsdSchema(new ClassPathResource("countries.xsd"));
	}
}