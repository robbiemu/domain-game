angular.module(MODULE_NAME).service('Socket', function ($rootScope, $websocket) {
/*    const userStream = $websocket('ws://localhost:8080/user')
    const gameStream = $websocket('ws://localhost:8080/game') */
    const stream = $websocket(`${DOMAIN_SERVER}${DEFAULT_STREAM}`)

    function getFirstWord (text) {
        return (text.indexOf(' ') > -1)? text.substring(0, text.indexOf(' ')): text
    }

    stream.onMessage(function(message){
        let command = getFirstWord(message.data)
        console.dir(message.data)
        switch (command) {
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
            case GAME_QUEUE_CHANGE:
                $rootScope.$broadcast(GAME_QUEUE_CHANGE, 
                    JSON.parse(message.data.substring(message.data.indexOf(' '), message.data.length)))                
                break
            case ONGOING_GAME_CHANGE:
        }
    })

    return {
        send: function (requested_stream, message) {
            console.log(requested_stream + ' ' + message)
            stream.send(requested_stream + ' ' + message)
        },
        close: function () {
            stream.send(CLOSE)
            stream=null
        }
    }
})