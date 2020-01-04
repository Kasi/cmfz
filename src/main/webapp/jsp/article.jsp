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
    <script src="${path}/kindeditor/kindeditor-all-min.js"></script>
    <script src="${path}/kindeditor/lang/zh-CN.js"></script>
    <%--  <script charset="utf-8" src="${path}/kindeditor/plugins/code/prettify.js"></script>--%>
    <link rel="stylesheet" href='${path}/kindeditor/themes/default/default.css'/>


    <script>
        //修改的富文本
        var editoredit;

        function preview(obj) {

            var content = "<iframe  id='iff' name='view'  frameborder ='0' height='800px' width='100%' scrolling='yes' srcdoc=''></iframe>";

            var dialog = KindEditor.dialog({

                width: '80%',
                title: '文章预览',
                body: content,
                closeBtn: {
                    name: '关闭',
                    click: function (e) {
                        dialog.remove();
                    }
                },
                yesBtn: {
                    name: '确定',
                    click: function (e) {

                        //alert(this.value);
                    }
                },
                noBtn: {
                    name: '取消',
                    click: function (e) {
                        dialog.remove();
                    }
                }
            });
            dialog.show();
            //预览数据
            /*url,[data],[callback],[type]String,Map,Function,StringV1.0*/
            $("#iff").attr("srcdoc", getContent(obj).content);
            $(".ke-dialog").css({left: "338px", top: "56px"});
        }

        function getContent(obj) {
            var datax;
            $.ajax({
                url: "${path}/article/viewArticle",
                type: "get",
                async: false,
                data: {articleId: $(obj.target).attr("id")},
                success: function (data) {
                    datax = data;

                },
                error: function (xhr, status, throwex) {
                    console.log("失败");
                },
                dataType: "json"
            });

            return datax;
        }

        //修改文章
        function editArticle(obj) {
            <!-- 富文本编辑器 -->


            var content = "<form action=\"\"> <textarea id=\"fwbedit\"></textarea> </form>";
            var dialog = KindEditor.dialog({

                width: '82%',
                title: '文章修改',
                body: content,
                closeBtn: {
                    name: '关闭',
                    click: function (e) {
                        dialog.remove();
                    }
                },
                yesBtn: {
                    name: '确定',
                    click: function (e) {

                        //alert(this.value);
                        //editoredit
                        $.ajax({
                            url: "${path}/article/changeArticleContent",
                            data: {articleId: $(obj.target).attr("id"), content: editoredit.html()},
                            success: function (data) {
                                console.log(data);
                                //关闭窗口
                                $(".ke-dialog").remove()
                                $(".ke-dialog-mask").remove();
                            },
                            error: function (xhr, status, throwex) {
                                console.log("修改文章内容失败")
                            },
                            dataType: "json"
                        });
                    }
                },
                noBtn: {
                    name: '取消',
                    click: function (e) {
                        dialog.remove();
                    }
                }
            });
            dialog.show();
            //创建富文本

            editoredit = KindEditor.create("#fwbedit", {
                width: "100%",
                height: "731px",
                /*  afterBlur: function () { //同步数据到文本域  使用内置api获取无需这个
                      this.sync();
                  },*/
                resizeType: 0,
                allowFileManager:
                    true,
                uploadJson:
                    "${path}/article/imageUpload",//远程上传图片接口地址
                fileManagerJson:
                    "${path}/article/imageView",//图片空间浏览地址
                filePostName:
                    "fileupload" //表单的name 服务器接收的name
            });
            //设置位置
            $(".ke-dialog").css({left: "338px", top: "52px"});

            //获取数据
            var data = getContent(obj);
            //设置内容
            editoredit.html(data.content);
        }
    </script>
    <style>
        #my1body {
            height: 600px;
        }

        #tr_id {
            display: none;
        }

        #tr_author_Id {
            display: none;
        }

        #editmodarticleDIV {
            top: 227px;
            left: 828px;
        }


    </style>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div>

            <!-- Nav tabs -->
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="active"><a href="#home" data-toggle="tab">文章</a></li>
                <li role="presentation"><a href="#profile" data-toggle="tab">添加文章</a></li>

            </ul>

            <!-- Tab panes -->
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane active" id="home">
                    <table id="articleDIV"></table>
                    <div id="articlePager"></div>
                </div>
                <div role="tabpanel" class="tab-pane" id="profile">
                    <!-- 富文本编辑器 -->
                    <form action="">
                        <textarea id="fwb"></textarea>
                    </form>

                </div>
            </div>

        </div>

    </div>
