package com.snackpub.core.wxlsjg.model.menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 菜单按钮类型基类
 *
 * @author snackpub
 * @date 2020/9/9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractButton implements Serializable {


    protected String name;
    protected String type;


}
