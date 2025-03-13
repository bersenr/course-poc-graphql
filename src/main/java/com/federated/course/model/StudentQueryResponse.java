package com.federated.course.model;

import java.util.List;

import lombok.Data;

/**
 * Represents the response structure for a GraphQL query fetching student data.
 * This class models both the retrieved data and potential GraphQL errors.
 */
@Data
public class StudentQueryResponse {
	private DataWrapper data;
	private List<GraphQLError> errors;

	/**
	 * Wrapper class for the student data returned by the GraphQL query. The
	 * variable name should match the GraphQL query response structure.
	 */
	@Data
	public static class DataWrapper {
		private Student findStudent;
	}

	/**
	 * Represents an error returned by the GraphQL API.
	 */
	@Data
	public static class GraphQLError {
		private String message;
		private List<Location> locations;
		private List<String> path;

		/**
		 * Represents the location in the GraphQL query where the error occurred.
		 */
		@Data
		public static class Location {
			private int line;
			private int column;
		}
	}
}
