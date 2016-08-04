const MODULE_NAME = 'domain'
angular.module(MODULE_NAME, ['ngRoute', 'ngWebSocket'])
	.constant('StylesDir', 'styles/')
	.constant('ScriptsDir', 'scripts/')
    .directive('link', ['$location', function($location) {
        return {
            link: function(scope, element, attrs) {
                element.on('click', function() {
                    scope.$apply(function() {
                        $location.path(attrs.link).replace()
                        console.log('location changed: ' + attrs.link)
                    })
                })
            }
        }
    }])
	
const DEFAULT_BOARD_ROWS = 40
const DEFAULT_BOARD_COLS = 40

const PLAYER_RIGHT = 'player_right'
const PLAYER_LEFT = 'player_left'
const TOWER_RIGHT = 'tower_right'
const TOWER_LEFT = 'tower_left'
const UNSET = ''
	
const MOVE_START = 'move_start'
const MOVE_PRIMARY_COMPLETE = 'move_primary_complete'
const MOVE_OVERRIDE_TOWER_CURRENT_PLAYER = 'current_player_tower'
	
const USER_SOCKET = 'user'
const GAME_SOCKET = 'game'
	
const LOGIN_SUCCESS = 'LOGIN_SUCCESS'
const LOGIN_FAILURE = 'LOGIN_FAILURE'
const REGISTRATION_SUCCESS = 'REGISTRATION_SUCCESS'
const REGISTRATION_FAILURE = 'REGISTRATION_FAILURE'

const LOGIN = 'LOGIN'
const REGISTER = 'REGISTER'

const GAME_QUEUE_CHANGE = 'GAME_QUEUE_CHANGE'

const ENQUEUE_FOR_GAME = 'ENQUEUE_FOR_GAME'
const SUBSCRIBE_LIST_GAME_QUEUE = 'SUBSCRIBE_LIST_GAMES'
const SUBSCRIBE_LIST_ONGOING_GAMES = 'SUBSCRIBE_LIST_ONGOING_GAMES'
const LIST_QUEUED_GAMES = 'LIST_QUEUED_GAMES'
const LIST_ONGOING_GAMES = 'LIST_ONGOING_GAMES'