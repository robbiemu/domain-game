angular.module(MODULE_NAME).controller('navController', function($scope, $location) {
	console.log('navController')

	$scope.destinations = []

	$scope.$on('$locationChangeSuccess', function () {
		$scope.destinations = []
		if(/\/login$/.test($location.path())) {
			$scope.destinations.push({path:'#/', text:'home'})
			$scope.destinations.push({path:'#/register', text:'register'})
		} else if(/\/register$/.test($location.path())) {
			$scope.destinations.push({path:'#/', text:'home'})
			$scope.destinations.push({path:'#/login', text:'login'})
		} else if(/\/(?:home)?$/.test($location.path())) {
			$scope.destinations.push({path:'#/login', text:'login'})
			$scope.destinations.push({path:'#/register', text:'register'})
		} 
	})

})