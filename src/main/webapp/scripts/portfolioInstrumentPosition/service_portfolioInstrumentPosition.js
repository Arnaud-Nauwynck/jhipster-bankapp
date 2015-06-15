'use strict';

ecommerceApp.factory('PortfolioInstrumentPosition', function ($resource) {
        return $resource('app/rest/portfolioInstrumentPositions/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
