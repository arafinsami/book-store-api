1. Run the API using IDE or ./gradlew bootRun
2. paste the permissions table in the following SQl Scripts
   
   INSERT INTO `permission` (`id`, `authority`, `route_name`) VALUES
   (1, 'BOOK_SAVE', '/book-save'),
   (2, 'BOOK_UPDATE', '/book-update'),
   (3, 'BOOK_DETAILS', '/book-details'),
   (4, 'BOOK_LIST', '/book-list'),
   (5, 'SAVE_CART', '/save-cart');
   
3. go to the browser and write the following URL as http://localhost:8080/setup to setup innitial data with
   User name : admin
   Password  : admin
   
4. Brows the browser and write the following URL as http://localhost:8080/swagger-ui/
