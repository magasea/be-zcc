package com.wensheng.zcc.wechat.module.dao.mysql.ext;

import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatUserExample;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WechatUserExtExample extends WechatUserExample {
    protected String andClause;



    public void setAndClause(String andClause) {
        this.andClause = andClause;
    }

    public String getAndClause() {
        return andClause;
    }


}