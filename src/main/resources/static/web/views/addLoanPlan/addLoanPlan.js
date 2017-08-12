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
            var financeName = $(".finance-name").val();
            var actualAnnual = $(".actual-annual").val();
            var earlyStage = $(".early-stage").val();
            var laterStage = $(".later-stage").val();
            var earlyRate = $(".early-rate").val();
            var laterRate = $(".later-rate").val();
            var addedRate = $(".added-rate").val();
            var postData = {
                "financeSolutionName": financeName,
                "actualAnnual": actualAnnual,
                "earlyStage": earlyStage,
                "laterStage": laterStage,
                "earlyRate": earlyRate,
                "laterRate": laterRate,
                "addedRate": addedRate,
            }
            if (financeName == "") {
                utils.showTip("请输入方案名称");
                return;
            }
            if (actualAnnual == "") {
                utils.showTip("请输入实际年化");
                return;
            }
            if (earlyStage == "") {
                utils.showTip("请输入前期");
                return;
            }
            if (laterStage == "") {
                utils.showTip("请输入后期");
                return;
            }
            if (earlyRate == "") {
                utils.showTip("请输入前期费率");
                return;
            }
            if (laterRate == "") {
                utils.showTip("请输入后期费率");
                return;
            }
            if (addedRate == "") {
                utils.showTip("请输入名义利率");
                return;
            }

            if (id) {
                this.handlerEdit(postData);
            } else {
                // if (password == "") {
                //     utils.showTip("请输入密码");
                //     return;
                // } else {
                //     postData["password"] = password;
                // }
                this.handlerAdd(postData);
            }
        },

        handlerAdd: function (postData) {
            var _this = this;
            utils.getPOST("/loanPlan", postData, function (res) {
                utils.showTip("添加成功");
                setTimeout(function () {
                    _this.refreshData();
                }, 1000);

            })
        },

        handlerEdit: function (postData) {
            var _this = this;
            postData["id"] = id;
            utils.getPUT("/loanPlan", postData, function (res) {
                utils.showTip("修改成功");

                setTimeout(function () {
                    _this.refreshData();
                }, 1000);

            })
        },

        initData: function () {
            var _this = this;
            if (id) {
                $(".sure-btn").text("修改");
                $(".p-line").hide();
                utils.getJSON("/loanPlan/" + id, {}, function (res) {
                    _this.dealData(res);
                })
            }
        },

        dealData: function (res) {
            $(".finance-name").val(res.financeSolutionName);
            $(".actual-annual").val(res.actualAnnual);
            $(".early-stage").val(res.earlyStage);
            $(".later-stage").val(res.laterStage);
            $(".early-rate").val(res.earlyRate);
            $(".later-rate").val(res.laterRate);
            $(".added-rate").val(res.addedRate);

        },

        refreshData: function () {
            closeTab(frameElement);
        }


    });

    var home = new Home();

});

seajs.use('./addLoanPlan.js');
