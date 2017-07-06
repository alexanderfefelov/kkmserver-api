# kkmserver-api demo

Демонстрационный проект для [kkmserver-api](https://github.com/alexanderfefelov/kkmserver-api]) на основе Play Framework (Scala).

![Скриншот](https://raw.githubusercontent.com/alexanderfefelov/kkmserver-api/master/demo/doc/screenshot.png)

Для запуска выполните команды

    git clone https://github.com/alexanderfefelov/kkmserver-api.git
    cd kkmserver-api
    sbt publishLocal
    cd demo
    vi conf/application.conf
    sbt run

и откройте в браузере ссылку <http://localhost:9000/>.
