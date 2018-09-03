# kkmserver-api

Scala API для сервера печати чеков/этикеток [KkmServer](https://kkmserver.ru/KkmServer).

Для установки выполните команды

    git clone https://github.com/alexanderfefelov/kkmserver-api.git
    git checkout tags/v0.3.0
    
Разверните [тестовую операционную систему](extra/kkmserver/vagrant/README.md) и запустите тесты 

    cd kkmserver-api
    sbt test

Если тесты завершились успешно, опубликуйте API

    cd kkmserver-api
    sbt "+ publishLocal"

и добавьте в файл `build.sbt` вашего проекта строку

    libraryDependencies += "com.github.alexanderfefelov" %% "kkmserver-api" % "0.3.0"

![Кассовый чек](doc/sales-check.png)
