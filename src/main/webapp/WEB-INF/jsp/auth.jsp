<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Вход - Новостной портал</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome для иконок -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .language-selector {
            cursor: pointer;
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
        .auth-container {
            max-width: 400px;
            margin: 50px auto;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0,0,0,0.1);
            background: white;
        }
    </style>
</head>
<body class="d-flex flex-column min-vh-100">  <!-- 🔑 Ключевые классы -->

    <jsp:include page="header.jsp"/>

    <!-- Основной контент -->
    <main class="flex-grow-1 d-flex align-items-center justify-content-center">
        <div class="container">
            <div class="auth-container">
                <h2 class="text-center mb-4">Вход в систему</h2>

                <c:if test="${not empty requestScope.errorMessage}">
                    <div class="alert alert-danger py-2 mb-3">
                        <span class="small">${requestScope.errorMessage}</span>
                    </div>
                </c:if>

                <c:if test="${not empty requestScope.successRegistrationMessage}">
                                    <div class="alert alert-success py-2 mb-3">
                                        <span class="small">${requestScope.successRegistrationMessage}</span>
                                    </div>
                                </c:if>

                <form action="Controller?command=do_auth" method="post">
                    <div class="mb-3">
                        <label for="login" class="form-label">Логин</label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="fas fa-user"></i></span>
                                <input type="text" class="form-control" id="login" name="login" required
                                    placeholder="Введите ваш логин">
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="password" class="form-label">Пароль</label>
                        <div class="input-group">
                            <span class="input-group-text"><i class="fas fa-lock"></i></span>
                            <input type="password" class="form-control" id="password" name="password" required
                                   placeholder="Введите ваш пароль">
                        </div>
                    </div>

                    <div class="mb-3 form-check">
                        <input type="checkbox" class="form-check-input" id="rememberMe" name="rememberMe">
                        <label class="form-check-label" for="rememberMe">Запомнить меня</label>
                    </div>

                    <button type="submit" class="btn btn-primary w-100 mb-3">
                        <i class="fas fa-sign-in-alt"></i> Войти
                    </button>

                    <div class="text-center">
                        <a href="#" class="text-decoration-none">Забыли пароль?</a>
                    </div>
                </form>

                <hr class="my-4">

                <div class="text-center">
                    <p>Нет аккаунта? <a href="Controller?command=GO_TO_REGISTRATION_PAGE" class="text-decoration-none">Зарегистрируйтесь</a></p>
                </div>
            </div>
        </div>
    </main>

    <jsp:include page="footer.jsp"/>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>