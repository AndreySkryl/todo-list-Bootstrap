(function () {
	'use strict';

	angular.module('todoListApp')
		.controller('ModalDialogChangePhotoOfUserCtrl',
			['$scope', '$rootScope', '$timeout', '$uibModalInstance', 'userService', 'sessionService',
				function ($scope, $rootScope, $timeout, $uibModalInstance, userService, sessionService) {
					$scope.close = function () {
						$uibModalInstance.close();
					};

					var guidOfUser = sessionService.get('uid');

					$scope.upload = function (dataUrl, picFile) {
						var promise = userService.uploadAndUpdatePhotoOfUser(dataUrl, picFile, guidOfUser);
						promise.success(function (data, status, headers, config) {
							$scope.result = data;
							$rootScope.$emit('user.photoOfUser::changed');
						}).error(function (data, status, headers, config) {
							alert(status);
						});
					};
				}
			]
		);
})();