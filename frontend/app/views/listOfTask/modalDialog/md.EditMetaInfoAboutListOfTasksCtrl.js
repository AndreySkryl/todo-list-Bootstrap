(function () {
	'use strict';

	angular.module('todoListApp')
		.controller('ModalDialogEditMetaInfoAboutListOfTasksCtrl', ['$scope', '$rootScope', '$uibModalInstance', 'listOfTasks',
			function ($scope, $rootScope, $uibModalInstance, listOfTasks) {
				$scope.tmpListOfTasks = listOfTasks;

				$scope.ok = function () {
					$uibModalInstance.close($scope.tmpListOfTasks);
				};

				$scope.cancel = function () {
					$uibModalInstance.dismiss('cancel');
				};
			}
		]);
})();