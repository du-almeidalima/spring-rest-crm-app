<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
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

        .response-box{
            padding: 20px;
            background-color: #e1e1e1;
            margin-top: 50px;
            height: 10vh;
            width: 80%;
            border: 1px dashed black;
            border-radius: 10px;
        }
    </style>
</head>
<body>
    <div class="container" style="margin-top: 5%;">
        <h1>Welcome to Spring CRM REST Project!</h1>
        <p class="text">This is a Customer Relation Management built with Spring MVC (REST). Here you can retrieve
        Customer data from a physical database (MySQL 8.0), feel free to play with!</p>
        <p>You can also check the project in this <a href="https://github.com/Xenosgrilda/spring-rest-crm-app" target="_blank">repo</a>.</p>

        <h2>Routes</h2>
        <ul style="position: relative; width: 60%;">
            <li><b>GET</b> <a class="space-left" href="api/customers">/api/customers</a></li>
            <li><b>GET</b> <a class="space-left" href="api/customers/1">/api/customers/{id}</a></li>
            <li><b>POST</b> <span class="space-left">/api/customers</span></li>
            <li><b>DELETE</b> <span class="space-left" >/api/customers/{id}</span></li>
            <li><b>PUT</b> <span class="space-left" >/api/customers/{id}</span></li>
        </ul>

        <h2>Try it!</h2>
        <button id="get-customer">Get Customer 1</button>
        <div class="response-box"></div>
    </div>
</body>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/static/app.js"></script>
</html>