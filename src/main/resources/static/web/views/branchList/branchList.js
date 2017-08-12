define(function (require, exports, module) {

    var userTable;

    var code;

    var userId;

    var id = utils.getQuery("id");

    var Home = Backbone.View.extend({

        el: document.getElementsByTagName('body')[0],

        events: {
            "click .edit-btn": "handlerEdit",
            "click .sign-btn": "handlerSign",
            "click .stop-btn": "handlerStop",
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
            this.render();
            this.initData();
          /*  this.getData();*/

        },

        render: function () {
            $("#toolBox").empty().append(this.template(this.model.toJSON()));
        },

        initData: function () {
           userTable = $('#table').DataTable({
                "ajax": {
                    url: baseUrl + "/merchant/branches",
                    "data": function (d) {
                        d.merchantId = id;
                        return JSON.stringify(d);
                    }
                },
                "columns": [
                    {"data": "merchantName"},
                    {"data": "name"},
                    {"data": "city"},
                    {"data": "address"},
                    {"data": "codeAddress"},
                    {
                        render:function (data, type, row, meta) {
                            var str = "";

                            if ($.inArray("/merchant/codeAddress-get", resourceData) > -1) {
                                str += "<button  data-text='二维码配置'  data-id='editCodeAddress' data-link='../branchProfile/branchProfile.html?id=" + row.id + "' class='btn btn-primary edit-btn btn-xs margin-right-5'>二维码配置</button>"
                            }

                            return str;
                        }
                    }
                ]
            });
            /*var _this = this;
            utils.getJSON("/merchant/branches/"+id, {}, function (res) {
                _this.dealData(res);
            });*/
        },

        handlerEdit: function (event) {
            addTab(event, true);
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

seajs.use('./branchList.js');
