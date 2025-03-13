package com.federated.course.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.federated.course.entity.Course;
import com.federated.course.exception.CourseNotFoundException;
import com.federated.course.exception.StudentNotFoundException;
import com.federated.course.model.Student;
import com.federated.course.model.StudentQueryResponse;
import com.federated.course.repo.CourseRepo;
import com.federated.course.util.GraphQLDocument;
import com.federated.course.util.GraphQLLoader;

import io.dapr.client.DaprClient;
import io.dapr.client.domain.HttpExtension;
import io.dapr.utils.TypeRef;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CourseService {

	@Autowired
	private CourseRepo courseRepo;

	@Autowired
	private DaprClient daprClient;

	/**
	 * Adds a new course to the repository.
	 *
	 * @param course The course to be added.
	 * @return The saved Course object.
	 */
	public Course addCourse(Course course) {
		return courseRepo.save(course);
	}

	/**
	 * Finds a course by its ID.
	 *
	 * @param id The ID of the course.
	 * @return An {@code Optional} containing the Course if found, otherwise empty.
	 */
	public Optional<Course> findCourse(Long id) {
		return courseRepo.findById(id);
	}

	/**
	 * Retrieves all courses from the repository.
	 *
	 * @return A list of all available courses.
	 */
	public List<Course> courses() {
		return courseRepo.findAll();
	}

	/**
	 * Retrieves the list of courses for a given student by querying the student
	 * service via GraphQL.
	 *
	 * @param student The student whose enrolled courses need to be fetched.
	 * @return A list of courses associated with the student.
	 * @throws StudentNotFoundException if the student is not found in the student
	 *                                  service.
	 * @throws CourseNotFoundException  if any of the courses linked to the student
	 *                                  are not found.
	 */
	public List<Course> getCourseList(Student student) {
		Long studentId = student.getId();

		// Build GraphQL request to fetch student details
		Map<String, Object> request = GraphQLLoader.buildGraphQLRequest(GraphQLDocument.FIND_STUDENT_GQL.getFilename(),
				Map.of("id", studentId));

		// Call the student service to verify if the student exists
		StudentQueryResponse response = daprClient.invokeMethod("student-service", "graphql", request,
				HttpExtension.POST, Map.of("Content-Type", "application/json"), new TypeRef<StudentQueryResponse>() {
				}).block();

		if (response == null || response.getData() == null) {
			log.error("Student not found: {}", response);
			throw new StudentNotFoundException("Student with id " + studentId + " not found");
		}

		// Retrieve the list of course IDs from the GraphQL response and map each ID to
		// a Course entity.
		List<Course> courses = response.getData().getFindStudent().getCourseIds().stream()
				.map(id -> courseRepo.findById(id)
						.orElseThrow(() -> new CourseNotFoundException("Course with id " + id + " not found")))
				.toList();

		return courses;
	}
}
