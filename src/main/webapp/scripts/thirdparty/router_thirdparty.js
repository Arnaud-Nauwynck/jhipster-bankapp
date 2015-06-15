'use strict';

ecommerceApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/thirdparty', {
                    templateUrl: 'views/thirdpartys.html',
                    controller: 'ThirdpartyController',
                    resolve:{
                        resolvedThirdparty: ['Thirdparty', function (Thirdparty) {
                            return Thirdparty.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
