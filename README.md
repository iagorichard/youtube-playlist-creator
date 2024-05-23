# Playlist Creator for YouTube
This repository uses the YouTube API to create playlists based on a set of musics from text file.

# Stacks
- Java 17 with Spring Boot

# Steps to run
1. Run the Spring Boot application "DemoApplication.java" located at package com.irrs.youtube.playlist.demo
2. Open a HTTP client (JS fetch, Postman, etc.) and use the following params in a POST request:
- "YTAPIKey" : The oauth token provided by the Google Cloud.
- "file" : The .txt file containing the musics to make the playlist (the file "assets/musics.txt" provides an example on how to set the musics.
- "name" : The name of the playlist.
- "description" : The description of the playlist.

# Status
Development in progress.

# TODO
- [X] To make the privacy policy page to grant access to oauth token
- [ ] To wait for the Google Cloud's authorization
- [ ] Test the application