(function () {
	'use strict';

	angular.module('todoListApp')
		.factory('userService', ['$http', '$rootScope', 'configAppService', function($http, $rootScope, configAppService) {
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

			return service;
		}]);
})();