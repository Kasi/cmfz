package com.cmfz.service.impl;

import com.cmfz.service.GoEasyService;
import io.goeasy.GoEasy;
import org.springframework.stereotype.Component;

/**
 * @className: GoEastServiceImpl
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2019-12-31 8:41
 * @Version 1.0
 */

@Component
public class GoEastServiceImpl implements GoEasyService<String> {

    @Override
    public void goEasy(String appkey, String host, String content, String channel) {
        // 创建消息推送模型
        GoEasy goEasy = new GoEasy(host, appkey);

        // 向订阅者发布消息
        goEasy.publish(channel, content);

    }
}
