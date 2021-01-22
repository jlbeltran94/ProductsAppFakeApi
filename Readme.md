# Android development Test

This is a sample app that provides a search functionality through the fakeApi products catalog, also allows the users to see the description and images of a selected product. 
For the development of this project were used:
 
 * [Kotlin](http://kotlinlang.org/) as main language
 * [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines?gclid=Cj0KCQiAifz-BRDjARIsAEElyGK5CyLrvgvGliSMbAreCzH3bW8tTX26pGhmyR4jkpaJBD5ZjcBQ_4waApIdEALw_wcB&gclsrc=aw.ds) for execute asynchronous tasks
 * [Android ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) for the MVVM pattern
 * [Android Navigation component](https://developer.android.com/guide/navigation/navigation-getting-started) for the app navigation
 * [Retrofit](https://square.github.io/retrofit/) for networking
 * [Coil](https://github.com/coil-kt/coil) for image loading
 * [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for dependency injection
 * [Paging 3 library](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) to paginate the displayed data
 * [mockito](https://site.mockito.org/) for testing
 
 ## App architecture
 
 The app was developed using the MVVM pattern and trying to follow Clean architecture and SOLID principles. Also uses the single activity pattern so we only have the main activity that in this case is in charge of handling the search intents and hosting the navigation component.  
 
<details>
  <summary>Project Structure</summary>
 
   ```
    ¦   App.kt // application file
    ¦   
    +---data  // package that contains the data layer related files
    ¦   +---api // contains the network interaction files
    ¦   ¦   ¦   
    ¦   ¦   +---models // models fro the network interaction
    ¦   ¦       
    ¦   +---providers
    ¦   ¦       
    ¦   +---sources // pagin sources
    ¦           
    +---di // dependency related files
    ¦
    +---domain  // package that contains the domain layer related files
    ¦   +---interactors 
    ¦   ¦       
    ¦   +---mappers
    ¦       
    +---ui // user interface related files
    ¦   ¦   
    ¦   +---adapters // recycler view adapters
    ¦   ¦       
    ¦   +---fullScreenCarousel
    ¦   ¦       
    ¦   +---models
    ¦   ¦       
    ¦   +---navigation
    ¦   ¦       
    ¦   +---productDetail
    ¦   ¦       
    ¦   +---products
    ¦   ¦       
    ¦   +---search
    ¦   ¦       
    ¦   +---views
    ¦           
    +---utils // utility classes and extensions
   ```
 </details>
 
 
 
 