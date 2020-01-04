package com.cmfz.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.util.List;

/**
 * @className: PhoneValidate
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2020-01-02 8:40
 * @Version 1.0
 */


public class PhoneValidate {

    /**
     * @param paramets 0 RegionId 1 PhoneNumbers 2 SignName 3 TemplateCode 4 TemplateParam 5 accessKeyId 6 accessSecret
     * @return
     */
    public String PhoneSendSMS(List<String> paramets) {
        DefaultProfile profile = DefaultProfile.getProfile(paramets.get(0), paramets.get(5), paramets.get(6));
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");


        request.putQueryParameter("RegionId", paramets.get(0));
        request.putQueryParameter("PhoneNumbers", paramets.get(1));
        request.putQueryParameter("SignName", paramets.get(2));
        request.putQueryParameter("TemplateCode", paramets.get(3));
        request.putQueryParameter("TemplateParam", paramets.get(4));
        try {
            CommonResponse response = client.getCommonResponse(request);

            //返回数据
            return response.getData();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return "{\"Message\":\"发生异常：代码行：\"" + Thread.currentThread().getStackTrace()[1].getLineNumber() + "}";
    }

}
