(function () {
	'use strict';

	angular.module('todoListApp')
		.controller('UserController', ['$scope', '$rootScope', 'userService', 'sessionService',
			function ($scope, $rootScope, userService, sessionService) {
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
			}
		]);
})();