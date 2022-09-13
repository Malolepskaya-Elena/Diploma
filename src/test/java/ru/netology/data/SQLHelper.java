package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import ru.netology.data.entity.CreditRequestEntity;
import ru.netology.data.entity.OrderEntity;
import ru.netology.data.entity.PaymentEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    public static QueryRunner runner  = new QueryRunner();
    private static Connection connection = getConnection();

    @SneakyThrows
    private static Connection getConnection() {
        try {
            //return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
            return DriverManager.getConnection(System.getProperty("db.url"), "app", "pass");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

/*    @SneakyThrows
    public static Connection start() {
        return connection;
    }*/

    @SneakyThrows
    public static void databaseCleanUp() {
        var deleteFromOrder = "DELETE FROM order_entity;";
        var deleteFromCredit = "DELETE FROM credit_request_entity;";
        var deleteFromPayment = "DELETE FROM payment_entity;";
        runner.update(connection, deleteFromOrder);
        runner.update(connection, deleteFromCredit);
        runner.update(connection, deleteFromPayment);
    }

    @SneakyThrows
    public static CreditRequestEntity getCreditRequestInfo() {
        var creditRequestInfo = "SELECT * FROM credit_request_entity LIMIT 1;";
        return runner.query(connection, creditRequestInfo, new BeanHandler<>(CreditRequestEntity.class));
    }

    @SneakyThrows
    public static PaymentEntity getPaymentInfo() {
        var paymentInfo = "SELECT * FROM payment_entity LIMIT 1;";
        ResultSetHandler<PaymentEntity> resultSetHandler = new BeanHandler<>(PaymentEntity.class);
        return runner.query(connection, paymentInfo, resultSetHandler);
    }

    @SneakyThrows
    public static OrderEntity getOrderInfo() {
        var orderInfo = "SELECT * FROM order_entity LIMIT 1;";
        ResultSetHandler<OrderEntity> resultSetHandler = new BeanHandler<>(OrderEntity.class);
        return runner.query(connection, orderInfo, resultSetHandler);
    }
}