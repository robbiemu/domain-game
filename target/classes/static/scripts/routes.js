(function(){
    angular.module(MODULE_NAME).config(['$routeProvider', 'StylesDir', 'ScriptsDir',
        function config ($routeProvider, StylesDir, ScriptsDir) {
        $routeProvider
            .when('/', {
                templateUrl: 'hypermedia/home/home_template.html',
                controller: 'homeController',
                resolve: {
                    factory: function (Res, Auth, $location, $rootScope) {
                    	checkRouting($rootScope, $location, Auth)
                        controllersAndServices(Res, {}, $rootScope)
                    }
                }
            })
            .when('/login', {
                templateUrl: 'hypermedia/user/login_template.html',
                controller: 'userController',
                resolve: {
                    factory: function (Res, $rootScope) {
                        controllersAndServices(Res, {}, $rootScope)
                    }
                }            	
            })
            .when('/register', {
                templateUrl: 'hypermedia/user/register_template.html',
                controller: 'userController',
                resolve: {
                    factory: function (Res, $rootScope) {
                        controllersAndServices(Res, {}, $rootScope)
                    }
                } 
            })
            .when('/play', {
                templateUrl: 'hypermedia/domain/play_template.html',
                controller: 'domainController',
                resolve: {
                    factory: function (Res, Auth, $location, $rootScope) {
                    	checkRouting($rootScope, $location, Auth)
                        controllersAndServices(Res, { styles: StylesDir + 'board.css' }, $rootScope)
                    }
                }
            })
            .otherwise('/')
        }
    ])

    const controllersAndServices = function (Res, Routes, $rootScope) {
        Res.clean_scripts()
        Res.clean_styles()

        if ('scripts' in Routes) {
            Res.script(Routes.scripts)
        }
        if ('styles' in Routes) {
            Res.style(Routes.styles)
        }
    }
    
    const checkRouting = function ($rootScope, $location, Auth, checkAdmin=false, fallback_url='/login') {
    	let pass = true
    	if (!Auth.isLoggedIn() || (checkAdmin && !Auth.isAdmin())) {
            	pass = false
    	}
    	if(!pass) {
    	    console.log(`${$location.path()} - route denied. User not logged in or authorized.`)
    	    //event.preventDefault()
    	    $location.path(fallback_url)        	
    	}
    }
    
})()