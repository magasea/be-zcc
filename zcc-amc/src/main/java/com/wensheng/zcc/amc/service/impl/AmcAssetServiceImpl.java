package com.wensheng.zcc.amc.service.impl;


import com.wensheng.zcc.amc.dao.mysql.mapper.AmcAssetMapper;
import com.wensheng.zcc.amc.dao.mysql.mapper.ext.AmcAssetExtMapper;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetAdditional;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetComment;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetDocument;
import com.wensheng.zcc.amc.module.dao.mongo.entity.AssetImage;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAsset;
import com.wensheng.zcc.amc.module.dao.mysql.auto.entity.AmcAssetExample;
import com.wensheng.zcc.amc.module.vo.AmcAssetDetailVo;
import com.wensheng.zcc.amc.module.vo.AmcAssetVo;
import com.wensheng.zcc.amc.service.AmcAssetService;
import com.wensheng.zcc.amc.service.impl.helper.Dao2VoUtils;
import com.wensheng.zcc.amc.utils.SQLUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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

    @Autowired
    AmcAssetExtMapper amcAssetExtMapper;

    @Autowired
    MongoTemplate wszccTemplate;



    @Override
    public AmcAssetVo create(AmcAsset amcAsset) {
        Long id = Long.valueOf(amcAssetMapper.insertSelective(amcAsset));

         amcAsset.setId(id);
         return Dao2VoUtils.convertDo2Vo(amcAsset);

    }

    @Override
    public AssetAdditional createAssetAddition(AssetAdditional additional) {
        Query query = new Query();
        query.addCriteria(Criteria.where("amcAssetId").is(additional.getAmcAssetId()));
        List<AssetAdditional> assetAdditionals = wszccTemplate.find(query, AssetAdditional.class);

        if(!CollectionUtils.isEmpty(assetAdditionals)){
            if(assetAdditionals.size() > 1){
                for(int idx = 1; idx < assetAdditionals.size(); idx ++){
                    wszccTemplate.remove(assetAdditionals.get(idx));
                }
            }
            Update update = new Update();
            update.set(assetAdditionals.get(0).get_id(), additional);
            wszccTemplate.findAndModify(query, update, AssetAdditional.class);
        }else{
            wszccTemplate.insert(additional);
        }
        return additional;
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
    public AmcAssetDetailVo queryAssetDetail(Long assetId) {
        AmcAsset amcAsset =  amcAssetMapper.selectByPrimaryKey(assetId);



        return queryMongoForAmcAsset(amcAsset);
    }

    private AmcAssetDetailVo queryMongoForAmcAsset(AmcAsset amcAsset){
        AmcAssetVo amcAssetVo = Dao2VoUtils.convertDo2Vo(amcAsset);
        Long assetId = amcAsset.getId();
        Query query = new Query();
        query.addCriteria(Criteria.where("amcAssetId").is(assetId));

        List<AssetAdditional> assetAdditionals =  wszccTemplate.find(query, AssetAdditional.class);

        Query queryComment = new Query();
        queryComment.addCriteria(Criteria.where("amcAssetId").is(assetId));
        List<AssetComment> assetComments = wszccTemplate.find(queryComment, AssetComment.class);

        Query queryAssetDocument = new Query();
        queryAssetDocument.addCriteria(Criteria.where("amcAssetId").is(assetId));
        List<AssetDocument> assetDocuments = wszccTemplate.find(queryAssetDocument, AssetDocument.class);

        Query queryAssetImage = new Query();
        queryAssetImage.addCriteria(Criteria.where("amcAssetId").is(assetId));
        List<AssetImage> assetImages = wszccTemplate.find(queryAssetImage, AssetImage.class);

        AmcAssetDetailVo amcAssetDetailVo = new AmcAssetDetailVo();
        amcAssetDetailVo.setAmcAssetVo(amcAssetVo);
        if(!CollectionUtils.isEmpty(assetAdditionals)){
            amcAssetDetailVo.setAssetAdditional(assetAdditionals.get(0));
        }
        if(!CollectionUtils.isEmpty(assetComments)){
            amcAssetDetailVo.setAssetComments(assetComments);
        }

        if(!CollectionUtils.isEmpty(assetDocuments)){
            amcAssetDetailVo.setAssetDocument(assetDocuments);
        }

        if(!CollectionUtils.isEmpty(assetImages)){
            amcAssetDetailVo.setAssetImageList(assetImages);
        }
        return amcAssetDetailVo;

    }

    @Override
    public List<AmcAssetDetailVo> queryAssetDetails(Long amcDebtId) {
        AmcAssetExample amcAssetExample = new AmcAssetExample();
        amcAssetExample.createCriteria().andDebtIdEqualTo(amcDebtId);
        List<AmcAsset> amcAssets = amcAssetMapper.selectByExample(amcAssetExample);
        List<AmcAssetDetailVo> amcAssetDetailVos = new ArrayList<>();
        for(AmcAsset amcAsset: amcAssets){
            amcAssetDetailVos.add(queryMongoForAmcAsset(amcAsset));
        }
        return amcAssetDetailVos;
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
        AmcAssetExample amcAssetExample = getAmcAssetExampleWithQueryParam(queryParam);
        return amcAssetMapper.countByExample(amcAssetExample);
    }

    @Override
    public Map<String, List<Long>> getAllAssetTitles() {
        AmcAssetExample amcAssetExample = new AmcAssetExample();
//        amcAssetExample.setDistinct(true);
        List<AmcAsset> amcAssets =  amcAssetExtMapper.selectAllTitlesByExample(amcAssetExample);
        Map<String, List<Long>> titleMap = new HashMap<>();
        for(AmcAsset amcAsset: amcAssets){
            if(!titleMap.containsKey(amcAsset.getTitle())){
                titleMap.put(amcAsset.getTitle(), new ArrayList<Long>());
            }
            titleMap.get(amcAsset.getTitle()).add(amcAsset.getId());

        }
        return titleMap;
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
