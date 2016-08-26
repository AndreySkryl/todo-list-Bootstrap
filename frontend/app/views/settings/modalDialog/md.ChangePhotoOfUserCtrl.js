(function () {
	'use strict';

	angular.module('todoListApp')
		.controller('ModalDialogChangePhotoOfUserCtrl',
			['$scope', '$rootScope', '$timeout', '$uibModalInstance', 'userService', 'sessionService',
				function ($scope, $rootScope, $timeout, $uibModalInstance, userService, sessionService) {
					$scope.cropper = {};
					$scope.cropper.sourceImage = null;
					$scope.cropper.croppedImage = null;

					var guidOfUser = sessionService.get('uid');

					$scope.changePhotoOfUser = function () {
						var promise = userService.updatePhotoOfUser($scope.cropper.croppedImage, guidOfUser);
						promise.success(function (data, status, headers, config) {
							$scope.result = data;
							$rootScope.$emit('user.photoOfUser::changed');
						}).error(function (data, status, headers, config) {
							alert(status);
						});
					};

					$scope.close = function () {
						$uibModalInstance.close();
					};
				}
			]
		);
})();