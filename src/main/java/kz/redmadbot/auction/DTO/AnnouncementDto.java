package kz.redmadbot.auction.DTO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AnnouncementDto {
    private long id;
    private String ownerEmail;
    @NotBlank(message = "Product name should not be empty")
    @Size(min = 5, message = "Product name should contain at least 5 characters")
    private String productName;
    @NotBlank(message = "Product name should not be empty")
    @Size(min = 5, message = "Product name should contain at least 5 characters")
    private String productDescription;
    private String pictureUrl;
    @Min(value = 1, message = "Cost should be minimum 1")
    private int minCost;

    private int currentCost;


    public AnnouncementDto() {
    }

    public AnnouncementDto(long id, String ownerEmail, String productName,
                           String productDescription, String pictureUrl, int minCost, int currentCost) {
        this.id = id;
        this.ownerEmail = ownerEmail;
        this.productName = productName;
        this.productDescription = productDescription;
        this.pictureUrl = pictureUrl;
        this.minCost = minCost;
        this.currentCost = currentCost;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCurrentCost() {
        return currentCost;
    }

    public void setCurrentCost(int currentCost) {
        this.currentCost = currentCost;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
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

    public int getMinCost() {
        return minCost;
    }

    public void setMinCost(int minCost) {
        this.minCost = minCost;
    }
}
