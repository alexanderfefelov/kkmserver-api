# kkmserver-api

Scala API для сервера печати чеков/этикеток KkmServer.

Для установки выполните команды

    git clone https://github.com/alexanderfefelov/kkmserver-api.git
    cd kkmserver-api
    git checkout tags/0.3.1
    
Разверните [тестовый инстанс KkmServer](extra/kkmserver/vagrant/README.md) и запустите тесты 

    cd kkmserver-api
    sbt test

Если тесты завершились успешно, опубликуйте API

    cd kkmserver-api
    sbt "+ publishLocal"

и добавьте в файл `build.sbt` вашего проекта строку

    libraryDependencies += "com.github.alexanderfefelov" %% "kkmserver-api" % "0.3.1"

![Кассовый чек](doc/sales-check.png)
