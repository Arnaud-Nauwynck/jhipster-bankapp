'use strict';

ecommerceApp.controller('PortfolioController', function ($scope, resolvedPortfolio, Portfolio, resolvedThirdparty) {

        $scope.portfolios = resolvedPortfolio;
        $scope.thirdpartys = resolvedThirdparty;

        $scope.create = function () {
            Portfolio.save($scope.portfolio,
                function () {
                    $scope.portfolios = Portfolio.query();
                    $('#savePortfolioModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.portfolio = Portfolio.get({id: id});
            $('#savePortfolioModal').modal('show');
        };

        $scope.delete = function (id) {
            Portfolio.delete({id: id},
                function () {
                    $scope.portfolios = Portfolio.query();
                });
        };

        $scope.clear = function () {
            $scope.portfolio = {id: null};
        };
    });
