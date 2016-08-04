angular.module(MODULE_NAME).service('Socket', function ($rootScope, $websocket) {
    const userStream = $websocket('ws://localhost:8080/user')
    const gameStream = $websocket('ws://localhost:8080/game')

    function getFirstWord (text) {
        return (text.indexOf(' ') > -1)? text.substring(0, text.indexOf(' ')): text
    }

    userStream.onMessage(function(message) {
        switch (getFirstWord(message.data)) {
            case LOGIN_SUCCESS:
                $rootScope.$broadcast(LOGIN_SUCCESS, true)
                break
            case LOGIN_FAILURE:
                $rootScope.$broadcast(LOGIN_FAILURE, 
                    JSON.parse(message.data.substring(message.data.indexOf(' '), message.data.length)))
                break
            case REGISTRATION_SUCCESS:
                $rootScope.$broadcast(REGISTRATION_SUCCESS, true)
                break
            case REGISTRATION_FAILURE:
                $rootScope.$broadcast(REGISTRATION_FAILURE, 
                    JSON.parse(message.data.substring(message.data.indexOf(' '), message.data.length)))
                break
        }
    })

    gameStream.onMessage(function(message) {
        console.dir(message)
    })

    return {
        send: function (stream, message) {
            if (stream === USER_SOCKET) {
                userStream.send(message)
            } else {
                gameStream.send(message)
            }
        },

        close: function(stream) {
            if (stream === USER_SOCKET) {
                userStream.send('CLOSE')
                userStream = null
            } else {
                gameStream.send('CLOSE')
                gameStream = null
            }
        }

    }
})