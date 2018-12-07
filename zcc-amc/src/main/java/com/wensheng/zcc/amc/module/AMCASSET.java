package packageModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
/**
*
*  @author author
*/
public class AMCASSET implements Serializable {

    private static final long serialVersionUID = 1544164198839L;


    /**
    * 主键
    * 
    * isNullAble:0
    */
    private Long id;

    /**
    * 抵押物名称
    * isNullAble:0,defaultVal:-1
    */
    private String title;

    /**
    * 抵押物类别
    * isNullAble:0,defaultVal:-1
    */
    private Integer type;

    /**
    * 抵押物处置状态
    * isNullAble:0,defaultVal:-1
    */
    private Integer status;

    /**
    * 抵押状态 质押 1、冻结 2、抵押 3、查封 4
    * isNullAble:0,defaultVal:-1
    */
    private Integer state;

    /**
    * 1-发布， 2-未发布， 0-已删除， 3-已放弃， 4-已售出
    * isNullAble:0,defaultVal:-1
    */
    private Integer editStatus;

    /**
    * 抵押物所属AMC ID
    * isNullAble:0,defaultVal:-1
    */
    private Long amcId;

    /**
    * AMC内部编码
    * isNullAble:0,defaultVal:-1
    */
    private String amcCode;

    /**
    * 抵押物类别司法评估价
    * isNullAble:0,defaultVal:-1
    */
    private String estmPrice;

    /**
    * 
    * isNullAble:0,defaultVal:-1
    */
    private Long debtId;

    /**
    * 拍卖起拍价
    * isNullAble:0,defaultVal:-1
    */
    private String initPrice;

    /**
    * 是否限购
    * isNullAble:0,defaultVal:-1
    */
    private Integer restrictions;

    /**
    * 面积(只适用不动产)
    * isNullAble:0,defaultVal:-1
    */
    private String area;

    /**
    * 土地面积
    * isNullAble:0,defaultVal:-1
    */
    private String landArea;

    /**
    * 发布时间
    * isNullAble:0,defaultVal:1900-01-01
    */
    private java.time.LocalDate publishDate;

    /**
    * 抵押物所在省
    * isNullAble:0,defaultVal:-1
    */
    private String province;

    /**
    * 抵押物所在市
    * isNullAble:0,defaultVal:-1
    */
    private String city;

    /**
    * 抵押物所在县
    * isNullAble:0,defaultVal:-1
    */
    private String county;

    /**
    * 抵押物地址
    * isNullAble:0,defaultVal:-1
    */
    private String street;

    /**
    * 小区(楼盘)名字 爬取使用
    * isNullAble:0,defaultVal:-1
    */
    private String buildingName;

    /**
    * gps 坐标经度
    * isNullAble:0,defaultVal:0
    */
    private String gpsLng;

    /**
    * gps 坐标纬度
    * isNullAble:0,defaultVal:0
    */
    private String gpsLat;


    public void setId(Long id){this.id = id;}

    public Long getId(){return this.id;}

    public void setTitle(String title){this.title = title;}

    public String getTitle(){return this.title;}

    public void setType(Integer type){this.type = type;}

    public Integer getType(){return this.type;}

    public void setStatus(Integer status){this.status = status;}

    public Integer getStatus(){return this.status;}

    public void setState(Integer state){this.state = state;}

    public Integer getState(){return this.state;}

    public void setEditStatus(Integer editStatus){this.editStatus = editStatus;}

    public Integer getEditStatus(){return this.editStatus;}

    public void setAmcId(Long amcId){this.amcId = amcId;}

    public Long getAmcId(){return this.amcId;}

    public void setAmcCode(String amcCode){this.amcCode = amcCode;}

    public String getAmcCode(){return this.amcCode;}

    public void setEstmPrice(String estmPrice){this.estmPrice = estmPrice;}

    public String getEstmPrice(){return this.estmPrice;}

    public void setDebtId(Long debtId){this.debtId = debtId;}

    public Long getDebtId(){return this.debtId;}

    public void setInitPrice(String initPrice){this.initPrice = initPrice;}

    public String getInitPrice(){return this.initPrice;}

    public void setRestrictions(Integer restrictions){this.restrictions = restrictions;}

    public Integer getRestrictions(){return this.restrictions;}

    public void setArea(String area){this.area = area;}

    public String getArea(){return this.area;}

    public void setLandArea(String landArea){this.landArea = landArea;}

    public String getLandArea(){return this.landArea;}

    public void setPublishDate(java.time.LocalDate publishDate){this.publishDate = publishDate;}

    public java.time.LocalDate getPublishDate(){return this.publishDate;}

    public void setProvince(String province){this.province = province;}

    public String getProvince(){return this.province;}

    public void setCity(String city){this.city = city;}

    public String getCity(){return this.city;}

    public void setCounty(String county){this.county = county;}

    public String getCounty(){return this.county;}

    public void setStreet(String street){this.street = street;}

    public String getStreet(){return this.street;}

    public void setBuildingName(String buildingName){this.buildingName = buildingName;}

    public String getBuildingName(){return this.buildingName;}

    public void setGpsLng(String gpsLng){this.gpsLng = gpsLng;}

    public String getGpsLng(){return this.gpsLng;}

    public void setGpsLat(String gpsLat){this.gpsLat = gpsLat;}

    public String getGpsLat(){return this.gpsLat;}
    @Override
    public String toString() {
        return "AMCASSET{" +
                "id='" + id + '\'' +
                "title='" + title + '\'' +
                "type='" + type + '\'' +
                "status='" + status + '\'' +
                "state='" + state + '\'' +
                "editStatus='" + editStatus + '\'' +
                "amcId='" + amcId + '\'' +
                "amcCode='" + amcCode + '\'' +
                "estmPrice='" + estmPrice + '\'' +
                "debtId='" + debtId + '\'' +
                "initPrice='" + initPrice + '\'' +
                "restrictions='" + restrictions + '\'' +
                "area='" + area + '\'' +
                "landArea='" + landArea + '\'' +
                "publishDate='" + publishDate + '\'' +
                "province='" + province + '\'' +
                "city='" + city + '\'' +
                "county='" + county + '\'' +
                "street='" + street + '\'' +
                "buildingName='" + buildingName + '\'' +
                "gpsLng='" + gpsLng + '\'' +
                "gpsLat='" + gpsLat + '\'' +
            '}';
    }

    public static Builder Build(){return new Builder();}

    public static ConditionBuilder ConditionBuild(){return new ConditionBuilder();}

    public static UpdateBuilder UpdateBuild(){return new UpdateBuilder();}

    public static QueryBuilder QueryBuild(){return new QueryBuilder();}

    public static class UpdateBuilder {

        private AMCASSET set;

        private ConditionBuilder where;

        public UpdateBuilder set(AMCASSET set){
            this.set = set;
            return this;
        }

        public AMCASSET getSet(){
            return this.set;
        }

        public UpdateBuilder where(ConditionBuilder where){
            this.where = where;
            return this;
        }

        public ConditionBuilder getWhere(){
            return this.where;
        }

        public UpdateBuilder build(){
            return this;
        }
    }

    public static class QueryBuilder extends AMCASSET{
        /**
        * 需要返回的列
        */
        private Map<String,Object> fetchFields;

        public Map<String,Object> getFetchFields(){return this.fetchFields;}

        private List<Long> idList;

        public List<Long> getIdList(){return this.idList;}

        private Long idSt;

        private Long idEd;

        public Long getIdSt(){return this.idSt;}

        public Long getIdEd(){return this.idEd;}

        private List<String> titleList;

        public List<String> getTitleList(){return this.titleList;}


        private List<String> fuzzyTitle;

        public List<String> getFuzzyTitle(){return this.fuzzyTitle;}

        private List<String> rightFuzzyTitle;

        public List<String> getRightFuzzyTitle(){return this.rightFuzzyTitle;}
        private List<Integer> typeList;

        public List<Integer> getTypeList(){return this.typeList;}

        private Integer typeSt;

        private Integer typeEd;

        public Integer getTypeSt(){return this.typeSt;}

        public Integer getTypeEd(){return this.typeEd;}

        private List<Integer> statusList;

        public List<Integer> getStatusList(){return this.statusList;}

        private Integer statusSt;

        private Integer statusEd;

        public Integer getStatusSt(){return this.statusSt;}

        public Integer getStatusEd(){return this.statusEd;}

        private List<Integer> stateList;

        public List<Integer> getStateList(){return this.stateList;}

        private Integer stateSt;

        private Integer stateEd;

        public Integer getStateSt(){return this.stateSt;}

        public Integer getStateEd(){return this.stateEd;}

        private List<Integer> editStatusList;

        public List<Integer> getEditStatusList(){return this.editStatusList;}

        private Integer editStatusSt;

