package com.edu.less08.dao.impl;

import com.edu.less08.dao.DaoException;
import com.edu.less08.dao.NewsDao;
import com.edu.less08.model.News;
import com.edu.less08.model.Status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HardcodeNewsDao implements NewsDao {
    @Override
    public List<News> getNewsPaginated(int offset, int limit) throws DaoException {
        return List.of();
    }

    @Override
    public List<News> getAllNews() throws DaoException {
        return List.of();
    }

    @Override
    public Optional<News> getNewsById(int idNews) throws DaoException {
        return Optional.empty();
    }

    @Override
    public News addNews(News news) throws DaoException {
        return null;
    }

    @Override
    public void deleteNewsById(int idNews) throws DaoException {

    }

    @Override
    public void updateNews(News news) throws DaoException {

    }

    @Override
    public int getAllNewsCount(Status... status) throws DaoException {
        return 0;
    }
    /*
    @Override
    public List<News> getNews(int indexFirst, int countNews) throws DaoException {
        List<News> allNews = getAllNews();
        if (indexFirst < 0 || indexFirst >= allNews.size()) {
            throw new IndexOutOfBoundsException();
        }
        if (countNews < 0 || indexFirst + countNews > allNews.size()) {
            throw new IndexOutOfBoundsException();
        }
        return allNews.stream()
                .skip(indexFirst)
                .limit(countNews)
                .collect(Collectors.toList());
    }

    @Override
    public List<News> getAllNews() throws DaoException {
        List<News> listNews = new ArrayList<>();
        listNews.add(new News(1,
                "Наша компания запустила инновационный проект в сфере IT",
                "Мы рады сообщить о запуске нового масштабного проекта в области информационных технологий. Данный проект направлен на создание передовой платформы для управления бизнес-процессами.",
                "Мы рады сообщить о запуске нового масштабного проекта в области информационных технологий. Данный проект направлен на создание передовой платформы для управления бизнес-процессами. В разработке принимали участие ведущие специалисты компании, и мы уверены, что этот продукт станет революционным в своей отрасли. Проект уже получил положительные отзывы от первых бета-тестеров и готов к полноценному выходу на рынок. Мы планируем продолжить развитие проекта, добавляя новые функции и улучшая пользовательский опыт.",
                "https://proforientator.ru/upload/webp/100/upload/iblock/8f8/97133qsfu0u9vsaizjfayt9uwz7m6qr0.webp",
                LocalDate.of(2024, 1, 15)));

        listNews.add(new News(2,
                "Заключено стратегическое партнерство с университетом",
                "Мы рады объявить о заключении стратегического партнерства с одним из ведущих технических университетов страны. В рамках сотрудничества планируется совместная разработка образовательных программ, проведение научных исследований в области компьютерных наук и искусственного интеллекта, а также организация стажировок для студентов.",
                "Мы рады объявить о заключении стратегического партнерства с одним из ведущих технических университетов страны. В рамках сотрудничества планируется совместная разработка образовательных программ, проведение научных исследований в области компьютерных наук и искусственного интеллекта, а также организация стажировок для студентов. Специалисты нашей компании будут принимать участие в проведении лекций и мастер-классов, а студенты университета получат возможность работать над реальными проектами в нашей компании. Мы уверены, что это партнерство откроет новые возможности для развития инноваций и подготовки высококвалифицированных кадров для IT-индустрии.",
                "https://storage.pravo.ru/image/184/92422.png?v=1656942413",
                LocalDate.of(2024, 1, 5)));

        listNews.add(new News(3,
                "Вышло крупное обновление нашего основного продукта",
                "Компания выпустила масштабное обновление версии 3.0 нашего флагманского продукта. В новой версии полностью переработан пользовательский интерфейс, добавлены функции на основе искусственного интеллекта для автоматизации рутинных задач, улучшена производительность и безопасность системы.",
                "Компания выпустила масштабное обновление версии 3.0 нашего флагманского продукта. В новой версии полностью переработан пользовательский интерфейс, добавлены функции на основе искусственного интеллекта для автоматизации рутинных задач, улучшена производительность и безопасность системы. Особое внимание было уделено удобству использования: теперь интерфейс стал более интуитивно понятным, а процессы работы ускорились в среднем на 40%. Также добавлена интеграция с популярными облачными сервисами и расширены возможности кастомизации под конкретные бизнес-потребности. Все существующие пользователи могут бесплатно обновиться до новой версии через личный кабинет.",
                "https://jukola.com.by/upload/medialibrary/b3a/b3a9ebd842cf909920b7d498f718179e/latest-news-subscribe-update.jpg",
                LocalDate.of(2024, 1, 10)));

        listNews.add(new News(4,
                "Приглашаем на ежегодную технологическую конференцию",
                "Дорогие коллеги и партнеры! Мы с гордостью объявляем о проведении ежегодной технологической конференции, которая состоится 20 февраля 2024 года. Мероприятие пройдет в современном конференц-центре Москвы и соберет ведущих экспертов в области разработки программного обеспечения, искусственного интеллекта и машинного обучения.",
                "Дорогие коллеги и партнеры! Мы с гордостью объявляем о проведении ежегодной технологической конференции, которая состоится 20 февраля 2024 года. Мероприятие пройдет в современном конференц-центре Москвы и соберет ведущих экспертов в области разработки программного обеспечения, искусственного интеллекта и машинного обучения. В программе конференции запланированы выступления известных спикеров, мастер-классы по последним технологическим трендам, панельные дискуссии и нетворкинг-сессии. Участники смогут узнать о новейших подходах в разработке, обменяться опытом и установить деловые контакты. Регистрация уже открыта на нашем официальном сайте.",
                "https://img.freepik.com/premium-photo/rear-view-audience-conference-hall-seminar-meeting-business-education-concept_157125-12997.jpg",
                LocalDate.of(2024, 1, 20)));

        listNews.add(new News(5,
                "Мы расширяемся и ищем талантливых разработчиков",
                "В связи с активным ростом и запуском новых проектов, наша компания открывает несколько вакансий для талантливых специалистов. Мы ищем опытных Java-разработчиков с глубоким пониманием Spring Framework, Hibernate и микросервисной архитектуры. Также открыты позиции для фронтенд-разработчиков, владеющих современными фреймворками React и Angular.",
                "В связи с активным ростом и запуском новых проектов, наша компания открывает несколько вакансий для талантливых специалистов. Мы ищем опытных Java-разработчиков с глубоким пониманием Spring Framework, Hibernate и микросервисной архитектуры. Также открыты позиции для фронтенд-разработчиков, владеющих современными фреймворками React и Angular. Для начинающих специалистов мы предлагаем программу стажировки с возможностью дальнейшего трудоустройства. Мы предоставляем конкурентную заработную плату, гибкий график работы, возможности для профессионального роста и обучения, а также комфортный офис в центре города. Если вы passionate about technology и хотите работать над интересными проектами, присылайте свое резюме на нашу почту.",
                "https://lnc.by/upload/iblock/977/zhh2h11p2ar2aw8py3cr8x2w6safc6t7.jpeg",
                LocalDate.of(2024, 1, 25)));

        return listNews;
    }

    @Override
    public Optional<News> getNewsById(int idNews) throws DaoException {
        return null;
    }

    @Override
    public News addNews(News news) throws DaoException {

        return news;
    }

    @Override
    public void deleteNewsById(int idNews) throws DaoException {

    }

    @Override
    public void updateNews(News news) throws DaoException {
        return null;
    }

    @Override
    public int findIndexNewsById(int idNews) throws DaoException {
        return 0;
    }
     */
}