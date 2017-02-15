package db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "place")
public class Place {
	
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "shopId")
    private int shopId;
    
    @Column(name = "columnNumber")
    private int columnNumber;
    
    @Column(name = "rowNumber")
    private int rowNumber;
    
    private boolean containsRequiredProduct = false;
    
    public Place() {
    	this.shopId = -1;
    	this.columnNumber = -1;
    	this.rowNumber = -1;
    }
    
    public Place(int shopId, int columnNumber, int rowNumber) {
    	this.shopId = shopId;
    	this.columnNumber = columnNumber;
    	this.rowNumber = rowNumber;
    }
    
    public Place(int columnNumber, int rowNumber) {
    	this.shopId = -1;
    	this.columnNumber = columnNumber;
    	this.rowNumber = rowNumber;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public int getColumnNumber() {
		return columnNumber;
	}

	public void setColumnNumber(int columnNumber) {
		this.columnNumber = columnNumber;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public boolean isContainsRequiredProduct() {
		return containsRequiredProduct;
	}

	public void setContainsRequiredProduct(boolean containsRequiredProduct) {
		this.containsRequiredProduct = containsRequiredProduct;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + columnNumber;
		result = prime * result + rowNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Place other = (Place) obj;
		if (columnNumber != other.columnNumber)
			return false;
		if (rowNumber != other.rowNumber)
			return false;
		return true;
	}
    
    
}
