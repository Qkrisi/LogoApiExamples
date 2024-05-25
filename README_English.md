# [LogoApi](https://github.com/qkrisi/LogoApi) examples

A magyar leírás [itt](https://github.com/Qkrisi/LogoApiExamples/blob/master/README.md) olvasható.

To use the examples, load [LogoApi_English.IMP](https://github.com/Qkrisi/LogoApiExamples/blob/master/LogoApi_English.IMP) in Imagine Logo.

We can start the server using the `connect` command, and stop it using the `dc` command.

## LogoCMD

Control the turtle from the command line (Python)

Run the `LogoCmd/logocmd.py` file using Python 3.7+. The program will connect to the server (running on the local machine) and wait for the commands (`$ `)

Type the commands (for example. `forward 50`) for the turtle to execute into the terminal and press enter.

The program can be stopped using the `_stop` command.

## LogoMote

Android app, using which we can control the turtle by tilting the phone.

The APK can be downloaded [here](https://qkrisi.hu/static/logo/LogoMoteEnglish.apk).

In Imagine, start listening to the phone using the `start` command. (This starts an infinite loop, you can stop it using the stop button in the control bar.)

In the app, specify the IP address of the server and connect to it using the `Connect` button. Upon a successful connection, the turtle will start moving according to the tilt of the device.

Using the `Pen` button you can toggle the state of the turtle's pen.
