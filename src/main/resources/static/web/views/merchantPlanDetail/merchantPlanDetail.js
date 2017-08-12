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
            utils.getJSON("/merchantPlan/"+id, {}, function (res) {
                _this.dealData(res);
            });
        },

        dealData: function (res) {
            $(".merchant-name").val(res.merchantName);
            $(".plan-name").val(res.planName);
            $(".orig-cost").val(res.origCost);
            $(".add-cost").val(res.addCost);
            $(".add-way").val(res.addWay);
            $(".pre-month-amount").val(res.preMonthAmount);
            $(".later-month-amount").val(res.laterMonthAmount);

        },


    });

    var home = new Home();

});

seajs.use('./merchantPlanDetail.js');
