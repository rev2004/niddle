Open source project dedicated to managing a private user's personal multimedia library. Once fully matured, you will be able to access a website where a highly customizable user interface will allow you to play any FFmpeg supported format on your local computer. (Pretty much anything). As long as you leave the website open on your computer, you should be able to access your music from the same website using your Facebook/Google sign in. <br /><br />

How easy it should be to use the website<br />
#Intro page<br />
#request to scan computer for media<br />
#While scanning, show License Agreement/Privacy Promise<br />
#After agreed, start showing music player populating with multimedia<br />
**If you wish to change the interface there will be a settings tab**<br />
<br />
<br />
<br />

Features and how they connect<br />
**Google Web Toolkit to create a nice UI**<br />
**Signed Java Applet to download and manage FFmpeg executable. This executable should use all instruction sets the CPU allows.**<br />
**Javascript to communicate between the Applet and Google Web Toolkit interface.**<br />
**Incorporate Playstation Media Server project into applet to allow sharing via DLNA/uPNP.**<br />
**SQLite database to store multimedia tag information, original format, FFmpeg transcoding profiles to determine best format for target device**<br />
**Use Facebook and Google single sign on as a base to identify**<br />
**If target device is a mobile device, transcode video on the fly in maximum battery saving profiles.**<br />
**Incorporate Dropbox (etc) as an always online storage medium**<br />
**No 100 MB updates that force you to restart. No pestering of updates. They will just show up when you restart the program.**<br />
**Use ssh as communication and firewall getaround for security**<br />
**No user information should be sent to the server. All that should be locally stored. The only thing the server should have information on is ipaddress so that when you go to the website, the website will connect you to the other devices.**<br />
<br />
**Some kind of extension support**<br />
**Offline mode**<br />

Ideally in the future an app for android, windows phones and ios will happen, but all the features should be based online through a webapp.<br />