<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="path" scope="page" value="${pageContext.request.contextPath}"></c:set>


<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>

    <script src="${path}/resources/js/jquery-3.4.1.min.js"></script>
    <script src="${path}/resources/js/ajaxfileupload.js"></script>
    <script src="${path}/resources/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${path}/resources/css/bootstrap.css"/>
    <script src="${path}/resources/jqgrid/grid.locale-cn.js"></script>
    <script src="${path}/resources/jqgrid/jquery.jqGrid.min.js"></script>
    <!-- 导入echarts. s d k -->
    <script src="${path}/resources/js/echarts.min.js"></script>
    <%--导入地图--%>
    <script src="${path}/resources/js/china.js"></script>

    <!-- 导入 goEasy sd k -->
    <script type="text/javascript" src="https://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <link rel="stylesheet" href="${path}/resources/css/ui.jqgrid-bootstrap.css">

    <style>
        #hy {
            margin-right: 86px;
        }

        #bq {
            float: none;
            text-align: center;
        }

        img {
            width: 100%;
        }
    </style>

</head>

<body>

<div class="container-fluid">
    <div class="row">
        <nav class="navbar navbar-default navbar-static-top col-md-12 col-lg-12">
            <p class="navbar-text navbar-left h3">持明法州后台管理系统</p>
            <p class="navbar-text navbar-right h3 "><a href="#" class="navbar-link">退出登录</a></p>
            <p class="navbar-text navbar-right h3" id="hy">欢迎：${sessionScope.admin.user_name}</p>
        </nav>
    </div>


    <div class="row">
        <div class="col-md-2">
            <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingOne">
                        <h4 class="panel-title text-center">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne"
                               aria-expanded="true" aria-controls="collapseOne">
                                用户管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body text-center">
                            <button class="btn btn-primary">测试</button>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingTwo">
                        <h4 class="panel-title text-center">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                               href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                上师管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                        <div class="panel-body text-center">
                            <button class="btn btn-primary">测试</button>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingThree">
                        <h4 class="panel-title text-center">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                               href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                                文章管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse" role="tabpanel"
                         aria-labelledby="headingThree">
                        <div class="panel-body text-center">
                            <button class="btn btn-primary" id="article">测试</button>
                        </div>
                    </div>
                </div>

                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingThree4">
                        <h4 class="panel-title text-center">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                               href="#collapseThree4" aria-expanded="false" aria-controls="collapseThree">
                                专辑管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseThree4" class="panel-collapse collapse" role="tabpanel"
                         aria-labelledby="headingThree">
                        <div class="panel-body text-center">
                            <button class="btn btn-primary" id="album">测试</button>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingThree5">
                        <h4 class="panel-title text-center">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                               href="#collapseThree5" aria-expanded="false" aria-controls="collapseThree">
                                轮播图管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseThree5" class="panel-collapse collapse" role="tabpanel"
                         aria-labelledby="headingThree">
                        <div class="panel-body text-center">
                            <button class="btn btn-primary" id="carousel">
                                查看所有轮播图
                            </button>
                        </div>
                    </div>
                </div>

                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingThree6">
                        <h4 class="panel-title text-center">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                               href="#collapseThree7" aria-expanded="false" aria-controls="collapseThree">
                                数据可视化管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseThree7" class="panel-collapse collapse" role="tabpanel"
                         aria-labelledby="headingThree">
                        <div class="panel-body text-center">
                            <button class="btn btn-primary" id="articleEcharts">
                                查看各项统计
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-10" id="carouselDIV">
            <div class="jumbotron">
                <h1>欢迎进入持明法州后台管理系统</h1>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                        <!-- Indicators -->
                        <ol class="carousel-indicators">
                            <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                            <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                            <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                        </ol>

                        <!-- Wrapper for slides -->
                        <div class="carousel-inner" role="listbox">
                            <div class="item active">
                                <img src="${path}/resources/images/shouye.jpg" alt="...">
                            </div>
                            <div class="item">
                                <img src="${path}/resources/images/shouye.jpg" alt="...">
                            </div>
                            <div class="item">
                                <img src="${path}/resources/images/shouye.jpg" alt="...">

                            </div>
                        </div>

                        <!-- Controls -->
                        <a class="left carousel-control" href="#carousel-example-generic" role="button"
                           data-slide="prev">
                            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                            <span class="sr-only">Previous</span>
                        </a>
                        <a class="right carousel-control" href="#carousel-example-generic" role="button"
                           data-slide="next">
                            <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                            <span class="sr-only">Next</span>
                        </a>
                    </div>
                </div>

            </div>
        </div>
        <%--=========================--%>

    </div>
    <%--图片轮播--%>

    <%--图片轮播END--%>
</div>
<%--底部--%>
<div class="row">
    <nav class="navbar navbar-default navbar-fixed-bottom col-md-12">
        <p class="navbar-text h3" id="bq">@白芷教育 baizhi.com</p>
    </nav>
</div>
<%--底部END--%>
</div>
</body>
</html>
<script>
    $(function () {
        $('#carousel-example-generic').carousel({
            interval: 2000
        });
        //绑定事件
        $("#carousel").on('click', loadCarousel);

        $("#album").on('click', loadAlbum);

        $("#article").on('click', loadArticle);

        $("#articleEcharts").on('click', loadEcharts)
    });

    //轮播图管理页面加载
    function loadCarousel() {
        $('#carouselDIV').empty();
        $('#carouselDIV').load('${path}/jsp/carousel.jsp');
    }

    //专辑管理页面加载

    function loadAlbum() {
        $('#carouselDIV').empty();
        $('#carouselDIV').load('${path}/jsp/album.jsp');
    }

    //文章管理页面加载
    function loadArticle() {
        $('#carouselDIV').empty();
        $('#carouselDIV').load('${path}/jsp/article.jsp');
    }

    // 数据可视化 页面加载
    function loadEcharts() {
        $('#carouselDIV').empty();
        $('#carouselDIV').load('${path}/jsp/chart.jsp');
    }
</script>