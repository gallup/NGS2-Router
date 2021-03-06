'use strict';

function AppController($scope, AdminService, $cookies) {
  $scope.AdminService = AdminService;
  if (!AdminService.loggedIn && $cookies.get('authenticationToken')) {
    var arrayOfStrings = $cookies.get('authenticationToken').split('|');
    if (arrayOfStrings.length == 2) {
      AdminService.authToken = arrayOfStrings[0];
      AdminService.username = arrayOfStrings[1];
      AdminService.loggedIn = true;
    }
  }

  $scope.logout = function() {
    $cookies.remove('authenticationToken');
    AdminService.authToken = '';
    AdminService.username = '';
    AdminService.loggedIn = false;
  }
}

AppController.$inject = ['$scope', 'AdminService', '$cookies'];

angular.module('clientApp')
  .controller('AppCtrl', AppController);
