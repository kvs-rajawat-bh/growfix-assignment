package com.project.workfix.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	
	@Column(name="name")
	private String name;
	
	
	@Temporal(TemporalType.DATE)
	private Date maturity;
	
	
	@Column(name="interest")
	private double interest;
	
	@OneToMany(mappedBy="product")
	private List<PricePerDay> ppd;


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Date getMaturity() {
		return maturity;
	}


	public void setMaturity(Date maturity) {
		this.maturity = maturity;
	}


	public double getInterest() {
		return interest;
	}


	public void setInterest(double interest) {
		this.interest = interest;
	}


	public List<PricePerDay> getPpd() {
		return ppd;
	}


	public void setPpd(List<PricePerDay> ppd) {
		this.ppd = ppd;
	}
	
	
}
