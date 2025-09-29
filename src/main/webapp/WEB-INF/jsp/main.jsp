<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Новостной портал</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome для иконок -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .news-card {
            transition: transform 0.3s ease;
            margin-bottom: 30px;
        }
        .news-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }
        .news-image {
            height: 300px;
            object-fit: cover;
            width: 100%;
        }
        .language-selector {
            cursor: pointer;
        }
        footer {
            margin-top: 50px;
        }
        .news-content {
            padding: 20px;
        }
        .navbar-brand-center {
            position: absolute;
            left: 50%;
            transform: translateX(-50%);
        }
        @media (max-width: 991px) {
            .navbar-brand-center {
                position: static;
                transform: none;
                text-align: center;
                margin: 0 auto;
            }
        }
    </style>
</head>
<body class="d-flex flex-column min-vh-100">
    <jsp:include page="header.jsp"/>

    <jsp:include page="content.jsp"/>

    <jsp:include page="footer.jsp"/>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>