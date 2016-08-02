(function(){
    angular.module(MODULE_NAME).config(['$routeProvider', 'StylesDir', 'ScriptsDir',
        function config ($routeProvider, StylesDir, ScriptsDir) {
        $routeProvider
            .when('/', {
                templateUrl: 'hypermedia/home/home_template.html',
                controller: 'homeController',
                resolve: {
                    factory: function (Res, $rootScope) {
                        controllersAndServices('home', Res, {}, $rootScope)
                    }
                }
            })
            .when('/play', {
                templateUrl: 'hypermedia/domain/play_template.html',
                controller: 'domainController',
                resolve: {
                    factory: function (Res, $rootScope) {
                        controllersAndServices('play', Res, { styles: StylesDir + 'board.css' }, $rootScope)
                    }
                }
            })
            .otherwise('/')
        }
    ])

    const controllersAndServices = function (route_name, Res, Routes, $rootScope) {
        Res.clean_scripts()
        Res.clean_styles()

        if ('scripts' in Routes) {
            Res.script(Routes.scripts)
        }
        if ('styles' in Routes) {
            Res.style(Routes.styles)
        }
    }
    
})()