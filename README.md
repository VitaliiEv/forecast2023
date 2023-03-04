# Описание приложения

Кратко опишите реализацию (об основных классах приложения — назначении и методах). Прокомментируйте вашу реализацию,
опишите, что, на ваш взгляд, нуждается в доработке в этой реализации,  
что может быть узким местом (по производительности, потреблении ресурсов и др.). Что не удалось или не успели сделать.

## Описание реализации

Обмен данными между клиентом и приложением производится в формате `JSON`. Приложение получает запрос погоды в виде
перечня городов. После этого приложение обращается к `Geocode API OpenWeatherMap`
для поиска сведений о городе. Сведения о локации сохраняются в базу данных. После этого проводится проверка временной
метки имеющегося прогноза для данной локации (по умолчанию период устаревания равен 1 часу и определен в
параметре `forecast2023.obsolete`). Если имеется актуальный прогноз в базе данных приложение отдает клиенту его. Если
нет - то выполняется запрос прогноза у `API OpenWeatherMap`. Полученные данные передаются пользователю.

При разработке приложенияиспользованы следующие инструменты:

* OpenApiGenerator - для генерации кода API (контроллеры и DTO)
* Mapstruct - для преобразования сущностей
* Spring RestTemplate - для обращения к API OpenWeatherMap и собственному API

## Классы приложения

* [api.yaml](src%2Fmain%2Fresources%2Fstatic%2Fapi.yaml) - спецификация API приложения
* [aop](src%2Fmain%2Fjava%2Fvitaliiev%2Fforecast2023%2Faop) - классы, отвечающие за сквозное логирование вызовов
* [api](src/main/java/vitaliiev/forecast2023/api) - контроллеры и сервисный слой API
* [client](src%2Fmain%2Fjava%2Fvitaliiev%2Fforecast2023%2Fclient) - web-client
* [config](src%2Fmain%2Fjava%2Fvitaliiev%2Fforecast2023%2Fconfig) - конфигурационные классы
* [domain](src%2Fmain%2Fjava%2Fvitaliiev%2Fforecast2023%2Fdomain) - классы JPA сущностей
* [dto](src%2Fmain%2Fjava%2Fvitaliiev%2Fforecast2023%2Fdto) - DTO отдаваемые API и получаемые от OWM
* [mappers](src%2Fmain%2Fjava%2Fvitaliiev%2Fforecast2023%2Fmappers) - классы преобразования сущностей
* [openweathermap](src%2Fmain%2Fjava%2Fvitaliiev%2Fforecast2023%2Fopenweathermap) - клиент OpenWeatherMap
* [repository](src%2Fmain%2Fjava%2Fvitaliiev%2Fforecast2023%2Frepository) - классы репозиториев

## Прокомментируйте вашу реализацию,

опишите, что, на ваш взгляд, нуждается в доработке в этой реализации,  
что может быть узким местом (по производительности, потреблении ресурсов и др.).

## TODO

* вывод погоды за заданный пользователем период (с «дата» по «дата). Данный функционал требует приобретения платного
  ключа к API OpenWeatherMap
* необходима обработка ошибок, получаемых OpenWeatherMap

# Запуск приложения

Перед запуском необходимо сгенерировать перейти в корневую директорию проекта и выполнить команду: `./gradlew build`

### Запуск из IDE

В файле [application.yml](src/main/resources/application.yml) задать значение параметра
`forecast2023.owm-api-key=`

По умолчанию запуск приложения осуществляется с `dev` профилем.

Для запуска c `prod` профилем необходимо:

* в файле [application.yml](src/main/resources/application.yml) выполнить команду: установить
  параметр: `spring.profile.active=prod`
* выполнить команду `docker compose -f docker/postgre.yml up`

### Запуск в контейнере

Выполнить следующие действия

* [gradle.properties.template](gradle.properties.template) переименовать в `gradle.properties` и установить в нем
  задать значение параметра `owmApikey`
* Перейти в корневую директорию проекта и выполнить команду: `./gradlew bootJar jibDockerBuild`
* Выполнить команду: `docker compose -f docker/app.yml up`
