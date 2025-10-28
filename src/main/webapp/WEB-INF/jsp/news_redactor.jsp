<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>
        <c:choose>
            <c:when test="${not empty requestScope.news}">Редактирование новости - Новостной портал</c:when>
            <c:otherwise>Добавление новости - Новостной портал</c:otherwise>
        </c:choose>
    </title>
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
        .news-form-container {
            max-width: 800px;
            margin: 30px auto;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0,0,0,0.1);
            background: white;
        }
        .form-label {
            font-weight: 500;
        }
        .preview-image {
            max-width: 100%;
            max-height: 300px;
            object-fit: cover;
            border-radius: 5px;
            margin-top: 10px;
            display: none;
        }
    </style>
</head>
<body class="d-flex flex-column min-vh-100">

    <jsp:include page="header.jsp"/>

    <!-- Основной контент -->
    <main class="flex-grow-1">
        <div class="container">
            <div class="news-form-container">
                <h2 class="text-center mb-4">
                    <c:choose>
                        <c:when test="${not empty requestScope.news}">
                            <i class="fas fa-edit me-2"></i>Редактирование новости
                        </c:when>
                        <c:otherwise>
                            <i class="fas fa-plus me-2"></i>Добавление новости
                        </c:otherwise>
                    </c:choose>
                </h2>

                <!-- Сообщения об ошибках -->
                <c:if test="${not empty requestScope.errorMessage}">
                    <div class="alert alert-danger py-2 mb-3">
                        <span class="small">${requestScope.errorMessage}</span>
                    </div>
                </c:if>

                <!-- Сообщения об успехе -->
                <c:if test="${not empty requestScope.successMessage}">
                    <div class="alert alert-success py-2 mb-3">
                        <span class="small">${requestScope.successMessage}</span>
                    </div>
                </c:if>

                <form action="Controller" method="post" id="newsForm">
                    <!-- Скрытое поле для команды -->
                    <input type="hidden" name="command" value="${not empty requestScope.news ? 'UPDATE_NEWS' : 'ADD_NEWS'}">

                    <!-- Скрытое поле ID новости для редактирования -->
                    <c:if test="${not empty requestScope.news}">
                        <input type="hidden" name="newsId" value="${requestScope.news.id}">
                    </c:if>

                    <!-- Заголовок новости -->
                    <div class="mb-3">
                        <label for="title" class="form-label">Заголовок новости *</label>
                        <div class="input-group">
                            <span class="input-group-text"><i class="fas fa-t"></i></span>
                            <input type="text" class="form-control" id="title" name="title" required
                                   placeholder="Введите заголовок новости"
                                   value="${requestScope.news.title}">
                        </div>
                    </div>

                    <!-- Краткое описание -->
                    <div class="mb-3">
                        <label for="brief" class="form-label">Краткое описание *</label>
                        <div class="input-group">
                            <span class="input-group-text"><i class="fas fa-align-left"></i></span>
                            <textarea class="form-control" id="brief" name="brief" required
                                      placeholder="Введите краткое описание новости"
                                      rows="3">${requestScope.news.brief}</textarea>
                        </div>
                        <div class="form-text">Краткое описание будет отображаться в списке новостей</div>
                    </div>

                    <!-- Полный текст новости -->
                    <div class="mb-3">
                        <label for="content" class="form-label">Полный текст новости *</label>
                        <div class="input-group">
                            <span class="input-group-text"><i class="fas fa-align-left"></i></span>
                            <textarea class="form-control" id="content" name="content" required
                                      placeholder="Введите полный текст новости"
                                      rows="8">${requestScope.content}</textarea>
                        </div>
                    </div>

                    <!-- Ссылка на изображение -->
                    <div class="mb-3">
                        <label for="imagePath" class="form-label">Ссылка на изображение</label>
                        <div class="input-group">
                            <span class="input-group-text"><i class="fas fa-image"></i></span>
                            <input type="url" class="form-control" id="imagePath" name="imagePath"
                                   placeholder="https://example.com/image.jpg"
                                   value="${requestScope.news.imagePath}">
                        </div>
                        <div class="form-text">Укажите URL-адрес изображения для новости</div>

                        <!-- Превью изображения -->
                        <div id="imagePreviewContainer" class="mt-2">
                            <img id="imagePreview" class="preview-image" src="" alt="Предпросмотр">
                        </div>
                    </div>

                    <!-- Кнопки действий -->
                    <div class="d-flex justify-content-between mt-4">
                        <a href="Controller?command=go_to_main_page" class="btn btn-outline-secondary">
                            <i class="fas fa-arrow-left me-2"></i>Вернуться на главную
                        </a>

                        <div>
                            <!-- Кнопка предпросмотра (можно добавить функционал позже) -->
                            <button type="button" class="btn btn-outline-primary me-2" id="previewBtn">
                                <i class="fas fa-eye me-2"></i>Предпросмотр
                            </button>

                            <!-- Кнопка сохранения -->
                            <button type="submit" class="btn btn-primary">
                                <c:choose>
                                    <c:when test="${not empty requestScope.news}">
                                        <i class="fas fa-save me-2"></i>Сохранить изменения
                                    </c:when>
                                    <c:otherwise>
                                        <i class="fas fa-plus me-2"></i>Добавить новость
                                    </c:otherwise>
                                </c:choose>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </main>

    <jsp:include page="footer.jsp"/>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        // Предпросмотр изображения
        document.getElementById('imagePath').addEventListener('input', function() {
            const imageUrl = this.value;
            const preview = document.getElementById('imagePreview');

            if (imageUrl) {
                preview.src = imageUrl;
                preview.style.display = 'block';

                // Обработка ошибок загрузки изображения
                preview.onerror = function() {
                    preview.style.display = 'none';
                };

                preview.onload = function() {
                    preview.style.display = 'block';
                };
            } else {
                preview.style.display = 'none';
            }
        });

        // Инициализация предпросмотра при загрузке страницы (для редактирования)
        document.addEventListener('DOMContentLoaded', function() {
            const initialImage = document.getElementById('imagePath').value;
            if (initialImage) {
                document.getElementById('imagePreview').src = initialImage;
                document.getElementById('imagePreview').style.display = 'block';
            }
        });

        // Обработка кнопки предпросмотра
        document.getElementById('previewBtn').addEventListener('click', function() {
            alert('Функция предпросмотра будет реализована в будущем');
            // Здесь можно добавить логику для открытия предпросмотра в модальном окне
        });

        // Базовая валидация формы
        document.getElementById('newsForm').addEventListener('submit', function(e) {
            const title = document.getElementById('title').value.trim();
            const brief = document.getElementById('brief').value.trim();
            const content = document.getElementById('content').value.trim();

            if (!title || !brief || !content) {
                e.preventDefault();
                alert('Пожалуйста, заполните все обязательные поля (отмеченные *)');
                return false;
            }

            if (title.length < 5) {
                e.preventDefault();
                alert('Заголовок должен содержать не менее 5 символов');
                return false;
            }

            if (brief.length < 10) {
                e.preventDefault();
                alert('Краткое описание должно содержать не менее 10 символов');
                return false;
            }

            if (content.length < 50) {
                e.preventDefault();
                alert('Полный текст новости должен содержать не менее 50 символов');
                return false;
            }
        });
    </script>
</body>
</html>