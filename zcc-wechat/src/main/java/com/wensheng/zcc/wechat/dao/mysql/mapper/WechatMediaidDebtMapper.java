package com.wensheng.zcc.wechat.dao.mysql.mapper;

import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatMediaidDebt;
import com.wensheng.zcc.wechat.module.dao.mysql.auto.entity.WechatMediaidDebtExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WechatMediaidDebtMapper {
    long countByExample(WechatMediaidDebtExample example);

    int deleteByExample(WechatMediaidDebtExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WechatMediaidDebt record);

    int insertSelective(WechatMediaidDebt record);

    List<WechatMediaidDebt> selectByExampleWithRowbounds(WechatMediaidDebtExample example, RowBounds rowBounds);

    List<WechatMediaidDebt> selectByExample(WechatMediaidDebtExample example);

    WechatMediaidDebt selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WechatMediaidDebt record, @Param("example") WechatMediaidDebtExample example);

    int updateByExample(@Param("record") WechatMediaidDebt record, @Param("example") WechatMediaidDebtExample example);

    int updateByPrimaryKeySelective(WechatMediaidDebt record);

    int updateByPrimaryKey(WechatMediaidDebt record);
}