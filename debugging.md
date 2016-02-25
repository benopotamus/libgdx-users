# Debugging an app #

## Android project ##

---

**Q: How I can get more information from emulator than: Sorry application xx has stopped unexpectedly?**

A: Errors and messages are shown in ConsoleView and LogCatView.

Window -> Show View -> Other -> LogCat
(android manifest has to be set to debuggable=>true)

---

**Q: My android-application was already released and people are reporting crashes. What to do?**

A: You could either write your own exception handler and let it sent crash-reports to your server, or simply use [ACRA](ACRA.md)

---

## Debugging an applet ##

This can be done using Java console when running a .jar file.


Another solution pointed out by ace\_quorthon in the forum is to open <b>a separate window</b> for the messages:

http://www.badlogicgames.com/forum/viewtopic.php?f=11&t=1788#p9953