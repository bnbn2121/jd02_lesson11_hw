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

        /* Стили для всплывающего уведомления */
        .alert-toast {
            position: fixed;
            top: 20px;
            left: 50%;
            transform: translateX(-50%);
            z-index: 9999;
            min-width: 400px;
            max-width: 90%;
            animation: toastAnimation 4s ease-in-out forwards;
        }

        @keyframes toastAnimation {
            0% {
                transform: translateX(-50%) translateY(-100%);
                opacity: 0;
            }
            15% {
                transform: translateX(-50%) translateY(0);
                opacity: 1;
            }
            85% {
                transform: translateX(-50%) translateY(0);
                opacity: 1;
            }
            100% {
                transform: translateX(-50%) translateY(-100%);
                opacity: 0;
                visibility: hidden;
            }
        }

        @media (max-width: 991px) {
            .navbar-brand-center {
                position: static;
                transform: none;
                text-align: center;
                margin: 0 auto;
            }
            .alert-toast {
                min-width: 90%;
                max-width: 90%;
            }
        }
    </style>
</head>
<body class="d-flex flex-column min-vh-100">
    <!-- Блок для отображения уведомлений -->
    <c:if test="${not empty sessionScope.errorMessage}">
        <div class="alert alert-danger alert-toast alert-dismissible fade show" role="alert">
            <div class="d-flex align-items-center">
                <i class="fas fa-exclamation-triangle me-2"></i>
                <div class="flex-grow-1">
                    <strong>Ошибка!</strong> ${sessionScope.errorMessage}
                </div>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </div>
        <c:remove var="errorMessage" scope="session"/>
    </c:if>

    <jsp:include page="header.jsp"/>

    <jsp:include page="content.jsp"/>

    <jsp:include page="footer.jsp"/>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>