package de.uniba.dsg.dsam.backend.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * 
 * @author Mohammed Mehedi Hasan
 *
 */
@Entity
@Table(name = "customer_order")
public class CustomerOrderEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;
	
	@Version
	private int version;
	
	private Date issueDate;
	private int orderAmount;
	
	@OneToMany(mappedBy = "customerOrderEntity", fetch = FetchType.LAZY, targetEntity = BeverageEntity.class)
	private List<BeverageEntity> beverageEntities = new ArrayList<>();
	
	/**
	 * Default constructor
	 */
	public CustomerOrderEntity() {
		
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	/**
	 * @return the issueDate
	 */
	public Date getIssueDate() {
		return issueDate;
	}
	
	/**
	 * @param issueDate the issueDate to set
	 */
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	/**
	 * @return the orderAmount
	 */
	public int getOrderAmount() {
		return orderAmount;
	}

	/**
	 * @param orderAmount the orderAmount to set
	 */
	public void setOrderAmount(int orderAmount) {
		this.orderAmount = orderAmount;
	}

	/**
	 * @return the beverageEntities
	 */
	public List<BeverageEntity> getBeverageEntities() {
		return beverageEntities;
	}

	/**
	 * @param beverageEntities the beverageEntities to set
	 */
	public void setBeverageEntities(List<BeverageEntity> beverageEntities) {
		this.beverageEntities = beverageEntities;
	}
}
