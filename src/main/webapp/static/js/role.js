$(function () {

    var myDataGrid = $("#myDataGrid");
    var allPermissions = $("#allPermissions");
    var selfPermissions = $("#selfPermissions");
    var myDialog = $("#myDialog");
    var myForm = $("#myForm");

    //包含所有方法的方法对象
    var methods = {
        //新增
        add: function () {
            //打开弹出框
            myDialog.dialog("setTitle", "新增角色");
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
            //回显已有权限
            selfPermissions.datagrid("load", {roleId: row.id});
            //回显菜单
            $.get("/menu/selectByRoleId.do",{roleId:row.id},function (data) {
                $("#menu_combotree").combotree("setValues",data);
            });

            //设置弹出框标题
            myDialog.dialog("setTitle", "编辑角色");
            //打开弹出框
            myDialog.dialog("open");
        },


        //保存
        save: function () {
            //提交表单
            myForm.form("submit", {
                url: "/role/saveOrUpdate.do",
                //提交表单时,设置已有权限的参数名称 permissions[i].id=XXX
                onSubmit: function (param) {
                    //获取已有权限的所有行
                    var rows = selfPermissions.datagrid("getRows");
                    $.each(rows, function (index, row) {
                        param["permissions[" + index + "].id"] = row.id;
                    });

                    //获取菜单下拉框的选中的值
                    var menuIds = $("#menu_combotree").combotree("getValues");
                    $.each(menuIds, function (index, menuId) {
                        param["menus[" + index + "].id"] = menuId;
                    });
                },
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
        url: "/role/query.do",
        pagination: true,
        rownumbers: true,
        singleSelect: true,
        columns: [
            [
                {title: "角色名称", field: "name", width: 50},
                {title: "角色编码", field: "sn", width: 50}
            ]
        ]
    });

    //对话框
    myDialog.dialog({
        width: "600",
        height: "470",
        buttons: "#form_btns",
        closed: "true",
        onClose: function () {
            //清空表单数据
            myForm.form("clear");
            //清空已有权限,并且重新查询所有权限
            selfPermissions.datagrid("loadData", []);
            allPermissions.datagrid("reload");
        },
        onOpen:function () {
            //重新查询菜单树形框
            $("#menu_combotree").combotree("reload");
        }
    });

    //所有权限(数据表格)
    allPermissions.datagrid({
        title: "所有权限",
        height: 300,
        fitColumns: true,
        url: "/permission/list.do",
        singleSelect: true,
        columns: [
            [
                {title: "权限名称", field: "name", width: 50, align: "center"}
            ]
        ],
        //单击权限时移动到右侧
        onClickRow: function (index, row) {
            //往右侧添加
            selfPermissions.datagrid("appendRow", row);
            //删除左侧的
            allPermissions.datagrid("deleteRow", index);
        }
    });

    //角色拥有的权限(数据表格)
    selfPermissions.datagrid({
        title: "已有权限",
        height: 300,
        fitColumns: true,
        url: "/permission/selectByRoleId.do",
        singleSelect: true,
        columns: [
            [
                {title: "权限名称", field: "name", width: 50, align: "center"}
            ]
        ],
        //单击权限时移动到左侧
        onClickRow: function (index, row) {
            //往左侧添加
            allPermissions.datagrid("appendRow", row);
            //删除右侧的
            selfPermissions.datagrid("deleteRow", index);
        },
        onLoadSuccess: function (data) {
            //data:{rows:... , total:... }
            //获取所有已有权限的id
            var ids = $.map(data.rows, function (row) {
                return row.id;
            });
            //当所有权限中的id,与ids中的值相同的时候,移除该权限
            var rows = allPermissions.datagrid("getRows");
            for (var i = rows.length - 1; i >= 0; i--) {
                var index = $.inArray(rows[i].id, ids);
                if (index > -1) {
                    allPermissions.datagrid("deleteRow", i);
                }
            }
        }
    });

    //菜单树
    $("#menu_combotree").combotree({
        //勾选复选框事件(需求:勾选一个复选框时,让父菜单也成为被勾选的状态)
        onCheck: function (node, checked) {
            //获取tree对象(combotree不能调用tree的方法)
            var tree = $("#menu_combotree").combotree("tree");
            //先获取父菜单节点
            var parentNode = tree.tree("getParent", node.target);
            //再选中父节点
            if (parentNode) {
                tree.tree("check", parentNode.target);
            }

            //需求:勾选一个父节点的时候,让所有子节点也被勾选,取消勾选父节点的时候,所有子节点也取消勾选
            //获取所有子节点
            /* var childrenNodes = tree.tree("getChildren", node.target);
             if(childrenNodes){
                 if(checked){
                     //勾选子节点
                     $.each(childrenNodes,function (index, ele) {
                         tree.tree("check", ele.target);
                     })
                 }else{
                     //取消勾选子节点
                     $.each(childrenNodes,function (index, ele) {
                         tree.tree("uncheck", ele.target);
                     })
                 }
             }*/
        }
    });

});



