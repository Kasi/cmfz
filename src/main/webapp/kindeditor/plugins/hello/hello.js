KindEditor.plugin('hello', function (K) {

    // 点击图标时执行
    editor.clickToolbar(name, function () {
        $("#my2").modal("show");
    });
});