        private Integer editStatusEd;

        public Integer getEditStatusSt(){return this.editStatusSt;}

        public Integer getEditStatusEd(){return this.editStatusEd;}

        private List<Long> amcIdList;

        public List<Long> getAmcIdList(){return this.amcIdList;}

        private Long amcIdSt;

        private Long amcIdEd;

        public Long getAmcIdSt(){return this.amcIdSt;}

        public Long getAmcIdEd(){return this.amcIdEd;}

        private List<String> amcCodeList;

        public List<String> getAmcCodeList(){return this.amcCodeList;}


        private List<String> fuzzyAmcCode;

        public List<String> getFuzzyAmcCode(){return this.fuzzyAmcCode;}

        private List<String> rightFuzzyAmcCode;

        public List<String> getRightFuzzyAmcCode(){return this.rightFuzzyAmcCode;}
        private List<String> estmPriceList;

        public List<String> getEstmPriceList(){return this.estmPriceList;}


        private List<String> fuzzyEstmPrice;

        public List<String> getFuzzyEstmPrice(){return this.fuzzyEstmPrice;}

        private List<String> rightFuzzyEstmPrice;

        public List<String> getRightFuzzyEstmPrice(){return this.rightFuzzyEstmPrice;}
        private List<Long> debtIdList;

        public List<Long> getDebtIdList(){return this.debtIdList;}

        private Long debtIdSt;

        private Long debtIdEd;

        public Long getDebtIdSt(){return this.debtIdSt;}

        public Long getDebtIdEd(){return this.debtIdEd;}

        private List<String> initPriceList;

        public List<String> getInitPriceList(){return this.initPriceList;}


        private List<String> fuzzyInitPrice;

        public List<String> getFuzzyInitPrice(){return this.fuzzyInitPrice;}

        private List<String> rightFuzzyInitPrice;

        public List<String> getRightFuzzyInitPrice(){return this.rightFuzzyInitPrice;}
        private List<Integer> restrictionsList;

        public List<Integer> getRestrictionsList(){return this.restrictionsList;}

        private Integer restrictionsSt;

        private Integer restrictionsEd;

        public Integer getRestrictionsSt(){return this.restrictionsSt;}

        public Integer getRestrictionsEd(){return this.restrictionsEd;}

        private List<String> areaList;

        public List<String> getAreaList(){return this.areaList;}


        private List<String> fuzzyArea;

        public List<String> getFuzzyArea(){return this.fuzzyArea;}

        private List<String> rightFuzzyArea;

        public List<String> getRightFuzzyArea(){return this.rightFuzzyArea;}
        private List<String> landAreaList;

        public List<String> getLandAreaList(){return this.landAreaList;}


        private List<String> fuzzyLandArea;

        public List<String> getFuzzyLandArea(){return this.fuzzyLandArea;}

        private List<String> rightFuzzyLandArea;

        public List<String> getRightFuzzyLandArea(){return this.rightFuzzyLandArea;}
        private List<java.time.LocalDate> publishDateList;

        public List<java.time.LocalDate> getPublishDateList(){return this.publishDateList;}

        private java.time.LocalDate publishDateSt;

        private java.time.LocalDate publishDateEd;

        public java.time.LocalDate getPublishDateSt(){return this.publishDateSt;}

        public java.time.LocalDate getPublishDateEd(){return this.publishDateEd;}

        private List<String> provinceList;

        public List<String> getProvinceList(){return this.provinceList;}


        private List<String> fuzzyProvince;

        public List<String> getFuzzyProvince(){return this.fuzzyProvince;}

        private List<String> rightFuzzyProvince;

        public List<String> getRightFuzzyProvince(){return this.rightFuzzyProvince;}
        private List<String> cityList;

        public List<String> getCityList(){return this.cityList;}


        private List<String> fuzzyCity;

        public List<String> getFuzzyCity(){return this.fuzzyCity;}

        private List<String> rightFuzzyCity;

        public List<String> getRightFuzzyCity(){return this.rightFuzzyCity;}
        private List<String> countyList;

        public List<String> getCountyList(){return this.countyList;}


        private List<String> fuzzyCounty;

        public List<String> getFuzzyCounty(){return this.fuzzyCounty;}

        private List<String> rightFuzzyCounty;

        public List<String> getRightFuzzyCounty(){return this.rightFuzzyCounty;}
        private List<String> streetList;

        public List<String> getStreetList(){return this.streetList;}


        private List<String> fuzzyStreet;

        public List<String> getFuzzyStreet(){return this.fuzzyStreet;}

        private List<String> rightFuzzyStreet;

        public List<String> getRightFuzzyStreet(){return this.rightFuzzyStreet;}
        private List<String> buildingNameList;

        public List<String> getBuildingNameList(){return this.buildingNameList;}


        private List<String> fuzzyBuildingName;

        public List<String> getFuzzyBuildingName(){return this.fuzzyBuildingName;}

        private List<String> rightFuzzyBuildingName;

        public List<String> getRightFuzzyBuildingName(){return this.rightFuzzyBuildingName;}
        private List<String> gpsLngList;

        public List<String> getGpsLngList(){return this.gpsLngList;}


        private List<String> fuzzyGpsLng;

        public List<String> getFuzzyGpsLng(){return this.fuzzyGpsLng;}

        private List<String> rightFuzzyGpsLng;

        public List<String> getRightFuzzyGpsLng(){return this.rightFuzzyGpsLng;}
        private List<String> gpsLatList;

        public List<String> getGpsLatList(){return this.gpsLatList;}


        private List<String> fuzzyGpsLat;

        public List<String> getFuzzyGpsLat(){return this.fuzzyGpsLat;}

        private List<String> rightFuzzyGpsLat;

        public List<String> getRightFuzzyGpsLat(){return this.rightFuzzyGpsLat;}
        private QueryBuilder (){
            this.fetchFields = new HashMap<>();
        }

        public QueryBuilder idBetWeen(Long idSt,Long idEd){
            this.idSt = idSt;
            this.idEd = idEd;
            return this;
        }

        public QueryBuilder idGreaterEqThan(Long idSt){
            this.idSt = idSt;
            return this;
        }
        public QueryBuilder idLessEqThan(Long idEd){
            this.idEd = idEd;
            return this;
        }


        public QueryBuilder id(Long id){
            setId(id);
            return this;
        }

        public QueryBuilder idList(Long ... id){
            this.idList = solveNullList(id);
            return this;
        }

        public QueryBuilder idList(List<Long> id){
            this.idList = id;
            return this;
        }

        public QueryBuilder fetchId(){
            setFetchFields("fetchFields","id");
            return this;
        }

        public QueryBuilder excludeId(){
            setFetchFields("excludeFields","id");
            return this;
        }

        public QueryBuilder fuzzyTitle (List<String> fuzzyTitle){
            this.fuzzyTitle = fuzzyTitle;
            return this;
        }

        public QueryBuilder fuzzyTitle (String ... fuzzyTitle){
            this.fuzzyTitle = solveNullList(fuzzyTitle);
            return this;
        }

        public QueryBuilder rightFuzzyTitle (List<String> rightFuzzyTitle){
            this.rightFuzzyTitle = rightFuzzyTitle;
            return this;
        }

        public QueryBuilder rightFuzzyTitle (String ... rightFuzzyTitle){
            this.rightFuzzyTitle = solveNullList(rightFuzzyTitle);
            return this;
        }

        public QueryBuilder title(String title){
            setTitle(title);
            return this;
        }

        public QueryBuilder titleList(String ... title){
            this.titleList = solveNullList(title);
            return this;
        }

        public QueryBuilder titleList(List<String> title){
            this.titleList = title;
            return this;
        }

        public QueryBuilder fetchTitle(){
            setFetchFields("fetchFields","title");
            return this;
        }

        public QueryBuilder excludeTitle(){
            setFetchFields("excludeFields","title");
            return this;
        }

        public QueryBuilder typeBetWeen(Integer typeSt,Integer typeEd){
            this.typeSt = typeSt;
            this.typeEd = typeEd;
            return this;
        }

        public QueryBuilder typeGreaterEqThan(Integer typeSt){
            this.typeSt = typeSt;
            return this;
        }
        public QueryBuilder typeLessEqThan(Integer typeEd){
            this.typeEd = typeEd;
            return this;
        }


        public QueryBuilder type(Integer type){
            setType(type);
            return this;
        }

        public QueryBuilder typeList(Integer ... type){
            this.typeList = solveNullList(type);
            return this;
        }

        public QueryBuilder typeList(List<Integer> type){
            this.typeList = type;
            return this;
        }

        public QueryBuilder fetchType(){
            setFetchFields("fetchFields","type");
            return this;
        }

        public QueryBuilder excludeType(){
            setFetchFields("excludeFields","type");
            return this;
        }

