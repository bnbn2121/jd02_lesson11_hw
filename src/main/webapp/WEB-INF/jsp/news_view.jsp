<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${news.title} - Новостной портал</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome для иконок -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .news-card {
            transition: transform 0.3s ease;
            margin-bottom: 30px;
        }
        .news-image {
            height: 400px;
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
            padding: 30px;
        }
        .navbar-brand-center {
            position: absolute;
            left: 50%;
            transform: translateX(-50%);
        }

        .news-meta {
            background-color: #f8f9fa;
            border-radius: 5px;
            padding: 15px;
            margin-bottom: 20px;
        }

        .news-full-content {
            line-height: 1.8;
            font-size: 1.1rem;
        }

        .news-full-content p {
            margin-bottom: 1.5rem;
        }

        .action-buttons {
            border-top: 1px solid #dee2e6;
            padding-top: 20px;
            margin-top: 30px;
        }

        @media (max-width: 991px) {
            .navbar-brand-center {
                position: static;
                transform: none;
                text-align: center;
                margin: 0 auto;
            }
            .news-image {
                height: 300px;
            }
        }
    </style>
</head>
<body class="d-flex flex-column min-vh-100">

    <jsp:include page="header.jsp"/>

    <!-- Основной контент -->
    <main class="flex-grow-1">
        <div class="container mt-4">
            <div class="row justify-content-center">
                <div class="col-12 col-lg-10">
                    <!-- Хлебные крошки -->
                    <nav aria-label="breadcrumb" class="mb-4">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="Controller?command=go_to_main_page">Главная</a></li>
                            <li class="breadcrumb-item active">${news.title}</li>
                        </ol>
                    </nav>

                    <c:choose>
                        <c:when test="${not empty requestScope.news}">
                            <div class="card news-card">
                                <!-- Изображение новости -->
                                <c:if test="${not empty news.imagePath}">
                                    <img src="${news.imagePath}" class="card-img-top news-image" alt="${news.title}">
                                </c:if>

                                <div class="news-content">
                                    <!-- Заголовок -->
                                    <h1 class="card-title mb-3">${news.title}</h1>

                                    <!-- Мета-информация -->
                                    <div class="news-meta">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <small class="text-muted">
                                                    <i class="fas fa-user me-2"></i>
                                                    <strong>Автор:</strong> ${news.publisher.login}
                                                </small>
                                            </div>
                                            <div class="col-md-6">
                                                <small class="text-muted">
                                                    <i class="fas fa-calendar me-2"></i>
                                                    <strong>Опубликовано:</strong> ${news.publishDate}
                                                </small>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Краткое описание -->
                                    <div class="border-start border-4 border-info ps-3 mb-4">
                                        <h5 class="mb-2 text-muted">Краткое описание:</h5>
                                        <p class="mb-0 fs-6">${news.brief}</p>
                                    </div>

                                    <!-- Полный текст новости -->
                                    <div class="news-full-content">
                                        ${news.content}
                                    </div>

                                    <!-- Кнопки действий -->
                                    <div class="action-buttons">
                                        <div class="d-flex justify-content-between align-items-center">
                                            <a href="Controller?command=go_to_main_page&currentPage=${requestScope.returnPage}" class="btn btn-outline-secondary">
                                                <i class="fas fa-arrow-left me-2"></i>Назад к списку новостей
                                            </a>

                                            <c:if test="${not empty sessionScope.user && sessionScope.user.id == news.publisher.id}">
                                                <a href="Controller?command=GO_TO_NEWS_REDACTOR_PAGE&newsId=${news.id}" class="btn btn-outline-primary">
                                                    <i class="fas fa-edit me-2"></i>Редактировать
                                                </a>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </c:when>
                        <c:otherwise>
                            <div class="alert alert-danger text-center">
                                <h4 class="alert-heading">Новость не найдена</h4>
                                <p>Запрошенная новость не существует или была удалена.</p>
                                <a href="Controller?command=go_to_main_page" class="btn btn-primary">
                                    <i class="fas fa-home me-2"></i>Вернуться на главную
                                </a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </main>

    <jsp:include page="footer.jsp"/>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        // Автоматическое форматирование изображений в контенте
        document.addEventListener('DOMContentLoaded', function() {
            const content = document.querySelector('.news-full-content');
            if (content) {
                // Находим все изображения в контенте и добавляем классы
                const images = content.querySelectorAll('img');
                images.forEach(img => {
                    img.classList.add('img-fluid', 'rounded', 'mb-3');
                    img.style.maxWidth = '100%';
                    img.style.height = 'auto';
                });

                // Добавляем классы к параграфам для лучшей читаемости
                const paragraphs = content.querySelectorAll('p');
                paragraphs.forEach(p => {
                    if (!p.classList.contains('alert')) {
                        p.classList.add('mb-3');
                    }
                });
            }
        });
    </script>
</body>
</html>