package com.innoventes.test.app.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.innoventes.test.app.validation.EvenNumberOrZero;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "company", uniqueConstraints = @UniqueConstraint(columnNames = "company_code"))
@Entity
public class Company extends BaseEntity {

//	Task 1: Add validation constraints to the following existing fields in Company
//	entity and CompanyDTO:

	@Id
	@SequenceGenerator(sequenceName = "company_seq", allocationSize = 1, name = "company_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_seq")
	private Long id;

	@NotBlank(message = "Company name is mandatory")
	@Size(min = 5, message = "Company name should be at least 5 characters")
	@Column(name = "company_name")
	private String companyName;

	@NotBlank(message = "Email is mandatory")
	@Email(message = "Invalid email format")
	@Column(name = "email")
	private String email;

	@PositiveOrZero(message = "Strength should be a positive number or zero")
	@EvenNumberOrZero
	@Column(name = "strength")
	private Integer strength;

	@Column(name = "website_url")
	private String webSiteURL;

//	Task 2: Add a new field with the specified validation constraints to Company
//	entity and CompanyDTO

	@Size(max = 5, message = "Company code should be 5 characters at most")
	@Pattern(regexp = "^[A-Za-z]{2}[0-9]{2}[ENen]?$", message = "Invalid company code format")
	@Column(name = "company_code")
	private String companyCode;
}