        public QueryBuilder statusBetWeen(Integer statusSt,Integer statusEd){
            this.statusSt = statusSt;
            this.statusEd = statusEd;
            return this;
        }

        public QueryBuilder statusGreaterEqThan(Integer statusSt){
            this.statusSt = statusSt;
            return this;
        }
        public QueryBuilder statusLessEqThan(Integer statusEd){
            this.statusEd = statusEd;
            return this;
        }


        public QueryBuilder status(Integer status){
            setStatus(status);
            return this;
        }

        public QueryBuilder statusList(Integer ... status){
            this.statusList = solveNullList(status);
            return this;
        }

        public QueryBuilder statusList(List<Integer> status){
            this.statusList = status;
            return this;
        }

        public QueryBuilder fetchStatus(){
            setFetchFields("fetchFields","status");
            return this;
        }

        public QueryBuilder excludeStatus(){
            setFetchFields("excludeFields","status");
            return this;
        }

        public QueryBuilder stateBetWeen(Integer stateSt,Integer stateEd){
            this.stateSt = stateSt;
            this.stateEd = stateEd;
            return this;
        }

        public QueryBuilder stateGreaterEqThan(Integer stateSt){
            this.stateSt = stateSt;
            return this;
        }
        public QueryBuilder stateLessEqThan(Integer stateEd){
            this.stateEd = stateEd;
            return this;
        }


        public QueryBuilder state(Integer state){
            setState(state);
            return this;
        }

        public QueryBuilder stateList(Integer ... state){
            this.stateList = solveNullList(state);
            return this;
        }

        public QueryBuilder stateList(List<Integer> state){
            this.stateList = state;
            return this;
        }

        public QueryBuilder fetchState(){
            setFetchFields("fetchFields","state");
            return this;
        }

        public QueryBuilder excludeState(){
            setFetchFields("excludeFields","state");
            return this;
        }

        public QueryBuilder editStatusBetWeen(Integer editStatusSt,Integer editStatusEd){
            this.editStatusSt = editStatusSt;
            this.editStatusEd = editStatusEd;
            return this;
        }

        public QueryBuilder editStatusGreaterEqThan(Integer editStatusSt){
            this.editStatusSt = editStatusSt;
            return this;
        }
        public QueryBuilder editStatusLessEqThan(Integer editStatusEd){
            this.editStatusEd = editStatusEd;
            return this;
        }


        public QueryBuilder editStatus(Integer editStatus){
            setEditStatus(editStatus);
            return this;
        }

        public QueryBuilder editStatusList(Integer ... editStatus){
            this.editStatusList = solveNullList(editStatus);
            return this;
        }

        public QueryBuilder editStatusList(List<Integer> editStatus){
            this.editStatusList = editStatus;
            return this;
        }

        public QueryBuilder fetchEditStatus(){
            setFetchFields("fetchFields","editStatus");
            return this;
        }

        public QueryBuilder excludeEditStatus(){
            setFetchFields("excludeFields","editStatus");
            return this;
        }

        public QueryBuilder amcIdBetWeen(Long amcIdSt,Long amcIdEd){
            this.amcIdSt = amcIdSt;
            this.amcIdEd = amcIdEd;
            return this;
        }

        public QueryBuilder amcIdGreaterEqThan(Long amcIdSt){
            this.amcIdSt = amcIdSt;
            return this;
        }
        public QueryBuilder amcIdLessEqThan(Long amcIdEd){
            this.amcIdEd = amcIdEd;
            return this;
        }


        public QueryBuilder amcId(Long amcId){
            setAmcId(amcId);
            return this;
        }

        public QueryBuilder amcIdList(Long ... amcId){
            this.amcIdList = solveNullList(amcId);
            return this;
        }

        public QueryBuilder amcIdList(List<Long> amcId){
            this.amcIdList = amcId;
            return this;
        }

        public QueryBuilder fetchAmcId(){
            setFetchFields("fetchFields","amcId");
            return this;
        }

        public QueryBuilder excludeAmcId(){
            setFetchFields("excludeFields","amcId");
            return this;
        }

        public QueryBuilder fuzzyAmcCode (List<String> fuzzyAmcCode){
            this.fuzzyAmcCode = fuzzyAmcCode;
            return this;
        }

        public QueryBuilder fuzzyAmcCode (String ... fuzzyAmcCode){
            this.fuzzyAmcCode = solveNullList(fuzzyAmcCode);
            return this;
        }

        public QueryBuilder rightFuzzyAmcCode (List<String> rightFuzzyAmcCode){
            this.rightFuzzyAmcCode = rightFuzzyAmcCode;
            return this;
        }

        public QueryBuilder rightFuzzyAmcCode (String ... rightFuzzyAmcCode){
            this.rightFuzzyAmcCode = solveNullList(rightFuzzyAmcCode);
            return this;
        }

        public QueryBuilder amcCode(String amcCode){
            setAmcCode(amcCode);
            return this;
        }

        public QueryBuilder amcCodeList(String ... amcCode){
            this.amcCodeList = solveNullList(amcCode);
            return this;
        }

        public QueryBuilder amcCodeList(List<String> amcCode){
            this.amcCodeList = amcCode;
            return this;
        }

        public QueryBuilder fetchAmcCode(){
            setFetchFields("fetchFields","amcCode");
            return this;
        }

        public QueryBuilder excludeAmcCode(){
            setFetchFields("excludeFields","amcCode");
            return this;
        }

        public QueryBuilder fuzzyEstmPrice (List<String> fuzzyEstmPrice){
            this.fuzzyEstmPrice = fuzzyEstmPrice;
            return this;
        }

        public QueryBuilder fuzzyEstmPrice (String ... fuzzyEstmPrice){
            this.fuzzyEstmPrice = solveNullList(fuzzyEstmPrice);
            return this;
        }

        public QueryBuilder rightFuzzyEstmPrice (List<String> rightFuzzyEstmPrice){
            this.rightFuzzyEstmPrice = rightFuzzyEstmPrice;
            return this;
        }

        public QueryBuilder rightFuzzyEstmPrice (String ... rightFuzzyEstmPrice){
            this.rightFuzzyEstmPrice = solveNullList(rightFuzzyEstmPrice);
            return this;
        }

        public QueryBuilder estmPrice(String estmPrice){
            setEstmPrice(estmPrice);
            return this;
        }

        public QueryBuilder estmPriceList(String ... estmPrice){
            this.estmPriceList = solveNullList(estmPrice);
            return this;
        }

        public QueryBuilder estmPriceList(List<String> estmPrice){
            this.estmPriceList = estmPrice;
            return this;
        }

        public QueryBuilder fetchEstmPrice(){
            setFetchFields("fetchFields","estmPrice");
            return this;
        }

        public QueryBuilder excludeEstmPrice(){
            setFetchFields("excludeFields","estmPrice");
            return this;
        }

        public QueryBuilder debtIdBetWeen(Long debtIdSt,Long debtIdEd){
            this.debtIdSt = debtIdSt;
            this.debtIdEd = debtIdEd;
            return this;
        }

        public QueryBuilder debtIdGreaterEqThan(Long debtIdSt){
            this.debtIdSt = debtIdSt;
            return this;
        }
        public QueryBuilder debtIdLessEqThan(Long debtIdEd){
            this.debtIdEd = debtIdEd;
            return this;
        }


        public QueryBuilder debtId(Long debtId){
            setDebtId(debtId);
            return this;
        }

        public QueryBuilder debtIdList(Long ... debtId){
            this.debtIdList = solveNullList(debtId);
            return this;
        }

        public QueryBuilder debtIdList(List<Long> debtId){
            this.debtIdList = debtId;
            return this;
        }

        public QueryBuilder fetchDebtId(){
            setFetchFields("fetchFields","debtId");
            return this;
        }

        public QueryBuilder excludeDebtId(){
            setFetchFields("excludeFields","debtId");
            return this;
        }

        public QueryBuilder fuzzyInitPrice (List<String> fuzzyInitPrice){
            this.fuzzyInitPrice = fuzzyInitPrice;
            return this;
        }

        public QueryBuilder fuzzyInitPrice (String ... fuzzyInitPrice){
            this.fuzzyInitPrice = solveNullList(fuzzyInitPrice);
            return this;
        }

        public QueryBuilder rightFuzzyInitPrice (List<String> rightFuzzyInitPrice){
            this.rightFuzzyInitPrice = rightFuzzyInitPrice;
            return this;
        }

        public QueryBuilder rightFuzzyInitPrice (String ... rightFuzzyInitPrice){
            this.rightFuzzyInitPrice = solveNullList(rightFuzzyInitPrice);
            return this;
        }

        public QueryBuilder initPrice(String initPrice){
            setInitPrice(initPrice);
            return this;
        }

