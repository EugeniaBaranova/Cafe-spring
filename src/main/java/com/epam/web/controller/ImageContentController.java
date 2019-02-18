package com.epam.web.controller;

import com.epam.web.controller.command.*;
import com.epam.web.controller.constant.RequestParameter;
import com.epam.web.entity.product.Product;
import com.epam.web.service.ProductService;

import com.epam.web.utils.ServletResponseUtils;
import com.epam.web.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Optional;


public class ImageContentController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ImageContentController.class);

    private static final String IMAGE_CONTENT_TYPE = "image/jpeg";

    private static final String COMMAND_FACTORY_ATTRIBUTE = "commandFactory";

    private ApplicationContext applicationContext;

    private CommandFactory commandFactory;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            if (!resp.isCommitted()) {
                String idString = req.getParameter(RequestParameter.ID);
                if (StringUtils.isNumeral(idString)) {
                    long id = Long.valueOf(req.getParameter(RequestParameter.ID));
                    ProductService productService =
                            applicationContext.getBean("productServiceImpl", ProductService.class);
                    Optional<Product> productOptional = productService.findProduct(id);
                    if (productOptional.isPresent()) {
                        Product product = productOptional.get();
                        byte[] image = product.getImage();
                        if (image != null) {
                            resp.setContentType(IMAGE_CONTENT_TYPE);
                            resp.getOutputStream().write(image, 0, image.length);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("[process] Exception.", e);
            resp.sendError(500);
        } finally {
            resp.getOutputStream().flush();
            resp.getOutputStream().close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (!resp.isCommitted()) {
                String command = req.getParameter(RequestParameter.COMMAND);
                if (CommandName.ADD_NEW_PRODUCT.equals(command) || CommandName.EDIT_PRODUCT.equals(command)) {
                    Command action = commandFactory.getCommand(command);
                    if (action != null) {
                        CommandResult commandResult = action.execute(req, resp);
                        ServletResponseUtils.sendResponse(req, resp, commandResult, getServletContext());
                    } else {
                        resp.sendError(404);
                    }
                } else {
                    resp.sendError(404);
                }
            }
        } catch (Exception e) {
            logger.error("[process] Exception.", e);
            resp.sendError(500);
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        Object applicationContext = servletContext.getAttribute("applicationContext");
        if (applicationContext != null) {
            this.applicationContext = (ApplicationContext) applicationContext;
        }
        Object commandFactory = servletContext.getAttribute(COMMAND_FACTORY_ATTRIBUTE);
        if (commandFactory != null) {
            this.commandFactory = (CommandFactory) commandFactory;
        }
        super.init(config);
    }
}
