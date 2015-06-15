'use strict';

ecommerceApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/portfolio', {
                    templateUrl: 'views/portfolios.html',
                    controller: 'PortfolioController',
                    resolve:{
                        resolvedPortfolio: ['Portfolio', function (Portfolio) {
                            return Portfolio.query().$promise;
                        }],
                        resolvedThirdparty: ['Thirdparty', function (Thirdparty) {
                            return Thirdparty.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
