package com.viwcy.custom.handle;

import com.viwcy.basecommon.entity.User;
import lombok.Data;

/**
 * TODO  Copyright (c) yun lu 2022 Fau (viwcy4611@gmail.com), ltd
 */
@Data
public abstract class AbstractRegisterHandle {

    private AbstractRegisterHandle next;

    public void check(User user) {

        boolean var = this.doCheck(user);
        if (var) {
            this.next.check(user);
        }
    }

    protected abstract boolean doCheck(User user);
}