        public QueryBuilder initPriceList(String ... initPrice){
            this.initPriceList = solveNullList(initPrice);
            return this;
        }

        public QueryBuilder initPriceList(List<String> initPrice){
            this.initPriceList = initPrice;
            return this;
        }

        public QueryBuilder fetchInitPrice(){
            setFetchFields("fetchFields","initPrice");
            return this;
        }

        public QueryBuilder excludeInitPrice(){
            setFetchFields("excludeFields","initPrice");
            return this;
        }

        public QueryBuilder restrictionsBetWeen(Integer restrictionsSt,Integer restrictionsEd){
            this.restrictionsSt = restrictionsSt;
            this.restrictionsEd = restrictionsEd;
            return this;
        }

        public QueryBuilder restrictionsGreaterEqThan(Integer restrictionsSt){
            this.restrictionsSt = restrictionsSt;
            return this;
        }
        public QueryBuilder restrictionsLessEqThan(Integer restrictionsEd){
            this.restrictionsEd = restrictionsEd;
            return this;
        }


        public QueryBuilder restrictions(Integer restrictions){
            setRestrictions(restrictions);
            return this;
        }

        public QueryBuilder restrictionsList(Integer ... restrictions){
            this.restrictionsList = solveNullList(restrictions);
            return this;
        }

        public QueryBuilder restrictionsList(List<Integer> restrictions){
            this.restrictionsList = restrictions;
            return this;
        }

        public QueryBuilder fetchRestrictions(){
            setFetchFields("fetchFields","restrictions");
            return this;
        }

        public QueryBuilder excludeRestrictions(){
            setFetchFields("excludeFields","restrictions");
            return this;
        }

        public QueryBuilder fuzzyArea (List<String> fuzzyArea){
            this.fuzzyArea = fuzzyArea;
            return this;
        }

        public QueryBuilder fuzzyArea (String ... fuzzyArea){
            this.fuzzyArea = solveNullList(fuzzyArea);
            return this;
        }

        public QueryBuilder rightFuzzyArea (List<String> rightFuzzyArea){
            this.rightFuzzyArea = rightFuzzyArea;
            return this;
        }

        public QueryBuilder rightFuzzyArea (String ... rightFuzzyArea){
            this.rightFuzzyArea = solveNullList(rightFuzzyArea);
            return this;
        }

        public QueryBuilder area(String area){
            setArea(area);
            return this;
        }

        public QueryBuilder areaList(String ... area){
            this.areaList = solveNullList(area);
            return this;
        }

        public QueryBuilder areaList(List<String> area){
            this.areaList = area;
            return this;
        }

        public QueryBuilder fetchArea(){
            setFetchFields("fetchFields","area");
            return this;
        }

        public QueryBuilder excludeArea(){
            setFetchFields("excludeFields","area");
            return this;
        }

        public QueryBuilder fuzzyLandArea (List<String> fuzzyLandArea){
            this.fuzzyLandArea = fuzzyLandArea;
            return this;
        }

        public QueryBuilder fuzzyLandArea (String ... fuzzyLandArea){
            this.fuzzyLandArea = solveNullList(fuzzyLandArea);
            return this;
        }

        public QueryBuilder rightFuzzyLandArea (List<String> rightFuzzyLandArea){
            this.rightFuzzyLandArea = rightFuzzyLandArea;
            return this;
        }

        public QueryBuilder rightFuzzyLandArea (String ... rightFuzzyLandArea){
            this.rightFuzzyLandArea = solveNullList(rightFuzzyLandArea);
            return this;
        }

        public QueryBuilder landArea(String landArea){
            setLandArea(landArea);
            return this;
        }

        public QueryBuilder landAreaList(String ... landArea){
            this.landAreaList = solveNullList(landArea);
            return this;
        }

        public QueryBuilder landAreaList(List<String> landArea){
            this.landAreaList = landArea;
            return this;
        }

        public QueryBuilder fetchLandArea(){
            setFetchFields("fetchFields","landArea");
            return this;
        }

        public QueryBuilder excludeLandArea(){
            setFetchFields("excludeFields","landArea");
            return this;
        }

        public QueryBuilder publishDateBetWeen(java.time.LocalDate publishDateSt,java.time.LocalDate publishDateEd){
            this.publishDateSt = publishDateSt;
            this.publishDateEd = publishDateEd;
            return this;
        }

        public QueryBuilder publishDateGreaterEqThan(java.time.LocalDate publishDateSt){
            this.publishDateSt = publishDateSt;
            return this;
        }
        public QueryBuilder publishDateLessEqThan(java.time.LocalDate publishDateEd){
            this.publishDateEd = publishDateEd;
            return this;
        }


        public QueryBuilder publishDate(java.time.LocalDate publishDate){
            setPublishDate(publishDate);
            return this;
        }

        public QueryBuilder publishDateList(java.time.LocalDate ... publishDate){
            this.publishDateList = solveNullList(publishDate);
            return this;
        }

        public QueryBuilder publishDateList(List<java.time.LocalDate> publishDate){
            this.publishDateList = publishDate;
            return this;
        }

        public QueryBuilder fetchPublishDate(){
            setFetchFields("fetchFields","publishDate");
            return this;
        }

        public QueryBuilder excludePublishDate(){
            setFetchFields("excludeFields","publishDate");
            return this;
        }

        public QueryBuilder fuzzyProvince (List<String> fuzzyProvince){
            this.fuzzyProvince = fuzzyProvince;
            return this;
        }

        public QueryBuilder fuzzyProvince (String ... fuzzyProvince){
            this.fuzzyProvince = solveNullList(fuzzyProvince);
            return this;
        }

        public QueryBuilder rightFuzzyProvince (List<String> rightFuzzyProvince){
            this.rightFuzzyProvince = rightFuzzyProvince;
            return this;
        }

        public QueryBuilder rightFuzzyProvince (String ... rightFuzzyProvince){
            this.rightFuzzyProvince = solveNullList(rightFuzzyProvince);
            return this;
        }

        public QueryBuilder province(String province){
            setProvince(province);
            return this;
        }

        public QueryBuilder provinceList(String ... province){
            this.provinceList = solveNullList(province);
            return this;
        }

        public QueryBuilder provinceList(List<String> province){
            this.provinceList = province;
            return this;
        }

        public QueryBuilder fetchProvince(){
            setFetchFields("fetchFields","province");
            return this;
        }

        public QueryBuilder excludeProvince(){
            setFetchFields("excludeFields","province");
            return this;
        }

        public QueryBuilder fuzzyCity (List<String> fuzzyCity){
            this.fuzzyCity = fuzzyCity;
            return this;
        }

        public QueryBuilder fuzzyCity (String ... fuzzyCity){
            this.fuzzyCity = solveNullList(fuzzyCity);
            return this;
        }

        public QueryBuilder rightFuzzyCity (List<String> rightFuzzyCity){
            this.rightFuzzyCity = rightFuzzyCity;
            return this;
        }

        public QueryBuilder rightFuzzyCity (String ... rightFuzzyCity){
            this.rightFuzzyCity = solveNullList(rightFuzzyCity);
            return this;
        }

        public QueryBuilder city(String city){
            setCity(city);
            return this;
        }

        public QueryBuilder cityList(String ... city){
            this.cityList = solveNullList(city);
            return this;
        }

        public QueryBuilder cityList(List<String> city){
            this.cityList = city;
            return this;
        }

        public QueryBuilder fetchCity(){
            setFetchFields("fetchFields","city");
            return this;
        }

        public QueryBuilder excludeCity(){
            setFetchFields("excludeFields","city");
            return this;
        }

        public QueryBuilder fuzzyCounty (List<String> fuzzyCounty){
            this.fuzzyCounty = fuzzyCounty;
            return this;
        }

        public QueryBuilder fuzzyCounty (String ... fuzzyCounty){
            this.fuzzyCounty = solveNullList(fuzzyCounty);
            return this;
        }

        public QueryBuilder rightFuzzyCounty (List<String> rightFuzzyCounty){
            this.rightFuzzyCounty = rightFuzzyCounty;
            return this;
        }

        public QueryBuilder rightFuzzyCounty (String ... rightFuzzyCounty){
            this.rightFuzzyCounty = solveNullList(rightFuzzyCounty);
            return this;
        }

        public QueryBuilder county(String county){
            setCounty(county);
            return this;
        }

        public QueryBuilder countyList(String ... county){
            this.countyList = solveNullList(county);
            return this;
        }

        public QueryBuilder countyList(List<String> county){
            this.countyList = county;
            return this;
        }

        public QueryBuilder fetchCounty(){
            setFetchFields("fetchFields","county");
            return this;
        }

        public QueryBuilder excludeCounty(){
            setFetchFields("excludeFields","county");
            return this;
        }

