(function () {
	'use strict';

	angular.module('todoListApp')
		.factory('userService', ['$http', '$rootScope', 'configAppService', 'Upload',
			function($http, $rootScope, configAppService, Upload) {
				var service = {};

				service.newUser = function (user) {
					return $http({
						method: 'POST',
						url: configAppService.api + '/user/add/one',
						data: user
					});
				};
				service.newUsers = function (users) {
					return $http({
						method: 'POST',
						url: configAppService.api + '/user/add/some',
						data: users
					});
				};

				service.getUserByGuid = function (guidOfUser) {
					return $http({
						method: 'GET',
						url: configAppService.api + '/user/get/one',
						params: { guidOfUser: guidOfUser }
					});
				};
				service.getAllUsers = function (guidOfUser) {
					return $http({
						method: 'GET',
						url: configAppService.api + '/user/get/all',
						params: { guidOfUser: guidOfUser }
					});
				};

				service.updateUser = function (user) {
					return $http({
						method: 'PUT',
						url: configAppService.api + '/user/edit/one',
						data: user
					});
				};

				service.deleteUser = function (guidOfUser) {
					return $http({
						method: 'DELETE',
						url: configAppService.api + '/user/delete/one',
						params: { guidOfUser: guidOfUser }
					});
				};
				service.deleteUsers = function (guidOfUserCollection) {
					return $http({
						method: 'DELETE',
						url: configAppService.api + '/user/delete/some',
						data: guidOfUserCollection
					});
				};

				// обновление картинки пользователя
				service.updatePhotoOfUser = function (croppedImage, guidOfUser) {
					return $http({
						method: 'POST',
						url: configAppService.api + '/user/update/picture/photoOfUser/fileAsBASE64String',
						headers: {
							'Accept': 'text/plain',
							'Content-Type': 'text/plain'
						},
						params: {
							guidOfUser: guidOfUser
						},
						data: croppedImage
					});
				};

				service.getPhotoOfUser = function (guidOfUser, promiseOrImage) {
					var promise = $http.get(configAppService.api + '/user/get/picture/photoOfUser', {
						params: {
							guidOfUser: guidOfUser
						}
					});

					if (promiseOrImage === null ||
						promiseOrImage === undefined ||
						promiseOrImage === "promise" ||
						promiseOrImage === 0) {
						return promise;
					} else {
						promise.success(function (data, status, headers, config) {
							return data;
						}).error(function (data, status, headers, config) {
							alert(status);
						});
					}
					return "";
				};

				return service;
			}
		]);
})();