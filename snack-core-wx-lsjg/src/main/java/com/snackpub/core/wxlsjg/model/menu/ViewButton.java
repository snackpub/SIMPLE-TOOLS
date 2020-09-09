package com.snackpub.core.wxlsjg.model.menu;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * view类型按钮
 *
 * @author snackpub
 * @date 2020/9/9
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ViewButton extends AbstractButton implements Serializable {

    private String url;

    public ViewButton() {
    }

    public ViewButton(String name, String type, String url) {
        super(name, type);
        this.url = url;
    }
}
