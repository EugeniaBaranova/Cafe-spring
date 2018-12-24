package com.epam.web.utils;

public class SqlUtils {

    public final static String INSERT_PRODUCT_STATEMENT = "INSERT INTO product (name,image_reference,cost,amount,category,description) " +
            "VALUES(?,?,?,?,?,?)";

}
