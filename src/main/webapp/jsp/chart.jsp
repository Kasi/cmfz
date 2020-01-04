<%@page isELIgnored="false" language="java" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set value="${pageContext.request.contextPath}" scope="page" var="path"></c:set>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>

    <script>
        $(function () {
            //创建 go easy
            var goEasy = new GoEasy({
                host: "hangzhou.goeasy.io",
                appkey: "BS-8cbc7f6807b847a29e61d57d0cb2911e"
            });
            goEasy.subscribe({
                channel: "goeasyTest", //订阅信道
                onMessage: function (message) {//消息回调


                    //获得数组
                    var parse = JSON.parse(message.content);

                    if (!(typeof (parse.article) == "undefined" || parse.article == null)) {
                        //发布的是文章数据

                        // 提取星期数组集
                        var weeks = new Array();

                        //提取每天数量
                        var numbers = new Array();

                        parse.article.forEach((item, index, array) => {
                            weeks[index] = item.week;
                            numbers[index] = item.number;
                        });


                        myCharts.setOption({
                            xAxis: {data: weeks},
                            series: {
                                data: numbers
                            }
                        });

                    }
                    //专辑一年发布数量
                    var parse1 = JSON.parse(message.content);
                    if (!(typeof (parse1.album) == "undefined" || parse1.album == null)) {
                        // 解析成数组
                        init.setOption({
                            series: {
                                data: parse1.album
                            }
                        });

                    }


                }
            });

            // 主动拿到初始数据
            $.get("${path}/echarts/getDay7", function (data) {
                // 提取星期数组集
                var weeks = new Array();

                //提取每天数量
                var numbers = new Array();

                data.forEach((item, index, array) => {
                    weeks[index] = item.week;
                    numbers[index] = item.number;
                });

                myCharts.setOption({
                    xAxis: {data: weeks},
                    series: {
                        data: numbers
                    }
                });
            }, "json");

            //主动拿到初始化数据

            $.get("${path}/album/getOneYear", function (data) {

                init.setOption({

                    series: {
                        data: data.album
                    }
                });
            }, "json");

        });

        //数组元素交换位置
        function arraypx(obj) {
            var a = obj;
            for (var i = 0; i < ~~(a.length / 2); i++) {

                //保存原值
                var p = a[i];

                //将后面的值放到前面
                a[i] = a[a.length - i - 1];

                //将前面的值放到后面
                a[a.length - i - 1] = p;

            }
            return a;


        }
    </script>

</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-7">
            <div id="main" style="width:600px;height:400px;"></div>
        </div>

        <div class="col-md-5">
            <div id="main1" style="width:600px;height:400px;"></div>
        </div>

    </div>

    <div class="row">
        <div class="col-md-7">
            <div id="main2" style="width:600px;height:400px;"></div>
        </div>
    </div>
</div>


<script type="text/javascript">


    //基于准备好的dom,初始化echarts实例


    var myCharts = echarts.init(document.getElementById("main"));


    //指定图表的配置项和数据

    var option = {
        title: {
            text: '最近七天文章发布量'
        },
        tooltip: {},
        legend: {
            data: ['文章数量']
        },
        xAxis: {

            data: []
        },
        yAxis: {},
        series: [{
            name: '文章数量',
            type: 'bar',

        }]


    }
    // 使用刚指定的配置项和数据显示图表
    myCharts.setOption(option);

    // 专辑发布数量

    //1. 初始化 echarts

    var init = echarts.init(document.getElementById("main1"));


    //配置
    var config = {
        title: {
            text: "最近一年专辑发布数量"
        },
        tooltip: {},
        legend: {
            data: ["专辑数量"]
        },
        xAxis: {
            data: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"]
        },
        yAxis: {},
        series: [{
            name: "专辑数量",
            type: "line"
        }]

    }

    // 使用配置
    init.setOption(config);

    //地图

    var mapinit = echarts.init(document.getElementById("main2"));
    var optionx = {
        title: {
            text: '用户地理分布图',
            left: 'center'

        },
        tooltip: {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['女士', '男士']
        },
        visualMap: {
            min: 0,
            max: 2500,
            left: 'left',
            top: 'bottom',
            text: ['高', '低'], // 文本，默认为数值文本
            calculable: true
        },
        toolbox: {
            show: true,
            orient: 'vertical',
            left: 'right',
            top: 'center',
            feature: {
                mark: {show: true},
                dataView: {show: true, readOnly: false},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        series: [
            {
                name: '女士',
                type: 'map',
                mapType: 'china',
                roam: false,
                label: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: true
                    }
                },
                data: [
                    {name: '北京', value: Math.round(Math.random() * 1000)},

                ]
            },
            {
                name: '男士',
                type: 'map',
                mapType: 'china',
                label: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: true
                    }
                },
                data: [
                    {name: '北京', value: Math.round(Math.random() * 1000)},

                ]
            }
        ]
    };
    mapinit.setOption(optionx);


</script>

</body>
</html>