package com.ibm.rms.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibm.rms.service.*;
import com.ibm.rms.exception.*;
import com.ibm.rms.model.*;


@RestController
@RequestMapping("/jobs")
public class JobController {

	@Autowired
	JobService jobService;
	
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin("*")
	public List<Job> getAllEmployees() {

		return jobService.getAll();
	}

	
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin("*")
	public ResponseEntity<ResponseMessage> createEmployee(@RequestBody @Valid Job job)
			throws URISyntaxException, ApplicationException {

		ResponseMessage resMsg;

		// Exception Handling moved to @ExceptionHandler
//		try {
		jobService.create(job);
//		} catch (ApplicationException e) {
//			resMsg = new ResponseMessage("Failure", e.getMessage());
//			return ResponseEntity.badRequest().body(resMsg);
//		}

		// Exception Handling moved to @ExceptionHandler
//		if(bindingResult.hasErrors()) {
//			resMsg = new ResponseMessage("Failure", "Validation Error");
//			return ResponseEntity.badRequest().body(resMsg);			
//		}

		resMsg = new ResponseMessage("Success", new String[] {"Job created successfully"});

		// Build newly created Employee resource URI - Employee ID is always 0 here.
		// Need to get the new Employee ID.
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(job.getJobId()).toUri();

		return ResponseEntity.created(location).body(resMsg);

	}
	
	// Update Job PUT /Jobs/{id}
		@PutMapping(value = "/{id}")
		@CrossOrigin("*")
		public String updateJob(@PathVariable int id, @RequestBody Job updatedJob) {
			updatedJob.setJobId(id);
			jobService.update(updatedJob);
			return "Job updated successfully";
		}

		// Delete Job DELETE /Jobs/{id}
		@DeleteMapping("/{id}")
		@CrossOrigin("*")
		public String deleteJob(@PathVariable int id) {
			jobService.delete(id);
			return "Job deleted successfully";
		}
		
}
