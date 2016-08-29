(function () {
	'use strict';

	angular.module('todoListApp')
		.controller('TasksController', ['$scope', '$rootScope', 'taskService', '$stateParams', '$uibModal', '$log',
			function ($scope, $rootScope, taskService, $stateParams, $uibModal, $log) {
				$scope.models = {
					selected: null,
					tasks: []
				};

				var guidOfListOfTasks = $stateParams.guidOfListOfTasks;

				$scope.updatePositionsOfTasks = function () {
					for (var id = 0; id < $scope.models.tasks.length; id++) {
						$scope.models.tasks[id].displayPosition = id;
					}

					var promise = taskService.updateTasks($scope.models.tasks);
					promise.success(function (data, status, headers, config) {
						$scope.syncModelWithServer();
					});
				};
				$scope.syncModelWithServer = function () {
					var promise = taskService.getAllTasksOfListOfTasks(guidOfListOfTasks);
					promise.success(function (data, status, headers, config) {
						$scope.models.tasks = data;
					}).error(function (data, status, headers, config) {
						alert(status);
					});
				};
				$scope.syncModelWithServer();

				$scope.newTask = function (descriptionOfTask) {
					var task = {
						'listOfTasksGuid': guidOfListOfTasks,
						'status': 'PLAN',
						'description': descriptionOfTask
					};

					var promise = taskService.newTask(task);
					promise.success(function (data, status, headers, config) {
						$scope.syncModelWithServer();
						$scope.descriptionOfTask = '';
					}).error(function (data, status, headers, config) {
						alert(status);
					});
				};
				$scope.deleteTask = function (task) {
					var promise = taskService.deleteTask(task.guid);
					promise.success(function (data, status, headers, config) {
						$scope.syncModelWithServer();
					}).error(function (data, status, headers, config) {
						alert(status);
					});
				};

				$scope.changeStatusToPlan = function (task) {
					task.status = 'PLAN';

					var promise = taskService.updateTask(task);
					promise.success(function (data, status, headers, config) {
						$scope.syncModelWithServer();
					}).error(function (data, status, headers, config) {
						alert(status);
					});
				};
				$scope.changeStatusToProcess = function (task) {
					task.status = 'PROCESS';

					var promise = taskService.updateTask(task);
					promise.success(function (data, status, headers, config) {
						$scope.syncModelWithServer();
					}).error(function (data, status, headers, config) {
						alert(status);
					});
				};
				$scope.changeStatusToDone = function (task) {
					task.status = 'DONE';

					var promise = taskService.updateTask(task);
					promise.success(function (data, status, headers, config) {
						$scope.syncModelWithServer();
					}).error(function (data, status, headers, config) {
						alert(status);
					});
				};

				$scope.$watch('models', function(model) {
					$scope.modelAsJson = angular.toJson(model, true);
				}, true);

				$scope.modalDialogForEditDescription = function(task, size) {
					var cloneOfTask = JSON.parse(JSON.stringify(task));
					var modalInstance = $uibModal.open({
						animation: true,
						templateUrl: 'views/listOfTask/modalDialog/md.editDescriptionOfTask.tpl.html',
						controller: 'ModalDialogEditDescriptionOfTaskCtrl',
						size: size,
						resolve: {
							task: function () {
								return cloneOfTask;
							}
						}
					});

					modalInstance.result.then(function (editedTask) {
						var promise = taskService.updateTask(editedTask);
						promise.success(function (data, status, headers, config) {
							$scope.syncModelWithServer();
						}).error(function (data, status, headers, config) {
							alert(status);
						});
					}, function () {
						$log.info('Modal dismissed at: ' + new Date());
					});
				};
			}
		]);
})();