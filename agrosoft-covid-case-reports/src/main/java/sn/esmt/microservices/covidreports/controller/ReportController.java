package sn.esmt.microservices.covidreports.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sn.esmt.microservices.covidreports.bean.CovidReport;
import sn.esmt.microservices.covidreports.dto.ReportPayload;
import sn.esmt.microservices.covidreports.exception.ResourceAlreadyExistsException;
import sn.esmt.microservices.covidreports.exception.ResourceNotFoundException;
import sn.esmt.microservices.covidreports.service.ReportService;
import sn.esmt.microservices.covidreports.utils.Response;

@RestController
@RequestMapping("reports")
public class ReportController {

	@Autowired
	private ReportService reportService;

	@Autowired
	private Response response;

	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody ReportPayload reportPayload) {

		try {
			reportService.create(reportPayload);
			response.prepareCreatedSuccessResponse(HttpStatus.CREATED.value(), "StateReport created successfully");
			return ResponseEntity.status(HttpStatus.CREATED.value()).body(response.getBody());
		} catch (IllegalArgumentException | ResourceAlreadyExistsException e) {
			response.prepareFailureResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getBody());
		} catch (Exception e) {
			response.prepareFailureResponse(e);
			return ResponseEntity.internalServerError().body(response.getBody());
		}
	}

	@GetMapping
	public ResponseEntity<Object> getReport(@RequestParam(name = "state", required = false) String state) {

		try {
			if (state != null) {
				CovidReport stateReport = reportService.getReportByState(state);
				response.prepareSuccessResponse(HttpStatus.OK.value(), " StateReport Found", stateReport);
				return ResponseEntity.ok(response.getBody());
			}
			List<CovidReport> states = reportService.getReports();

			response.prepareSuccessResponse(HttpStatus.OK.value(), states.size() + " StateReport Found", states);
			return ResponseEntity.ok(response.getBody());

		} catch (ResourceNotFoundException e) {
			response.prepareFailureResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.getBody());
		} catch (Exception e) {
			response.prepareFailureResponse(e);
			return ResponseEntity.internalServerError().body(response.getBody());
		}
	}

	@PutMapping
	public ResponseEntity<Object> update(@Valid @RequestBody ReportPayload reportPayload) {

		try {
			reportService.update(reportPayload);
			response.prepareSuccessResponse(HttpStatus.OK.value(), "StateReport updated successfully");
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
}
