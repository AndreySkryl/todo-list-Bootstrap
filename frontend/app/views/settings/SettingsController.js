(function () {
	'use strict';

	angular.module('todoListApp')
		.controller('SettingsController', ['$scope', '$rootScope', '$uibModal', 'userService', 'sessionService', '$log',
			function ($scope, $rootScope, $uibModal, userService, sessionService, $log) {
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

					var promise2 = userService.getPhotoOfUser(guidOfUser);
					promise2.success(function (data, status, headers, config) {
						$scope.photoOfUser = data;
					}).error(function (data, status, headers, config) {
						alert(status);
					});
				};
				syncModelWithServer();

				$scope.modalDialogChangePhotoOfUser = function (size) {
					var modalInstance = $uibModal.open({
						animation: true,
						templateUrl: 'views/settings/modalDialog/md.ChangePhotoOfUser.tpl.html',
						controller: 'ModalDialogChangePhotoOfUserCtrl',
						size: size
					});

					modalInstance.result.then(function (listOfTasks) {}, function () {
						$log.info('Modal dismissed at: ' + new Date());
						userService.getPhotoOfUser(guidOfUser);
					});
				};

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

				$rootScope.$on('user.photoOfUser::changed', function (event, data) {
					var promise = userService.getPhotoOfUser(guidOfUser);
					promise.success(function (data, status, headers, config) {
						$scope.photoOfUser = data;
					}).error(function (data, status, headers, config) {
						alert(status);
					});
				});
			}
		]);
})();