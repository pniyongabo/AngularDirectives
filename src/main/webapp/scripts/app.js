var addAuthorModal,listAuthorsModal; 

var lmsModule = angular.module('lmsApp', [ 'ngRoute', 'ui.bootstrap']);

lmsModule.filter('format', function () {
	   return function (input) {
	      return input+"10";
	   };
	});
lmsModule.directive('ngLmsSearchBox', ['$http', function($http) {
	  return {
	      restrict: 'E',
	      replace: 'true',
	      scope: {
	    	  searchtype: '@searchtype',
	    	  searchText: '@ngModel'	  
	        },
	      templateUrl: 'searchBox.html',
	      link: function ($scope, element, attrs) {
	    	  	if($scope.searchtype == "Authors") {
		            element.bind('keyup', function () {
		        		$http({
			  			  method: "POST",
						  url: "searchAuthors?searchText="+$scope.searchText
		        		}).success(function(data) {
		    				$scope.authors = data;
		    			});
		            	
		            });
	    	  	} else if ($scope.searchtype == "Publishers") {
	    	  		//bind method for publishers
	    	  	}
	        }
	  };
	}]);

lmsModule.config([ "$routeProvider", function($routeProvider) {
	return $routeProvider.when("/", {
		redirectTo : "/home"
	}).when("/home", {
		templateUrl : "home.html"
	}).when("/admin", {
		templateUrl : "admin.html"
	}).when("/addAuthor", {
		templateUrl : "addAuthor.html"
	}).when("/editAuthor", {
		templateUrl : "editAuthor.html"
	}).when("/listAuthors", {
		templateUrl : "listAuthors.html"
	}).when("/editBook", {
		templateUrl : "editBook.html"
	})
} ]);

lmsModule.controller('adminCtrl', ["$scope", "$http", "$modal", function($scope, $http, $modal) {
	$scope.showAddAuthor = function() {
		addAuthorModal = $modal.open({
            templateUrl: "addAuthor.html",
            controller: "addAuthorCtrl"
        });
	};
	$scope.listAuthors = function() {
		listAuthorsModal = $modal.open({
            templateUrl: "listAuthors.html",
            controller: "listAuthorsCtrl"
        });
	};
}]);

lmsModule.controller('addAuthorCtrl', ["$scope", "$http", "$modal", "authorService", function($scope, $http, $modal, authorService) {
	$scope.cancel = function() {
		addAuthorModal.close('close');
	};

	$scope.save = function() {
		var author = {authorId :'', authorName: ''};
		author.authorName = $scope.authorName;
		var result = authorService.insertAuthor(author);
		alert(result);
		addAuthorModal.close('close');
	};
}]);

//service to call listAuthors
lmsModule.factory('authorService', ["$scope", "$http", function($scope,$http){
	return {
        getAuthors : function () {
        	$http.post("listAuthors").
	    		success(function(data) {
	    			authors = data;
	    		});
            return authors;
        },
        insertAuthor: function(author) {
        	$http({
        			method: "POST",
        			url: "insertAuthor", 
        			data: author}).
        		success(function(data) {
        			result = data;
        		});
        	return result;
        }
	
    };
}]);


authorService.insertAuthor(author);


lmsModule.controller('listAuthorsCtrl', ["$scope", "$http", "$modal", "authorService", function($scope, $http, $modal, authorService) {
	$http.post("countAuthors").
		success(function(data) {
			var range = [];
			for(var i=1;(i<=data/10);i++) {
			  range.push(i);
			}
			$scope.range = range;
			$scope.authors = authorService.getAuthors;
		});
	
	$scope.pageAuthor = function(pgNo) {
		console.log(pgNo);
		$http({
		    method: "POST",
		    url: "listAuthors?pageNo="+pgNo
//		    data: {
//		    	pageNo: pgNo
//		    }
		}).success(function(data) {
				$scope.authors = data;
			});
	}
}]);