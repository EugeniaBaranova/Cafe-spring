package com.epam.web.repository.specification;

import java.util.List;

public interface Specification {

    String toSql();

    List<String> getParameters();
}
