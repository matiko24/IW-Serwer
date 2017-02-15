package db;

import javax.persistence.Column;
import javax.persistence.Entity;
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
    @Column(name = "shopId")
    private int id;

    @Column(name = "shopName")
    private String name;

    @Column(name = "created")
    private Date created;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getCreated() {
        return created;
    }
}
