package com.cmfz;

/**
 * @className: Test
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2020-01-02 8:36
 * @Version 1.0
 */

import org.apache.commons.codec.digest.DigestUtils;

/*
pom.xml
<dependency>
  <groupId>com.aliyun</groupId>
  <artifactId>aliyun-java-sdk-core</artifactId>
  <version>4.0.3</version>
</dependency>
*/
public class Test {
    public static void main(String[] args) {
        //GitHub
        String s = DigestUtils.md5Hex("123456a54121f4-2c44-4f06-b732-aa2160cc3e8d");

        System.out.println(s);
    }
}
