$(function () {
    //字典
    var dictionary_datagrid = $("#dictionary_datagrid");
    var dictionary_dialog = $("#dictionary_dialog");
    var dictionary_form = $("#dictionary_form");

    var dictionaryItem_datagrid = $("#dictionaryItem_datagrid");
    var dictionaryItem_dialog = $("#dictionaryItem_dialog");
    var dictionaryItem_form = $("#dictionaryItem_form");

    var dicMethodObj = {
        add:function () {
            //打开弹出框
            dictionary_dialog.dialog("setTitle", "数据字典目录新增");
            dictionary_dialog.dialog("open");
        },
        edit:function(){
            //判断是否选中数据
            var row = dictionary_datagrid.datagrid("getSelected");
            if (!row) {
                $.messager.alert('温馨提示', '请先选择一条数据！', 'warning');
                return;
            }
            //回显表单数据
            dictionary_form.form("load", row);

            //设置弹出框标题
            dictionary_dialog.dialog("setTitle", "数据字典目录编辑");
            //打开弹出框
            dictionary_dialog.dialog("open");
        },

        //保存
        save: function () {
            //提交表单
            dictionary_form.form("submit", {
                url: "/systemDictionary/saveOrUpdate.do",
                success: function (data) {
                    //先转换为json对象
                    var json = JSON.parse(data);
                    if (json.success) {
                        $.messager.alert('温馨提示', '保存成功！', 'info', function () {
                        });
                        //关闭弹出框
                        dicMethodObj["cancel"]();
                        //重新加载数据
                        dictionary_datagrid.datagrid("reload");
                    } else {
                        $.messager.alert('温馨提示', json.msg, 'error')
                    }
                }
            });
        },

        //删除
        del:function () {
            //判断是否选中数据
            var row = dictionary_datagrid.datagrid("getSelected");
            if (!row) {
                $.messager.alert('温馨提示', '请先选择一条数据！', 'warning');
                return;
            }

            //弹出确认框
            $.messager.confirm('温馨提示', '您确认要删除吗？', function (r) {
                if (r) {
                    //发送请求
                    $.get("/systemDictionary/delete.do", {id: row.id}, function (data) {
                        if (data.success) {
                            $.messager.alert('温馨提示', '操作成功！', 'info', function () {
                            });
                            //重新加载数据
                            dictionary_datagrid.datagrid("reload");
                        } else {
                            $.messager.alert('温馨提示', '操作失败！', 'error')
                        }
                    })
                }
            });
        },

        //刷新
        reload:function () {
            dictionary_datagrid.datagrid("reload");
        },

        //取消
        cancel: function () {
            dictionary_dialog.dialog("close");
        }

    }

    //字典toolbar按钮统一绑定事件
    $("#dictionary_toolbar a[data-cmd]").on("click",function () {
        dicMethodObj[$(this).data("cmd")]();
    });

    //字典buttons按钮统一绑定事件
    $("#dictionary_btns a[data-cmd]").on("click",function () {
        dicMethodObj[$(this).data("cmd")]();
    });


    //字典datagrid
    dictionary_datagrid.datagrid({
        fit: true,
        fitColumns: true,
        toolbar: "#dictionary_toolbar",
        url: "/systemDictionary/query.do",
        pagination: true,
        rownumbers: true,
        singleSelect: true,
        columns: [
            [
                {title: "目录编号", field: "sn", width: 50},
                {title: "目录名称", field: "name", width: 50},
                {title: "目录简介", field: "intro", width: 50}
            ]
        ],
        onClickRow:function (index,row) {
            $.get("/systemDictionaryItem/query.do",{parentId:row.id},function (data) {
                dictionaryItem_datagrid.datagrid("load",data);
            });
        }
    });

    //字典dialog
    dictionary_dialog.dialog({
        width: "320",
        height: "240",
        buttons: "#dictionary_btns",
        closed: "true",
        onClose: function () {
            //清空表单数据
            dictionary_form.form("clear");
        }
    });


    //-----------------------------------------------

    //明细datagrid
    dictionaryItem_datagrid.datagrid({
        fit: true,
        fitColumns: true,
        toolbar: "#dictionaryItem_toolbar",
        pagination: true,
        rownumbers: true,
        singleSelect: true,
        columns: [
            [
                {title: "明细名称", field: "name", width: 50},
                {title: "明细简介", field: "intro", width: 50},
                {
                    title: "所属目录", field: "parent", width: 50, formatter: function (value) {
                        return value ? parent.name:"";
                    }
                }
            ]
        ]
    });

    var itemMethodObj = {
        add:function () {
            //打开弹出框
            dictionaryItem_dialog.dialog("setTitle", "数据字典明细新增");
            dictionaryItem_dialog.dialog("open");
        },
        edit:function(){
            //判断是否选中数据
            var row = dictionaryItem_datagrid.datagrid("getSelected");
            if (!row) {
                $.messager.alert('温馨提示', '请先选择一条数据！', 'warning');
                return;
            }

            if (row.parent) {
                row["parent.id"] = row.parent.id;
            }

            //回显表单数据
            dictionaryItem_form.form("load", row);

            //设置弹出框标题
            dictionaryItem_dialog.dialog("setTitle", "数据字典目录编辑");
            //打开弹出框
            dictionaryItem_dialog.dialog("open");
        },

        //保存
        save: function () {
            //提交表单
            dictionaryItem_form.form("submit", {
                url: "/systemDictionaryItem/saveOrUpdate.do",
                success: function (data) {
                    //先转换为json对象
                    var json = JSON.parse(data);
                    if (json.success) {
                        $.messager.alert('温馨提示', '保存成功！', 'info', function () {
                        });
                        //关闭弹出框
                        itemMethodObj["cancel"]();
                        //重新加载数据
                        dictionaryItem_datagrid.datagrid("reload");
                    } else {
                        $.messager.alert('温馨提示', json.msg, 'error')
                    }
                }
            });
        },

        //删除
        del:function () {
            //判断是否选中数据
            var row = dictionaryItem_datagrid.datagrid("getSelected");
            if (!row) {
                $.messager.alert('温馨提示', '请先选择一条数据！', 'warning');
                return;
            }

            //弹出确认框
            $.messager.confirm('温馨提示', '您确认要删除吗？', function (r) {
                if (r) {
                    //发送请求
                    $.get("/systemDictionaryItem/delete.do", {id: row.id}, function (data) {
                        if (data.success) {
                            $.messager.alert('温馨提示', '操作成功！', 'info', function () {
                            });
                            //重新加载数据
                            dictionaryItem_datagrid.datagrid("reload");
                        } else {
                            $.messager.alert('温馨提示', '操作失败！', 'error')
                        }
                    })
                }
            });
        },

        //刷新
        reload:function () {
            dictionaryItem_datagrid.datagrid("reload");
        },

        //取消
        cancel: function () {
            dictionaryItem_dialog.dialog("close");
        }

    }

    //字典toolbar按钮统一绑定事件
    $("#dictionaryItem_toolbar a[data-cmd]").on("click",function () {
        itemMethodObj[$(this).data("cmd")]();
    });

    //字典buttons按钮统一绑定事件
    $("#dictionaryItem_btns a[data-cmd]").on("click",function () {
        itemMethodObj[$(this).data("cmd")]();
    });

    //明细dialog
    dictionaryItem_dialog.dialog({
        width: "320",
        height: "240",
        buttons: "#dictionaryItem_btns",
        closed: "true",
        onClose: function () {
            //清空表单数据
            dictionary_form.form("clear");
        }
    });
});



