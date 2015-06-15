'use strict';

ecommerceApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/instrument', {
                    templateUrl: 'views/instruments.html',
                    controller: 'InstrumentController',
                    resolve:{
                        resolvedInstrument: ['Instrument', function (Instrument) {
                            return Instrument.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
