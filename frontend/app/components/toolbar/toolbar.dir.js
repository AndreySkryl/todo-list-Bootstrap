(function() {
	'use strict';

	angular.module('todoListApp')
		.directive('toolbar', toolbar);

	function toolbar() {
		return {
			templateUrl: 'components/toolbar/toolbar.tpl.html',
			controller: 'ToolbarController',
			controllerAs: 'toolbar'
		};
	}

	angular.module('todoListApp')
		.controller('ToolbarController', ['$scope', '$rootScope', 'loginService', 'sessionService', 'userService',
			function ($scope, $rootScope, loginService, sessionService, userService) {
				$scope.logout = function () {
					loginService.logout();
				};

				$scope.user = {};
				var guidOfUser = sessionService.get('uid');
				var syncModelWithServer = function () {
					var promise = userService.getUserByGuid(guidOfUser);

					promise.success(function (data, status, headers, config) {
						$scope.user = data;
					}).error(function (data, status, headers, config) {
						alert(status);
					});
				};
				syncModelWithServer();

				$rootScope.$on('userSettings::updated', function (event, data) {
					syncModelWithServer();
				});
			}]);
})();