'use strict';

ecommerceApp.controller('InstrumentController', function ($scope, resolvedInstrument, Instrument) {

        $scope.instruments = resolvedInstrument;

        $scope.create = function () {
            Instrument.save($scope.instrument,
                function () {
                    $scope.instruments = Instrument.query();
                    $('#saveInstrumentModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.instrument = Instrument.get({id: id});
            $('#saveInstrumentModal').modal('show');
        };

        $scope.delete = function (id) {
            Instrument.delete({id: id},
                function () {
                    $scope.instruments = Instrument.query();
                });
        };

        $scope.clear = function () {
            $scope.instrument = {name: null, type: null, id: null};
        };
    });
