(function () {
	'use strict';

	angular.module('todoListApp')
		.controller('ModalDialogAddColleagueCtrl', ['$scope', '$rootScope', 'listOfTasksService', '$stateParams', 'sessionService',
			function ($scope, $rootScope, listOfTasksService, $stateParams, sessionService) {
				$scope.unsignedColleagues = [];

				var guidOfListOfTasks = $stateParams.guidOfListOfTasks;
				var guidOfUser = sessionService.get('uid');

				var syncModelWithServer = function () {
					var promise = listOfTasksService.getAllUnsigned(guidOfListOfTasks, guidOfUser);

					promise.success(function (data, status, headers, config) {
						$scope.unsignedColleagues = data;
					}).error(function (data, status, headers, config) {
						alert(status);
					});
				};
				syncModelWithServer();

				$scope.addColleague = function (guidOfUnsignedColleague) {
					var promise = listOfTasksService.subscribe(guidOfListOfTasks, guidOfUnsignedColleague);

					promise.success(function (data, status, headers, config) {
						syncModelWithServer();
						$rootScope.$emit('subscriber::added');
					}).error(function (data, status, headers, config) {
						alert(status);
					});
				};
			}
		]);
})();