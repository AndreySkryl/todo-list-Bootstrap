(function () {
	'use strict';

	angular.module('todoListApp')
		.controller('ModalDialogEditDescriptionOfTaskCtrl', ['$scope', '$rootScope', '$uibModalInstance', 'task',
			function ($scope, $rootScope, $uibModalInstance, task) {
				$scope.tmpTask = task;

				$scope.ok = function () {
					$uibModalInstance.close($scope.tmpTask);
				};

				$scope.cancel = function () {
					$uibModalInstance.dismiss('cancel');
				};
			}
		]);
})();