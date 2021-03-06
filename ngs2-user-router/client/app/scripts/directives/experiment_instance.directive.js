(function() {
  'use strict';

  function ExperimentInstanceController($scope, ExperimentInstanceService, AlertService, $timeout) {
    $scope.dirty = false;
    $scope.saved = false;
    $scope.hover = false;
    $scope.editing = false;
    $scope.deleted = false;

    $scope.editExperimentInstance = function(experimentInstance) {
      if ($scope.editing) {
        $scope.dirty = true;
        //console.log('updateExperiment', experimentInstance);
        ExperimentInstanceService.updateExperimentInstance(experimentInstance)
          .then(function() {
            $scope.dirty = false;
            $scope.saved = true;
            $timeout(function() { $scope.saved = false; }, 1000);
          });
      }
      $scope.editing = !$scope.editing;
    };

    $scope.toggleExperimentInstance = function(experimentInstance) {
      experimentInstance.status = (experimentInstance.status === 'STOPPED') ? 'ACTIVE' : 'STOPPED';
    };

    $scope.deleteExperimentInstance = function(experimentInstance) {
      //console.log('deleteExperimentInstance', experimentInstance);
      if (window.confirm('Are you sure you want this delete this experiment instance? This cannot be undone.')) {
        ExperimentInstanceService.removeExperimentInstance(experimentInstance.id)
          .then(function (resp) {
            console.log('resp', resp);
            $scope.deleted = true;
            AlertService.add('success', 'Experiment instance successfully removed.');
          },
          function (resp) {
            console.log('resp', resp);
            AlertService.add('warning', 'There was an error when trying to remove the experiment instance.');
          });
      }
    };
  }

  ExperimentInstanceController.$inject = ['$scope', 'ExperimentInstanceService', 'AlertService', '$timeout'];

  function ExperimentInstance() {
    return {
      restrict: 'A',
      replace: true,
      templateUrl: 'views/templates/experiment_instance.template.html',
      controller: 'ExperimentInstanceController',
      transclude: false,
      scope: true,
      bindToController: {
        experimentInstance: '='
      }
    };
  }


  angular.module('app.directives.experiment_instance', [])
    .directive('experimentInstance', ExperimentInstance)
    .controller('ExperimentInstanceController', ExperimentInstanceController);

})();
