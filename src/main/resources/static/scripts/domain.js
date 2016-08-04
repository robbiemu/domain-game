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

const DOMAIN_SERVER = 'ws://localhost:8080/'
const USER_SOCKET = 'user'
const GAME_SOCKET = 'game'
const DEFAULT_STREAM = 'domain'
	
const MOVE_START = 'move_start'
const MOVE_PRIMARY_COMPLETE = 'move_primary_complete'
const MOVE_OVERRIDE_TOWER_CURRENT_PLAYER = 'current_player_tower'
		
const LOGIN_SUCCESS = 'LOGIN_SUCCESS'
const LOGIN_FAILURE = 'LOGIN_FAILURE'
const REGISTRATION_SUCCESS = 'REGISTRATION_SUCCESS'
const REGISTRATION_FAILURE = 'REGISTRATION_FAILURE'

const LOGIN = 'LOGIN'
const REGISTER = 'REGISTER'
const CLOSE = 'CLOSE'

const GAME_QUEUE_CHANGE = 'GAME_QUEUE_CHANGE'

const QUEUE_FOR_GAME = 'QUEUE_FOR_GAME'
const SUBSCRIBE_LIST_GAME_QUEUE = 'SUBSCRIBE_LIST_GAME_QUEUE'
const UNSUBSCRIBE_LIST_GAME_QUEUE = 'UNSUBSCRIBE_LIST_GAME_QUEUE'
const SUBSCRIBE_LIST_ONGOING_GAMES = 'SUBSCRIBE_LIST_ONGOING_GAMES'
const UNSUBSCRIBE_LIST_ONGOING_GAMES = 'UNSUBSCRIBE_LIST_ONGOING_GAMES'
const LIST_QUEUED_GAMES = 'LIST_QUEUED_GAMES'
const LIST_ONGOING_GAMES = 'LIST_ONGOING_GAMES'