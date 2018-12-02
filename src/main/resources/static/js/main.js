'use strict ';

angular.module('main_app', ['ngRoute', 'ngCookies'])
       .config(config)
       .run(run);

config.$inject = ['$routeProvider', '$httpProvider'];

function config($routeProvider, $httpProvider) {
    $routeProvider
        .when('/', {
            templateUrl  : '/view/cart.html',
            controller   : 'CartController',
            controllerAs : 'ctrl',
            resolve: {
                    items: function ($q, CartService) {
                        console.log('Load all items');
                        var deferred = $q.defer();
                        CartService.loadItems().then(deferred.resolve, deferred.resolve);
                        return deferred.promise;
                    }
                }
        })
        .when('/login', {
          templateUrl  : '/view/login.html',
          controller   : 'LoginController',
          controllerAs : 'ctrl'
        })
        .when('/add', {
          templateUrl  : '/view/add.html',
          controller   : 'CartAddController',
          controllerAs : 'ctrl'
        })
        .otherwise({redirectTo : '/'});

        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    };

run.$inject = ['$rootScope', '$location', '$cookies', '$http'];
function run($rootScope, $location, $cookies, $http) {
     $rootScope.globals = $cookies.get('globals') || {};
     $rootScope.$on('$locationChangeStart', function (event, next, current) {
            if ($location.path() !== '/login' && !$rootScope.globals.currentUser) {
                $location.path('/login');
            }
        });
};


angular.module('main_app').controller("LoginController", loginController);
loginController.$inject = ['$location', 'AuthenticationService', 'MessageService'];
function loginController ($location, AuthenticationService, MessageService) {
    
    var self = this;
    self.login = login;

    AuthenticationService.ClearCredentials();

    function login() {
        self.dataLoading = true;
        AuthenticationService.Login(self.username, self.password, function(response) {
            if(response.success) {
                AuthenticationService.setCredentials(self.username);
                $location.path('/');
            } else {
                MessageService.error(response.message);
                self.dataLoading = false;
            }
        });
    };

};

angular.module('main_app').controller("MenuController", menuController);
menuController.$inject = ['$location', 'AuthenticationService'];
function menuController ($location, AuthenticationService) {
    
    var self = this;
    self.logout = logout;

    function logout() {
        console.log('Logout click');
        AuthenticationService.Logout(function(response) {
            AuthenticationService.ClearCredentials();
            $location.path('/login');
        });
    };
};


angular.module('main_app').factory('AuthenticationService', authenticationService);
authenticationService.$inject = ['$http', '$cookies', '$rootScope'];
function authenticationService($http, $cookies, $rootScope) {
    var service = {};

    service.Login = function (username, password, callback) {
        var headers = {"Authorization" : "Basic " + btoa(username + ":" + password) };
        var result = {};

        $http.get('/api/login', {headers : headers})
            .then(function (response) {
                if (response.data.name === username) {
                    result = { success: true };
                } else {
                    result = { success: false, message: 'Username or password is incorrect' };
                }
                callback(result);
            }, function() {
                result = { success: false, message: 'Authentification error' };
                callback(result);
            });
    }

    service.Logout = function (callback) {

        console.log('Logout in AuthenticationService');
        $http.post('/api/logout', {})
            .then(function (response) {
                callback();
            }, function() {
                callback();
            });
    }

    service.setCredentials = function (username) {
 
        $rootScope.globals = {
            currentUser: {
                username: username
            }
        };
  
        $cookies.put('globals', $rootScope.globals);
    };
  
    service.ClearCredentials = function () {
        $rootScope.globals = {};
        $cookies.remove('globals');
    };
  
    return service;
};

angular.module('main_app').controller("CartController", cartController);
cartController.$inject = ['CartService'];
function cartController(CartService) {
    var self = this;
    self.getItems = getItems;
    self.deleteItem = deleteItem;

    function getItems() {
        return CartService.list();
    }

    function deleteItem(itemId) {
        console.log('deleteItem click');
        return CartService.deleteItem(itemId).then(
            function(response) {
                    CartService.loadItems();
            },
            function(responseErr) {
                    MessageService.error(responseErr);
            });
    }
};

angular.module('main_app').controller("CartAddController", cartAddController);
cartAddController.$inject = ['$location', 'MessageService', 'CartService'];
function cartAddController($location, MessageService, CartService) {
    var self = this;
    self.add = add;

    function add() {
        CartService.add(self.item)
            .then(function(response) {
                if(response.success) {
                    $location.path('/');
                } else {
                    MessageService.error(response.message);
                }
            });
    };
};

angular.module('main_app').factory('CartService', cartService);
cartService.$inject = ['$rootScope','$http', '$q'];
function cartService($rootScope, $http, $q) {
    var service = {};
    service.add = add;
    service.list = list;
    service.loadItems = loadItems;
    service.deleteItem = deleteItem;

    $rootScope.items = {};

    return service;

    function add(item) {
        return $http.post('/api/items/add', item).then(handleSuccess, handleError('Error adding item'));
    }

    function loadItems() {
        var deferred = $q.defer();
        return $http.get('/api/items')
                    .then(
                        function (response) {
                            $rootScope.items = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            deferred.reject(errResponse);
                        });
        return deferred.promise;
    }

    function deleteItem(itemId) {
        console.log('deleteItem in CartService');
        var deferred = $q.defer();
        return $http.get('/api/items/delete/'+itemId)
                    .then(
                        function (response) {
                            $rootScope.items = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            deferred.reject(errResponse);
                        });
        return deferred.promise;
    }

    function list() {
        return $rootScope.items;
    }

    function handleSuccess(res) {
        return { success: true};
    }
 
    function handleError(error) {
        return function () {
            return { success: false, message: error };
        };
    }
}

angular.module('main_app').factory('MessageService', messageService);
messageService.$inject = ['$rootScope'];
function messageService($rootScope) {
    var service = {};

    service.success = success;
    service.error = error;

    initService();

    return service;

    function initService() {
        $rootScope.$on('$locationChangeStart', function () {
            clearMessage();
        });
        
        function clearMessage() {
            var message = $rootScope.message;
            if (message) {
                delete $rootScope.message;
            }
        }
    }

    function success(text) {
        $rootScope.message = {
            text: text,
            type: 'success'
        };
    }

    function error(text) {
        $rootScope.message = {
            text: text,
            type: 'error'
        };
    }
}