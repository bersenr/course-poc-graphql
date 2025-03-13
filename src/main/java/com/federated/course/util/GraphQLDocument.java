package com.federated.course.util;

/**
 * Enum representing available GraphQL query documents. This provides a
 * type-safe way to reference GraphQL files.
 */
public enum GraphQLDocument {

	FIND_STUDENT_GQL("findStudent.gql"); // GraphQL query file for finding a student

	private final String filename;

	GraphQLDocument(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return filename;
	}
}
