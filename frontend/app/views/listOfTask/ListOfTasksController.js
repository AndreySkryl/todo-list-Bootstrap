(function () {
	'use strict';

	angular.module('todoListApp')
		.controller('ListOfTasksController', ['$scope', '$rootScope', 'listOfTasksService', '$stateParams', '$uibModal', '$log',
			function ($scope, $rootScope, listOfTasksService, $stateParams, $uibModal, $log) {
				$scope.listOfTasks = {};
				$scope.subscribers = [];

				$scope.statusOfTasks = '';

				var guidOfListOfTasks = $stateParams.guidOfListOfTasks;

				var syncModelWithServer = function () {
					var promise = listOfTasksService.getListOfTasks(guidOfListOfTasks);
					promise.success(function (data, status, headers, config) {
						$scope.listOfTasks = data;
					}).error(function (data, status, headers, config) {
						alert(status);
					});

					promise = listOfTasksService.getAllSubscribers(guidOfListOfTasks);
					promise.success(function (data, status, headers, config) {
						$scope.subscribers = data;
					}).error(function (data, status, headers, config) {
						alert(status);
					});
				};
				syncModelWithServer();

				$rootScope.$on('subscriber::added', function () {
					syncModelWithServer();
				});

				$scope.deleteSubscriber = function (subscriber) {
					var promise = listOfTasksService.unsubscribe(guidOfListOfTasks, subscriber.guid);
					promise.success(function (data, status, headers, config) {
						syncModelWithServer();
					}).error(function (data, status, headers, config) {
						alert(status);
					});
				};

				$scope.popUpDialogAddColleague = function (size) {
					var modalInstance = $uibModal.open({
						animation: true,
						templateUrl: 'views/listOfTask/modalDialog/ModalDialogAddColleague.tpl.html',
						controller: 'ModalDialogAddColleagueCtrl',
						size: size
					});

					modalInstance.result.then(function () {}, function () {
						$log.info('Modal dismissed at: ' + new Date());
					});
				};

			}
		]);
})();