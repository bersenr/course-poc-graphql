package com.federated.course.resolver;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.federation.EntityMapping;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.federated.course.entity.Course;
import com.federated.course.exception.CourseNotFoundException;
import com.federated.course.model.Student;
import com.federated.course.service.CourseService;

@Controller
public class CourseResolver {

	@Autowired
	private CourseService courseService;

	/**
	 * Handles GraphQL mutation to add a new course.
	 *
	 * @param name        The name of the course.
	 * @param description A brief description of the course.
	 * @return The newly created Course object.
	 */
	@MutationMapping
	public Course addCourse(@Argument String name, @Argument String description) {
		Course course = new Course();
		course.setName(name);
		course.setDescription(description);
		return courseService.addCourse(course);
	}

	/**
	 * Handles GraphQL query to find a course by its ID.
	 *
	 * @param id The ID of the course.
	 * @return The Course object if found.
	 * @throws CourseNotFoundException if no course is found with the given ID.
	 */
	@QueryMapping
	public Course findCourse(@Argument Long id) {
		return courseService.findCourse(id)
				.orElseThrow(() -> new CourseNotFoundException("Course with ID " + id + " not found"));
	}

	/**
	 * Handles GraphQL query to fetch all available courses.
	 *
	 * @return A list of all courses.
	 */
	@QueryMapping
	public List<Course> courses() {
		return courseService.courses();
	}

	/**
	 * Maps a Student entity to the Student schema. Used in a federated GraphQL
	 * setup for resolving student-related queries.
	 *
	 * @param id The ID of the student.
	 * @return A Student object with the given ID.
	 */
	@EntityMapping
	public Student student(@Argument Long id) {
		return new Student(id);
	}

	/**
	 * Resolves the list of courses for a given student in a federated GraphQL
	 * implementation.
	 *
	 * @param student The student whose courses are to be fetched.
	 * @return A list of courses associated with the student.
	 */
	@SchemaMapping
	public List<Course> courseList(Student student) {
		return courseService.getCourseList(student);
	}
}
