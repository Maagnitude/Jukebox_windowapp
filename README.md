# A windowed version of the Jukebox_app!

## **Runs on terminal**
java -jar target/JukeBox-1.0-SNAPSHOT.jar

or on **IDEA by clicking "Run"**

**On the first window, that asks for the folder name, give the full path**

Done:
1) Opens a window and asks the user for the path to the folder with the songs.
2) Opens a window with buttons (play, pause, next, previous, stop) and a list of songs in the folder.
3) If you press play, it plays the first one. If you press pause, it stops, and with play it plays again from where it was.
4) If you press stop, the song stops and goes back to the beginning, whether you're playing or paused.
5) If you press next it goes to the next one, even when it reaches the end of the list, it goes back to the first one
6) If you press previous it goes to the previous one, and if you reach the beginning of the list, it goes to the last one.
7) If you double click on a song in the list, it plays that song, either before or after you clicked on something.
8) If you close the window by pressing the 'x' in the upper right corner, the application is closed

Remains to be done:
1) Loggers that display messages in the terminal, like in the other work
2) Make the window a little better looking
3) To put in the strategies, loop, order, random, like buttons, which when pressed, will be applied from the next song played
4) Bar at the bottom of the window, so that if the user selects a particular note of the song, it will play that note
5) The metadata, artist, album etc, to be displayed in the window as well.
6) When a song is clicked, and then you click "next", it plays the next song after the last one played without clicking.
In other words, it refreshes the index of the song, instead of increasing it by 1.