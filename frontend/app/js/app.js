(function () {
	'use strict';

	angular.module('todoListApp', ['ui.router', 'ui.bootstrap', 'dndLists'])
		.config(['$urlRouterProvider', '$stateProvider',
			function ($urlRouterProvider, $stateProvider) {

				$urlRouterProvider.otherwise('/');

				$stateProvider
					.state('signUp', {
						url: '/signUp',
						templateUrl: 'views/signUp/signUp.tpl.html',
						controller: 'SignUpController'
					})
					.state('login', {
						url: '/login',
						templateUrl: 'views/login/login.tpl.html',
						controller: 'LoginController'
					})
					.state('main', {
						url: '/',
						templateUrl: 'views/main/main.tpl.html',
						controller: 'ListsOfTasksController'
					})
					.state('users', {
						url: '/users',
						templateUrl: 'views/users/users.tpl.html',
						controller: 'UsersController'
					})
					.state('settings', {
						url: '/settings',
						templateUrl: 'views/settings/settings.tpl.html',
						controller: 'SettingsController'
					})
					.state('listOfTasks', {
						url: '/listOfTask/:guidOfListOfTasks',
						templateUrl: 'views/listOfTask/listOfTask.tpl.html',
						controller: 'ListOfTasksController',
						params: {
							guidOfListOfTasks: ''
						}
					});

		}])
		.factory('configAppService', [function() {
			var service = {
				protocol: 'http',
				host: 'localhost',
				port: 8080
			};

			service.api = service.protocol + '://' + service.host + ':' + service.port;

			return service;
		}])
		.run(['$rootScope', '$state', '$location', 'loginService', function ($rootScope, $state, $location, loginService) {
			//The list of states that do not need authentication (like auth)
			var authorizedStates = ['signUp', 'login'];

			// This method reports if a state is one of the authorizes states
			var isChildOfAuthorizedStated = function (stateName) {
				var isChild = false;
				authorizedStates.forEach(function (state) {
					if (stateName.indexOf(state) === 0) {
						isChild = true;
					}
				});
				return isChild;
			};

			$rootScope.$on('$stateChangeStart',
				// The stateChangeStart handler
				function (event, toState, toParams, fromState, fromParams) {
					// Check if the future state is not an authorized state
					// and if the user is not authenticated.
					if (!isChildOfAuthorizedStated(toState.name) && !loginService.isLogged()) {
						event.preventDefault();
						$state.go('login');
					}
				}
			);
		}]);
})();