</div>


<div class="modal fade bs-example-modal-lg" id="my1" data-backdrop="false" data-keybord="false">
    <div class="modal-dialog modal-lg" style="width:82%;top:30px;left:166px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">文章预览</h4>
            </div>
            <div class="modal-body" id="my1body">
                <h1>sdsaodsaodiosa</h1>


            </div>

        </div>

    </div>
</div>


<!-- 保存文章 -->
<div class="modal fade bs-example-modal-lg" id="my2" data-backdrop="false" data-keybord="false">
    <div class="modal-dialog modal-lg" style="width:40%;height:100px;top:30px;left:100px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">保存文章</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="formx">
                    <div class="form-group">
                        <label for="author_Name" class="col-sm-2 control-label">作者名</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="author_Name" name="author_Name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="article_Title" class="col-sm-2 control-label">文章标题</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="article_Title" name="article_Title">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="author_Id" class="col-sm-2 control-label">所属作者id</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="author_Id" id="author_Id">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="is_Show" class="col-sm-2 control-label">文章是否可见</label>
                        <div class="col-sm-10">
                            <select class="form-control" id="is_Show" name="is_Show">
                                <option value="1">可见</option>
                                <option value="2">不可见</option>
                            </select>
                        </div>
                    </div>
                </form>
            </div>

            <div class="modal-footer">
                <button class="btn btn-primary" id="save" data-dismiss="modal">保存文章</button>
            </div>
        </div>

    </div>
</div>

</body>
<script>
    var editor;
    $(function () {
        //********************绑定事件
        $("#save").on('click', addArticle);
        //********************
        $("#articleDIV").jqGrid({
            url: "${path}/article/queryArticlePage",
            editurl: "${path}/article/optionedit",
            datatype: "json",
            styleUI: "Bootstrap",
            autowidth: true,
            viewrecords: true,
            pager: "#articlePager",
            multiselect: true,
            caption: "文章管理",
            viewrecords: true,
            height: "600px",
            rowList: [2, 4, 8],
            rowNum: 10,

            gridComplete: function () {
                //绑定事件
                //文章预览
                $(".previewbtn").on('click', preview);
                $(".articlebtn").on('click', editArticle);
            },
            onSelectRow: function (rowid, status) {

            },
            colNames: [
                "id",
                "author_Name",
                "article_Time",
                "article_Title",
                "author_Id",
                "is_Delete",
                "is_Show",
                "operation"
            ],
            colModel: [
                {name: "id", editable: true},
                {name: "author_Name"},
                {name: "article_Time"},
                {name: "article_Title"},
                {name: "author_Id", editable: true},
                {
                    name: "is_Delete",
                    editable: true,
                    edittype: "select",
                    editoptions: {value: "1:假删除;2:不删除"}
                },

                {
                    name: "is_Show",
                    editable: true,
                    edittype: "select",
                    editoptions: {value: "1:对外可见;2:不可见"}

                },
                {
                    name: "operation", formatter: function (cellvalue, options, rowObject) {
                        var but = "<a id=" + rowObject.id + " class='btn btn-primary glyphicon glyphicon-eye-open previewbtn'></a>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" +
                            "<a id=" + rowObject.id + " class='btn btn-primary glyphicon glyphicon-edit articlebtn'></a>";
                        return but;
                    }
                }
            ]

        }).jqGrid("navGrid", "#articlePager", {
            edittext: "修改文章信息",
            deltext: "删除文章",
            /* searchtext: "搜索",*/
            add: false,
            search: false,
            refreshtext: "刷新文章"
        })

    });

    editor = KindEditor.create("#fwb", {
        width: "1562px",
        height: "731px",
        /*  afterBlur: function () { //同步数据到文本域  使用内置api获取无需这个
              this.sync();
          },*/
        resizeType: 0,
        allowFileManager: true,
        uploadJson: "${path}/article/imageUpload",//远程上传图片接口地址
        fileManagerJson: "${path}/article/imageView",//图片空间浏览地址
        filePostName: "fileupload" //表单的name 服务器接收的name
    });

    //添加文章
    function addArticle() {
        //序列化表单数据

        var serialize = $("#formx").serialize();

        $.ajax({
            url: "${path}/article/save",
            type: "post",
            data: serialize + "&content=" + editor.html(),
            success: function (data) {
                console.log("上传成功");
            },
            error: function (xhr, status, throwex) {
                console.log("上传失败");
            },
            datatype: "json"
        });
    }
</script>

</html>