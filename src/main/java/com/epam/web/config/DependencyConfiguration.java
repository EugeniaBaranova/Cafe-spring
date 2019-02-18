package com.epam.web.config;

import com.epam.web.controller.command.Command;
import com.epam.web.controller.command.cart.AddToCartCommand;
import com.epam.web.controller.command.cart.ChangeCartItemCountCommand;
import com.epam.web.controller.command.cart.DeleteFromCartCommand;
import com.epam.web.controller.command.cart.ShowCartCommand;
import com.epam.web.controller.command.order.MakeOrderCommand;
import com.epam.web.controller.command.order.ShowOrdersCommand;
import com.epam.web.controller.command.product.AddProductCommand;
import com.epam.web.controller.command.product.ShowAllByCategoryCommand;
import com.epam.web.controller.command.product.ShowProductCommand;
import com.epam.web.controller.command.product.ShowProductsCategoryCommand;
import com.epam.web.controller.command.user.LoginCommand;
import com.epam.web.controller.command.user.LogoutCommand;
import com.epam.web.controller.command.user.RegisterUserCommand;
import com.epam.web.entity.order.Order;
import com.epam.web.entity.order.OrderItem;
import com.epam.web.entity.product.Product;
import com.epam.web.entity.user.User;
import com.epam.web.repository.RepositoryFactory;
import com.epam.web.service.*;
import com.epam.web.service.impl.cart.CartServiceImpl;
import com.epam.web.service.impl.order.OrderServiceImpl;
import com.epam.web.service.impl.product.ProductServiceImpl;
import com.epam.web.service.impl.user.UserServiceImpl;
import com.epam.web.service.validation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epam.web")
public class DependencyConfiguration {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/epam-cafe");
        dataSource.setUsername("root");
        dataSource.setPassword("481415");
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

/*    @Bean(name = "repositoryFactory")
    public RepositoryFactory repositoryFactory() {
        return new RepositoryFactory();
    }

    @Bean(name = "userValidator")
    public Validator<User> userValidator() {
        return new UserValidator();
    }

    @Bean(name = "productValidator")
    public Validator<Product> productValidator() {
        return new ProductValidator();
    }

    @Bean(name = "orderValidator")
    public Validator<Order> orderValidator() {
        return new OrderValidator();
    }

    @Bean(name = "orderItemValidator")
    public Validator<OrderItem> orderItemValidator() {
        return new OrderItemValidator();
    }

    @Bean(name = "userServiceImpl")
    public UserService userService() {
        return new UserServiceImpl(
                repositoryFactory(),
                userValidator()
        );
    }

    @Bean(name = "productServiceImpl")
    public ProductService productService() {
        return new ProductServiceImpl(
                repositoryFactory(),
                productValidator());
    }

    @Bean(name = "orderServiceImpl")
    public OrderService orderService() {
        return new OrderServiceImpl(
                repositoryFactory(),
                orderValidator()
        );
    }

    @Bean(name = "cartServiceImpl")
    public CartService cartService() {
        return new CartServiceImpl();
    }

    @Bean(name = "add_to_cart")
    public Command addToCartCommand(){
        return new AddToCartCommand();
    }
    @Bean(name = "change_cart_count")
    public Command changeCartItemCountCommand(){
        return new ChangeCartItemCountCommand(cartService());
    }
    @Bean(name = "delete_from_cart")
    public Command deleteFromCartCommand(){
        return new DeleteFromCartCommand();
    }
    @Bean(name = "show_cart")
    public Command showCartCommand(){
        return new ShowCartCommand(productService());
    }

    @Bean(name = "make_order")
    public Command makeOrderCommand(){
        return new MakeOrderCommand(orderService(), productService());
    }
    @Bean(name = "show_orders")
    public Command showOrdersCommand(){
        return new ShowOrdersCommand(orderService());
    }

    @Bean(name = "add_product")
    public Command addProductCommand(){
        return new AddProductCommand(productService());
    }
    @Bean(name = "show_product")
    public Command showProductCommand(){
        return new ShowProductCommand(productService());
    }
    @Bean(name = "show_category_products")
    public Command showProductsCategoryCommand(){
        return new ShowProductsCategoryCommand(productService());
    }
    @Bean(name = "showAllByCategoryCommand")
    public Command showAllByCategoryCommand(){
        return new ShowAllByCategoryCommand(productService());
    }

    @Bean(name = "log_in")
    public Command loginCommand(){
        return new LoginCommand(userService());
    }
    @Bean(name = "log_out")
    public Command logoutCommand(){
        return new LogoutCommand();
    }
    @Bean(name = "registration")
    public Command registerUserCommand(){
        return new RegisterUserCommand(userService());
    }*/
}
