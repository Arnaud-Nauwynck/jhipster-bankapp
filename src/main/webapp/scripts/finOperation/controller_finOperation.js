'use strict';

ecommerceApp.controller('FinOperationController', function ($scope, resolvedFinOperation, FinOperation, resolvedPortfolio, resolvedInstrument) {

        $scope.finOperations = resolvedFinOperation;
        $scope.portfolios = resolvedPortfolio;
        $scope.instruments = resolvedInstrument;

        $scope.create = function () {
            FinOperation.save($scope.finOperation,
                function () {
                    $scope.finOperations = FinOperation.query();
                    $('#saveFinOperationModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.finOperation = FinOperation.get({id: id});
            $('#saveFinOperationModal').modal('show');
        };

        $scope.delete = function (id) {
            FinOperation.delete({id: id},
                function () {
                    $scope.finOperations = FinOperation.query();
                });
        };

        $scope.clear = function () {
            $scope.finOperation = {creationDate: null, valueDate: null, quantity: null, comment: null, id: null};
        };
    });