        public QueryBuilder fuzzyStreet (List<String> fuzzyStreet){
            this.fuzzyStreet = fuzzyStreet;
            return this;
        }

        public QueryBuilder fuzzyStreet (String ... fuzzyStreet){
            this.fuzzyStreet = solveNullList(fuzzyStreet);
            return this;
        }

        public QueryBuilder rightFuzzyStreet (List<String> rightFuzzyStreet){
            this.rightFuzzyStreet = rightFuzzyStreet;
            return this;
        }

        public QueryBuilder rightFuzzyStreet (String ... rightFuzzyStreet){
            this.rightFuzzyStreet = solveNullList(rightFuzzyStreet);
            return this;
        }

        public QueryBuilder street(String street){
            setStreet(street);
            return this;
        }

        public QueryBuilder streetList(String ... street){
            this.streetList = solveNullList(street);
            return this;
        }

        public QueryBuilder streetList(List<String> street){
            this.streetList = street;
            return this;
        }

        public QueryBuilder fetchStreet(){
            setFetchFields("fetchFields","street");
            return this;
        }

        public QueryBuilder excludeStreet(){
            setFetchFields("excludeFields","street");
            return this;
        }

        public QueryBuilder fuzzyBuildingName (List<String> fuzzyBuildingName){
            this.fuzzyBuildingName = fuzzyBuildingName;
            return this;
        }

        public QueryBuilder fuzzyBuildingName (String ... fuzzyBuildingName){
            this.fuzzyBuildingName = solveNullList(fuzzyBuildingName);
            return this;
        }

        public QueryBuilder rightFuzzyBuildingName (List<String> rightFuzzyBuildingName){
            this.rightFuzzyBuildingName = rightFuzzyBuildingName;
            return this;
        }

        public QueryBuilder rightFuzzyBuildingName (String ... rightFuzzyBuildingName){
            this.rightFuzzyBuildingName = solveNullList(rightFuzzyBuildingName);
            return this;
        }

        public QueryBuilder buildingName(String buildingName){
            setBuildingName(buildingName);
            return this;
        }

        public QueryBuilder buildingNameList(String ... buildingName){
            this.buildingNameList = solveNullList(buildingName);
            return this;
        }

        public QueryBuilder buildingNameList(List<String> buildingName){
            this.buildingNameList = buildingName;
            return this;
        }

        public QueryBuilder fetchBuildingName(){
            setFetchFields("fetchFields","buildingName");
            return this;
        }

        public QueryBuilder excludeBuildingName(){
            setFetchFields("excludeFields","buildingName");
            return this;
        }

        public QueryBuilder fuzzyGpsLng (List<String> fuzzyGpsLng){
            this.fuzzyGpsLng = fuzzyGpsLng;
            return this;
        }

        public QueryBuilder fuzzyGpsLng (String ... fuzzyGpsLng){
            this.fuzzyGpsLng = solveNullList(fuzzyGpsLng);
            return this;
        }

        public QueryBuilder rightFuzzyGpsLng (List<String> rightFuzzyGpsLng){
            this.rightFuzzyGpsLng = rightFuzzyGpsLng;
            return this;
        }

        public QueryBuilder rightFuzzyGpsLng (String ... rightFuzzyGpsLng){
            this.rightFuzzyGpsLng = solveNullList(rightFuzzyGpsLng);
            return this;
        }

        public QueryBuilder gpsLng(String gpsLng){
            setGpsLng(gpsLng);
            return this;
        }

        public QueryBuilder gpsLngList(String ... gpsLng){
            this.gpsLngList = solveNullList(gpsLng);
            return this;
        }

        public QueryBuilder gpsLngList(List<String> gpsLng){
            this.gpsLngList = gpsLng;
            return this;
        }

        public QueryBuilder fetchGpsLng(){
            setFetchFields("fetchFields","gpsLng");
            return this;
        }

        public QueryBuilder excludeGpsLng(){
            setFetchFields("excludeFields","gpsLng");
            return this;
        }

        public QueryBuilder fuzzyGpsLat (List<String> fuzzyGpsLat){
            this.fuzzyGpsLat = fuzzyGpsLat;
            return this;
        }

        public QueryBuilder fuzzyGpsLat (String ... fuzzyGpsLat){
            this.fuzzyGpsLat = solveNullList(fuzzyGpsLat);
            return this;
        }

        public QueryBuilder rightFuzzyGpsLat (List<String> rightFuzzyGpsLat){
            this.rightFuzzyGpsLat = rightFuzzyGpsLat;
            return this;
        }

        public QueryBuilder rightFuzzyGpsLat (String ... rightFuzzyGpsLat){
            this.rightFuzzyGpsLat = solveNullList(rightFuzzyGpsLat);
            return this;
        }

        public QueryBuilder gpsLat(String gpsLat){
            setGpsLat(gpsLat);
            return this;
        }

        public QueryBuilder gpsLatList(String ... gpsLat){
            this.gpsLatList = solveNullList(gpsLat);
            return this;
        }

        public QueryBuilder gpsLatList(List<String> gpsLat){
            this.gpsLatList = gpsLat;
            return this;
        }

        public QueryBuilder fetchGpsLat(){
            setFetchFields("fetchFields","gpsLat");
            return this;
        }

        public QueryBuilder excludeGpsLat(){
            setFetchFields("excludeFields","gpsLat");
            return this;
        }
        private <T>List<T> solveNullList(T ... objs){
            if (objs != null){
            List<T> list = new ArrayList<>();
                for (T item : objs){
                    if (item != null){
                        list.add(item);
                    }
                }
                return list;
            }
            return null;
        }

        public QueryBuilder fetchAll(){
            this.fetchFields.put("AllFields",true);
            return this;
        }

        public QueryBuilder addField(String ... fields){
            List<String> list = new ArrayList<>();
            if (fields != null){
                for (String field : fields){
                    list.add(field);
                }
            }
            this.fetchFields.put("otherFields",list);
            return this;
        }
        @SuppressWarnings("unchecked")
        private void setFetchFields(String key,String val){
            Map<String,Boolean> fields= (Map<String, Boolean>) this.fetchFields.get(key);
            if (fields == null){
                fields = new HashMap<>();
            }
            fields.put(val,true);
            this.fetchFields.put(key,fields);
        }

        public AMCASSET build(){return this;}
    }


    public static class ConditionBuilder{
        private List<Long> idList;

        public List<Long> getIdList(){return this.idList;}

        private Long idSt;

        private Long idEd;

        public Long getIdSt(){return this.idSt;}

        public Long getIdEd(){return this.idEd;}

        private List<String> titleList;

        public List<String> getTitleList(){return this.titleList;}


        private List<String> fuzzyTitle;

        public List<String> getFuzzyTitle(){return this.fuzzyTitle;}

        private List<String> rightFuzzyTitle;

        public List<String> getRightFuzzyTitle(){return this.rightFuzzyTitle;}
        private List<Integer> typeList;

        public List<Integer> getTypeList(){return this.typeList;}

        private Integer typeSt;

        private Integer typeEd;

        public Integer getTypeSt(){return this.typeSt;}

        public Integer getTypeEd(){return this.typeEd;}

        private List<Integer> statusList;

        public List<Integer> getStatusList(){return this.statusList;}

        private Integer statusSt;

        private Integer statusEd;

        public Integer getStatusSt(){return this.statusSt;}

        public Integer getStatusEd(){return this.statusEd;}

        private List<Integer> stateList;

        public List<Integer> getStateList(){return this.stateList;}

        private Integer stateSt;

        private Integer stateEd;

        public Integer getStateSt(){return this.stateSt;}

        public Integer getStateEd(){return this.stateEd;}

        private List<Integer> editStatusList;

        public List<Integer> getEditStatusList(){return this.editStatusList;}

        private Integer editStatusSt;

        private Integer editStatusEd;

        public Integer getEditStatusSt(){return this.editStatusSt;}

        public Integer getEditStatusEd(){return this.editStatusEd;}

        private List<Long> amcIdList;

        public List<Long> getAmcIdList(){return this.amcIdList;}

        private Long amcIdSt;

        private Long amcIdEd;

        public Long getAmcIdSt(){return this.amcIdSt;}

        public Long getAmcIdEd(){return this.amcIdEd;}

        private List<String> amcCodeList;

        public List<String> getAmcCodeList(){return this.amcCodeList;}


        private List<String> fuzzyAmcCode;

        public List<String> getFuzzyAmcCode(){return this.fuzzyAmcCode;}

        private List<String> rightFuzzyAmcCode;

        public List<String> getRightFuzzyAmcCode(){return this.rightFuzzyAmcCode;}
        private List<String> estmPriceList;

        public List<String> getEstmPriceList(){return this.estmPriceList;}


        private List<String> fuzzyEstmPrice;

        public List<String> getFuzzyEstmPrice(){return this.fuzzyEstmPrice;}

