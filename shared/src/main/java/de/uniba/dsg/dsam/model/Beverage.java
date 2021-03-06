package de.uniba.dsg.dsam.model;

import java.util.Optional;

public class Beverage extends AbstractDto {
    private String manufacturer;
    private String name;
    private int quantity;
    private int availableQuantity;
    private double price;

    private Incentive incentive;

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
	 * @return the availableQuantity
	 */
	public int getAvailableQuantity() {
		return availableQuantity;
	}

	/**
	 * @param availableQuantity the availableQuantity to set
	 */
	public void setAvailableQuantity(int availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Optional<Incentive> getIncentive() {
        return Optional.ofNullable(incentive);
    }

    public void setIncentive(Incentive incentive) {
        this.incentive = incentive;
    }

    @Override
    public String toString() {
        return "Beverage{" +
                "manufacturer='" + manufacturer + '\'' +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", availableQuantity=" + availableQuantity +
                ", price=" + price +
                ", incentive=" + incentive +
                '}';
    }
}
