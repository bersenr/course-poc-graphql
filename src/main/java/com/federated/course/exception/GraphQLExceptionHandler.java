package com.federated.course.exception;

import java.util.Map;

import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {

	/**
	 * Handles exceptions thrown during GraphQL query execution. This method maps
	 * specific exceptions to custom GraphQL errors for better error handling.
	 *
	 * @param ex  The exception that was thrown.
	 * @param env The GraphQL environment providing details about the query
	 *            execution.
	 * @return A formatted GraphQL error with appropriate status and metadata.
	 * @see <a href="https://www.baeldung.com/spring-graphql-error-handling"> Spring
	 *      GraphQL Error Handling - Baeldung</a>
	 */
	@Override
	protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
		if (ex instanceof CourseNotFoundException) {
			log.error("Course not found: {}", ex.getMessage(), ex);
			return GraphqlErrorBuilder.newError().errorType(GraphQLCustomError.COURSE_NOT_FOUND_ERROR)
					.message(ex.getMessage()).extensions(Map.of("status", 404))
					.path(env.getExecutionStepInfo().getPath()).location(env.getField().getSourceLocation()).build();
		} else if (ex instanceof StudentNotFoundException) {
			log.error("Student not found: {}", ex.getMessage(), ex);
			return GraphqlErrorBuilder.newError().errorType(GraphQLCustomError.STUDENT_NOT_FOUND_ERROR)
					.message(ex.getMessage()).extensions(Map.of("status", 404))
					.path(env.getExecutionStepInfo().getPath()).location(env.getField().getSourceLocation()).build();
		} else {
			log.error("Unexpected error: {}", ex.getMessage(), ex);
			return GraphqlErrorBuilder.newError().errorType(GraphQLCustomError.INTERNAL_SERVER_ERROR)
					.message("An unexpected error occurred").extensions(Map.of("status", 500))
					.path(env.getExecutionStepInfo().getPath()).location(env.getField().getSourceLocation()).build();
		}
	}
}