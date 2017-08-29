# KkmServer в виртуальной машине

## Что это?

Это набор файлов, позволяющий с помощью [Vagrant](https://www.vagrantup.com/) в автоматическом режиме создать виртуальную машину
[VirtualBox](https://www.virtualbox.org/) на основе Windows Server 2012 R2 Standard Evaluation 64-bit, в которой
KkmServer будет установлен и запущен в режиме "служба Windows". Кроме того, в KkmServer будет зарегистрирован эмулятор ККТ.

Внутрь виртуальной машины с хост-машины будут проброшены порты:

* 5893 - KkmServer
* 2200 - RDP

## Системные требования

* VirtualBox
* Vagrant

## Управление виртуальной машиной

* Установка и первый запуск

```
copy ..\dist\Setup_KkmServer_2.0.22.06_10.05.2017.msi resources\Setup_KkmServer.msi
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
