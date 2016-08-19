(function () {
	'use strict';

	angular.module('todoListApp')
		.factory('sessionService', ['$http', 'configAppService', function ($http, configAppService) {
			return {
				set: function (key, value) {
					return sessionStorage.setItem(key, value);
				},
				get: function (key) {
					return sessionStorage.getItem(key);
				},
				destroy: function (key) {
					return sessionStorage.removeItem(key);
				}
			};
		}]);
})();