angular.module(MODULE_NAME).factory('Auth', function () {
    var user

    return {
        setUser : function (aUser) {
            if((aUser !== null) && !('isAdmin' in aUser && aUSer.isAdmin)) {
                aUser.isAdmin = false
            }
            user = aUser
        },
        isLoggedIn : function () {
            return (user)? user: false
        },
        isAdmin : function () {
            return user.isAdmin
        }
    }
})