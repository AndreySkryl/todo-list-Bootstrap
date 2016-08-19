(function () {
	'use strict';

	angular.module('todoListApp')
		.controller('SettingsController', ['$scope', '$rootScope', 'userService', 'sessionService',
			function ($scope, $rootScope, userService, sessionService) {
				$scope.userSettings = {
					login: "",
					lastName: "",
					firstName: "",
					password: "",
					eMail: ""
				};
				$scope.newPassword = '';
				$scope.confirmOfNewPassword = '';

				var guidOfUser = sessionService.get('uid');

				var syncModelWithServer = function () {
					var promise = userService.getUserByGuid(guidOfUser);

					promise.success(function (data, status, headers, config) {
						$scope.userSettings = data;
						$scope.newPassword = '';
						$scope.confirmOfNewPassword = '';
					}).error(function (data, status, headers, config) {
						alert(status);
					});
				};
				syncModelWithServer();

				$scope.apply = function () {
					var tmpOfUserSettings = JSON.parse(JSON.stringify($scope.userSettings));

					if ($scope.newPassword !== null && $scope.newPassword !== undefined && $scope.newPassword !== '' &&
						$scope.confirmOfNewPassword !== null && $scope.confirmOfNewPassword !== undefined && $scope.confirmOfNewPassword !== '') {
						if ($scope.newPassword === $scope.confirmOfNewPassword) {
							tmpOfUserSettings.password = $scope.newPassword;
						} else {
							alert('newPassword !== confirmOfNewPassword');
							return;
						}
					}

					var promise = userService.updateUser(tmpOfUserSettings);
					promise.success(function (data, status, headers, config) {
						syncModelWithServer();
						$rootScope.$emit('userSettings::updated', $scope.userSettings);
					}).error(function (data, status, headers, config) {
						alert(status);
					});
				};
			}
		]);
})();