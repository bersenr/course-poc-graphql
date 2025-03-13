package com.federated.course.config;

import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.data.federation.FederationSchemaFactory;

/**
 * Configuration class for enabling GraphQL federation support.
 * 
 * @see <a href=
 *      "https://docs.spring.io/spring-graphql/reference/federation.html">
 *      Spring GraphQL Federation Documentation</a>
 */
@Configuration
public class FederationConfig {

	@Bean
	public GraphQlSourceBuilderCustomizer customizer(FederationSchemaFactory factory) {
		return builder -> builder.schemaFactory(factory::createGraphQLSchema);
	}

	@Bean
	public FederationSchemaFactory schemaFactory() {
		return new FederationSchemaFactory();
	}
}
