package sn.esmt.microservices.covidreports.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportPayload implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String oldState = null;

	@NotEmpty(message = "state is required")
	private String state;

	private String oldCaseType = null;

	@NotEmpty(message = "caseType is required")
	private String caseType;


}
