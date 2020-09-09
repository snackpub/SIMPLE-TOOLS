package com.snackpub.core.wxlsjg.model.menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * click类型按钮
 *
 * @author snackpub
 * @date 2020/9/9
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ClickButton extends AbstractButton implements Serializable {

    private String key;

    public ClickButton(String name, String type, String key) {
        super(name, type);
        this.key = key;
    }


}
