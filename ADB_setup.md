# ADB Setup

## Platform Tools
First of all, you need to install the **Platform Tools** by visiting this [site](https://developer.android.com/studio/releases/platform-tools.html#downloads) and downloading the zip file for your platform

Then, you must extract the zip somewhere in your computer

## Enabling Developer mode

For continue, you must activate the developer mode in your device. If you already done this, you're fine.
Otherwise go [here](README.md#installation) and follow the steps number **2** and **3**

## Opening ADB
Now, you must open a terminal in the **Platform Tools** folder you extracted earlier. For doing this, you can do it in 2 ways:
- Manually changing directory by using the `cd` command.
- Using system's shortcuts:

> | OS | Shortcut |
> | -- | -------- |
> | Windows | <kbd>Shift</kbd> + <kbd>Right Click</kbd> -> Open Powershell window here |
> | Linux | <kbd>Right Click</kbd> -> Open in Terminal |
> | MacOS | Go to System Preferences and select `Keyboard` > `Shortcuts` > `Services`. <br>Find `New Terminal at Folder` in the settings and click the box.<br> Then simply: <kbd>Right Click</kbd> -> Open in Terminal |

## Plug your Device
Plug your device in the computer and allow usb debugging. <br/>
Now, run this command on your terminal:
```sh
# Windows
adb devices

# Linux & MacOS
./adb devices
```
The output contains a list of attached devices, if you see your device, you have correctly set ADB and you can continue to the [installation](README.md#installation). <br />
Otherwise you did something wrong, check better and repeat the previous steps
