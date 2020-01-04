package com.cmfz;

import com.cmfz.dao.AlbumDao;
import com.cmfz.dao.ArticleDao;
import io.goeasy.GoEasy;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest(classes = App.class)
@RunWith(SpringRunner.class)
class AppTest {
     /*  @Autowired
       private ArticleElastCRUD articleElastCRUD;*/

    @Autowired

    private AlbumDao albumDao;

    @Autowired
    private ArticleDao articleDao;


    @Test
    public void test220() {
        //一对多的导入

    }

    @Test
    public void test227() {
        // go easy

        //服务器向客户端发送


        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-c5d32e6f4b5d43f39bda54d7d8ed8086");

        goEasy.publish("goeasyTest", "hello goEasy");


    }

    @Test
    public void test242() {

    }

    /**
     * 学生类
     */
    class Student {
        private String name;
        private Integer age;
        private Date bir;

        public Student(String name, Integer age, Date bir) {
            this.name = name;
            this.age = age;
            this.bir = bir;
        }

        public String getName() {
            return name;
        }

        public Integer getAge() {
            return age;
        }

        public Date getBir() {
            return bir;
        }
    }
}
