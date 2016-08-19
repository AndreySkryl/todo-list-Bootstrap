(function () {
	'use strict';

	angular.module('todoListApp')
		.controller('ListOfTasksController', ['$scope', '$rootScope', 'listOfTasksService', '$stateParams', '$uibModal', '$log',
			function ($scope, $rootScope, listOfTasksService, $stateParams, $uibModal, $log) {
				$scope.listOfTasks = {};
				$scope.subscribers = [];

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
						size: size,
						resolve: {
							/*items: function () {
								return $scope.items;
							}*/
						}
					});

					modalInstance.result.then(function (/*selectedItem*/) {
						/*$scope.selected = selectedItem;*/
					}, function () {
						$log.info('Modal dismissed at: ' + new Date());
					});
				};

			}
		]);

	/*angular.module('todoListApp').controller('ModalDemoCtrl', ['$scope', '$uibModal', '$log', function ($scope, $uibModal, $log) {

		$scope.items = ['item1', 'item2', 'item3'];

		$scope.animationsEnabled = true;

		$scope.open = function (size) {

			var modalInstance = $uibModal.open({
				animation: $scope.animationsEnabled,
				templateUrl: 'myModalContent.html',
				controller: 'ModalInstanceCtrl',
				size: size,
				resolve: {
					items: function () {
						return $scope.items;
					}
				}
			});

			modalInstance.result.then(function (selectedItem) {
				$scope.selected = selectedItem;
			}, function () {
				$log.info('Modal dismissed at: ' + new Date());
			});
		};

		$scope.toggleAnimation = function () {
			$scope.animationsEnabled = !$scope.animationsEnabled;
		};

	}]);*/

	// Please note that $uibModalInstance represents a modal window (instance) dependency.
	// It is not the same as the $uibModal service used above.

	/*angular.module('todoListApp').controller('ModalInstanceCtrl', ['$scope', '$uibModalInstance', 'items', function ($scope, $uibModalInstance, items) {

		$scope.items = items;
		$scope.selected = {
			item: $scope.items[0]
		};

		$scope.ok = function () {
			$uibModalInstance.close($scope.selected.item);
		};

		$scope.cancel = function () {
			$uibModalInstance.dismiss('cancel');
		};

	}]);*/
})();