<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <!-- Кнопка Главная слева -->
        <div class="d-flex">
            <a href="Controller?command=go_to_main_page" class="btn btn-outline-light me-2">
                <i class="fas fa-home"></i> На главную
            </a>
        </div>

        <!-- Название по центру -->
        <a class="navbar-brand navbar-brand-center" href="Controller?command=go_to_main_page">Новостной портал</a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <div class="d-flex ms-auto">
                <!-- Проверяем, авторизован ли пользователь -->
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <!-- Блок для авторизованного пользователя -->
                        <div class="d-flex align-items-center me-3">
                            <span class="text-light me-2">
                                <i class="fas fa-user"></i> ${sessionScope.user.login}
                            </span>
                        </div>

                        <!-- Кнопка Создать новость (только для авторизованных) -->
                        <a href="Controller?command=GO_TO_NEWS_REDACTOR_PAGE&currentPage=${requestScope.currentPage}" class="btn btn-outline-success me-3">
                            <i class="fas fa-plus-circle"></i> Предложить новость
                        </a>

                        <!-- Выбор языка -->
                        <div class="dropdown me-3">
                            <button class="btn btn-outline-light dropdown-toggle" type="button"
                                    data-bs-toggle="dropdown">
                                <i class="fas fa-globe"></i> Русский
                            </button>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item" href="#">Русский</a></li>
                                <li><a class="dropdown-item" href="#">English</a></li>
                            </ul>
                        </div>

                        <!-- Кнопка Выход -->
                        <form action="Controller" method="post" class="d-inline">
                            <input type="hidden" name="command" value="DO_LOG_OUT">
                            <button type="submit" class="btn btn-outline-warning">
                                <i class="fas fa-sign-out-alt"></i> Выход
                            </button>
                        </form>
                    </c:when>

                    <c:otherwise>
                        <!-- Блок для неавторизованного пользователя -->
                        <!-- Выбор языка -->
                        <div class="dropdown me-3">
                            <button class="btn btn-outline-light dropdown-toggle" type="button"
                                    data-bs-toggle="dropdown">
                                <i class="fas fa-globe"></i> Русский
                            </button>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item" href="#">Русский</a></li>
                                <li><a class="dropdown-item" href="#">English</a></li>
                            </ul>
                        </div>

                        <!-- Кнопки вход/регистрация -->
                        <a href="Controller?command=GO_TO_AUTH_PAGE" class="btn btn-outline-light me-2">
                            <i class="fas fa-sign-in-alt"></i> Вход
                        </a>
                        <a href="Controller?command=GO_TO_REGISTRATION_PAGE" class="btn btn-primary">
                            <i class="fas fa-user-plus"></i> Регистрация
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</nav>