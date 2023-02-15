package kz.redmadbot.auction.entity;

import javax.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@MappedSuperclass
public class Abstract_entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @CreatedDate
    @Column(name = "created_at")
    private Date createdAt;

    public Abstract_entity() {
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}