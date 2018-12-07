package packageMapper.read.base;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import packageModel.AMCASSET;
/**
*  @author author
*/
public interface AMCASSETBaseReadMapper {


    List<AMCASSET> queryAMCASSET(AMCASSET object);

    AMCASSET queryAMCASSETLimit1(AMCASSET object);

}