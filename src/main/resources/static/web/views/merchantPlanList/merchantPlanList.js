define(function (require, exports, module) {

    var userTable;

    var code;

    var userId;

    var Home = Backbone.View.extend({

        el: document.getElementsByTagName('body')[0],

        events: {
            "click .del-btn": "handlerDelete",
            "click .add-btn": "handlerAdd",
            "click .refresh-btn": "handlerRefresh",
            "click .detail-btn" : "handlerDetail"
        },
        template: _.template($('#buttonTemplate').html()),

        initialize: function () {
            this.model = new Backbone.Model();
            this.model.set("resourceData", resourceData);
            this.render();
            this.initData();
        },

        render: function () {
            $("#toolBox").empty().append(this.template(this.model.toJSON()));
        },

        initData: function () {
            userTable = $('#table').DataTable({
                "ajax": {
                    url: baseUrl + "/merchantPlan/tables"
                },
                "columns": [
                    {"data": "merchantName"},
                    {"data": "planName"},
                    {
                        render: function (data, type, row, meta) {
                            var str = "";
                            if ($.inArray("/merchantPlan/*-get", resourceData) > -1) {
                                str += "<button  data-text='查看详细资料'  data-id='viewDetail' data-link='../merchantPlanDetail/merchantPlanDetail.html?id=" + row.id + "' class='btn btn-primary detail-btn btn-xs margin-right-5'>查看详细资料</button>"
                            }

                            if ($.inArray("/merchantPlan/*-delete", resourceData) > -1) {
                                str += "<button data-id='" + row.id + "' class='btn btn-danger del-btn btn-xs'><i class='fa fa-trash-o' aria-hidden='true'></i> 删除</button>"
                            }
                            return str;
                        }


                    }
                ]
            });
        },

        handlerDetail: function (event) {
            addTab(event, true);
        },

        handlerDelete: function (event) {
            var target = $(event.currentTarget);
            resourceId = target.data("id");
            utils.showConfirm("确定要删除吗？", this.handlerSureDel);
        },

        handlerSureDel: function () {
            var _this = this;
            utils.getDelete("/merchantPlan/" + resourceId, {}, function (res) {
                utils.showTip("删除成功");
                setTimeout(function () {
                    userTable.ajax.reload(null, false);
                }, 1000);
            })
        },

        handlerAdd: function (event) {
            var id = $(".item-ul li.active", parent.document).data("id");
            addTab(event, true, id);
        },

        handlerRefresh: function () {
            userTable.ajax.reload(null, false);
        }


    });

    var home = new Home();

});

seajs.use('./merchantPlanList.js');
