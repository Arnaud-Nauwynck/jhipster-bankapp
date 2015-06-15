'use strict';

ecommerceApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/portfolioInstrumentPosition', {
                    templateUrl: 'views/portfolioInstrumentPositions.html',
                    controller: 'PortfolioInstrumentPositionController',
                    resolve:{
                        resolvedPortfolioInstrumentPosition: ['PortfolioInstrumentPosition', function (PortfolioInstrumentPosition) {
                            return PortfolioInstrumentPosition.query().$promise;
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
