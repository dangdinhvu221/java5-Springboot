package poly.edu.assignment_earphone.models;

import java.math.BigDecimal;

public class CartDetails {
    private Long id;
    private String name;
    private Integer quantity;
    private BigDecimal price;
    private String image;
    private Long earPhoneId;
    private Long orderId;

    public CartDetails() {
    }

    public CartDetails(Long id, String name, Integer quantity, BigDecimal price, String image, Long earPhoneId, Long orderId) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
        this.earPhoneId = earPhoneId;
        this.orderId = orderId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getEarPhoneId() {
        return earPhoneId;
    }

    public void setEarPhoneId(Long earPhoneId) {
        this.earPhoneId = earPhoneId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
