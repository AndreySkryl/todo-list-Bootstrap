(function () {
	'use strict';

	angular.module('todoListApp')
		.controller('ListOfTasksController', ['$scope', '$rootScope', 'listOfTasksService', 'sessionService', '$stateParams', '$uibModal', '$log',
			function ($scope, $rootScope, listOfTasksService, sessionService, $stateParams, $uibModal, $log) {
				$scope.listOfTasks = {};
				$scope.subscribers = [];

				$scope.statusOfTasks = '';

				var guidOfListOfTasks = $stateParams.guidOfListOfTasks;
				$scope.guidOfUser = sessionService.get('uid');

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

				$rootScope.$on('listOfTasks::update', function () {
					syncModelWithServer();
				});
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

				$scope.modalDialogEditMetaInfoAboutListOfTasks = function (size) {
					var cloneOfListOfTasks = JSON.parse(JSON.stringify($scope.listOfTasks));
					var modalInstance = $uibModal.open({
						animation: true,
						templateUrl: 'views/listOfTask/modalDialog/md.editMetaInfoAboutListOfTasks.tpl.html',
						controller: 'ModalDialogEditMetaInfoAboutListOfTasksCtrl',
						size: size,
						resolve: {
							listOfTasks: function () {
								return cloneOfListOfTasks;
							}
						}
					});

					modalInstance.result.then(function (listOfTasks) {
						var promise = listOfTasksService.updateListOfTasks($scope.guidOfUser, listOfTasks);
						promise.success(function (data, status, headers, config) {
							syncModelWithServer();
						}).error(function (data, status, headers, config) {
							alert(status);
						});
					}, function () {
						$log.info('Modal dismissed at: ' + new Date());
					});
				};

				$scope.modalDialogAddColleague = function (size) {
					var modalInstance = $uibModal.open({
						animation: true,
						templateUrl: 'views/listOfTask/modalDialog/md.addColleague.tpl.html',
						controller: 'ModalDialogAddColleagueCtrl',
						size: size
					});
				};
			}
		]);
})();