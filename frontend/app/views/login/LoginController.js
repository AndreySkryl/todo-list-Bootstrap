(function () {
	'use strict';

	angular.module('todoListApp')
		.controller('LoginController', ['$scope', 'loginService', function ($scope, loginService) {
			$scope.login = function (user) {
				loginService.login(user);
			};
		}]);
})();