package com.federated.course.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class CourseController {

	/**
	 * Health check endpoint for the Course Service. This endpoint can be used by
	 * monitoring tools to verify if the service is running.
	 *
	 * @return HTTP 200 OK with "OK" if the service is healthy, or HTTP 500 Internal
	 *         Server Error with "Unhealthy" if an exception occurs.
	 */
	@GetMapping("/health")
	public ResponseEntity<String> healthCheck() {
		try {
			log.info("Health check OK for Course Service");
			return ResponseEntity.ok("OK");
		} catch (Exception e) {
			log.error("Health check failed!", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unhealthy");
		}
	}
}
