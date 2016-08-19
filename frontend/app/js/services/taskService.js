(function () {
	'use strict';

	angular.module('todoListApp')
		.factory('taskService', ['$http', '$rootScope', 'configAppService', function ($http, $rootScope, configAppService) {
			var service = {};

			service.newTask = function (task) {
				return $http({
					method: 'POST',
					url: configAppService.api + '/task/add/one',
					data: task
				});
			};
			service.newTasks = function (tasks) {
				return $http({
					method: 'POST',
					url: configAppService.api + '/task/add/some',
					data: tasks
				});
			};

			service.getTaskByGuid = function (guidOfTask) {
				return $http({
					method: 'GET',
					url: configAppService.api + '/task/get/one',
					params: {guidOfTask: guidOfTask}
				});
			};
			service.getAllTasksOfListOfTasks = function (guidOfListOfTasks) {
				return $http({
					method: 'GET',
					url: configAppService.api + '/task/get/all',
					params: {guidOfListOfTasks: guidOfListOfTasks}
				});
			};
			service.getCountOfListOfTasks = function () {
				return $http({
					method: 'GET',
					url: configAppService.api + '/task/get/all/count'
				});
			};

			service.updateTask = function (task) {
				return $http({
					method: 'PUT',
					url: configAppService.api + '/task/edit/one',
					data: task
				});
			};

			service.deleteTask = function (guidOfTask) {
				return $http({
					method: 'DELETE',
					url: configAppService.api + '/task/delete/one',
					params: {guidOfTask: guidOfTask}
				});
			};
			service.deleteTasks = function (guidesOfTasks) {
				return $http({
					method: 'DELETE',
					url: configAppService.api + '/task/delete/some',
					data: guidesOfTasks
				});
			};

			return service;
		}]);
})();