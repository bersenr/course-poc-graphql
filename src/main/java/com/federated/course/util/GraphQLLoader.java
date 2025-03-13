package com.federated.course.util;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;

public class GraphQLLoader {

	/**
	 * Builds a GraphQL request by loading a query from a file and attaching
	 * variables.
	 *
	 * @param fileName  The name of the GraphQL query file located in the
	 *                  {@code graphql-documents} directory.
	 * @param variables A map of variables to be included in the query. Can be
	 *                  {@code null}.
	 * @return A map containing the GraphQL query and its variables.
	 * @throws RuntimeException If the query file cannot be read.
	 */
	public static Map<String, Object> buildGraphQLRequest(String fileName, Map<String, Object> variables) {
		try {
			ClassPathResource resource = new ClassPathResource("graphql-documents/" + fileName);
			String query = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

			return Map.of("query", query, "variables", variables != null ? variables : Map.of());

		} catch (Exception e) {
			throw new RuntimeException("Failed to build GraphQL request", e);
		}
	}
}
