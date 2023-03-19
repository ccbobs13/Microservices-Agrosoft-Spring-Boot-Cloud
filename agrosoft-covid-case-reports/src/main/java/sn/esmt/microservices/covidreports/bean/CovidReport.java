package sn.esmt.microservices.covidreports.bean;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "covid_reports")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CovidReport {

	@Id
	@Column(length = 24)
	@NotEmpty(message = "State is required")
	private String state;

	@OneToOne(mappedBy = "covidReport", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private ReportDetails report;

	public void addReport(ReportDetails report) {
		this.report = report;
		report.setCovidReport(this);
	}

	public void removeReport(ReportDetails report) {
		if (report != null) {
			report.setCovidReport(null);
		}
		this.report = null;
	}

}
