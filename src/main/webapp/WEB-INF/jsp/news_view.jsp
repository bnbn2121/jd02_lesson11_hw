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
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            border: 1px solid #dee2e6;
            margin-bottom: 30px;
        }
        .news-image {
            height: 400px;
            object-fit: cover;
            width: 100%;
        }
        .news-content {
            padding: 2rem;
        }
        .news-meta {
            background-color: #f8f9fa;
            border-radius: 8px;
            padding: 1rem;
            margin-bottom: 1.5rem;
            border-left: 4px solid #0d6efd;
        }
        .news-full-content {
            line-height: 1.7;
            font-size: 1.1rem;
            color: #333;
        }
        .news-full-content p {
            margin-bottom: 1.5rem;
        }
        .news-full-content img {
            max-width: 100%;
            height: auto;
            border-radius: 8px;
            margin: 1rem 0;
        }
        .action-buttons {
            border-top: 1px solid #e9ecef;
            padding-top: 1.5rem;
            margin-top: 2rem;
        }
        .comments-section {
            margin-top: 3rem;
            padding-top: 2rem;
            border-top: 1px solid #e9ecef;
        }
        .comment-card {
            border: 1px solid #e9ecef;
            border-radius: 8px;
            padding: 1.25rem;
            margin-bottom: 1rem;
            background-color: #fff;
            transition: box-shadow 0.2s ease;
        }
        .comment-card:hover {
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }
        .comment-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 0.75rem;
            flex-wrap: wrap;
            gap: 0.5rem;
        }
        .comment-author {
            font-weight: 600;
            color: #495057;
            font-size: 0.95rem;
        }
        .comment-date {
            color: #6c757d;
            font-size: 0.85rem;
            background-color: #f8f9fa;
            padding: 0.25rem 0.5rem;
            border-radius: 4px;
            border: 1px solid #e9ecef;
        }
        .comment-actions {
            display: flex;
            gap: 0.5rem;
        }
        .btn-comment-action {
            width: 28px;
            height: 28px;
            padding: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            border: none;
            background: transparent;
            color: #6c757d;
            border-radius: 4px;
            font-size: 0.8rem;
            transition: all 0.2s ease;
        }
        .btn-edit-comment:hover {
            background-color: #0d6efd;
            color: white;
        }
        .btn-delete-comment:hover {
            background-color: #dc3545;
            color: white;
        }
        .comment-text {
            color: #495057;
            line-height: 1.5;
            white-space: pre-line;
        }
        .comment-edit-mode {
            display: none;
        }
        .comment-edit-textarea {
            margin-bottom: 0.75rem;
            resize: vertical;
            min-height: 100px;
        }
        .comment-edit-actions {
            display: flex;
            gap: 0.5rem;
            justify-content: flex-end;
        }
        .no-comments {
            text-align: center;
            color: #6c757d;
            font-style: italic;
            padding: 2rem;
            background-color: #f8f9fa;
            border-radius: 8px;
        }
        .add-comment-section {
            background-color: #f8f9fa;
            border-radius: 8px;
            padding: 1.5rem;
            margin-top: 2rem;
        }
        .btn-group-actions {
            display: flex;
            gap: 0.5rem;
        }
        .breadcrumb {
            background-color: #f8f9fa;
            padding: 0.75rem 1rem;
            border-radius: 8px;
        }

        /* Адаптивность */
        @media (max-width: 768px) {
            .news-image {
                height: 300px;
            }
            .news-content {
                padding: 1.5rem;
            }
            .btn-group-actions {
                flex-direction: column;
                width: 100%;
                margin-top: 1rem;
            }
            .btn-group-actions .btn {
                width: 100%;
                margin-bottom: 0.5rem;
            }
            .action-buttons .d-flex {
                flex-direction: column;
            }
            .comment-header {
                flex-direction: column;
                align-items: flex-start;
            }
            .comment-actions {
                align-self: flex-end;
            }
        }

        @media (max-width: 576px) {
            .news-image {
                height: 250px;
            }
            .news-content {
                padding: 1rem;
            }
            .comment-card {
                padding: 1rem;
            }
            .add-comment-section {
                padding: 1rem;
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
                                        <div class="d-flex justify-content-between align-items-center flex-wrap gap-2">
                                            <a href="Controller?command=go_to_main_page&currentPage=${requestScope.currentPage}" class="btn btn-outline-secondary">
                                                <i class="fas fa-arrow-left me-2"></i>Назад к списку новостей
                                            </a>

                                            <c:set var="isNewsOwner" value="${sessionScope.user.id eq news.publisher.id}" />
                                            <c:set var="isModerator" value="${sessionScope.user.role eq 'MODERATOR'}" />
                                            <c:set var="isAdmin" value="${sessionScope.user.role eq 'ADMIN'}" />
                                            <c:set var="isSuperadmin" value="${sessionScope.user.role eq 'SUPERADMIN'}" />
                                            <c:if test="${not empty sessionScope.user && (isNewsOwner || isModerator || isAdmin || isSuperadmin)}">
                                                <div class="btn-group-actions">
                                                    <a href="Controller?command=GO_TO_NEWS_REDACTOR_PAGE&newsId=${news.id}&currentPage=${requestScope.currentPage}" class="btn btn-outline-primary">
                                                        <i class="fas fa-edit me-2"></i>Редактировать
                                                    </a>
                                                    <!-- Кнопка удаления новости -->
                                                    <button type="button" class="btn btn-outline-danger" data-bs-toggle="modal" data-bs-target="#deleteNewsModal">
                                                        <i class="fas fa-trash me-2"></i>Удалить
                                                    </button>
                                                </div>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Модальное окно подтверждения удаления -->
                            <div class="modal fade" id="deleteNewsModal" tabindex="-1" aria-labelledby="deleteNewsModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="deleteNewsModalLabel">Подтверждение удаления</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <p>Вы уверены, что хотите удалить новость <strong>"${news.title}"</strong>?</p>
                                            <p class="text-danger"><small>Это действие невозможно отменить. Все комментарии к этой новости также будут удалены.</small></p>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Отмена</button>
                                            <form action="Controller" method="post" style="display: inline;">
                                                <input type="hidden" name="command" value="delete_news">
                                                <input type="hidden" name="newsId" value="${news.id}">
                                                <input type="hidden" name="currentPage" value="${requestScope.currentPage}">
                                                <button type="submit" class="btn btn-danger">
                                                    <i class="fas fa-trash me-2"></i>Удалить
                                                </button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Секция комментариев -->
                            <div class="comments-section">
                                <h4 class="mb-4">
                                    <i class="fas fa-comments me-2"></i>Комментарии
                                    <c:if test="${not empty requestScope.comments}">
                                        <span class="badge bg-secondary ms-2">${requestScope.comments.size()}</span>
                                    </c:if>
                                </h4>

                                <!-- Список комментариев -->
                                <c:choose>
                                    <c:when test="${not empty requestScope.comments}">
                                        <div class="comments-list">
                                            <c:forEach var="comment" items="${requestScope.comments}">
                                                <div class="comment-card" id="comment-${comment.id}">
                                                    <div class="comment-header">
                                                        <span class="comment-author">
                                                            <i class="fas fa-user me-1"></i>${comment.author.login}
                                                        </span>

                                                        <div style="display: flex; align-items: center; gap: 10px;">
                                                            <span class="comment-date">
                                                                <i class="fas fa-calendar me-1"></i>${comment.publishDate}
                                                            </span>

                                                            <!-- Кнопки действий с комментарием -->
                                                            <c:if test="${not empty sessionScope.user}">
                                                                <c:set var="isCommentOwner" value="${sessionScope.user.id eq comment.author.id}" />
                                                                <div class="comment-actions">
                                                                    <c:if test="${isCommentOwner}">
                                                                        <button type="button" class="btn btn-comment-action btn-edit-comment"
                                                                                onclick="toggleCommentEdit(${comment.id})"
                                                                                title="Редактировать комментарий">
                                                                            <i class="fas fa-edit"></i>
                                                                        </button>
                                                                    </c:if>
                                                                    <c:if test="${isCommentOwner || isModerator || isAdmin || isSuperadmin}">
                                                                        <button type="button" class="btn btn-comment-action btn-delete-comment"
                                                                                data-bs-toggle="modal" data-bs-target="#deleteCommentModal"
                                                                                data-comment-id="${comment.id}" data-comment-author="${comment.author.login}"
                                                                                title="Удалить комментарий">
                                                                            <i class="fas fa-times"></i>
                                                                        </button>
                                                                    </c:if>
                                                                </div>
                                                            </c:if>
                                                        </div>
                                                    </div>

                                                    <!-- Режим просмотра комментария -->
                                                    <div class="comment-view-mode" id="comment-view-${comment.id}">
                                                        <div class="comment-text">
                                                            ${comment.text}
                                                        </div>
                                                    </div>

                                                    <!-- Режим редактирования комментария -->
                                                    <div class="comment-edit-mode" id="comment-edit-${comment.id}">
                                                        <form action="Controller" method="post" class="comment-edit-form">
                                                            <input type="hidden" name="command" value="update_comment">
                                                            <input type="hidden" name="commentId" value="${comment.id}">
                                                            <input type="hidden" name="newsId" value="${news.id}">
                                                            <input type="hidden" name="currentPage" value="${requestScope.currentPage}">

                                                            <textarea class="form-control comment-edit-textarea"
                                                                      name="commentText"
                                                                      rows="3">${comment.text}</textarea>
                                                            <div class="comment-edit-actions">
                                                                <button type="button" class="btn btn-sm btn-secondary"
                                                                        onclick="toggleCommentEdit(${comment.id})">
                                                                    Отмена
                                                                </button>
                                                                <button type="submit" class="btn btn-sm btn-primary">
                                                                    Сохранить
                                                                </button>
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="no-comments">
                                            <i class="fas fa-comment-slash fa-2x mb-3"></i>
                                            <p>Пока нет комментариев. Будьте первым!</p>
                                        </div>
                                    </c:otherwise>
                                </c:choose>

                                <!-- Форма добавления комментария -->
                                <c:if test="${not empty sessionScope.user}">
                                    <div class="add-comment-section">
                                        <h5 class="mb-3">Добавить комментарий</h5>
                                        <form action="Controller" method="post">
                                            <input type="hidden" name="command" value="add_comment">
                                            <input type="hidden" name="newsId" value="${news.id}">
                                            <input type="hidden" name="currentPage" value="${requestScope.currentPage}">

                                            <div class="mb-3">
                                                <textarea class="form-control" name="commentText" rows="4"
                                                          placeholder="Введите ваш комментарий..."
                                                          required></textarea>
                                            </div>
                                            <div class="d-flex justify-content-end">
                                                <button type="submit" class="btn btn-primary">
                                                    <i class="fas fa-paper-plane me-2"></i>Отправить комментарий
                                                </button>
                                            </div>
                                        </form>
                                    </div>
                                </c:if>
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

    <!-- Модальное окно подтверждения удаления комментария -->
    <div class="modal fade" id="deleteCommentModal" tabindex="-1" aria-labelledby="deleteCommentModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="deleteCommentModalLabel">Подтверждение удаления</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p>Вы уверены, что хотите удалить комментарий пользователя <strong id="commentAuthorName"></strong>?</p>
                    <p class="text-danger"><small>Это действие невозможно отменить.</small></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Отмена</button>
                    <form id="deleteCommentForm" action="Controller" method="post" style="display: inline;">
                        <input type="hidden" name="command" value="delete_comment">
                        <input type="hidden" name="commentId" id="commentId">
                        <input type="hidden" name="newsId" value="${news.id}">
                        <input type="hidden" name="currentPage" value="${requestScope.currentPage}">
                        <button type="submit" class="btn btn-danger">
                            <i class="fas fa-trash me-2"></i>Удалить
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="footer.jsp"/>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Автоматическое форматирование изображений в контенте
            const content = document.querySelector('.news-full-content');
            if (content) {
                const images = content.querySelectorAll('img');
                images.forEach(img => {
                    img.classList.add('img-fluid', 'rounded');
                    img.style.maxWidth = '100%';
                    img.style.height = 'auto';
                });
            }

            // Обработка модального окна удаления комментария
            const deleteCommentModal = document.getElementById('deleteCommentModal');
            if (deleteCommentModal) {
                deleteCommentModal.addEventListener('show.bs.modal', function (event) {
                    const button = event.relatedTarget;
                    const commentId = button.getAttribute('data-comment-id');
                    const commentAuthor = button.getAttribute('data-comment-author');

                    document.getElementById('commentAuthorName').textContent = commentAuthor;
                    document.getElementById('commentId').value = commentId;
                });
            }
        });

        // Функция переключения между режимами просмотра и редактирования комментария
        function toggleCommentEdit(commentId) {
            const viewMode = document.getElementById('comment-view-' + commentId);
            const editMode = document.getElementById('comment-edit-' + commentId);

            if (viewMode.style.display !== 'none') {
                viewMode.style.display = 'none';
                editMode.style.display = 'block';

                const textarea = editMode.querySelector('textarea');
                textarea.focus();
                textarea.setSelectionRange(textarea.value.length, textarea.value.length);
            } else {
                viewMode.style.display = 'block';
                editMode.style.display = 'none';
            }
        }
    </script>
</body>
</html>