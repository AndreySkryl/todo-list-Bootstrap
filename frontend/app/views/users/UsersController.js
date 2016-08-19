(function () {
	'use strict';

	angular.module('todoListApp')
		.controller('UsersController', ['$scope', '$rootScope', 'colleagueService', 'userService', 'sessionService',
			function ($scope, $rootScope, colleagueService, userService, sessionService) {
				$scope.users = [];
				var guidOfUser = sessionService.get('uid');

				var syncModelWithServer = function () {
					var promise = userService.getAllUsers(guidOfUser);

					promise.success(function (data, status, headers, config) {
						$scope.users = data;
					}).error(function (data, status, headers, config) {
						alert(status);
					});
				};
				syncModelWithServer();

				$scope.newColleague = function (user) {
					var promise = colleagueService.newColleague(guidOfUser, user.guid);

					promise.success(function (data, status, headers, config) {
						syncModelWithServer();
						$rootScope.$emit('users::updated');
					}).error(function (data, status, headers, config) {
						alert(status);
					});
				};

				$rootScope.$on('colleagues::updated', function (event, data) {
					syncModelWithServer();
				});
			}
		]);
})();