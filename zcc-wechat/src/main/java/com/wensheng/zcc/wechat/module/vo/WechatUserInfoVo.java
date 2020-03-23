package com.wensheng.zcc.wechat.module.vo;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

/*

"subscribe": 1,
"openid": "otvxTs4dckWG7imySrJd6jSi0CWE",
"nickname": "iWithery",
"sex": 1,
"language": "zh_CN",
"city": "揭阳",
"province": "广东",
"country": "中国",

"headimgurl": "http://thirdwx.qlogo.cn/mmopen/xbIQx1GRqdvyqkMMhEaGOX802l1CyqMJNgUzKP8MeAeHFicRDSnZH7FY4XB7p8XHXIf6uJA2SCunTPicGKezDC4saKISzRj3nz/0",

"subscribe_time": 1434093047,
"unionid": "oR5GjjgEhCMJFyzaVZdrxZ2zRRF4",
"remark": "",

"groupid": 0,
"tagid_list":[128,2],
"subscribe_scene": "ADD_SCENE_QR_CODE",
"qr_scene": 98765,
"qr_scene_str": "

 */

@Data
public class WechatUserInfoVo {

  Integer subscribe;
  @SerializedName("openid")
  String openId;
  @SerializedName("nickname")
  String nickName;
  Integer sex;
  String language;
  String city;
  String province;
  String country;
  @SerializedName("headimgurl")
  String headImgurl;
  @SerializedName("subscribe_time")
  Long subscribeTime;
  String unionid;
  String remark;
  @SerializedName("groupid")
  String groupId;
  @SerializedName("tagid_list")
  List<Integer> tagIdList;
  @SerializedName("subscribe_scene")
  String subscribeScene;
  @SerializedName("qr_scene")
  Integer qrScene;
  @SerializedName("qr_scene_str")
  String qrSceneStr;
}
