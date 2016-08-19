(function () {
	'use strict';

	angular.module('todoListApp')
		.factory('colleagueService', ['$http', '$rootScope', 'configAppService', function ($http, $rootScope, configAppService) {
			var service = {};

			service.newColleague = function (guidOfUser, guidOfColleague) {
				return $http({
					method: 'POST',
					url: configAppService.api + '/colleague/add/one',
					params: {
						guidOfUser: guidOfUser,
						guidOfColleague: guidOfColleague
					}
				});
			};
			service.newColleagues = function (guidOfUser, guidesOfColleagues) {
				return $http({
					method: 'POST',
					url: configAppService.api + '/colleague/add/some',
					params: {guidOfUser: guidOfUser},
					data: guidesOfColleagues
				});
			};

			service.getGuidOfColleagues = function (guidOfUser) {
				return $http({
					method: 'GET',
					url: configAppService.api + '/colleague/get/all/guides',
					params: {guidOfUser: guidOfUser}
				});
			};
			service.getAllColleaguesAsUsers = function (guidOfUser) {
				return $http({
					method: 'GET',
					url: configAppService.api + '/colleague/get/all/as/user',
					params: {guidOfUser: guidOfUser}
				});
			};

			service.deleteOneColleague = function (guidOfUser, guidOfColleague) {
				return $http({
					method: 'DELETE',
					url: configAppService.api + '/colleague/delete/one',
					params: {
						guidOfUser: guidOfUser,
						guidOfColleague: guidOfColleague
					}
				});
			};
			service.deleteSomeColleagues = function (guidOfUser, guidesOfColleagues) {
				return $http({
					method: 'DELETE',
					url: configAppService.api + '/colleague/delete/some',
					params: {guidOfUser: guidOfUser},
					data: guidesOfColleagues
				});
			};
			service.deleteAllColleagues = function (guidOfUser) {
				return $http({
					method: 'DELETE',
					url: configAppService.api + '/colleague/delete/all',
					params: {guidOfUser: guidOfUser}
				});
			};

			return service;
		}]);
})();