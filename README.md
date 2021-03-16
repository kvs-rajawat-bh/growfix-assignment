# growfix-assignment

Run the application on localhost:15000/ 

Java version used:- 11


Endpoint details:-

1. First endpoint :- Takes an excel sheet as input and stores data in database.
   
   url:- localhost:15000/upload
   
   I used postman to test the endpoint.
   
2. Second endpoint :- Take 2 inputs, id of a product and date. If a maching detail exist in database, it returns 3 information, product name, interest rate and price.
    
   url:- localhost:15000/get-info?id=3&date=2021-03-03
   
   date query must be in this format, yyyy-MM-dd
