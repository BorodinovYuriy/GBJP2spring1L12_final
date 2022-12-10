angular.module('app',[]).controller('indexController', function($scope, $http){
    const contextPath = 'http://localhost:8080/myapp/api/v1';
    const singletonCartPath = 'http://localhost:8080/myapp/api/v1/singleton_cart';
    $scope.pageNumber = 1;


//Получение списка продуктов
$scope.fillTable = function() {
                $http({
                    url: contextPath +'/products',
                    method: 'GET',
                    params: {
                             p: $scope.pageNumber,
                             min_cost: $scope.filter ? $scope.filter.min_cost : null,
                             max_cost: $scope.filter ? $scope.filter.max_cost : null,
                             title_part: $scope.filter ? $scope.filter.title_part : null
                    }
                }).then(function(response) {
                                  $scope.ProductList = response.data.content;
                              });
                      };

//Пагинация
$scope.change_page = function(pageVar) {
      $scope.pageNumber = $scope.pageNumber + pageVar;
             if($scope.pageNumber <= 0){
                    $scope.pageNumber = 1
             }
             $http({
                    url: contextPath +'/products',
                    method: 'GET',
                    params: {
                             p: $scope.pageNumber,
                             min_cost: $scope.filter ? $scope.filter.min_cost : null,
                             max_cost: $scope.filter ? $scope.filter.max_cost : null,
                             title_part: $scope.filter ? $scope.filter.title_part : null
                    }
             }).then(function(response) {
                     $scope.ProductList = response.data.content;
                     });
};

//Добавление продукта
    $scope.submitCreateNewProduct = function(){
    /*alert("Отправка!"+ $scope.newProduct);*/
                $http.post(contextPath + '/products', $scope.newProduct)
                    .then(function(response) {
                     location.reload();
                });
        };

//Удаление продукта
    $scope.deleteProductById = function(id){
            $http.delete(contextPath + '/products/' + id)
            .then(function(response) {
                location.reload();
            });
    };

//singletonCart

    $scope.loadSingletoneCartList = function() {
           $http.get(singletonCartPath)
                .then(function(response) {
                     $scope.SingletoneCartList = response.data;
                });
    };

    $scope.deleteFromSingleToneCart = function(id) {
           $http.delete(singletonCartPath + '/' + id)
                .then(function(response) {
                     $scope.loadSingletoneCartList();
                });
    };

    $scope.addToSingleToneCartList = function(id, title, cost) {
           $http({
                  url: singletonCartPath,
                  method: 'POST',
                  params: {
                     id : id,
                     title : title,
                     cost : cost
                  }
           }).then(function(response) {
                   $scope.loadSingletoneCartList();
           });
    };


    $scope.loadSingletoneCartList();
    $scope.fillTable();

});
