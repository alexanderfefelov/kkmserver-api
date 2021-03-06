# KkmServer в виртуальной машине

## Что это?

Это набор файлов, позволяющий с помощью Vagrant в автоматическом режиме создать виртуальную машину
VirtualBox на основе Windows Server 2016 Standard Evaluation 64-bit, в которой
KkmServer будет установлен и запущен в режиме "служба Windows". Кроме того, в KkmServer будут настроены три эмулятора ККТ:

1. [незарегистрированный](doc/foo.png)
1. [зарегистрированный с ФФД 1.0](doc/bar.png)
1. [зарегистрированный с ФФД 1.05](doc/baz.png)

Внутрь виртуальной машины с хост-машины будут проброшены порты:

* 5893 - KkmServer
* 2200 - RDP

## Системные требования

* [VirtualBox](https://www.virtualbox.org/)
* [Vagrant](https://www.vagrantup.com/)
* [vagrant-triggers](https://github.com/emyl/vagrant-triggers)

## Управление виртуальной машиной

* Подготовка и первый запуск

```
copy ..\dist\Setup_KkmServer_P.Q.RR.SS_ДД.ММ.ГГГГ.exe resources\Setup_KkmServer.exe
vagrant up
```

* Останов

```
vagrant halt
```

* Запуск после останова

```
vagrant up
```

* Удаление

```
vagrant destroy
```

## Учетные записи

* Windows: `Administrator` / `vagrant`
* KkmServer: `Admin` / `admin` и `User` / `user`

## Активация Windows в виртуальной машине

Для активации Windows используйте команду

```
slmgr.vbs /rearm
```

После активации виртуальную машину необходимо перезагрузить. Для проверки статуса активации Windows запустите

```
slmgr.vbs /dlv
```
