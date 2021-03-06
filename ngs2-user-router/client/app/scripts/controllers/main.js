'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the clientApp
 */
function MainController($scope, ExperimentService, UsersService, $uibModal, AlertService, AdminService, $location) {

  $scope.experiments = [];
  $scope.users = [];
  $scope.loggedInAs = "";
  $scope.loggedIn = AdminService.loggedIn;

  if (AdminService.loggedIn) {
    $scope.loggedInAs = AdminService.username;
    //console.log("Getting experiments");
    ExperimentService.getAllExperiments().then(function(resp) {
      $scope.experiments = resp;
    });

    //console.log("Getting users");
    UsersService.getAllUsers().then(function(resp) {
      $scope.users = resp;
      $scope.userStats = {};
      angular.forEach($scope.users, function(user) {
        if ($scope.userStats.hasOwnProperty(user.status)) {
          $scope.userStats[user.status] ++;
        } else {
          $scope.userStats[user.status] = 1;
        }
      });
    });
  }

  $scope.createExperiment = function() {
    var modalInstance = $uibModal.open({
      animation: true,
      templateUrl: 'views/modals/create-experiment.modals.html',
      controller: 'CreateExperimentController',
      size: 'lg'
    });

    modalInstance.result.then(function(newExperiment) {
      $scope.experiments.push(newExperiment);
    });
  };

  $scope.closeAlert = function(alert) {
    console.log('closeAlert');
    AlertService.closeAlert(alert);
  };
}

MainController.$inject = ['$scope', 'ExperimentService', 'UsersService', '$uibModal', 'AlertService', 'AdminService', '$location'];

angular.module('clientApp')
  .controller('MainCtrl', MainController);
