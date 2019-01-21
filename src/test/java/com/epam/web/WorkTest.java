package com.epam.web;

import com.epam.web.utils.SqlUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class WorkTest {


    @Test
    public void test(){



    }





    private String getTable() {
        return "product";
    }

    private List<String> getFields() {
        return new ArrayList<>(Arrays.asList("id","name","desctiprion"));
    }
}
