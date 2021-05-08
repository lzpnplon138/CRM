$(function () {
    //数据表格
    $("#myDataGrid").datagrid({
        fit: true,
        fitColumns: true,
        toolbar: "#myToolbar",
        url: "/permission/query.do",
        pagination: true,
        rownumbers: true,
        singleSelect: true,
        columns: [
            [
                {title: "权限名称", field: "name", width: 50},
                {title: "权限表达式", field: "resource", width: 50}
            ]
        ]

    });

});

//加载权限
function reload() {
    //弹出确认框
    $.messager.confirm('确认', '确认操作？', function (r) {
        if (r) {
            //发送请求
            $.get("/permission/reload.do", function (data) {
                if (data.success) {
                    $.messager.alert('温馨提示', '操作成功！', 'info', function () {
                    });
                    //重新加载数据
                    $("#myDataGrid").datagrid("reload");
                } else {
                    $.messager.alert('温馨提示', '操作失败！', 'error')
                }
            })
        }
    });
}

