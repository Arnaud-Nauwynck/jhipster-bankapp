'use strict';

ecommerceApp.controller('ThirdpartyController', function ($scope, resolvedThirdparty, Thirdparty) {

        $scope.thirdpartys = resolvedThirdparty;

        $scope.create = function () {
            Thirdparty.save($scope.thirdparty,
                function () {
                    $scope.thirdpartys = Thirdparty.query();
                    $('#saveThirdpartyModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.thirdparty = Thirdparty.get({id: id});
            $('#saveThirdpartyModal').modal('show');
        };

        $scope.delete = function (id) {
            Thirdparty.delete({id: id},
                function () {
                    $scope.thirdpartys = Thirdparty.query();
                });
        };

        $scope.clear = function () {
            $scope.thirdparty = {name: null, type: null, address: null, zipCode: null, id: null};
        };
    });
