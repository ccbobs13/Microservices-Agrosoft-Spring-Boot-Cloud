package sn.esmt.microservices.apigateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AgrosoftApiGatewayConfiguration {

	@Bean
	RouteLocator gatewayRouter (RouteLocatorBuilder builder) {
		return builder.routes()
				.route(
						p -> p
						.path("/get")
						.filters(f -> f
								.addRequestHeader("myHeaderParam", "HeaderParamValue")
								.addRequestParameter("Myparameter", "ParamValue"))
						.uri("http://httpbin.org:80"))
				.route(
						p -> p
						.path("/agrosoft/covid/cases/reports/**")
						.filters(f-> f.prefixPath("/tscy-etu"))
						.uri("lb://covid-case-reports-service"))
				.route(
						p -> p
						.path("/agrosoft/covid/cases/**")
						.filters(f-> f.prefixPath("/tscy-etu"))
						.uri("lb://covid-case-registration-service"))
				
				.build();
	}
}
