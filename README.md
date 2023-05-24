Project 1: Aisle has millions of users from over 150 countries. Each user gets 10 free daily Likes. Unused Likes are not carried forward to the next day. We must refresh the number of daily Likes at 12:00 pm according to the user's local time.How would you go about doing this in a scalable way? No need to write code, simply explain to us in theory the backend logic in your own words.

Answer : 
1. We have to take user's local time zone which we can take from user as part of registration in app, Based on that we can refresh the like count based on their local 12:00PM.
2. Whenever user hits the like API on server side we need to check if it current time is passed by the 12:00PM local time of user if yes then set that users unique counter to 10 else decrese the counter by 1 and if counter becomes 0 show the maximum likes reached message.
3. For scalablity we can use load balancing techniques or employ caching mechanisams such as catching user's likes which will provide faster access.





**Prerequisite**
=============
*  Android Studio 4.1.2
*  Android Version 6.0 to 10.0
*  Kotlin Language
 
**Feature**
=============
* MVVM Architecture
* AndroidX
* Material Design
* Dependency Ejection(Koin)
* Api Calling(Retrofit, Moshi)
* Local Database(Room)
* Shared Preference With Encryption(Tink)
* Image Loading(Glide) 
* Debugging(Timber) 
* RunTime Permission(EasyPermission) 
* Location
* Kotlin Coroutines
* ApiViewStateConstraintLayout(CustomView for API state handling)

 **Utility**
 =============
 * AlertExt: This class has commonly used extension function for snackbar alerts
 * ContextExt: This class extension function user for activity context operations like start activity and hide keyboard
 * CustomViewExt: This class used for ApiViewStateConstraintLayout operations
 * LifeCycleExt: This class has used for mutable live data observer extension function
 * StringExt: This class used for string operations
 * Utils: This class used for common utility operations
 * ViewExt: This class has commonly used extension function for view operations
