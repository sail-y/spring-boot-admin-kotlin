define(function (require, exports, module) {

    var userTable;

    var code;

    var userId;

    var resourceId;

    var Home = Backbone.View.extend({

        el: document.getElementsByTagName('body')[0],

        events: {
            "click .pass-btn": "handlerPass",
            "click .refuse-btn": "handlerRefuse",
            "click .refresh-btn": "handlerRefresh",
            "click .search-btn" : "handlerSearch"
        },
        template: _.template($('#buttonTemplate').html()),

        initialize: function () {
            this.model = new Backbone.Model();
            this.model.set("resourceData", resourceData);
            this.render();
            this.initData();
            // this.getData();

        },

        render: function () {
            $("#toolBox").empty().append(this.template(this.model.toJSON()));
            this.handlerLoadDatetimepicker();

        },

        initData: function () {
            userTable = $('#table').DataTable({
                "ajax": {
                    url: baseUrl + "/loanBorrow/tables",
                    data: function (params) {
                        $.extend(params, $.serializeObject($('#searchForm')));
                        return JSON.stringify(params);
                    }
                },
                "columns": [
                    {"data": "borrowNumber"},
                    {"data": "userName"},
                    {"data": "userPhone"},
                    {"data": "userIdCard"},
                    {"data": "approvePeriodAmount"},
                    {"data": "createTime"},
                    {"data": "approvePlanName"},
                    {"data": "approveStateName"},
                    {"data": "approveAmount"},
                    {
                        render: function (data, type, row, meta) {
                            var str = "";
                            if(row.approveState && row.approveState == 2){
                                if ($.inArray("/loanBorrow/pass/*-post", resourceData) > -1) {
                                    str += "<button data-id='" + row.id + "' class='btn btn-primary pass-btn btn-xs  margin-right-5'><i class='fa fa-check' aria-hidden='true'></i> 同意</button>"
                                }
                                if ($.inArray("/loanBorrow/refuse/*-post", resourceData) > -1) {
                                    str += "<button data-id='" + row.id + "' class='btn btn-danger refuse-btn btn-xs'><i class='fa fa-times' aria-hidden='true'></i> 拒绝</button>"
                                }
                            }
                            return str;
                        }


                    }
                ]
            });
        },

        handlerPass: function (event) {
            var target = $(event.currentTarget);
            resourceId = target.data("id");
            utils.showConfirm("确认要通过吗？", this.handlerSurePass);
        },
        handlerSurePass: function () {
            var _this = this;
            utils.getPOST("/loanBorrow/pass/" + resourceId, {}, function (res) {
                utils.showTip("申请通过");
                setTimeout(function () {
                    userTable.ajax.reload(null, false);
                }, 1000);
            })
        },
        handlerRefuse: function (event) {
            var target = $(event.currentTarget);
            resourceId = target.data("id");
            utils.showConfirm("确认要拒绝吗？", this.handlerSureRefuse);

        },
        handlerSureRefuse: function () {
            var _this = this;
            utils.getPOST("/loanBorrow/refuse/" + resourceId, {}, function (res) {
                utils.showTip("拒绝申请");
                setTimeout(function () {
                    userTable.ajax.reload(null, false);
                }, 1000);
            })
        },
        handlerLoadDatetimepicker: function () {
            $(".datetime-start").datetimepicker({
                // format : 'yyyy-mm-dd hh:ii:ss',
                minView:'hour',
                language: 'zh-CN',
                autoclose:true
//        startDate:new Date()
            }).on("click",function(){
                $(".datetime-start").datetimepicker("setEndDate",$(".datetime-end").val())
            });
            $(".datetime-end").datetimepicker({
                // format : 'yyyy-mm-dd hh:ii:ss',
                minView:'hour',
                language: 'zh-CN',
                autoclose:true
//        startDate:new Date()
            }).on("click",function(){
                $(".datetime-end").datetimepicker("setStartDate",$(".datetime-start").val())
            });
        },
        handlerSearch: function () {
            userTable.ajax.reload();
       	 //    var borrowNumber = $(".borrow-number").val();
            // var approveState = $(".approve-state").val();
            // var datetimeStart = $(".payTimeStart").val();
            // var datetimeEnd = $(".datetime-end").val();
        	// table.ajax.url( baseUrl + "/paytuition/getpaytuitionrecord?name=" + name ).load();

        },

        handlerRefresh: function () {
            userTable.ajax.reload(null, false);
        }

    });

    var home = new Home();

});

seajs.use('./loanBorrowList.js');
