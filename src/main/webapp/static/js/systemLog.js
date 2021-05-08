$(function () {

    var myDataGrid = $("#myDataGrid");


    //数据表格
    myDataGrid.datagrid({
        fit: true,
        fitColumns: true,
        toolbar: "#myToolbar",
        url: "/systemLog/query.do",
        pagination: true,
        rownumbers: true,
        singleSelect: true,
        columns: [
            [
                {
                    title: "操作用户", field: "name", width: 50, formatter: function (value,row,index) {
                        if(row.opUser){
                            return row.opUser.username;
                        }
                    }
                },
                {title: "操作时间", field: "opTime", width: 50},
                {title: "登录IP", field: "opIp", width: 50},
                {title: "功能", field: "function", width: 50},
                {title: "参数", field: "params", width: 50}
            ]
        ]

    });


});



