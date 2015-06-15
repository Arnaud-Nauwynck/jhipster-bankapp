'use strict';

ecommerceApp.factory('Instrument', function ($resource) {
        return $resource('app/rest/instruments/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
