# kkmserver-api

Scala API для сервера печати чеков/этикеток [KkmServer](https://kkmserver.ru/KkmServer).

Для установки выполните команды

    git clone https://github.com/alexanderfefelov/kkmserver-api.git
    cd kkmserver-api
    sbt publishLocal

и добавьте в файл `build.sbt` вашего проекта строку

    libraryDependencies += "alexanderfefelov.github.com" %% "kkmserver-api" % "0.0-SNAPSHOT"
