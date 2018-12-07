package com.wensheng.zcc.sso.dao;

import com.wensheng.zcc.sso.model.ZccMiniappUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZCCUserDao extends CrudRepository<ZccMiniappUser, Integer> {

    ZccMiniappUser findByUsername(String userName);

    ZccMiniappUser findByWechatId(String wechatId);

    ZccMiniappUser findByInitcode(String initCode);
}
