package com.wensheng.zcc.amc.dao.mongo;

import com.wensheng.zcc.amc.module.dao.mongo.origin.AmcAssetOrigin;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author chenwei on 12/7/18
 * @project zcc-backend
 */
public interface OriginAssetRepo extends MongoRepository<AmcAssetOrigin, String> {
}

