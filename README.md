# Assignments for course Application development for Android DA401A_Nataliia_Mykhailovska
 Solutions to these assignments should be presented as a separate Android Studio module contained within your Android Studio project for this course.
## Assignment 1
### Description
In this assignment you'll familiarize yourself with the tools used for developing Android applications and you get an overview of the structure of an application. In the assignment we will build a simple application that contains an Activity and two Fragments.
### Requirements
#### Pass (G)
In this app we want random quotes to appear when a button is clicked and it should look at least this nice.
The app should contain one Activity and two Fragments (one Fragment with the button “Get Quote” and one Fragment for the quote).
* When the button “Get Quote” is pressed the second Fragment should be visible with a new random quote (Have at least 3 quotes).
* Have all app texts on buttons etc. placed in XML files (You can have the quotes hardcoded in Java).
* Have proper back navigation from the Quote Fragment back to the next Quote Fragment.
* Override the methods onCreate, onStart, onResume , onPause, onDestroy, onStop in your activity and use LogCat to monitor when the methods are called.
* Override onCreateView and onCreate in one of your fragments and use LogCat to monitor when the methods are called.

#### Pass with Distinction (VG)
* Have the quotes as an array in the strings.xml file.
* Have todays date in the Quote fragment.
* Implement a second language in the app. 
* Place the logic for collecting date and for randomizing the quotes as static methods in a sep- arate class called Helpers.

## Assignment 2
### Description
In this assignment you'll be working with movie-related data and how to present this nicely
with images and text. Attached to this assignment is an archive file containing an XML file
and drawables for ten different movies. Your task is to create a simple application which
presents these movies in a way that is aesthetically pleasing and visually comprehensive.
Your application will have one activity and the two following fragments:
* A master screen presenting the movie collection.
* A detail screen that shows when the user clicks on a movie, containing detailed
information about the specific movie.

### Setup
Download movie resources from It's Learning. Add these to the module resources. Create
Movie.java to represent a movie. Create fragments and adapter.
Helpful search queries: “Android resource types”, “BaseAdapter”.
### Design
Create all necessary layout files. Customize the theme color palette. Make sure to follow
the material design guidelines.
Helpful search queries: “GridView”, “ListView”, “AppCompat theme”.
### Coding tips (If you need them)
In master screen fragment load movie resources, create Movie objects and add to List.
Create list component (listview or gridview). Create adapter. Hook your adapter to your list
component object. Add click listener to list. When item clicked, create detail screen
fragment, put clicked item data in a Bundle object. In detail screen fragment, show detailed
info for specific movie.
Helpful search queries: “Fragment Bundle”.
### Requirements
#### Pass (G)
* Application should have one activity, two fragments. Implement master/detail navigation fow with proper back navigation (fragment a b, → then b → a when back button is pressed)
* Master view presents movie collection with posters, year and title. Detail view show fanart, year, title and overview.
* Customized color palette. All layouts should follow the Material Design Guidelines.
#### Pass with Distinction (VG)
• Different layouts for landscape and portrait orientations in both fragments.

## Assignment 3
### Description
In this assignment you'll be fetching data from the Internet to be presented in an UI. To
prevent network operations to lock up the UI thread, all networking must be performed on
background threads.
You'll be making HTTP requests to GitHub and the online movie platform Trakt and
handling the responses.
### The AsyncTask
We suggest you use the AsyncTask for your background operations. We also recommend
that you use HttpURLConnection to make requests to the APIs.
You should show a loading indicator (ProgressBar) every time a background task is
running. Also give the app Internet permission in the manifest.
#### For Pass (G)
When the user clicks the floating action button, a quote String is downloaded from GitHub.
Meanwhile a horizontal loading indicator is shown (at the top). When the operation is
complete, the text is added to a listview.
#### For Pass with Distinction (VG)
First do the ZenFragment described above, then recreate the movie fragment from last
weeks assignment. This time you download all movie information as JSON using Trakt.
Deserialize the returned JSON and create Movie objects. You only need the title, year and
poster thumbnail. The adapter should have its own AsyncTask to download images in the
getView method.

### Requirements
#### Pass (G)
* One fragment with floating action button.
* ListView with icons.
* When user clicks button, download a quotefrom url and present in ListView.
* Show horizontal loading indicator while background tasks are running.
* Follow the Material Design guidelines.

#### Pass with Distinction (VG)
* Do the ZenFragment so that it fulfills the requirements for Pass (G).
* Recreate the MoviesFragment from week before. Should look similar to the screenshot in this document.
* All movie information should be downloaded from Trakt as JSON.
* Show horizontal loading indicator while background tasks are running.
* Follow the Material Design guidelines.

## Assignment 4 -Treasure Hunt

### Description
The idea is a modern treasure hunt, you have to visit a number of different locations (at least 4), and navigate to them with the help of Google Maps.
### Requirements
#### Pass (G)
• When you visit a location, this happens: A dialog is presented with a question and three alternative answers + the phone vibrates + sound/music is played.
#### Pass with Distinction (VG) you also have to:
• Only next location is reveled so the player has to visit the locations in a specific order.
• Use a customized location icon.
• Have different questions with alternatives for all locations (if you answer wrong you can’t continue).
• Well-structured code following code conventions.

## Assignment 5
### Description
In this assignment you'll be working menus: the Options Menu and the
Contextual Action Mode. You can also add navigation with tabs if you want.
### Requirements
#### Pass (G)
Use either the quote fragment or the movies fragment from assignment 3. You
should aim for something similar to this:
* [MoviesFragment] (https://www.youtube.com/watch?v=nABlEQ5Zb0o&feature=youtu.be)
* [ZenFragment] (https://www.youtube.com/watch?v=MSly4dfNk8w&feature=youtu.be)

#### For VG (Pass with Distinction)
[Tabs with viewPager] (https://www.youtube.com/watch?v=PDBSchA_LSw&feature=youtu.be)

First do the G assignment.
Have two tabs and use a viewpager so that you can swipe between the tabs as
well. One of the two fragments should be either the movies fragment or the
quotes fragment and the other one could be a fragment of your choice, but in
my case I have both.

