'use strict';

ecommerceApp.controller('PortfolioInstrumentPositionController', function ($scope, resolvedPortfolioInstrumentPosition, PortfolioInstrumentPosition, resolvedPortfolio, resolvedInstrument) {

        $scope.portfolioInstrumentPositions = resolvedPortfolioInstrumentPosition;
        $scope.portfolios = resolvedPortfolio;
        $scope.instruments = resolvedInstrument;

        $scope.create = function () {
            PortfolioInstrumentPosition.save($scope.portfolioInstrumentPosition,
                function () {
                    $scope.portfolioInstrumentPositions = PortfolioInstrumentPosition.query();
                    $('#savePortfolioInstrumentPositionModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.portfolioInstrumentPosition = PortfolioInstrumentPosition.get({id: id});
            $('#savePortfolioInstrumentPositionModal').modal('show');
        };

        $scope.delete = function (id) {
            PortfolioInstrumentPosition.delete({id: id},
                function () {
                    $scope.portfolioInstrumentPositions = PortfolioInstrumentPosition.query();
                });
        };

        $scope.clear = function () {
            $scope.portfolioInstrumentPosition = {date: null, quantity: null, id: null};
        };
    });
