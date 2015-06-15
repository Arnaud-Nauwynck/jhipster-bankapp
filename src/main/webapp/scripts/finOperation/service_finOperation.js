'use strict';

ecommerceApp.factory('FinOperation', function ($resource) {
        return $resource('app/rest/finOperations/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
