package sn.esmt.microservices.covidcaseregistration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sn.esmt.microservices.covidcaseregistration.bean.CovidCase;
import sn.esmt.microservices.covidcaseregistration.exception.ResourceNotFoundException;
import sn.esmt.microservices.covidcaseregistration.service.CovidCaseService;
import sn.esmt.microservices.covidcaseregistration.utils.Response;

@RestController
@RequestMapping("cases")
public class CovidCaseController {

	@Autowired
	private CovidCaseService covidCaseService;

	@Autowired
	private Response response;

	@GetMapping
	public ResponseEntity<Object> list() {
		try {
			List<CovidCase> cases = covidCaseService.findAll();
			response.prepareSuccessResponse(HttpStatus.OK.value(), cases.size() + " Cases Found", cases);
			return ResponseEntity.ok(response.getBody());

		} catch (Exception e) {
			response.prepareFailureResponse(e);
			return ResponseEntity.internalServerError().body(response.getBody());
		}
	}

	@GetMapping("caseID/{caseID}")
	public ResponseEntity<Object> findByCaseID(@PathVariable Long caseID) {
		try {
			CovidCase covidCase = covidCaseService.findOne(caseID);
			response.prepareSuccessResponse(HttpStatus.OK.value(), "Case with caseID: " + caseID + " Found", covidCase);
			return ResponseEntity.ok(response.getBody());

		} catch (ResourceNotFoundException e) {
			response.prepareFailureResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.getBody());
		} catch (Exception e) {
			response.prepareFailureResponse(e);
			return ResponseEntity.internalServerError().body(response.getBody());
		}
	}

	@GetMapping("{nationalID}")
	public ResponseEntity<Object> listByNationalID(@PathVariable String nationalID) {
		try {
			List<CovidCase> cases = covidCaseService.findByNationalID(nationalID);
			response.prepareSuccessResponse(HttpStatus.OK.value(), cases.size() + " Cases Found", cases);
			return ResponseEntity.ok(response.getBody());

		} catch (Exception e) {
			response.prepareFailureResponse(e);
			return ResponseEntity.internalServerError().body(response.getBody());
		}
	}

	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody CovidCase covidCase) {
		try {
			CovidCase newCase = covidCaseService.create(covidCase);
			response.prepareCreatedSuccessResponse(HttpStatus.CREATED.value(), "CovidCase created successfully",
					newCase.getCaseID(), "caseID");
			return ResponseEntity.status(HttpStatus.CREATED.value()).body(response.getBody());

		} catch (Exception e) {
			response.prepareFailureResponse(e);
			return ResponseEntity.internalServerError().body(response.getBody());
		}
	}

	@PutMapping
	public ResponseEntity<Object> update(@Valid @RequestBody CovidCase covidCase) {
		try {
			CovidCase updatedCase = covidCaseService.update(covidCase);
			response.prepareSuccessResponse(HttpStatus.OK.value(), "CovidCase with caseID: "+covidCase.getCaseID()+ " updated successfully", updatedCase);
			return ResponseEntity.ok(response.getBody());

		} catch (IllegalArgumentException e) {
			response.prepareFailureResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getBody());
		} catch (ResourceNotFoundException e) {
			response.prepareFailureResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.getBody());
		} catch (Exception e) {
			response.prepareFailureResponse(e);
			return ResponseEntity.internalServerError().body(response.getBody());
		}
	}

	@DeleteMapping("{caseID}")
	public ResponseEntity<Object> delete(@Valid @PathVariable Long caseID) {
		try {
			covidCaseService.delete(caseID);
			response.prepareSuccessResponse(HttpStatus.OK.value(),
					" Case with caseID: " + caseID + " deleted successfully");
			return ResponseEntity.ok(response.getBody());

		} catch (ResourceNotFoundException e) {
			response.prepareFailureResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.getBody());
		} catch (Exception e) {
			response.prepareFailureResponse(e);
			return ResponseEntity.internalServerError().body(response.getBody());
		}
	}

}
