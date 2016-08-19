(function () {
	'use strict';

	angular.module('todoListApp')
		.factory('loginService', ['$http', '$rootScope', '$state', 'sessionService', 'configAppService',
			function ($http, $rootScope, $state, sessionService, configAppService) {
				return {
					login: function (user) {
						var promise = $http.post(configAppService.api + '/user/login', user);
						promise.success(function (data, status, headers, config) {
							var uid = data;
							if (uid !== '') {
								sessionService.set('uid', uid);
								$state.go('main');
							}
						}).error(function (data, status, headers, config) {
							alert(data);
						});
					},
					logout: function () {
						sessionService.destroy('uid');
						$state.go('login');
					},
					isLogged: function () {
						return !!sessionService.get('uid');
					},

					signUp: function (user) {
						return $http.post(configAppService.api + '/user/signUp', user);
					}
				};
			}]);
})();