(function () {
	'use strict';

	angular.module('todoListApp')
		.factory('listOfTasksService', ['$http', '$rootScope', 'configAppService', function ($http, $rootScope, configAppService) {
			var service = {};

			service.newListOfTasks = function (listOfTasks) {
				return $http({
					method: 'POST',
					url: configAppService.api + '/list_of_tasks/add/one',
					data: listOfTasks
				});
			};

			service.newListsOfTasksBasedOnTheTemplate = function (listOfTasks, guidOfTemplateListOfTasks) {
				return $http({
					method: 'POST',
					url: configAppService.api + '/list_of_tasks/add/one/basedOnTemplate',
					data: listOfTasks,
					params: {
						guidOfListOfTasks: guidOfTemplateListOfTasks
					}
				});
			};

			service.newListsOfTasks = function (listOfTasksCollection) {
				return $http({
					method: 'POST',
					url: configAppService.api + '/list_of_tasks/add/some',
					data: listOfTasksCollection
				});
			};

			service.getListOfTasks = function (guidOfListOfTasks) {
				return $http({
					method: 'GET',
					url: configAppService.api + '/list_of_tasks/get/one',
					params: {guidOfListOfTasks: guidOfListOfTasks}
				});
			};
			service.getAllListsOfTasksOfUser = function (guidOfUser) {
				return $http({
					method: 'GET',
					url: configAppService.api + '/list_of_tasks/get/all',
					params: {guidOfUser: guidOfUser}
				});
			};

			service.updateListOfTasks = function (guidOfUser, listOfTasks) {
				return $http({
					method: 'PUT',
					url: configAppService.api + '/list_of_tasks/edit/one',
					params: {guidOfUser: guidOfUser},
					data: listOfTasks
				});
			};

			service.deleteListOfTasks = function (guidOfListOfTasks, guidOfUser) {
				return $http({
					method: 'DELETE',
					url: configAppService.api + '/list_of_tasks/delete/one',
					params: {
						guidOfListOfTasks: guidOfListOfTasks,
						guidOfUser: guidOfUser
					}
				});
			};

			service.subscribe = function (guidOfListOfTasks, guidOfUser) {
				return $http({
					method: 'POST',
					url: configAppService.api + '/list_of_tasks/subscribe',
					params: {
						guidOfListOfTasks: guidOfListOfTasks,
						guidOfUser: guidOfUser
					}
				});
			};
			service.unsubscribe = function (guidOfListOfTasks, guidOfUser) {
				return $http({
					method: 'DELETE',
					url: configAppService.api + '/list_of_tasks/unsubscribe',
					params: {
						guidOfListOfTasks: guidOfListOfTasks,
						guidOfUser: guidOfUser
					}
				});
			};
			service.getAllSubscribers = function (guidOfListOfTasks) {
				return $http({
					method: 'GET',
					url: configAppService.api + '/list_of_tasks/get/all_subscribers',
					params: {guidOfListOfTasks: guidOfListOfTasks}
				});
			};

			service.getAllUnsigned = function (guidOfListOfTasks, guidOfUser) {
				return $http({
					method: 'GET',
					url: configAppService.api + '/list_of_tasks/get/all_unsigned',
					params: {
						guidOfListOfTasks: guidOfListOfTasks,
						guidOfUser: guidOfUser
					}
				});
			};

			return service;
		}]);
})();