<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Library Page</title>
    <style>
        body, html {
            height: 100%;
            margin: 0;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            text-align: center;
        }
        a[role="button"] {
            display: inline-block;
            padding: 10px 20px;
            margin: 0 10px;
            text-decoration: none;
            color: #FFF;
            background-color: #007BFF;
            border: 1px solid transparent;
            border-radius: 5px;
            cursor: pointer;
        }

        a[role="button"]:hover {
            background-color: #0056b3;
        }
        a.button {
            display: inline-block;
            padding: 10px 20px;
            margin: 0 10px;
            text-decoration: none;
            color: #FFFFFF;
            background-color: #007BFF;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
    </style>
</head>
<body>


<h1>Hello This is my Library page</h1>


<div class="buttons">
    <a href="/book" class="button" role="button">List of books</a>
    <a href="/person" class="button" role="button">List of peoples</a>
</div>

</body>
</html>
