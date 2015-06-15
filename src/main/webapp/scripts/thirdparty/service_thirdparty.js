'use strict';

ecommerceApp.factory('Thirdparty', function ($resource) {
        return $resource('app/rest/thirdpartys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
