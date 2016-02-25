# ACRA #

ACRA empowers your apps to send crash-reports to your server.

The benefit is that you don't have to write your own exception handler for this task.


## What does it do? ##
Every time your app crashed, a crash-report will get sent to a server you specify.
The report can be sent to various destinations:
  * GoogleDocs
  * eMail
  * Own server-side HTTP POST script
  * Or simply implement your own ACRA-backend for sending!

## Does the user see the crash report? ##
ACRA can run in 3 different modes.
  * Silent(default): The normal ForceClose-Dialog comes up and a crash report will get sent
  * Toast: ACRA will display a toast and sent the crash-report
  * Notification: The user can decide if the report should be sent or not and the user can enter an optional comment.

**_For an exact tutorial on how to use ACRA, see the link below._**

---

## Links ##

http://code.google.com/p/acra/

free BugSense backend as standalone plugin for your app and for integration with ACRA<br />
http://www.bugsense.com/docs