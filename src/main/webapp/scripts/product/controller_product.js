'use strict';

ecommerceApp.controller('ProductController', function ($scope, resolvedProduct, Product) {

        $scope.products = resolvedProduct;

        $scope.create = function () {
            Product.save($scope.product,
                function () {
                    $scope.products = Product.query();
                    $('#saveProductModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.product = Product.get({id: id});
            $('#saveProductModal').modal('show');
        };

        $scope.delete = function (id) {
            Product.delete({id: id},
                function () {
                    $scope.products = Product.query();
                });
        };

        $scope.clear = function () {
            $scope.product = {name: null, shortDescr: null, longDescr: null, photoURL: null, price: null, id: null};
        };
    });
