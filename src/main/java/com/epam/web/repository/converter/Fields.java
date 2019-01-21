package com.epam.web.repository.converter;

public class Fields {
    public static final String ID = "id";

    public static class Product {
        public static final String NAME = "name";
        public static final String COST = "cost";
        public static final String AMOUNT = "amount";
        public static final String CATEGORY = "category";
        public static final String DESCRIPTION = "description";
        public static final String IMAGE = "image";
    }

    public static class User {
        public static final String EMAIL = "e_mail";
        public static final String PASSWORD = "password";
        public static final String LOGIN = "login";
        public static final String LOYALTY_POINTS = "loyalty_points";
        public static final String BLOCKED = "blocked";
        public static final String ROLE = "role";
        public static final String NAME = "name";
    }


    public static class Order {
        public final static String USER_ID = "user_id";
        public final static String ORDER_DATE = "order_date";
        public final static String RECEIVING_DATE = "receiving_date";
        public final static String SUM = "sum";
        public final static String PAYMENT_METHOD = "payment_method";
        public final static String ORDER_STATE = "order_state";
        public final static String PAID = "paid";
        public final static String MARK = "mark";
        public final static String REVIEW = "review";
    }

    public static class OrderItem {
        public final static String MEAL_ID = "meal_id";
        public final static String ORDER_ID = "order_id";
        public final static String COUNT = "count";
    }

}
