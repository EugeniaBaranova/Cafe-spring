package com.epam.web.repository;

import com.epam.web.repository.exception.TransactionException;

import java.sql.Connection;

public class TransactionUtils {

    public static void commit(Connection connection) {
        doAction(con -> {con.commit();
        con.setAutoCommit(true);}
                ,connection
                ,"Exception while commit transaction");
    }

    public static void begin(Connection connection) {

        doAction(con -> con.setAutoCommit(false)
                ,connection
                ,"Exception while begin transaction");
    }

    public static void rollBack(Connection connection) {
        doAction(con -> con.rollback()
                ,connection
                ,"Exception while rollBack transaction");
    }

    public static void close(Connection connection){
        doAction(connection1 -> connection1.close()
                ,connection
                ,"Exception while execution method");
    }

    private static void doAction(Action<Connection> consumer, Connection connection, String message) {
        try {
            if (connection != null) {
                consumer.doAction(connection);
            }
        } catch (Exception e) {
            throw new TransactionException(message, e);
        }
    }


    private  interface Action<T>{
        void doAction(T t) throws Exception;
    }

}
