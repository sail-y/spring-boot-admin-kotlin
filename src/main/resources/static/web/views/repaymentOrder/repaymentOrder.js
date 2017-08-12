define(function (require, exports, module) {

    var table;
    
    var code;

    var userId;

    var resourceId;

    var Home = Backbone.View.extend({

        el: document.getElementsByTagName('body')[0],

        events: {
            "click .edit-btn": "handlerEdit",
            "click .del-btn": "handlerDelete",
            "click .add-btn": "handlreAdd",
            "click .close-btn": "handlerClose",
            "click .auth-sure": "handlerAuth",
            "click .auth-btn": "handlerShow",
            "click .refresh-btn" : "handlerRefresh",
            "click .search-btn" : "handlerSearch"
        },
        template: _.template($('#buttonTemplate').html()),
        initialize: function () {
            this.model = new Backbone.Model();
            this.model.set("resourceData", resourceData);
            this.initData();
            this.render();
            this.hideView();
            this.getData();

        },

        render: function () {
            $("#toolBox").empty().append(this.template(this.model.toJSON()));
        },

        initData: function () {
            console.log(baseUrl)
            table = $('#table').DataTable({
                "ajax": {
                    url: baseUrl + "/repaymentorder/getrepaymentorder",
                    data: function (params) {
                        $.extend(params, $.serializeObject($('#searchForm')));
                        return JSON.stringify(params);
                    }
                },
                "columns": [
                    {"data": "orderNumber"},
                    {"data": "borrowId"},
                    {"data": "amount"},
                    {"data": "createTime"},
                    {"data": "repayExpireDate"},
                    {"data": "totalRepay"},
                    {"data": "serviceCharge"},
                    {"data": "addAmount"},
                    {"data": "repayStatus"},
                    {"data": "repayStartDate"}
                ]
            });
        },

        handlerEdit: function (event) {

            addTab(event, true);
        },

        handlerDelete: function (event) {
            var target = $(event.currentTarget);
            resourceId = target.data("id");
            $(".alert-view .alert-txt", parent.document).text("确定要删除吗？");
            $(".alert-view", parent.document).show();

        },

        hideView: function () {
            var _this = this;

            $(".alert-view .s-btn", parent.document).click(function () {
                $(".alert-view", parent.document).hide();
                _this.handlerSureDel();
            })
        },

        handlerSureDel: function () {
            var _this = this;
            utils.getDelete("/role/" + resourceId, {}, function (res) {
                utils.showTip("删除成功");
                setTimeout(function () {
                    table.ajax.reload(null, false);
                }, 1000);
            })
        },

        handlreAdd: function (event) {
            addTab(event, true);
        },

        getData: function () {
           
        },

        handlerClose: function (event) {
            var target = $(event.currentTarget);
            target.parent().parent().hide();
        },

        handlerRefresh: function () {
            table.ajax.reload(null, false);
        },

        handlerSearch: function () {
        	table.ajax.reload();
//        	table.ajax.reload(null, false);
//        	var name = $(".name").val();
//            var schoolName = $(".schoolName").val();
//            var compName = $(".compName").val();
//            var payTimeStart = $(".payTimeStart").val();
//            var payTime = $(".payTimeEnd").val();
//        	table.ajax.url( baseUrl + "/paytuition/getpaytuitionrecord?name=" + name ).load();
        }
    });

    var home = new Home();

});

seajs.use('./repaymentOrder.js');
