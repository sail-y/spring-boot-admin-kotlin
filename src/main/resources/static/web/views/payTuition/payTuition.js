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
            this.handlerLoadDatetimepicker();
        },

        initData: function () {
            console.log(baseUrl)
            table = $('#table').DataTable({
                "ajax": {
                    url: baseUrl + "/paytuition/getpaytuitionrecord",
                    data: function (params) {
                        $.extend(params, $.serializeObject($('#searchForm')));
                        return JSON.stringify(params);
                    }
                },
                "columns": [
                    {"data": "name"},
                    {"data": "idCardNumber"},
                    {"data": "schoolName"},
                    {"data": "studyCourse"},
                    {"data": "courseAmount"},
                    {"data": "installmentAmount"},
                    {"data": "payTime"},
                    {"data": "payAmount"}
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

        handlerLoadDatetimepicker: function () {
            $(".datetime-start").datetimepicker({
                minView:'hour',
                language: 'zh-CN',
                autoclose:true
            }).on("click",function(){
                $(".datetime-start").datetimepicker("setEndDate",$(".datetime-end").val())
            });
            $(".datetime-end").datetimepicker({
                minView:'hour',
                language: 'zh-CN',
                autoclose:true
            }).on("click",function(){
                $(".datetime-end").datetimepicker("setStartDate",$(".datetime-start").val())
            });
        },
        
        handlerRefresh: function () {
            table.ajax.reload(null, false);
        },

        handlerSearch: function () {
        	table.ajax.reload();
        }
    });

    var home = new Home();

});

seajs.use('./payTuition.js');
