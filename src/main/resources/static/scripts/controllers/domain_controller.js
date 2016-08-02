angular.module(MODULE_NAME).controller('domainController', function($scope) {	
	const domain = this
	
	domain.board = {}
	domain.board.dim = {}
	domain.board.dim.rows = DEFAULT_BOARD_ROWS
	domain.board.dim.cols = DEFAULT_BOARD_COLS
	
	$scope.rows = [...Array(domain.board.dim.rows).keys()]	
	$scope.cols = [...Array(domain.board.dim.cols).keys()]	
	
	domain.board.state = init_board()
	
	/* this is just to get up and running. this must be done on the server */
	function init_board() {
		let board_size = (domain.board.dim.rows * domain.board.dim.cols)
		let unset_count = Math.floor(board_size/3)
		let left_count = unset_count
		let right_count = unset_count
		unset_count += board_size - (unset_count + left_count + right_count)
			
		let unset_chance = unset_count/(board_size*1.0)
		let left_chance = left_count/(board_size*1.0)
		let right_chance = right_count/(board_size*1.0)
		
		let chance = 0
		let tmp_board = []		
		for(let i = 0; i < domain.board.dim.rows; i++) {
			tmp_board[i] = []

			for(let j = 0; j < domain.board.dim.cols; j++) {
				unset_chance = unset_count/(board_size*1.0)
				left_chance = left_count/(board_size*1.0)
				right_chance = right_count/(board_size*1.0)

				chance = Math.random()
				if(chance < right_chance) {
					right_count--
					tmp_board[i][j] = PLAYER_RIGHT
				} else if (chance < unset_chance + left_chance) {
					left_count--
					tmp_board[i][j] = PLAYER_LEFT
				} else {
					unset_count--
					tmp_board[i][j] = UNSET
				}
				board_size--
			}
		}
/*		$http.post(MOVE_URL, { board: tmp_board })
		.then((tx_response) => {
			if(tx_response.status !== 200) {
				//handle error
			} else {
				if(tx_response.data.type === "board initialized") {
					domain.board.state = tx_response.data.settings.board
					domain.player_left = tx_response.data.settings.player_left
					domain.player_right = tx_response.data.settings.player_right
				} else {
					//handle error
				}
			}
		}) */
		return tmp_board
	}
	
	$scope.get_CSS_for_cel = function (x,y) {
		switch(domain.board.state[x][y]) {
		case PLAYER_LEFT:
			return  PLAYER_LEFT
		case PLAYER_RIGHT:
			return  PLAYER_RIGHT
		case TOWER_LEFT:
			return  TOWER_LEFT
		case TOWER_RIGHT:
			return  TOWER_RIGHT
		case UNSET:
			return 'unset'
		default:
		}
	}
	
	function getXY () {
		
	}
	
	const CURRENT_PLAYER = PLAYER_LEFT /* TODO - make right, get credentials from user, 
		then assign based on server response at game start */
    angular.element(document).ready(function () {
        $('.domain-col').click(function(){
        	if(domain.move.player === CURRENT_PLAYER) {
        		let xy = getXY($(this))
        		if(domain.move.state === MOVE_START) {
        			domain.move.primary = xy
        			$(this).addClass(MOVE_OVERRIDE_TOWER_CURRENT_PLAYER) 
        			domain.move.state = MOVE_PRIMARY_COMPLETE
        		} else if ( xy === domain.move.primary_of_pair ) {
        			// unset move
        			$(this).removeClass(MOVE_OVERRIDE_TOWER_CURRENT_PLAYER) 
        			domain.move.state = MOVE_START
        		} else {
        			// complete the move
        			domain.move.secondary = xy
        			$http.post(MOVE_URL, { player: CURRENT_PLAYER, primary: domain.move.primary, secondary: domain.move.secondary })
        				.then((tx_response) => {
        					domain.move.state = MOVE_START // TODO - verify you don't need to keep this for error handling
        					if(tx_response.status !== 200) {
        						//handle error
        					} else {
        						if(tx_response.data.type === "move processed") {
        							domain.board.state = tx_response.data.settings.board
        						} else {
        							//handle error
        						}
        					}
        				})
        		}
        	}
        })
    })
	
})