package com.epam.web.controller.command.product;

import com.epam.web.controller.command.Command;
import com.epam.web.controller.command.CommandResult;
import com.epam.web.controller.constant.Pages;
import com.epam.web.controller.constant.SessionAttribute;
import com.epam.web.entity.SavingResult;
import com.epam.web.entity.enums.ProductCategory;
import com.epam.web.entity.product.Product;
import com.epam.web.entity.validation.Error;
import com.epam.web.service.ProductService;
import com.epam.web.service.exception.ServiceException;
import com.epam.web.utils.StringUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Formatter;
import java.util.List;
import java.util.Set;

import static com.epam.web.controller.constant.RequestParameter.*;

public class AddProductCommand implements Command {

    private static final String IMAGE_CONTENT_TYPE = "image/jpeg";
    private static final String COST_PATTERN = "^[0-9]{1,}.[0-9]{2}$";

    private ProductService productService;

    public AddProductCommand(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        HttpSession session = req.getSession(true);
        Product additionCandidate = convertFromReq(req);
        SavingResult<Product> result = getProductService().addProduct(additionCandidate);
        if (result.hasError()) {
            Set<Error> errors = result.getErrors();
            session.setAttribute(SessionAttribute.ADDITION_ERRORS, errors);
            session.setAttribute(SessionAttribute.UNSUCCESSFUL_ADDITION, true);
            return CommandResult.redirect(Pages.ADD_PRODUCT_PAGE);
        }
        Product newProduct = result.getData();
        session.setAttribute(SessionAttribute.PRODUCT, newProduct);
        return CommandResult.redirect(Pages.PRODUCT_PAGE);
    }


    protected Product convertFromReq(HttpServletRequest req) throws ServiceException {
        try {
            Product product = new Product();
            FileItemFactory fileItemFactory = new DiskFileItemFactory();
            ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
            List<FileItem> fileItems = fileUpload.parseRequest(req);
            for (FileItem item : fileItems) {
                if (item.isFormField()) {
                    setFormFieldValues(product, item);
                } else {
                    setImageValue(product, item);
                }
            }
            return product;
        } catch (FileUploadException e) {
            // TODO: 17.01.2019 try-catch here?
            String errorMessage = getErrorFormatter()
                    .format("[convertFromReq] Exception while execution method. Method parameter:'%s'", req)
                    .toString();
            throw new ServiceException(errorMessage, e);
        }
    }

    private void setFormFieldValues(Product product, FileItem item) {
        if (product != null && item != null) {
            String fieldName = item.getFieldName();
            String fieldValue = item.getString();
            switch (fieldName) {
                case NAME:
                    product.setName(fieldValue);
                    break;
                case COST:
                    if (StringUtils.isDigit(fieldValue)) {
                        product.setCost(BigDecimal.valueOf(Double.valueOf(fieldValue)));
                    }
                    break;
                case AMOUNT:
                    product.setAmount(Integer.valueOf(fieldValue));
                    break;
                case PRODUCT_CATEGORY:
                    product.setCategory(ProductCategory.valueOf(fieldValue.toUpperCase()));
                    break;
                case DESCRIPTION:
                    product.setDescription(fieldValue);
                    break;
            }
        }
    }

    private void setImageValue(Product product, FileItem item) {
        if (product != null && item != null) {
            String contentType = item.getContentType();
            if (IMAGE_CONTENT_TYPE.equals(contentType)) {
                byte[] image = item.get();
                //*InputStream inputStream = new ByteArrayInputStream(itemBytes);
                String fieldName = item.getFieldName();
                String fileName = FilenameUtils.getName(item.getName());
                product.setImage(image);
            }
        }
    }

    private ProductService getProductService() {
        return productService;
    }

    // TODO: 17.01.2019 better?
    private Formatter getErrorFormatter() {
        //using new instance every call of the method cause java.util.Formatter is not thread safe class.
        return new Formatter();
    }
}
