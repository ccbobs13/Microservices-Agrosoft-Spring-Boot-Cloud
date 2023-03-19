package sn.esmt.microservices.covidcaseregistration.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "covid_case_id", length = 24)
	@JsonIgnore
	private Long id;
	
	@Column(name = "STREET_ADDRESS", nullable = false, length = 50)
	@NotEmpty(message = "streetAddress is required")
	private String streetAddress;

	@Column(name = "CITY", nullable = false, length = 30)
	@NotEmpty(message = "city is required")
	private String city;

	@Column(name = "STATE", nullable = false, length = 30)
	@NotEmpty(message = "state is required")
	private String state;

	@Column(name = "POSTAL", nullable = false, length = 30)
	@NotEmpty(message = "postal is required")
	private String postal;

	@Column(name = "COUNTRY", nullable = false, length = 30)
	@NotEmpty(message = "country is required")
	private String country;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "covid_case_id")
	@MapsId
	@JsonIgnore
	private CovidCase covidCase;

}
