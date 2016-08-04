angular.module(MODULE_NAME).controller('homeController', function($scope, Auth, Socket) {
	console.log('homeController')

	$scope.games = []
	Socket.send(GAME_SOCKET, LIST_QUEUED_GAMES)
	Socket.send(GAME_SOCKET, SUBSCRIBE_LIST_GAME_QUEUE)

	$scope.$on(GAME_QUEUE_CHANGE, function(msg, tx_response) {
		$scope.games = tx_response.games
	})

	$scope.queue = function () {
		Socket.send(GAME_SOCKET, ENQUEUE_FOR_GAME)
	}

})