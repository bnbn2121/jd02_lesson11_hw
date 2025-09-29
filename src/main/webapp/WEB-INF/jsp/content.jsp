<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Основной контент -->
    <main class="flex-grow-1">
        <div class="container mt-4">
            <div class="row">
                <!-- Основная область с новостями -->
                <div class="col-12">
                    <h2 class="mb-4">Последние новости</h2>

                    <c:forEach var="news" items="${requestScope.listNews}">
                        <div class="card news-card">
                                                <div class="row g-0">
                                                    <div class="col-md-4">
                                                        <img src="${news.pathImage}"
                                                             class="img-fluid news-image" alt="${news.title}">
                                                    </div>
                                                    <div class="col-md-8">
                                                        <div class="news-content">
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

                    <!-- Пагинация -->
                        <nav aria-label="Page navigation" class="mt-4">
                            <ul class="pagination justify-content-center">
                                <li class="page-item disabled">
                                    <a class="page-link" href="#" tabindex="-1">Предыдущая</a>
                                </li>
                                    <li class="page-item active"><a class="page-link" href="#">1</a></li>
                                    <li class="page-item"><a class="page-link" href="#">2</a></li>
                                    <li class="page-item"><a class="page-link" href="#">3</a></li>
                                    <li class="page-item"><a class="page-link" href="#">4</a></li>
                                    <li class="page-item"><a class="page-link" href="#">5</a></li>
                                    <li class="page-item">
                                    <a class="page-link" href="#">Следующая</a>
                                </li>
                            </ul>
                        </nav>
                </div>
            </div>
        </div>
    </main>