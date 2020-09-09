# OnePlus Battery Saver


## Introduction
This app aims to improve OnePlus battery life by managing the refresh rate.<br />
In particular situations (Power saving mode, certain battery percentage reached), this app sets the screen frame rate to 60Hz <br />
And then bring it back to 90Hz when everything goes back to normal <br />
This is my first project in Android, and i would like to thank [Samuele794](https://it.linkedin.com/in/samuele794) for mentoring me during the development of this app.

## Supported Devices
| Device Name | Model |
| ----------- | ----- |
| OnePlus Nord | AC2001, AC2003 |
| OnePlus 8 | IN2013, IN2017 |
| OnePlus 7T Pro McLaren Edition | HD1925 |
| OnePlus 7T Pro | HD1911, HD1913, HD1910 |
| OnePlus 7T | HD1901, HD1903, HD1900, HD1907, HD1905 |
| OnePlus 7 Pro 5G | GM1925 |
| OnePlus 7 Pro | GM1911, GM1913, GM1917, GM1910, GM1915 |

## Features
- Changes the refresh rate when energy saver is enabled
- You can set a battery percentage for triggering the refresh rate change
- Notifies you when the refresh rate changes

## Installation
1) Install it on your phone (first check the [Supported Devices](#supported-devices) section)

2) Enable developer tools (Settings -> About Phone -> Click on "Build number" several times)

3) Enable USB debugging (Settings -> System -> Developer options -> USB Debugging)

4) Install & set up ADB ([Tutorial Here](ADB_setup.md))

5) Plug your device in your computer and allow USB Debugging

6) Open terminal and run this command
```sh
# Windows
adb shell pm grant com.collez.opbatterysaver android.permission.WRITE_SECURE_SETTINGS

# Linux & MacOS
./adb shell pm grant com.collez.opbatterysaver android.permission.WRITE_SECURE_SETTINGS
```

## Special Thanks
I would like to thank 2 awesome guys that helped me a lot with this project

| Name | Role | Github | LinkedIn |
| ---- | ---- | ------ | -------- |
| Samuele794 | Mentor & Developer | [Samuele's GitHub](https://github.com/samuele794) | [Samuele's LinkedIn](https://www.linkedin.com/in/samuele794/) |
| Ludovico Besana | Designer | [Ludovico's GitHub](https://github.com/ludovicobesana) | [Ludovico's LinkedIn](https://www.linkedin.com/in/ludovicobesana/) |

### Todos
- Add support for OnePlus 8 Pro
- Add in-app auto updater
- Support more languages

### Supported Languages
- English
- Italian
- German
- French

### Contacts
You can contact me on Discord, my name is `Collez#9445`

### Disclaimer
**I do not take responsibility for any damage caused by my application, use it at your own risk**

### License
[Apache License 2.0](LICENSE)

© Marco Colletti 2020
