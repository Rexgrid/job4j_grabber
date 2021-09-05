# job4j_grabber
Приложение парсит сайты с вакансиями. Первый сайт будет sql.ru. В нем есть раздел job. Программа должна считывать все вакансии относящиеся к Java и записывать их в базу.

В проекте нужно использовать maven, travis, jacoco, checkstyle.
Приложение должно собираться в jar.
Система запускается по расписанию. Период запуска указывается в настройках - app.properties.
Доступ к интерфейсу будет через REST API.
расширение:

В проект можно добавить новые сайты без изменения кода.
В проекте можно сделать параллельный парсинг сайтов.


[![Build Status](https://app.travis-ci.com/Rexgrid/job4j_grabber.svg?branch=main)](https://app.travis-ci.com/Rexgrid/job4j_grabber)
[![codecov](https://codecov.io/gh/Rexgrid/job4j_grabber/branch/main/graph/badge.svg?token=9VJ45V9Z3M)](https://codecov.io/gh/Rexgrid/job4j_grabber)
