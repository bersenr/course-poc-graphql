package com.federated.course.exception;

import graphql.ErrorClassification;

/**
 * Enum representing custom GraphQL error classifications. These errors are used
 * to categorize specific failures in the GraphQL API.
 */
public enum GraphQLCustomError implements ErrorClassification {
	/** Represents an unexpected internal server error. */
	INTERNAL_SERVER_ERROR,

	/** Error thrown when a requested course is not found. */
	COURSE_NOT_FOUND_ERROR,

	/** Error thrown when a requested student is not found. */
	STUDENT_NOT_FOUND_ERROR
}
