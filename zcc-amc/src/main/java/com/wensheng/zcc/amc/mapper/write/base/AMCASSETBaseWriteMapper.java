package packageMapper.write.base;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import packageModel.AMCASSET;
/**
*  @author author
*/
public interface AMCASSETBaseWriteMapper {

    int insertAMCASSET(AMCASSET object);

    int updateAMCASSET(AMCASSET object);

    int update(AMCASSET.UpdateBuilder object);

}