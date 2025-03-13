package com.federated.course.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a Student entity used in a federated GraphQL schema. This model
 * links students with their associated courses.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

	private Long id;
	private String name;
	private List<Long> courseIds;

	/**
	 * Constructor to create a Student entity with only an ID.
	 *
	 * @param id The unique identifier of the student.
	 */
	public Student(Long id) {
		this.id = id;
	}
}
