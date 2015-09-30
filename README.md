# InstagramClient

Application that uses Instagram API to fetch current popular photos and display them in a read-only version to the user. Created in Android Studio (CodePath assignment)

![alt tag] (https://github.com/davidlevitsky/InstagramClient/blob/master/InstagramClient.gif)

Application features:

1. Utilizes infinitely scrolling list to display photos
2. Displays username, time posted, number of likes
3. Contains profile picture of the user account that posted  
4. Implements pull-to-refresh for popular stream with colorful loading bar
5. Maintains aspect ratio of Instagram photos
6. Uses custom launcher icon 


Implementation details:

1. Custom library RoundedImageView creates circular image view for profile pictures
2. Android Async and Picasso libraries load photos
3. SwipeRefreshLayout allows user to swipe down to refresh photo stream