        private List<String> rightFuzzyEstmPrice;

        public List<String> getRightFuzzyEstmPrice(){return this.rightFuzzyEstmPrice;}
        private List<Long> debtIdList;

        public List<Long> getDebtIdList(){return this.debtIdList;}

        private Long debtIdSt;

        private Long debtIdEd;

        public Long getDebtIdSt(){return this.debtIdSt;}

        public Long getDebtIdEd(){return this.debtIdEd;}

        private List<String> initPriceList;

        public List<String> getInitPriceList(){return this.initPriceList;}


        private List<String> fuzzyInitPrice;

        public List<String> getFuzzyInitPrice(){return this.fuzzyInitPrice;}

        private List<String> rightFuzzyInitPrice;

        public List<String> getRightFuzzyInitPrice(){return this.rightFuzzyInitPrice;}
        private List<Integer> restrictionsList;

        public List<Integer> getRestrictionsList(){return this.restrictionsList;}

        private Integer restrictionsSt;

        private Integer restrictionsEd;

        public Integer getRestrictionsSt(){return this.restrictionsSt;}

        public Integer getRestrictionsEd(){return this.restrictionsEd;}

        private List<String> areaList;

        public List<String> getAreaList(){return this.areaList;}


        private List<String> fuzzyArea;

        public List<String> getFuzzyArea(){return this.fuzzyArea;}

        private List<String> rightFuzzyArea;

        public List<String> getRightFuzzyArea(){return this.rightFuzzyArea;}
        private List<String> landAreaList;

        public List<String> getLandAreaList(){return this.landAreaList;}


        private List<String> fuzzyLandArea;

        public List<String> getFuzzyLandArea(){return this.fuzzyLandArea;}

        private List<String> rightFuzzyLandArea;

        public List<String> getRightFuzzyLandArea(){return this.rightFuzzyLandArea;}
        private List<java.time.LocalDate> publishDateList;

        public List<java.time.LocalDate> getPublishDateList(){return this.publishDateList;}

        private java.time.LocalDate publishDateSt;

        private java.time.LocalDate publishDateEd;

        public java.time.LocalDate getPublishDateSt(){return this.publishDateSt;}

        public java.time.LocalDate getPublishDateEd(){return this.publishDateEd;}

        private List<String> provinceList;

        public List<String> getProvinceList(){return this.provinceList;}


        private List<String> fuzzyProvince;

        public List<String> getFuzzyProvince(){return this.fuzzyProvince;}

        private List<String> rightFuzzyProvince;

        public List<String> getRightFuzzyProvince(){return this.rightFuzzyProvince;}
        private List<String> cityList;

        public List<String> getCityList(){return this.cityList;}


        private List<String> fuzzyCity;

        public List<String> getFuzzyCity(){return this.fuzzyCity;}

        private List<String> rightFuzzyCity;

        public List<String> getRightFuzzyCity(){return this.rightFuzzyCity;}
        private List<String> countyList;

        public List<String> getCountyList(){return this.countyList;}


        private List<String> fuzzyCounty;

        public List<String> getFuzzyCounty(){return this.fuzzyCounty;}

        private List<String> rightFuzzyCounty;

        public List<String> getRightFuzzyCounty(){return this.rightFuzzyCounty;}
        private List<String> streetList;

        public List<String> getStreetList(){return this.streetList;}


        private List<String> fuzzyStreet;

        public List<String> getFuzzyStreet(){return this.fuzzyStreet;}

        private List<String> rightFuzzyStreet;

        public List<String> getRightFuzzyStreet(){return this.rightFuzzyStreet;}
        private List<String> buildingNameList;

        public List<String> getBuildingNameList(){return this.buildingNameList;}


        private List<String> fuzzyBuildingName;

        public List<String> getFuzzyBuildingName(){return this.fuzzyBuildingName;}

        private List<String> rightFuzzyBuildingName;

        public List<String> getRightFuzzyBuildingName(){return this.rightFuzzyBuildingName;}
        private List<String> gpsLngList;

        public List<String> getGpsLngList(){return this.gpsLngList;}


        private List<String> fuzzyGpsLng;

        public List<String> getFuzzyGpsLng(){return this.fuzzyGpsLng;}

        private List<String> rightFuzzyGpsLng;

        public List<String> getRightFuzzyGpsLng(){return this.rightFuzzyGpsLng;}
        private List<String> gpsLatList;

        public List<String> getGpsLatList(){return this.gpsLatList;}


        private List<String> fuzzyGpsLat;

        public List<String> getFuzzyGpsLat(){return this.fuzzyGpsLat;}

        private List<String> rightFuzzyGpsLat;

        public List<String> getRightFuzzyGpsLat(){return this.rightFuzzyGpsLat;}

        public ConditionBuilder idBetWeen(Long idSt,Long idEd){
            this.idSt = idSt;
            this.idEd = idEd;
            return this;
        }

        public ConditionBuilder idGreaterEqThan(Long idSt){
            this.idSt = idSt;
            return this;
        }
        public ConditionBuilder idLessEqThan(Long idEd){
            this.idEd = idEd;
            return this;
        }


        public ConditionBuilder idList(Long ... id){
            this.idList = solveNullList(id);
            return this;
        }

        public ConditionBuilder idList(List<Long> id){
            this.idList = id;
            return this;
        }

        public ConditionBuilder fuzzyTitle (List<String> fuzzyTitle){
            this.fuzzyTitle = fuzzyTitle;
            return this;
        }

        public ConditionBuilder fuzzyTitle (String ... fuzzyTitle){
            this.fuzzyTitle = solveNullList(fuzzyTitle);
            return this;
        }

        public ConditionBuilder rightFuzzyTitle (List<String> rightFuzzyTitle){
            this.rightFuzzyTitle = rightFuzzyTitle;
            return this;
        }

        public ConditionBuilder rightFuzzyTitle (String ... rightFuzzyTitle){
            this.rightFuzzyTitle = solveNullList(rightFuzzyTitle);
            return this;
        }

        public ConditionBuilder titleList(String ... title){
            this.titleList = solveNullList(title);
            return this;
        }

        public ConditionBuilder titleList(List<String> title){
            this.titleList = title;
            return this;
        }

        public ConditionBuilder typeBetWeen(Integer typeSt,Integer typeEd){
            this.typeSt = typeSt;
            this.typeEd = typeEd;
            return this;
        }

        public ConditionBuilder typeGreaterEqThan(Integer typeSt){
            this.typeSt = typeSt;
            return this;
        }
        public ConditionBuilder typeLessEqThan(Integer typeEd){
            this.typeEd = typeEd;
            return this;
        }


        public ConditionBuilder typeList(Integer ... type){
            this.typeList = solveNullList(type);
            return this;
        }

        public ConditionBuilder typeList(List<Integer> type){
            this.typeList = type;
            return this;
        }

        public ConditionBuilder statusBetWeen(Integer statusSt,Integer statusEd){
            this.statusSt = statusSt;
            this.statusEd = statusEd;
            return this;
        }

        public ConditionBuilder statusGreaterEqThan(Integer statusSt){
            this.statusSt = statusSt;
            return this;
        }
        public ConditionBuilder statusLessEqThan(Integer statusEd){
            this.statusEd = statusEd;
            return this;
        }


        public ConditionBuilder statusList(Integer ... status){
            this.statusList = solveNullList(status);
            return this;
        }

        public ConditionBuilder statusList(List<Integer> status){
            this.statusList = status;
            return this;
        }

        public ConditionBuilder stateBetWeen(Integer stateSt,Integer stateEd){
            this.stateSt = stateSt;
            this.stateEd = stateEd;
            return this;
        }

        public ConditionBuilder stateGreaterEqThan(Integer stateSt){
            this.stateSt = stateSt;
            return this;
        }
        public ConditionBuilder stateLessEqThan(Integer stateEd){
            this.stateEd = stateEd;
            return this;
        }


        public ConditionBuilder stateList(Integer ... state){
            this.stateList = solveNullList(state);
            return this;
        }

        public ConditionBuilder stateList(List<Integer> state){
            this.stateList = state;
            return this;
        }

        public ConditionBuilder editStatusBetWeen(Integer editStatusSt,Integer editStatusEd){
            this.editStatusSt = editStatusSt;
            this.editStatusEd = editStatusEd;
            return this;
        }

        public ConditionBuilder editStatusGreaterEqThan(Integer editStatusSt){
            this.editStatusSt = editStatusSt;
            return this;
        }
        public ConditionBuilder editStatusLessEqThan(Integer editStatusEd){
            this.editStatusEd = editStatusEd;
            return this;
        }


        public ConditionBuilder editStatusList(Integer ... editStatus){
            this.editStatusList = solveNullList(editStatus);
            return this;
        }

