# redalpha
Redalpha test.


Http requests examples:

1) To add call:
  POST http://localhost:8080/redalpha/calls
  Content-Type: application/json
Request Body:
  {
  "firstname": "fristname",
  "lastname": "lastname",
  "phone": "(111) (222 333)"
  }
  
2) To get calls:
  GET http://localhost:8080/redalpha/calls
