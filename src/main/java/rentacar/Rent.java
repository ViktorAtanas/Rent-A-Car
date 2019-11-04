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
@Table(name="rent")
public class Rent {
	private int idRent;
	private Date dateRent;
	private Date dateReturn;
	private double traveledKM;
	private double totalPrice;
	private Operator operator;
	private Car car;
	private Client client;
	
	public Rent() {
		
	}
	
	public Rent(Date dateRent, Date dateReturn, double traveledKM, double totalPrice, Operator operator, Car car,
			Client client) {
		super();
		this.dateRent = dateRent;
		this.dateReturn = dateReturn;
		this.traveledKM = traveledKM;
		this.totalPrice = totalPrice;
		this.operator = operator;
		this.car = car;
		this.client = client;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idRent")
	public int getIdRent() {
		return idRent;
	}

	public void setIdRent(int idRent) {
		this.idRent = idRent;
	}

	public Date getDateRent() {
		return dateRent;
	}

	public void setDateRent(Date dateRent) {
		this.dateRent = dateRent;
	}

	public Date getDateReturn() {
		return dateReturn;
	}

	public void setDateReturn(Date dateReturn) {
		this.dateReturn = dateReturn;
	}

	public double getTraveledKM() {
		return traveledKM;
	}

	public void setTraveledKM(double traveledKM) {
		this.traveledKM = traveledKM;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	@ManyToOne(cascade = CascadeType.ALL) 
	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	@ManyToOne(cascade = CascadeType.ALL) 
	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}
	@ManyToOne(cascade = CascadeType.ALL) 
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}



	@Override
	public String toString() {
		return "Rent [idRent=" + idRent + ", dateRent=" + dateRent + ", dateReturn=" + dateReturn + ", traveledKM="
				+ traveledKM + ", totalPrice=" + totalPrice + ", operator=" + operator + ", car=" + car + ", client="
				+ client + "]\n";
	}
	
	
	

	
	
	
}