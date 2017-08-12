define(function (require, exports, module) {

    var userTable;

    var code;

    var userId;

    var id;

    var Home = Backbone.View.extend({

        el: document.getElementsByTagName('body')[0],

        events: {
            "click .edit-btn": "handlerEdit",
            "click .sign-btn": "handlerSign",
            "click .stop-btn": "handlerStop",
            "click .branch-btn": "handlerBranch",
            "click .del-btn": "handlerDelete",
            "click .add-btn": "handlerAdd",
            "click .close-btn": "handlerClose",
            "click .auth-sure": "handlerAuth",
            "click .auth-btn": "handlerShow",
            "click .refresh-btn": "handlerRefresh"
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
                    url: baseUrl + "/merchant/tables"
                },
                "columns": [
                    {"data": "name"},
                    {"data": "organizationCode"},
                    {"data": "cooperationState"},
                    {
                        render: function (data, type, row, meta) {
                            var str = "";

                            if ($.inArray("/merchant/*-get", resourceData) > -1) {
                                str += "<button  data-text='查看详细资料'  data-id='viewDetail' data-link='../merchantProfile/merchantProfile.html?id=" + row.id + "' class='btn btn-primary edit-btn btn-xs margin-right-5'>查看详细资料</button>"
                            }

                            if ($.inArray("/merchant/sign-put", resourceData) > -1) {
                                str += "<button data-text='签约'  data-id='" + row.id + "' class='btn btn-primary sign-btn btn-xs margin-right-5'>签约</button>"
                            }

                            if ($.inArray("/merchant/stop-put", resourceData) > -1) {
                                str += "<button data-text='停止合作'   data-id='" + row.id + "' class='btn btn-primary stop-btn btn-xs margin-right-5'>停止合作</button>"
                            }

                            if ($.inArray("/merchant/code/*-get", resourceData) > -1) {
                                str += "<button data-text='查看机构及二维码'  data-id='viewBranch' data-link='../branchList/branchList.html?id=" + row.id + "' class='btn btn-primary branch-btn btn-xs margin-right-5'>查看机构及二维码</button>"
                            }
/*
                            if ($.inArray("/user/!*-delete", resourceData) > -1) {
                                str += "<button data-id='" + row.id + "' class='btn btn-danger del-btn btn-xs'><i class='fa fa-trash-o' aria-hidden='true'></i> 删除</button>"
                            }*/
                            return str;
                        }


                    }
                ]
            });
        },

        handlerEdit: function (event) {
            addTab(event, true);
        },

        handlerBranch: function (event) {
            addTab(event, true);
        },

        handlerSign: function (event) {
            id = $(event.currentTarget).data("id");
            utils.showConfirm("确认签约吗？", this.handleSureSign);
        },

        handlerStop: function (event) {
            id = $(event.currentTarget).data("id");
            utils.showConfirm("确认停止合作吗？", this.handleSureStop);
        },

        handleSureSign: function () {
            var _this = this;
            utils.getPUT("/merchant/sign/" + id, {}, function (res) {
                utils.showTip("签约成功，账号和密码已发送至你的邮箱");
                setTimeout(function () {
                    userTable.ajax.reload(null, false);
                }, 1000);
            })
        },

        handleSureStop: function () {
            var _this = this;
            utils.getPUT("/merchant/stop/" + id, {}, function (res) {
                utils.showTip("停止合作成功");
                setTimeout(function () {
                    userTable.ajax.reload(null, false);
                }, 1000);
            })
        },


        handlerAdd: function (event) {
            var id = $(".item-ul li.active", parent.document).data("id");
            addTab(event, true, id);
        },


        handlerClose: function (event) {
            var target = $(event.currentTarget);
            target.parent().parent().hide();
        },


        handlerRefresh: function () {
            userTable.ajax.reload(null, false);
        }


    });

    var home = new Home();

});

seajs.use('./merchantList.js');
