package com.epam.web.entity;

public class OrderItem extends Entity{

    private Long mealId;
    private Long orderId;
    private int count;

    public OrderItem(Long id,
                     Long mealId,
                     Long orderId,
                     int count) {
        super(id);
        this.mealId = mealId;
        this.orderId = orderId;
        this.count = count;
    }

    public Long getMealId() {
        return mealId;
    }

    public void setMealId(Long mealId) {
        this.mealId = mealId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
