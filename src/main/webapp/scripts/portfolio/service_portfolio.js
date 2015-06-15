'use strict';

ecommerceApp.factory('Portfolio', function ($resource) {
        return $resource('app/rest/portfolios/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
