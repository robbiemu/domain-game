const MODULE_NAME = 'domain'
	
const DEFAULT_BOARD_ROWS = 40
const DEFAULT_BOARD_COLS = 40

const PLAYER_RIGHT = 'player_right'
const PLAYER_LEFT = 'player_left'
const TOWER_RIGHT = 'tower_right'
const TOWER_LEFT = 'tower_left'
const UNSET = ''
	
const MOVE_START = 'move_start'
conts MOVE_PRIMARY_COMPLETE = 'move_primary_complete'
const MOVE_OVERRIDE_TOWER_CURRENT_PLAYER = 'current_player_tower'

angular.module(MODULE_NAME, ['ngRoute'])
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