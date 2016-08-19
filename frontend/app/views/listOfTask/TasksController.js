(function () {
	'use strict';

	angular.module('todoListApp')
		.controller('TasksController', ['$scope', '$rootScope', 'taskService', '$stateParams', function ($scope, $rootScope, taskService, $stateParams) {
			$scope.tasks = [];
			var guidOfListOfTasks = $stateParams.guidOfListOfTasks;

			var syncModelWithServer = function () {
				var promise = taskService.getAllTasksOfListOfTasks(guidOfListOfTasks);
				promise.success(function (data, status, headers, config) {
					$scope.tasks = data;
				}).error(function (data, status, headers, config) {
					alert(status);
				});
			};
			syncModelWithServer();

			$scope.newTask = function (descriptionOfTask) {
				var task = {
					'listOfTasksGuid': guidOfListOfTasks,
					'status': 'PLAN',
					'description': descriptionOfTask
				};

				var promise = taskService.newTask(task);
				promise.success(function (data, status, headers, config) {
					syncModelWithServer();
				}).error(function (data, status, headers, config) {
					alert(status);
				});
			};
			$scope.deleteTask = function (task) {
				var promise = taskService.deleteTask(task.guid);
				promise.success(function (data, status, headers, config) {
					syncModelWithServer();
				}).error(function (data, status, headers, config) {
					alert(status);
				});
			};


			$scope.changeStatusToPlan = function (task) {
				task.status = 'PLAN';

				var promise = taskService.updateTask(task);
				promise.success(function (data, status, headers, config) {
					syncModelWithServer();
				}).error(function (data, status, headers, config) {
					alert(status);
				});
			};
			$scope.changeStatusToProcess = function (task) {
				task.status = 'PROCESS';

				var promise = taskService.updateTask(task);
				promise.success(function (data, status, headers, config) {
					syncModelWithServer();
				}).error(function (data, status, headers, config) {
					alert(status);
				});
			};
			$scope.changeStatusToDone = function (task) {
				task.status = 'DONE';

				var promise = taskService.updateTask(task);
				promise.success(function (data, status, headers, config) {
					syncModelWithServer();
				}).error(function (data, status, headers, config) {
					alert(status);
				});
			};
		}]);
})();