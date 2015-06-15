'use strict';

ecommerceApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/finOperation', {
                    templateUrl: 'views/finOperations.html',
                    controller: 'FinOperationController',
                    resolve:{
                        resolvedFinOperation: ['FinOperation', function (FinOperation) {
                            return FinOperation.query().$promise;
                        }],
                        resolvedPortfolio: ['Portfolio', function (Portfolio) {
                            return Portfolio.query().$promise;
                        }],
                        resolvedInstrument: ['Instrument', function (Instrument) {
                            return Instrument.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
