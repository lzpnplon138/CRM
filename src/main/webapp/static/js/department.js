$(function () {

    var myDataGrid = $("#myDataGrid");
    var myDialog = $("#myDialog");
    var myForm = $("#myForm");

    //包含所有方法的方法对象
    var methods = {
        //新增
        add: function () {
            //打开弹出框
            myDialog.dialog("setTitle", "新增部门");
            myDialog.dialog("open");

        },

        //编辑
        edit: function () {
            //判断是否选中数据
            var row = myDataGrid.datagrid("getSelected");
            if (!row) {
                $.messager.alert('温馨提示', '请先选择一条数据！', 'warning');
                return;
            }
            //回显表单数据
            myForm.form("load", row);

            //设置弹出框标题
            myDialog.dialog("setTitle", "编辑部门");
            //打开弹出框
            myDialog.dialog("open");
        },

        //改变状态
        changeState: function () {
            //判断是否选中数据
            var row = myDataGrid.datagrid("getSelected");
            if (!row) {
                $.messager.alert('温馨提示', '请先选择一条数据！', 'warning');
                return;
            }

            //弹出确认框
            $.messager.confirm('确认', '确认操作？', function (r) {
                if (r) {
                    //发送请求
                    $.get("/department/changeState.do", {id: row.id}, function (data) {
                        if (data.success) {
                            $.messager.alert('温馨提示', '操作成功！', 'info', function () {
                            });
                            //重新加载数据
                            myDataGrid.datagrid("reload");
                        } else {
                            $.messager.alert('温馨提示', '操作失败！', 'error')
                        }
                    })
                }
            });
        },

        //保存
        save: function () {
            //提交表单
            myForm.form("submit", {
                url: "/department/saveOrUpdate.do",
                success: function (data) {
                    //先转换为json对象
                    var json = JSON.parse(data);
                    if (json.success) {
                        $.messager.alert('温馨提示', '保存成功！', 'info', function () {
                        });
                        //关闭弹出框
                        methods["cancel"]();
                        //重新加载数据
                        myDataGrid.datagrid("reload");
                    } else {
                        $.messager.alert('温馨提示', json.msg, 'error')
                    }
                }
            });
        },

        //取消
        cancel: function () {
            myDialog.dialog("close");
        }

    };

    //统一绑定事件
    $("[data-cmd]").click(function () {
        methods[$(this).data("cmd")]();
    });
    
    //数据表格
    myDataGrid.datagrid({
        fit: true,
        fitColumns: true,
        toolbar: "#myToolbar",
        url: "/department/query.do",
        pagination: true,
        rownumbers: true,
        singleSelect: true,
        columns: [
            [
                {title: "部门名称", field: "name", width: 50},
                {title: "部门编码", field: "sn", width: 50},
                {
                    title: "状态", field: "state", width: 50, formatter: function (value, row, index) {
                        return value ? "<font color='green'>启用</font>" : "<font color='red'>禁用</font>";
                    }
                }
            ]
        ],
        onSelect: function (index, row) {
            //判断部门的状态显示文字
            if (row.state) {
                $("#state_btn").linkbutton({
                    text: "禁用"
                })
            } else {
                $("#state_btn").linkbutton({
                    text: "启用"
                })
            }
        }

    });

    //对话框
    myDialog.dialog({
        width: "320",
        height: "400",
        buttons: "#form_btns",
        closed: "true",
        onClose: function () {
            //清空表单数据
            $("#myFrom").form("clear");
        }
    });

});



