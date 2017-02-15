package db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Belhaver on 13.02.2017.
 */
@Entity
@Table(name = "shop")
public class Shop {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shopId")
    private int shopId;

    @Column(name = "shopName")
    private String shopName;
    
    @Column(name = "columnCount")
    private int columnsCount;
    
    @Column(name = "rowCount")
    private int rowsCount;
    
    public Shop() {
    	super();
    	this.shopName = "";
    	this.columnsCount = -1;
    	this.rowsCount = -1;
    }
    
    public Shop(String name, int columnsCount, int rowsCount) {
    	super();
    	this.shopName = name;
    	this.columnsCount = columnsCount;
    	this.rowsCount = rowsCount;
    }

    public int getId() {
        return shopId;
    }

    public String getName() {
        return shopName;
    }
    
    public int getColumnCount() {
    	return columnsCount;
    }
    
    public int getRowCount() {
    	return rowsCount;
    }
}
