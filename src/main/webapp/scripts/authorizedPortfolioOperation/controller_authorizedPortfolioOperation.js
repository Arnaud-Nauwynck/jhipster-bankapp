'use strict';

ecommerceApp.controller('AuthorizedPortfolioOperationController', function ($scope, resolvedAuthorizedPortfolioOperation, AuthorizedPortfolioOperation, resolvedInstrument, resolvedPortfolio) {

        $scope.authorizedPortfolioOperations = resolvedAuthorizedPortfolioOperation;
        $scope.instruments = resolvedInstrument;
        $scope.portfolios = resolvedPortfolio;

        $scope.create = function () {
            AuthorizedPortfolioOperation.save($scope.authorizedPortfolioOperation,
                function () {
                    $scope.authorizedPortfolioOperations = AuthorizedPortfolioOperation.query();
                    $('#saveAuthorizedPortfolioOperationModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.authorizedPortfolioOperation = AuthorizedPortfolioOperation.get({id: id});
            $('#saveAuthorizedPortfolioOperationModal').modal('show');
        };

        $scope.delete = function (id) {
            AuthorizedPortfolioOperation.delete({id: id},
                function () {
                    $scope.authorizedPortfolioOperations = AuthorizedPortfolioOperation.query();
                });
        };

        $scope.clear = function () {
            $scope.authorizedPortfolioOperation = {name: null, operType: null, minQuantity: null, maxQuantity: null, instrumentId:null, fromPortfolioId:null, toPortfolioId:null, id: null};
        };
    });
