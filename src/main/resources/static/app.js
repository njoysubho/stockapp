var module=angular.module("stockModule",[]);
module.controller("stockController",function($scope,$http){
	$scope.getStocks=function(){
		$http.get("/api/stocks")
	    .then(function(response) {
	        $scope.stocks = response.data;
	    });
	};
	$scope.getStocks();
});