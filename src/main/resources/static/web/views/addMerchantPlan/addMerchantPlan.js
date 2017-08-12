define(function (require, exports, module) {


    var id = utils.getQuery("id");

    var Home = Backbone.View.extend({

        el: document.getElementsByTagName('body')[0],

        events: {
            "click .sure-btn": "handlerSure"
        },

        template: _.template($('#projectTemplate').html()),
        loanPlanTemplate:_.template($('#loanPlanTemplate').html()),
        initialize: function () {
            this.model = new Backbone.Model();
            this.initData();

        },

        handlerSure: function () {
            var merchantId = $(".merchantSelect").val();
            var planId = $(".merchantPlanSelect").val();
            var postData = {
                "merchantId": merchantId,
                "planId": planId
            }
            if (merchantId == "") {
                utils.showTip("请选择公司名称");
                return;
            }
            if (planId == "") {
                utils.showTip("请选择金融方案");
                return;
            }

            this.handlerAdd(postData);
        },

        handlerAdd: function (postData) {
            var _this = this;
            utils.getPOST("/merchantPlan", postData, function (res) {
                utils.showTip("添加成功");
                setTimeout(function () {
                    _this.refreshData();
                }, 1000);

            })
        },

        initData: function () {
            var _this = this;
            utils.getPOST("/merchantPlan/getLoanPlanList", {}, function (res) {
                _this.model.set("list", res);
                $(".merchantPlanSelect").empty().append(_this.loanPlanTemplate(_this.model.toJSON()));
            })

            utils.getPOST("/merchantPlan/getMerchantList", {}, function (res) {
                _this.model.set("list", res);
                $(".merchantSelect").empty().append(_this.template(_this.model.toJSON()));
            })
        },

        refreshData: function () {
            closeTab(frameElement);
        }


    });

    var home = new Home();

});

seajs.use('./addMerchantPlan.js');
