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



        refreshData: function () {
            closeTab(frameElement);
        },

        initData: function () {
            var _this = this;
            utils.getJSON("/merchant/detail/"+id, {}, function (res) {
                _this.dealData(res);
            });
        },

        dealData: function (res) {
            $(".name").val(res.name);
            $(".organizationCode").val(res.organizationCode);
            $(".address").val(res.address);
            $(".linkman").val(res.linkman);
            $(".phoneNumber").val(res.phoneNumber);
            $(".email").val(res.email);
            $(".cooperationState").val(res.cooperationState);

        },


    });

    var home = new Home();

});

seajs.use('./merchantProfile.js');
