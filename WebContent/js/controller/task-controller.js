angular.module('andreypatricio').controller('TaskController', function($scope, $http) {

	$scope.tasks = [];
	$scope.task = {}
	$scope.msg = '';

	$http.get('/task-manager/rest/task/get')
        .success(function(data) {
            $scope.tasks = data;
        })
        .error(function(erro) {
            console.log(erro);
    });
	
	$scope.newTask = function() {
		if($scope.new.$valid){
			$http.post('/task-manager/rest/task/new', $scope.task)
			.success(function() {
				$scope.msg = 'Task created!';
				$scope.task = {};
				$scope.tasks.push($scope.task);
			})
			.error(function(error){
				$scope.msg = 'Error during task creation!';
			});
		}
	};

    $scope.deleteTask = function(task){
        $http.delete('/task-manager/rest/task/delete/'+ task.id)
        .success(function() {
            $scope.msg = 'Task removed!';
            var taskID = $scope.tasks.indexOf(task);
            $scope.tasks.splice(taskID, 1);
        })
        .error(function(erro){
            console.log(erro);
        });
    };


    $scope.doneTask = function(task){
        $http.put('/task-manager/rest/task/done/'+ task.id)
        .success(function() {
            $scope.msg = 'Task done!';
        })
        .error(function(erro){
            console.log(erro);
        });
    };

});
