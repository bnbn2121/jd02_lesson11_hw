<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<main class="flex-grow-1">
    <div class="container mt-4">
        <div class="row">
            <div class="col-12">
                <h2 class="mb-4">Последние новости</h2>

                <c:choose>
                    <c:when test="${not empty requestScope.listNews}">
                        <c:forEach var="news" items="${requestScope.listNews}">
                            <div class="card news-card mb-4">
                                <div class="row g-0">
                                    <div class="col-md-4">
                                        <img src="${news.imagePath}"
                                             class="img-fluid news-image" alt="${news.title}">
                                    </div>
                                    <div class="col-md-8">
                                        <div class="news-content p-3">
                                            <h3 class="card-title">${news.title}</h3>
                                            <p class="card-text">${news.brief}</p>
                                            <div class="d-flex justify-content-between align-items-center">
                                                <small class="text-muted">${news.publishDate}</small>
                                                <a href="#" class="btn btn-primary">Читать далее</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="alert alert-info" role="alert">
                            Новости не найдены.
                        </div>
                    </c:otherwise>
                </c:choose>

                <!-- Пагинация -->
                <c:if test="${requestScope.totalPages > 1}">
                    <nav aria-label="Page navigation" class="mt-4">
                        <ul class="pagination justify-content-center">
                            <!-- Кнопка "Предыдущая" -->
                            <li class="page-item ${requestScope.viewPage == 1 ? 'disabled' : ''}">
                                <a class="page-link"
                                   href="Controller?command=go_to_main_page&viewPage=${prevPage}"
                                   aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>

                            <!-- Номера страниц -->
                            <c:forEach begin="1" end="${requestScope.totalPages}" var="pageNum">
                                <li class="page-item ${pageNum == requestScope.viewPage ? 'active' : ''}">
                                    <a class="page-link" href="Controller?command=go_to_main_page&viewPage=${pageNum}">${pageNum}</a>
                                </li>
                            </c:forEach>

                            <!-- Кнопка "Следующая" -->
                            <li class="page-item ${requestScope.viewPage == requestScope.totalPages ? 'disabled' : ''}">
                                <a class="page-link"
                                   href="Controller?command=go_to_main_page&viewPage=${nextPage}"
                                   aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </li>
                        </ul>
                    </nav>
                </c:if>
            </div>
        </div>
    </div>
</main>