        public ConditionBuilder editStatusList(List<Integer> editStatus){
            this.editStatusList = editStatus;
            return this;
        }

        public ConditionBuilder amcIdBetWeen(Long amcIdSt,Long amcIdEd){
            this.amcIdSt = amcIdSt;
            this.amcIdEd = amcIdEd;
            return this;
        }

        public ConditionBuilder amcIdGreaterEqThan(Long amcIdSt){
            this.amcIdSt = amcIdSt;
            return this;
        }
        public ConditionBuilder amcIdLessEqThan(Long amcIdEd){
            this.amcIdEd = amcIdEd;
            return this;
        }


        public ConditionBuilder amcIdList(Long ... amcId){
            this.amcIdList = solveNullList(amcId);
            return this;
        }

        public ConditionBuilder amcIdList(List<Long> amcId){
            this.amcIdList = amcId;
            return this;
        }

        public ConditionBuilder fuzzyAmcCode (List<String> fuzzyAmcCode){
            this.fuzzyAmcCode = fuzzyAmcCode;
            return this;
        }

        public ConditionBuilder fuzzyAmcCode (String ... fuzzyAmcCode){
            this.fuzzyAmcCode = solveNullList(fuzzyAmcCode);
            return this;
        }

        public ConditionBuilder rightFuzzyAmcCode (List<String> rightFuzzyAmcCode){
            this.rightFuzzyAmcCode = rightFuzzyAmcCode;
            return this;
        }

        public ConditionBuilder rightFuzzyAmcCode (String ... rightFuzzyAmcCode){
            this.rightFuzzyAmcCode = solveNullList(rightFuzzyAmcCode);
            return this;
        }

        public ConditionBuilder amcCodeList(String ... amcCode){
            this.amcCodeList = solveNullList(amcCode);
            return this;
        }

        public ConditionBuilder amcCodeList(List<String> amcCode){
            this.amcCodeList = amcCode;
            return this;
        }

        public ConditionBuilder fuzzyEstmPrice (List<String> fuzzyEstmPrice){
            this.fuzzyEstmPrice = fuzzyEstmPrice;
            return this;
        }

        public ConditionBuilder fuzzyEstmPrice (String ... fuzzyEstmPrice){
            this.fuzzyEstmPrice = solveNullList(fuzzyEstmPrice);
            return this;
        }

        public ConditionBuilder rightFuzzyEstmPrice (List<String> rightFuzzyEstmPrice){
            this.rightFuzzyEstmPrice = rightFuzzyEstmPrice;
            return this;
        }

        public ConditionBuilder rightFuzzyEstmPrice (String ... rightFuzzyEstmPrice){
            this.rightFuzzyEstmPrice = solveNullList(rightFuzzyEstmPrice);
            return this;
        }

        public ConditionBuilder estmPriceList(String ... estmPrice){
            this.estmPriceList = solveNullList(estmPrice);
            return this;
        }

        public ConditionBuilder estmPriceList(List<String> estmPrice){
            this.estmPriceList = estmPrice;
            return this;
        }

        public ConditionBuilder debtIdBetWeen(Long debtIdSt,Long debtIdEd){
            this.debtIdSt = debtIdSt;
            this.debtIdEd = debtIdEd;
            return this;
        }

        public ConditionBuilder debtIdGreaterEqThan(Long debtIdSt){
            this.debtIdSt = debtIdSt;
            return this;
        }
        public ConditionBuilder debtIdLessEqThan(Long debtIdEd){
            this.debtIdEd = debtIdEd;
            return this;
        }


        public ConditionBuilder debtIdList(Long ... debtId){
            this.debtIdList = solveNullList(debtId);
            return this;
        }

        public ConditionBuilder debtIdList(List<Long> debtId){
            this.debtIdList = debtId;
            return this;
        }

        public ConditionBuilder fuzzyInitPrice (List<String> fuzzyInitPrice){
            this.fuzzyInitPrice = fuzzyInitPrice;
            return this;
        }

        public ConditionBuilder fuzzyInitPrice (String ... fuzzyInitPrice){
            this.fuzzyInitPrice = solveNullList(fuzzyInitPrice);
            return this;
        }

        public ConditionBuilder rightFuzzyInitPrice (List<String> rightFuzzyInitPrice){
            this.rightFuzzyInitPrice = rightFuzzyInitPrice;
            return this;
        }

        public ConditionBuilder rightFuzzyInitPrice (String ... rightFuzzyInitPrice){
            this.rightFuzzyInitPrice = solveNullList(rightFuzzyInitPrice);
            return this;
        }

        public ConditionBuilder initPriceList(String ... initPrice){
            this.initPriceList = solveNullList(initPrice);
            return this;
        }

        public ConditionBuilder initPriceList(List<String> initPrice){
            this.initPriceList = initPrice;
            return this;
        }

        public ConditionBuilder restrictionsBetWeen(Integer restrictionsSt,Integer restrictionsEd){
            this.restrictionsSt = restrictionsSt;
            this.restrictionsEd = restrictionsEd;
            return this;
        }

        public ConditionBuilder restrictionsGreaterEqThan(Integer restrictionsSt){
            this.restrictionsSt = restrictionsSt;
            return this;
        }
        public ConditionBuilder restrictionsLessEqThan(Integer restrictionsEd){
            this.restrictionsEd = restrictionsEd;
            return this;
        }


        public ConditionBuilder restrictionsList(Integer ... restrictions){
            this.restrictionsList = solveNullList(restrictions);
            return this;
        }

        public ConditionBuilder restrictionsList(List<Integer> restrictions){
            this.restrictionsList = restrictions;
            return this;
        }

        public ConditionBuilder fuzzyArea (List<String> fuzzyArea){
            this.fuzzyArea = fuzzyArea;
            return this;
        }

        public ConditionBuilder fuzzyArea (String ... fuzzyArea){
            this.fuzzyArea = solveNullList(fuzzyArea);
            return this;
        }

        public ConditionBuilder rightFuzzyArea (List<String> rightFuzzyArea){
            this.rightFuzzyArea = rightFuzzyArea;
            return this;
        }

        public ConditionBuilder rightFuzzyArea (String ... rightFuzzyArea){
            this.rightFuzzyArea = solveNullList(rightFuzzyArea);
            return this;
        }

        public ConditionBuilder areaList(String ... area){
            this.areaList = solveNullList(area);
            return this;
        }

        public ConditionBuilder areaList(List<String> area){
            this.areaList = area;
            return this;
        }

        public ConditionBuilder fuzzyLandArea (List<String> fuzzyLandArea){
            this.fuzzyLandArea = fuzzyLandArea;
            return this;
        }

        public ConditionBuilder fuzzyLandArea (String ... fuzzyLandArea){
            this.fuzzyLandArea = solveNullList(fuzzyLandArea);
            return this;
        }

        public ConditionBuilder rightFuzzyLandArea (List<String> rightFuzzyLandArea){
            this.rightFuzzyLandArea = rightFuzzyLandArea;
            return this;
        }

        public ConditionBuilder rightFuzzyLandArea (String ... rightFuzzyLandArea){
            this.rightFuzzyLandArea = solveNullList(rightFuzzyLandArea);
            return this;
        }

        public ConditionBuilder landAreaList(String ... landArea){
            this.landAreaList = solveNullList(landArea);
            return this;
        }

        public ConditionBuilder landAreaList(List<String> landArea){
            this.landAreaList = landArea;
            return this;
        }

        public ConditionBuilder publishDateBetWeen(java.time.LocalDate publishDateSt,java.time.LocalDate publishDateEd){
            this.publishDateSt = publishDateSt;
            this.publishDateEd = publishDateEd;
            return this;
        }

        public ConditionBuilder publishDateGreaterEqThan(java.time.LocalDate publishDateSt){
            this.publishDateSt = publishDateSt;
            return this;
        }
        public ConditionBuilder publishDateLessEqThan(java.time.LocalDate publishDateEd){
            this.publishDateEd = publishDateEd;
            return this;
        }


        public ConditionBuilder publishDateList(java.time.LocalDate ... publishDate){
            this.publishDateList = solveNullList(publishDate);
            return this;
        }

        public ConditionBuilder publishDateList(List<java.time.LocalDate> publishDate){
            this.publishDateList = publishDate;
            return this;
        }

        public ConditionBuilder fuzzyProvince (List<String> fuzzyProvince){
            this.fuzzyProvince = fuzzyProvince;
            return this;
        }

        public ConditionBuilder fuzzyProvince (String ... fuzzyProvince){
            this.fuzzyProvince = solveNullList(fuzzyProvince);
            return this;
        }

        public ConditionBuilder rightFuzzyProvince (List<String> rightFuzzyProvince){
            this.rightFuzzyProvince = rightFuzzyProvince;
            return this;
        }

