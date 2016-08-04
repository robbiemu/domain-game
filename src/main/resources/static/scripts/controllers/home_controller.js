angular.module(MODULE_NAME).controller('homeController', function($scope, Auth, Socket) {
	console.log('homeController')

	$scope.games = []
	Socket.send(GAME_SOCKET, LIST_QUEUED_GAMES)
	Socket.send(GAME_SOCKET, SUBSCRIBE_LIST_GAME_QUEUE)

	$scope.$on(GAME_QUEUE_CHANGE, function(msg, tx_response) {
		$scope.games = tx_response
	})

	$scope.queue = function () {
		Socket.send(GAME_SOCKET, QUEUE_FOR_GAME)
	}

	$scope.$on('$locationChangeSuccess', function () {
		Socket.send(GAME_SOCKET, LIST_QUEUED_GAMES)
		Socket.send(GAME_SOCKET, SUBSCRIBE_LIST_GAME_QUEUE)
	})

	 $scope.$on("$destroy", function(){
		Socket.send(GAME_SOCKET, UNSUBSCRIBE_LIST_GAME_QUEUE)
	 })

})