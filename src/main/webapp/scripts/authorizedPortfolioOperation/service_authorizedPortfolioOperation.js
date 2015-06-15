'use strict';

ecommerceApp.factory('AuthorizedPortfolioOperation', function ($resource) {
        return $resource('app/rest/authorizedPortfolioOperations/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