        public ConditionBuilder rightFuzzyProvince (String ... rightFuzzyProvince){
            this.rightFuzzyProvince = solveNullList(rightFuzzyProvince);
            return this;
        }

        public ConditionBuilder provinceList(String ... province){
            this.provinceList = solveNullList(province);
            return this;
        }

        public ConditionBuilder provinceList(List<String> province){
            this.provinceList = province;
            return this;
        }

        public ConditionBuilder fuzzyCity (List<String> fuzzyCity){
            this.fuzzyCity = fuzzyCity;
            return this;
        }

        public ConditionBuilder fuzzyCity (String ... fuzzyCity){
            this.fuzzyCity = solveNullList(fuzzyCity);
            return this;
        }

        public ConditionBuilder rightFuzzyCity (List<String> rightFuzzyCity){
            this.rightFuzzyCity = rightFuzzyCity;
            return this;
        }

        public ConditionBuilder rightFuzzyCity (String ... rightFuzzyCity){
            this.rightFuzzyCity = solveNullList(rightFuzzyCity);
            return this;
        }

        public ConditionBuilder cityList(String ... city){
            this.cityList = solveNullList(city);
            return this;
        }

        public ConditionBuilder cityList(List<String> city){
            this.cityList = city;
            return this;
        }

        public ConditionBuilder fuzzyCounty (List<String> fuzzyCounty){
            this.fuzzyCounty = fuzzyCounty;
            return this;
        }

        public ConditionBuilder fuzzyCounty (String ... fuzzyCounty){
            this.fuzzyCounty = solveNullList(fuzzyCounty);
            return this;
        }

        public ConditionBuilder rightFuzzyCounty (List<String> rightFuzzyCounty){
            this.rightFuzzyCounty = rightFuzzyCounty;
            return this;
        }

        public ConditionBuilder rightFuzzyCounty (String ... rightFuzzyCounty){
            this.rightFuzzyCounty = solveNullList(rightFuzzyCounty);
            return this;
        }

        public ConditionBuilder countyList(String ... county){
            this.countyList = solveNullList(county);
            return this;
        }

        public ConditionBuilder countyList(List<String> county){
            this.countyList = county;
            return this;
        }

        public ConditionBuilder fuzzyStreet (List<String> fuzzyStreet){
            this.fuzzyStreet = fuzzyStreet;
            return this;
        }

        public ConditionBuilder fuzzyStreet (String ... fuzzyStreet){
            this.fuzzyStreet = solveNullList(fuzzyStreet);
            return this;
        }

        public ConditionBuilder rightFuzzyStreet (List<String> rightFuzzyStreet){
            this.rightFuzzyStreet = rightFuzzyStreet;
            return this;
        }

        public ConditionBuilder rightFuzzyStreet (String ... rightFuzzyStreet){
            this.rightFuzzyStreet = solveNullList(rightFuzzyStreet);
            return this;
        }

        public ConditionBuilder streetList(String ... street){
            this.streetList = solveNullList(street);
            return this;
        }

        public ConditionBuilder streetList(List<String> street){
            this.streetList = street;
            return this;
        }

        public ConditionBuilder fuzzyBuildingName (List<String> fuzzyBuildingName){
            this.fuzzyBuildingName = fuzzyBuildingName;
            return this;
        }

        public ConditionBuilder fuzzyBuildingName (String ... fuzzyBuildingName){
            this.fuzzyBuildingName = solveNullList(fuzzyBuildingName);
            return this;
        }

        public ConditionBuilder rightFuzzyBuildingName (List<String> rightFuzzyBuildingName){
            this.rightFuzzyBuildingName = rightFuzzyBuildingName;
            return this;
        }

        public ConditionBuilder rightFuzzyBuildingName (String ... rightFuzzyBuildingName){
            this.rightFuzzyBuildingName = solveNullList(rightFuzzyBuildingName);
            return this;
        }

        public ConditionBuilder buildingNameList(String ... buildingName){
            this.buildingNameList = solveNullList(buildingName);
            return this;
        }

        public ConditionBuilder buildingNameList(List<String> buildingName){
            this.buildingNameList = buildingName;
            return this;
        }

        public ConditionBuilder fuzzyGpsLng (List<String> fuzzyGpsLng){
            this.fuzzyGpsLng = fuzzyGpsLng;
            return this;
        }

        public ConditionBuilder fuzzyGpsLng (String ... fuzzyGpsLng){
            this.fuzzyGpsLng = solveNullList(fuzzyGpsLng);
            return this;
        }

        public ConditionBuilder rightFuzzyGpsLng (List<String> rightFuzzyGpsLng){
            this.rightFuzzyGpsLng = rightFuzzyGpsLng;
            return this;
        }

        public ConditionBuilder rightFuzzyGpsLng (String ... rightFuzzyGpsLng){
            this.rightFuzzyGpsLng = solveNullList(rightFuzzyGpsLng);
            return this;
        }

        public ConditionBuilder gpsLngList(String ... gpsLng){
            this.gpsLngList = solveNullList(gpsLng);
            return this;
        }

        public ConditionBuilder gpsLngList(List<String> gpsLng){
            this.gpsLngList = gpsLng;
            return this;
        }

        public ConditionBuilder fuzzyGpsLat (List<String> fuzzyGpsLat){
            this.fuzzyGpsLat = fuzzyGpsLat;
            return this;
        }

        public ConditionBuilder fuzzyGpsLat (String ... fuzzyGpsLat){
            this.fuzzyGpsLat = solveNullList(fuzzyGpsLat);
            return this;
        }

        public ConditionBuilder rightFuzzyGpsLat (List<String> rightFuzzyGpsLat){
            this.rightFuzzyGpsLat = rightFuzzyGpsLat;
            return this;
        }

        public ConditionBuilder rightFuzzyGpsLat (String ... rightFuzzyGpsLat){
            this.rightFuzzyGpsLat = solveNullList(rightFuzzyGpsLat);
            return this;
        }

        public ConditionBuilder gpsLatList(String ... gpsLat){
            this.gpsLatList = solveNullList(gpsLat);
            return this;
        }

        public ConditionBuilder gpsLatList(List<String> gpsLat){
            this.gpsLatList = gpsLat;
            return this;
        }

        private <T>List<T> solveNullList(T ... objs){
            if (objs != null){
            List<T> list = new ArrayList<>();
                for (T item : objs){
                    if (item != null){
                        list.add(item);
                    }
                }
                return list;
            }
            return null;
        }

        public ConditionBuilder build(){return this;}
    }

    public static class Builder {

        private AMCASSET obj;

        public Builder(){
            this.obj = new AMCASSET();
        }

        public Builder id(Long id){
            this.obj.setId(id);
            return this;
        }
        public Builder title(String title){
            this.obj.setTitle(title);
            return this;
        }
        public Builder type(Integer type){
            this.obj.setType(type);
            return this;
        }
        public Builder status(Integer status){
            this.obj.setStatus(status);
            return this;
        }
        public Builder state(Integer state){
            this.obj.setState(state);
            return this;
        }
        public Builder editStatus(Integer editStatus){
            this.obj.setEditStatus(editStatus);
            return this;
        }
        public Builder amcId(Long amcId){
            this.obj.setAmcId(amcId);
            return this;
        }
        public Builder amcCode(String amcCode){
            this.obj.setAmcCode(amcCode);
            return this;
        }
        public Builder estmPrice(String estmPrice){
            this.obj.setEstmPrice(estmPrice);
            return this;
        }
        public Builder debtId(Long debtId){
            this.obj.setDebtId(debtId);
            return this;
        }
        public Builder initPrice(String initPrice){
            this.obj.setInitPrice(initPrice);
            return this;
        }
        public Builder restrictions(Integer restrictions){
            this.obj.setRestrictions(restrictions);
            return this;
        }
        public Builder area(String area){
            this.obj.setArea(area);
            return this;
        }
        public Builder landArea(String landArea){
            this.obj.setLandArea(landArea);
            return this;
        }
        public Builder publishDate(java.time.LocalDate publishDate){
            this.obj.setPublishDate(publishDate);
            return this;
        }
        public Builder province(String province){
            this.obj.setProvince(province);
            return this;
        }
        public Builder city(String city){
            this.obj.setCity(city);
            return this;
        }
        public Builder county(String county){
            this.obj.setCounty(county);
            return this;
        }
        public Builder street(String street){
            this.obj.setStreet(street);
            return this;
        }
        public Builder buildingName(String buildingName){
            this.obj.setBuildingName(buildingName);
            return this;
        }
        public Builder gpsLng(String gpsLng){
            this.obj.setGpsLng(gpsLng);
            return this;
        }
        public Builder gpsLat(String gpsLat){
            this.obj.setGpsLat(gpsLat);
            return this;
        }
        public AMCASSET build(){return obj;}
    }

}
