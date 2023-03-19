package sn.esmt.microservices.covidreports.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "report_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDetails {

	@Id
	@Column(name = "report_id", length = 24)
	@JsonIgnore
	private String id;

	private Integer total = 0;
	private Integer positive = 0;
	private Integer recovered = 0;
	private Integer death = 0;
	private Integer vaccinated = 0;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
    @JoinColumn(name = "report_id")
	@JsonIgnore
	private CovidReport covidReport;
}
