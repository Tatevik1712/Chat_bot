# Chat_Bot JavaFX

Простое JavaFX-приложение "Chat_Bot" с графическим интерфейсом. Ниже приведены инструкции по сборке `.jar`-файла и созданию установочного файла для Windows.

---

## Требования

- Java JDK 17 или новее
- IntelliJ IDEA
- Maven
- Inno Setup (для создания .exe установщика)

---

## Сборка .JAR-файла

1. Убедитесь, что у вас есть главный запускной класс:

```java
public class Launcher {
    public static void main(String[] args) {
        Application.launch(ChatBotApp.class, args);
    }
}```
---
2. Откройте проект в IntelliJ IDEA.
Перейдите в File > Project Structure > Artifacts.
Нажмите +, выберите JAR > From modules with dependencies.
Укажите главный класс (Launcher) и папку вывода (out/artifacts/).

3. Соберите JAR-файл:
Меню Build > Build Artifacts > Build.

После сборки, JAR-файл будет находиться по пути:
out/artifacts/Chat_Bot_jar/Chat_Bot.jar

---
## Установка Inno Setup
Перейдите на сайт: https://jrsoftware.org/isdl.php

Скачайте и установите последнюю версию Inno Setup.
---
## Создание установщика .exe
Создайте простой .iss скрипт (пример ниже).
Откройте его в Inno Setup Compiler и нажмите "Compile".
---
