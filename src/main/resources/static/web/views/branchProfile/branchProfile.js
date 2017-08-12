define(function (require, exports, module) {


    var id = utils.getQuery("id");

    var Home = Backbone.View.extend({

        el: document.getElementsByTagName('body')[0],

        events: {
            "click .sure-btn": "handlerSure"
        },

        initialize: function () {
            this.model = new Backbone.Model();
            this.initData();
        },

        handlerSure: function () {
            var name = $(".name").val();
            var codeAddress = $(".codeAddress").val();
            var postData = {
                "id":id,
                "name": name,
                "codeAddress": codeAddress,
            }
            if (codeAddress == "") {
                utils.showTip("请输入二维码地址");
                return;
            }

            this.handlerAdd(postData);
        },

        handlerAdd: function (postData) {
            var _this = this;
            utils.getPUT("/merchant/branch", postData, function (res) {
                utils.showTip("修改成功");
                setTimeout(function () {
                    _this.refreshData();
                }, 1000);

            })
        },

        refreshData: function () {
            closeTab(frameElement);
        },

        initData: function () {
            var _this = this;
            utils.getJSON("/merchant/branch/detail/"+id, {}, function (res) {
                _this.dealData(res);
            });
        },

        dealData: function (res) {
            $(".name").val(res.name);
            $(".codeAddress").val(res.codeAddress);
        },


    });

    var home = new Home();

});

seajs.use('./branchProfile.js');
