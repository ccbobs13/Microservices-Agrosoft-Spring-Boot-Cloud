package sn.esmt.microservices.covidcaseregistration.bean;

import java.io.Serializable;
import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "covid_cases")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CovidCase implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CASE_ID")
	private Long caseID;

	@Column(name = "SOURCE", nullable = false, length = 64)
	@NotEmpty(message = "source is required")
	private String source;

	@Column(name = "CASE_TYPE", nullable = false, length = 10)
	@NotEmpty(message = "caseType is required")
	private String caseType;

	@Column(name = "FIRST_NAME", nullable = false, length = 64)
	@NotEmpty(message = "firstName is required")
	private String firstName;

	@Column(name = "LAST_NAME", nullable = false, length = 64)
	@NotEmpty(message = "lastName is required")
	private String lastName;

	@Column(name = "PHONE", nullable = false, length = 15)
	@NotEmpty(message = "phone is required")
	private String phone;

	@Column(name = "EMAIL", length = 30)
	private String email;

	@Column(name = "DATE_OF_BIRTH", nullable = false)
	@NotNull(message = "dateOfBirth is required")
	private LocalDate dateOfBirth;

	@Column(name = "NATIONAL_ID", nullable = false, length = 20)
	@NotEmpty(message = "nationalID is required")
	private String nationalID;

	@Column(name = "NATIONAL_ID_TYPE", nullable = false, length = 30)
	@NotEmpty(message = "nationalIDType is required")
	private String nationalIDType;

	@OneToOne(mappedBy = "covidCase", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@NotNull(message = "Address is required")
	@Valid
	private Address address; 

	@CreationTimestamp
	@Column(name = "CREATE_DATE", updatable = false)
	private LocalDate createDate;

	@UpdateTimestamp
	@Column(name = "UPDATE_DATE")
	private LocalDate updateDate;
	
	
	public void addAddress(Address address) {
		address.setState(address.getState().toUpperCase());
		this.address = address;
		address.setCovidCase(this);
	}
	
	public void removeAddress(Address address) {
		if(address != null) {
			address.setCovidCase(null);
		}
		this.address = null;
	}

}
