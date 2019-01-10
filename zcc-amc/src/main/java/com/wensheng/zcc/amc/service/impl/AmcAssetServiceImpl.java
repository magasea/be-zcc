package com.wensheng.zcc.amc.service.impl;


import com.wensheng.zcc.amc.dao.mysql.mapper.AmcAssetMapper;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAsset;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAssetExample;
import com.wensheng.zcc.amc.module.vo.AmcAssetVo;
import com.wensheng.zcc.amc.service.AmcAssetService;
import com.wensheng.zcc.amc.service.impl.helper.Dao2VoUtils;
import com.wensheng.zcc.amc.utils.AmcNumberUtils;
import com.wensheng.zcc.amc.utils.SQLUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author chenwei on 12/5/18
 * @project zcc-backend
 */
@Service
public class AmcAssetServiceImpl implements AmcAssetService {
    @Autowired
    AmcAssetMapper amcAssetMapper;



    @Override
    public AmcAssetVo create(AmcAsset amcAsset) {
        Long id = Long.valueOf(amcAssetMapper.insertSelective(amcAsset));

        return null;
    }

    @Override
    public AmcAssetVo del(AmcAsset amcAsset) {
        return null;
    }

    @Override
    public AmcAssetVo update(AmcAsset amcAsset) {
        return null;
    }

    @Override
    public List<AmcAssetVo> queryAll(int offset, int size) {
        return null;
    }

    @Override
    public AmcAssetVo get(Long amcAssetId) {
        return null;
    }

    @Override
    public List<AmcAssetVo> query(AmcAsset queryCond, int offset, int size) {
        return null;
    }



    @Override
    public List<AmcAssetVo> queryAssetPage(int offset, int pageSize, Map<String, Object> queryParam,
        Map<String, Direction> orderByParam) throws Exception {


        AmcAssetExample amcAssetExample = getAmcAssetExampleWithQueryParam(queryParam);
        amcAssetExample.setOrderByClause(SQLUtils.getOrderBy(orderByParam));
        RowBounds rowBounds = new RowBounds(offset, pageSize);
        List<AmcAsset> amcAssets = amcAssetMapper.selectByExampleWithRowbounds(amcAssetExample, rowBounds);
        List<AmcAssetVo> amcAssetVos = Dao2VoUtils.convertDoList2VoList(amcAssets);
        return amcAssetVos;
    }

    @Override
    public Long getAssetCount(Map<String, Object> queryParam) {
        return null;
    }

    private AmcAssetExample getAmcAssetExampleWithQueryParam(Map<String, Object> queryParam){
        AmcAssetExample amcAssetExample = new AmcAssetExample();
        if(CollectionUtils.isEmpty(queryParam)){
            for(Entry<String, Object> item: queryParam.entrySet()){
                if(item.getKey().equals("DebtId")){
                    amcAssetExample.createCriteria().andDebtIdEqualTo((Long)item.getValue());
                }
                if(item.getKey().equals("EditStatus")){
                    amcAssetExample.createCriteria().andEditStatusEqualTo((Integer) item.getValue());
                }
                if(item.getKey().equals("Area")){
                    if(item.getValue() instanceof List && !CollectionUtils.isEmpty((List)item.getValue())){
                        amcAssetExample.createCriteria().andAreaBetween((Long)(((List) item.getValue()).get(0)),
                            (Long)(((List) item.getValue()).get(1)));
                    }
                }
                if(item.getKey().equals("Status")){
                    amcAssetExample.createCriteria().andStatusEqualTo((Integer) item.getValue());
                }
                if(item.getKey().equals("Title")){

                    amcAssetExample.createCriteria().andTitleLike((String)item.getValue());
                }
            }
        }
        return amcAssetExample;
    }


}
