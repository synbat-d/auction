package kz.redmadbot.auction.entity;

import javax.persistence.*;

@Entity
@Table(name = "announcements",
        uniqueConstraints = { @UniqueConstraint(name = "unique_product_name_and_ownerUser",
                columnNames = {"product_name", "owner_user_id"})})
public class Announcement extends Abstract_entity {
    @Column(name = "product_name")
    private String productName;
    @Column(name = "product_description")
    private String productDescription;
    @Column(name = "picture_url")
    private String pictureUrl;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "owner_user_id", nullable = true)
    private User ownerUser;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "buyer_user_id", nullable = true)
    private User buyerUser;
    @Column(name = "min_cost")
    private int minCost;
    @Column(name = "current_cost")
    private int currentCost;
    @Column(name = "status")
    private boolean status;

    public Announcement() {
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public User getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(User ownerUser) {
        this.ownerUser = ownerUser;
    }

    public User getBuyerUser() {
        return buyerUser;
    }

    public void setBuyerUser(User buyerUser) {
        this.buyerUser = buyerUser;
    }

    public int getMinCost() {
        return minCost;
    }

    public void setMinCost(int minCost) {
        this.minCost = minCost;
    }

    public int getCurrentCost() {
        return currentCost;
    }

    public void setCurrentCost(int currentCost) {
        this.currentCost = currentCost;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
