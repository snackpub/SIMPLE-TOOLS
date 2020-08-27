package com.snack.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author snackpub
 * @date 2020/8/25
 */
@Data
@AllArgsConstructor
public class Order implements Serializable {

    private String id;
    private String name;
}
