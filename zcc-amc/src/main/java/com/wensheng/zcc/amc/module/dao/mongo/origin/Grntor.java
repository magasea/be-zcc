package com.wensheng.zcc.amc.module.dao.mongo.origin;

import java.util.Objects;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * 
 */

@Data
@Document(collection = "guarantor")
public class Grntor {
    private long id;
    private long contract;
    private long debtNo;
    private String type;
    private String name;


}
