$(function () {
    //菜单
    $("#myTree").tree({
        url: "/menu/selectEmployeeMenu.do",
        onClick: function (node) {
            //动态加添选项卡
            if (node.url) {
                //判断选项卡是否已经存在,存在则选中,否则新增
                if ($("#myTabs").tabs("exists",node.text)) {
                    //选中选项卡
                    $("#myTabs").tabs("select",node.text)
                } else {
                    //新增选项卡
                    $("#myTabs").tabs("add", {
                        title: node.text,
                        closable: true,
                        //href:node.url  只能引入页面的body部分,可能回出现id重复或者方法同名的问题
                        content: "<iframe src=" + node.url + " width='100%' height='100%' frameborder='0'></iframe>"
                    })
                }
            } else {
                // 折叠/展开
                $("#myTree").tree("toggle", node.target);
            }
        }
    });
});
