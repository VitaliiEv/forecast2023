# Описание приложения

## Описание реализации

В состав приложения входит web-клиент и сервер. Обмен данными между клиентом и приложением производится в
формате `JSON`.

Приложение получает от клиента запрос погоды в виде перечня городов. После этого приложение обращается
к `Geocode API OpenWeatherMap` для поиска сведений о городе. Сведения о локации сохраняются в базу данных. После этого
проводится проверка временной метки имеющегося прогноза для данной локации (по умолчанию период устаревания равен 1 часу
и определен в параметре `forecast2023.obsolete`). Если имеется актуальный прогноз в базе данных, то приложение отдает
клиенту
его. Если нет - то выполняется запрос прогноза у `API OpenWeatherMap`. Полученные данные передаются пользователю.

При разработке приложенияиспользованы следующие инструменты:

* OpenApiGenerator - для генерации кода API (контроллеры и DTO)
* Mapstruct - для преобразования сущностей
* Spring RestTemplate - для обращения к API OpenWeatherMap и собственному API

## Классы приложения

* [api.yaml](src%2Fmain%2Fresources%2Fstatic%2Fapi.yaml) - спецификация API приложения
* [aop](src%2Fmain%2Fjava%2Fvitaliiev%2Fforecast2023%2Faop) - классы, отвечающие за сквозное логирование вызовов
* [api](src/main/java/vitaliiev/forecast2023/api) - контроллеры и сервисный слой API
* [client](src%2Fmain%2Fjava%2Fvitaliiev%2Fforecast2023%2Fclient) - web-клиент
* [config](src%2Fmain%2Fjava%2Fvitaliiev%2Fforecast2023%2Fconfig) - конфигурационные классы
* [domain](src%2Fmain%2Fjava%2Fvitaliiev%2Fforecast2023%2Fdomain) - классы JPA сущностей
* [dto](src%2Fmain%2Fjava%2Fvitaliiev%2Fforecast2023%2Fdto) - DTO отдаваемые API и получаемые от OWM
* [mappers](src%2Fmain%2Fjava%2Fvitaliiev%2Fforecast2023%2Fmappers) - классы преобразования сущностей
* [openweathermap](src%2Fmain%2Fjava%2Fvitaliiev%2Fforecast2023%2Fopenweathermap) - клиент OpenWeatherMap
* [repository](src%2Fmain%2Fjava%2Fvitaliiev%2Fforecast2023%2Frepository) - классы репозиториев

## Необходимые доработки

* вывод погоды за заданный пользователем период (с «дата» по «дата»). Данный функционал требует приобретения платного
  ключа к API OpenWeatherMap
* необходима обработка ошибок, получаемых OpenWeatherMap
* кэширование запросов к таблице БД с перечнем городов
* оптимизация запросов к БД с использоварнием JQL/HQL
* защита конечных точек REST API с доступом по JWT токену

# Запуск приложения

Перед запуском необходимо сгенерировать классы OpenApiGenerator и Mapstruct. Для этого необходимо перейти в корневую
директорию проекта и выполнить команду: `./gradlew build`
После запуска приложения доступны следующие конечные точки:

* [Web-клиент](http://localhost:8080)
* [Спецификация API в формате JSON](http://localhost:8080/v3/api-docs)
* Конечные точки API http://localhost:8080/api/v1/**

### Запуск из IDE

В файле [application.yml](src/main/resources/application.yml) необходимо задать значение параметра
`forecast2023.owm-api-key=`, либо задать значиние переменной окружения `FORECAST2023_OWM_API_KEY`

По умолчанию запуск приложения осуществляется с `dev` профилем.

Для запуска c `prod` профилем необходимо:

* в файле [application.yml](src/main/resources/application.yml) выполнить команду: установить
  параметр: `spring.profile.active=prod`
* выполнить команду `docker compose -f docker/postgre.yml up`

### Запуск в контейнере

По умолчанию приложение в контейнере запускается с `prod` профилем
Выполнить следующие действия:

* [gradle.properties.template](gradle.properties.template) скопировать в `gradle.properties` и задать в нем значение
  параметра `owmApikey`
* Перейти в корневую директорию проекта и выполнить команду: `./gradlew bootJar jibDockerBuild`
* Выполнить команду: `docker compose -f docker/app.yml up`
