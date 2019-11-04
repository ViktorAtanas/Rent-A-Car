package rentacar;

import java.util.Date;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="opis")
public class Opis {
	private int idOpis;
	private double currKM;
	private String problems;
	private Date date;
	private Car car;
	
	public Opis() {
		
	}

	public Opis(double currKM, Date date, String problems, Car car) {
		super();
		this.currKM = currKM;
		this.date = date;
		this.problems = problems;
		this.car = car;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idOpis")
	public int getIdOpis() {
		return idOpis;
	}

	public void setIdOpis(int idOpis) {
		this.idOpis = idOpis;
	}

	public double getCurrKM() {
		return currKM;
	}

	public void setCurrKM(double currKM) {
		this.currKM = currKM;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getProblems() {
		return problems;
	}

	public void setProblems(String problems) {
		this.problems = problems;
	}
	@ManyToOne(cascade = CascadeType.ALL) 
	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}
	
	
	
	
}
