angular.module('andreypatricio',['ngRoute'])
.config(function ($routeProvider) {

	$routeProvider.when('/', {
		templateUrl: 'page/main.html',
		controller: 'TaskController'
	});
	
	$routeProvider.otherwise({ redirectTo: '/'});
});
