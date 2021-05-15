$(function () {
    var myDataGrid = $("#myDataGrid");
    var myDialog = $("#myDialog");
    var myForm = $("#myForm");

    //数据表格
    myDataGrid.datagrid({
        fit: true,
        fitColumns: true,
        toolbar: "#myToolbar",
        url: "/employee/query.do",
        pagination: true,
        rownumbers: true,
        singleSelect: true,
        //默认排序
        sortName: "hireDate",
        sortOrder: "desc",
        columns: [
            [
                {title: "用户名", field: "username", width: 50},
                {title: "真实姓名", field: "realname", width: 50},
                {title: "电话", field: "tel", width: 50},
                {title: "邮箱", field: "email", width: 50},
                {
                    title: "部门名称", field: "dept", width: 50, formatter: function (value, row, index) {
                        return value ? value.name : "";
                    }
                },
                {title: "入职时间", field: "hireDate", width: 50, sortable: true},
                {
                    title: "状态", field: "state", width: 50, formatter: function (value, row, index) {
                        return value ? "<font color='green'>在职</font>" : "<font color='red'>离职</font>";
                    }
                },
                {
                    title: "是否管理员", field: "admin", width: 50, formatter: function (value, row, index) {
                        return value ? "<font color='green'>是</font>" : "<font color='red'>否</font>";
                    }
                }
            ]
        ],
        onSelect: function (index, row) {
            //判断员工的状态显示文字
            if (row.state) {
                $("#state_btn").linkbutton({
                    text: "离职"
                })
            } else {
                $("#state_btn").linkbutton({
                    text: "复职"
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
            myForm.form("clear");
        },
        onOpen: function () {
            //重新查询角色下拉框
            $("#role_combobox").combobox("reload");
        }
    });

    //文件上传框
    $("#import").dialog({
        width: "400",
        height: "200",
        closed: "true",
        title: "文件上传",
        buttons: "#import_btns"
    });

    //使用一个对象统一管理事件的方法
    var methods = {
        //多条件查询
        query: function () {
            //获取查询条件的值(推荐使用easyui里面的方法)
            var keyword = $("#keyword").textbox("getValue");
            var minDate = $("#minDate").textbox("getValue");
            var maxDate = $("#maxDate").textbox("getValue");
            var deptId = $("#deptId").textbox("getValue");
            //让表格重新加载数据,从首页加载,并且带上查询参数
            myDataGrid.datagrid("load", {
                keyword: keyword,
                minDate: minDate,
                maxDate: maxDate,
                deptId: deptId
            })
        },

        //新增
        add: function () {
            //显示密码框
            $("#tr_password").show();
            //开启密码框验证
            $("#passwordbox").passwordbox("enableValidation");
            //超管默认"否"
            $("#admin").combobox("select", "0");
            //打开弹出框
            myDialog.dialog("setTitle", "新增员工");
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
            //隐藏密码框
            $("#tr_password").hide();
            //禁用密码框验证
            $("#passwordbox").passwordbox("disableValidation");
            //回显角色
            $.get("/role/selectByEmployeeId.do", {employeeId: row.id}, function (data) {
                $("#role_combobox").combobox("setValues", data);
            });

            //处理部门数据
            if (row.dept) {
                row["dept.id"] = row.dept.id;
            }
            //回显表单数据
            myForm.form("load", row);

            //设置弹出框标题
            myDialog.dialog("setTitle", "编辑员工");
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
                    $.get("/employee/changeState.do", {id: row.id}, function (data) {
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
                url: "/employee/saveOrUpdate.do",
                //提交表单时,设置角色的参数名称 roles[i].id=XXX
                onSubmit: function (param) {
                    //所有已选择的角色的id
                    var roleIds = $("#role_combobox").combobox("getValues");
                    $.each(roleIds, function (index, roleId) {
                        param["roles[" + index + "].id"] = roleId;
                    });
                    //手动调用验证表单的方法
                    return myForm.form("validate");
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
        },

        //导出
        export: function () {
            window.open("/employee/export.do?" + $("#queryForm").serialize());
        },

        //导入按钮,单击打开上传窗口
        import: function () {
            $("#import").dialog("open");
        },

        //关闭文件上传框
        close: function () {
            $("#import").dialog("close");
        },

        //导入文件
        upload: function () {
            //获取上传文件控件内容
            var file = $("#filebox_file_id_1")[0].files[0];
            //判断控件中是否存在文件内容，如果不存在，弹出提示信息，阻止进一步操作
            if (file == null) {
                alert('错误，请选择一个指定文件!');
                return;
            }
            //获取文件名称
            var fileName = file.name;
            //获取文件类型名称
            var file_typename = fileName.substring(fileName.lastIndexOf('.'), fileName.length);
            //这里限定上传文件文件类型必须为.xlsx，如果文件类型不符，提示错误信息
            if (file_typename == '.xlsx' || file_typename == '.xls') {
                //计算文件大小
                var fileSize = 0;
                //如果文件大小大于1024字节X1024字节，则显示文件大小单位为MB，否则为KB
                if (file.size > 1024 * 1024) {
                    fileSize = Math.round(file.size * 100 / (1024 * 1024)) / 100;

                    if (fileSize > 10) {
                        alert('错误，文件超过10MB，禁止上传！');
                        return;
                    }
                    fileSize = fileSize.toString() + 'MB';
                }
                else {
                    fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';
                }
                //将文件名和文件大小显示在前端label文本中
                $("#fileName").html("<span style='color:Blue'>文件名: " + file.name + ',大小: ' + fileSize + "</span>");
                //获取form数据
                var formData = new FormData($("#importFileForm")[0]);
                //调用apicontroller后台action方法，将form数据传递给后台处理。contentType必须设置为false,否则chrome和firefox不兼容
                $.ajax({
                    url: "/employee/importFile.do",
                    type: 'POST',
                    data: formData,
                    async: false,
                    cache: false,
                    contentType: false,
                    processData: false,
                    //请求成功
                    success: function (data) {
                        if (data.success) {
                            //上传成功后将控件内容清空，并显示上传成功信息
                            $("#file").val(null);
                            $("#uploadInfo").html("<span style='color:Red'>上传成功</span>");
                        } else {
                            //上传失败后
                            $("#uploadInfo").html("<span style='color:Red'>上传失败,请检查文件内容是否符合规范</span>");
                        }
                    }
                });
            }
            else {
                alert("文件类型错误");
                //将错误信息显示在前端label文本中
                $("#fileName").html("错误!请选择指定类型的文件!");
            }
        }


    };

    //绑定事件
    $("[data-cmd]").click(function () {
        var methodName = $(this).data("cmd");
        methods[methodName]();
    });

});


