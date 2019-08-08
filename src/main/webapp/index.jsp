<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Spring CRM REST</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/skeleton/2.0.4/skeleton.css" />
    <style>
        .space-left{
            position: absolute;
            right: 30%;
        }
    </style>
</head>
<body>
    <div class="container" style="margin-top: 5%;">
        <h1>Welcome to Spring CRM REST project!</h1>
        <p class="text">This is a Customer Relation Management built with Spring MVC (REST). Here you can retrieve
        Customer data from a physical database (MySQL 8.0), feel free to play with!</p>
        <p>You can also check the project in this <a href="https://github.com/Xenosgrilda/spring-rest-crm-app" target="_blank">repo</a>.</p>

        <h2>Routes</h2>
        <ul style="position: relative; width: 60%;">
            <li><b>GET</b> <a class="space-left" href="api/customers">/api/customers</a></li>
            <li><b>GET</b> <a class="space-left" href="api/customers/1">/api/customers/{id}</a></li>
            <li><b>POST</b> <a class="space-left" href="api/customers">/api/customers</a></li>
            <li><b>DELETE</b> <a class="space-left" href="api/customers/1">/api/customers/{id}</a></li>
            <li><b>PUT</b> <a class="space-left" href="api/customers">/api/customers/{id}</a></li>
        </ul>
    </div>
</body>
</html>