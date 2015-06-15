'use strict';

ecommerceApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/authorizedPortfolioOperation', {
                    templateUrl: 'views/authorizedPortfolioOperations.html',
                    controller: 'AuthorizedPortfolioOperationController',
                    resolve:{
                        resolvedAuthorizedPortfolioOperation: ['AuthorizedPortfolioOperation', function (AuthorizedPortfolioOperation) {
                            return AuthorizedPortfolioOperation.query().$promise;
                        }],
                        resolvedInstrument: ['Instrument', function (Instrument) {
                            return Instrument.query().$promise;
                        }],
                        resolvedPortfolio: ['Portfolio', function (Portfolio) {
                            return Portfolio.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
