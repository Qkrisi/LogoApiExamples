# [LogoApi](https://github.com/qkrisi/LogoApi) mintaprogramok

A mintaprogramok használatához töltsük be a [LogoApi.IMP](https://github.com/Qkrisi/LogoApiExamples/blob/master/LogoApi.IMP) fájlt Imagine Logo-ban!

`csatlakoztat` parancssal indíthatjuk el a szervert, `bont` parancssal állíthatjuk le.

## LogoCMD

Teknőc irányítása terminálból (Python)

`logocmd.py` fájlt elindítva a program csatlakozik a (helyi gépen futó) szerverhez, majd várja a parancsokat (`> `)

Enter gomb lenyomásával tudunk egy parancsot elküldeni (pl. `elõre 50`) a teknőcnek.

`_stop` parancsot kiadva állíthatjuk meg a programot.

## LogoMote

Android alkalmazás, melynek segítségével a teknőcöt a telefon döntésével tudjuk irányítani.

Az APK fájlt [innen](https://qkrisi.hu/static/logo/LogoMote.apk) lehet letölteni.

Imagine-ben a `start` eljárás futtatásával tudjuk elindítani a teknőc mozgatását. (Ez egy végtelen ciklust indít el, az eszközsorban lévő megállít gombbal tudjuk megállítani.)

Az alkalmazásban a szerver IP-címét kell megadni, majd a `Csatlakozás` gombra kattintva tudunk hozzá csatlakozni. Ezután a teknőc a telefon dőlésének megfelelően fog mozogni.

`Toll` gomb megnyomásával felvehetjük, illetve leengedhetjük a teknőc tollát.
