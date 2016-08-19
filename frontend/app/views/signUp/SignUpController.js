(function () {
	'use strict';

	angular.module('todoListApp')
		.controller('SignUpController', ['$scope', '$state', 'loginService', function ($scope, $state, loginService) {
			$scope.signUp = function (user) {
				var promise = loginService.signUp(user);
				promise.success(function (data, status, headers, config) {
					$state.go('login');
				}).error(function (data, status, headers, config) {
					alert(data);
				});
			};
		}]);
})();