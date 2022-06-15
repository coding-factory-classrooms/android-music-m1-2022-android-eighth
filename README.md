# Spofity
![image](https://user-images.githubusercontent.com/61171763/173767689-e6138c9c-1d96-44e0-bda6-d9b9e5841779.png) [The APK](https://github.com/coding-factory-classrooms/android-music-m1-2021-android-eighth/blob/main/Spofity.apk?raw=true)

Clearly a very original theme idea, and definitley not stolen from Spotify

BackEnd : provided by our Teacher Robin 


Architecutre Used : **MVVM**

functions containted in this project :
- Media Player using a seekbar
- RecyclerViews to display the Album or the Playlist contained songs and to display the different albums we have
- Cache 1+ songs
- Create / Delete a playlist
- Add / Remove 1+ albums to a playlist
- Play / Next / Previous / Song.
- Auto Play Next Song
- Color the song being played in your RecyclerView
- using the library Room to create a Database, and store our Playlists there along side the albums each playlist contains
- And more ..

**Errors not resolved :**
- [Music Player] Resetting MediaPlayer onPlayMusic even if the currently played music is the one that was Pressed.
- [PlayList] if our GlobalCurrentIndex >5, our system colors the Song in the GlobalCurrentIndex + the song in the globalCurrentIndex - 5 for no reason .. :(
- [Auto Play] Whenever we exist the Media Player Fragment, and the MediaPlayer verifies onCompletion it tries to **Next** the song but the app crashes.


![image](https://user-images.githubusercontent.com/61171763/173765383-7b0a8b4a-c077-4da0-963f-363a3d1382fe.png)


Very Helpful tutorial, definitley saved us a lot of time : [Youtube video](https://www.youtube.com/watch?v=1D1Jo1sLBMo&ab_channel=EasyTuto)

The Playlists made by Robin were indispensable
