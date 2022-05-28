package com.example.demo;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.miscellaneous.CurrentlyPlaying;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.data.player.PauseUsersPlaybackRequest;
import se.michaelthelin.spotify.requests.data.player.SkipUsersPlaybackToNextTrackRequest;
import se.michaelthelin.spotify.requests.data.player.SkipUsersPlaybackToPreviousTrackRequest;
import se.michaelthelin.spotify.requests.data.player.StartResumeUsersPlaybackRequest;

import java.io.IOException;
import java.net.URI;


@org.springframework.stereotype.Controller
public class PageController {

    static final String clientId = "89a4bb620ad848989b787b700f508fe3";
    static final String clientSecret = "f37b655edd98488b9a7185fd0eab7036";
    static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8888/login");
    static final String code = " ";
    static final String id = "6lQM0ttt55S8PtXkFHVURB";
    static final String artistId = "6fOMl44jA4Sp5b9PpYCkzz";
    static final String userId = "ls8leqsl3wi0qibqp8hrcomzf";


    static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();


    static final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
//          .state("x4xkmn9pu3j6ukrs8n")
            .scope("user-read-email,user-read-private,user-modify-playback-state,user-read-currently-playing,user-read-playback-state")
//          .show_dialog(true)
            .build();

    public static void authorizationCodeUri_Sync() {
        final URI uri = authorizationCodeUriRequest.execute();
        System.out.println("SpotifyURI: " + uri.toString());
        System.out.println("DiscordURI: " + "https://discord.com/api/oauth2/authorize?client_id=971347051902291988&redirect_uri=http%3A%2F%2Flocalhost%3A8888%2FdiscRedir&response_type=code&scope=email%20identify");
    }

    private static final StartResumeUsersPlaybackRequest startResumeUsersPlaybackRequest = spotifyApi
            .startResumeUsersPlayback()
//          .context_uri("spotify:album:5zT1JLIj9E57p3e1rFm9Uq")
            .device_id("1b6c9a4b4760cb26b8aecf9b93bd66193dd75217")
//          .offset(JsonParser.parseString("{\"uri\":\"spotify:track:01iyCAUm8EvOFqVWYJ3dVX\"}").getAsJsonObject())
//          .uris(JsonParser.parseString("[\"spotify:track:01iyCAUm8EvOFqVWYJ3dVX\"]").getAsJsonArray())
//          .position_ms(10000)
            .build();

    private static final PauseUsersPlaybackRequest pauseUsersPlaybackRequest = spotifyApi.pauseUsersPlayback()
            .device_id("1b6c9a4b4760cb26b8aecf9b93bd66193dd75217")
            .build();

    private static final SkipUsersPlaybackToPreviousTrackRequest skipUsersPlaybackToPreviousTrackRequest = spotifyApi
            .skipUsersPlaybackToPreviousTrack()
            .device_id("1b6c9a4b4760cb26b8aecf9b93bd66193dd75217")
            .build();

    private static final SkipUsersPlaybackToNextTrackRequest skipUsersPlaybackToNextTrackRequest = spotifyApi
            .skipUsersPlaybackToNextTrack()
            .device_id("1b6c9a4b4760cb26b8aecf9b93bd66193dd75217")
            .build();




    @RequestMapping("/redir")
    public void redir() {

    }
/*
    @RequestMapping("/login")
    public void login() {

    }
*/
    @PostMapping("/play")
    public void play(){
        try {
            final String string = spotifyApi
                    .startResumeUsersPlayback().build().execute();

            //System.out.println("Null: " + string);
        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @PostMapping("/pause")
    public void pause(){
        try {
            final String string = spotifyApi.pauseUsersPlayback().build().execute();

            //System.out.println("Null: " + string);
        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @PostMapping("/previous")
    public void previous() {
        try {
            final String string = spotifyApi.skipUsersPlaybackToPreviousTrack().build().execute();

            //System.out.println("Null: " + string);
        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @PostMapping("/next")
    public void next() {
        try {
            final String string = spotifyApi.skipUsersPlaybackToNextTrack().build().execute();

            //System.out.println("Null: " + string);
        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @RequestMapping(value="/currentduration", headers = "Accept=application/json")
    @ResponseBody
    public String CurrentDuration(){

        try {
            final CurrentlyPlaying currentlyPlaying = spotifyApi.getUsersCurrentlyPlayingTrack().build().execute();

            long DurationMinutes = (currentlyPlaying.getItem().getDurationMs() / 1000) / 60;
            long DurationSeconds = (currentlyPlaying.getItem().getDurationMs() / 1000) % 60;
            String duration = (DurationMinutes > 9 ? String.valueOf(DurationMinutes) : "0" + DurationMinutes) + ":" +
                    (DurationSeconds > 9 ? String.valueOf(DurationSeconds) : "0" + DurationSeconds);
            //System.out.println(minutes + ":" + seconds + " / " + DurationMinutes + ":" + DurationSeconds);
            return "{ \"duration\":\"" + duration + "\"}";
        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
            return "{ \"duration\":\" no access token  \"}";
        }
    }

    @RequestMapping(value="/currenttime", headers = "Accept=application/json")
    @ResponseBody
    public String CurrentTime(){

        try {
            final CurrentlyPlaying currentlyPlaying = spotifyApi.getUsersCurrentlyPlayingTrack().build().execute();
            long minutes = (currentlyPlaying.getProgress_ms() / 1000) / 60;
            long seconds = (currentlyPlaying.getProgress_ms() / 1000) % 60;
            long DurationMinutes = (currentlyPlaying.getItem().getDurationMs() / 1000) / 60;
            long DurationSeconds = (currentlyPlaying.getItem().getDurationMs() / 1000) % 60;
            String timer = (minutes > 9 ? String.valueOf(minutes) : "0" + minutes) + ":" +
                    (seconds > 9 ? String.valueOf(seconds) : "0" + seconds);
            //System.out.println(minutes + ":" + seconds + " / " + DurationMinutes + ":" + DurationSeconds);
            return "{ \"time\":\"" + timer + "\"}";
        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
            return "{ \"time\":\" no access token  \"}";
        }
    }


    @RequestMapping(value="/currentsong", headers = "Accept=application/json")
    @ResponseBody
    public String CurrentSong(){

        try {
            final CurrentlyPlaying currentlyPlaying = spotifyApi.getUsersCurrentlyPlayingTrack().build().execute();
           // System.out.println(currentlyPlaying.getItem().getName());
            return "{ \"name\":\"" + currentlyPlaying.getItem().getName() + "\"}";
        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
           // System.out.println("ErrorSong: " + e.getMessage());
            return "{ \"name\":\" no access token  \"}";
        }
    }

    @RequestMapping(value="/currentartist", headers = "Accept=application/json")
    @ResponseBody
    public void CurrentArtist() {

    }

    @GetMapping("/spotify")
    public void Spotify(){

    }

    @GetMapping("/discord")
    public void Discord(){

    }

    @GetMapping("/discRedir")
    public void discRedir(@RequestParam(name = "code", required = false) String code, Model model) {
        model.addAttribute("code", code);

        System.out.println("DiscordCode: " + code);
    }

    @GetMapping("/login")
    public String login(@RequestParam(name = "code", required = false) String code, Model model) {
        model.addAttribute("code", code);

        final AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code)
                .build();
        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
            System.out.println("SpotifyCode: " + code);
            System.out.println("AccessToken: " + spotifyApi.getAccessToken());
            System.out.println("RefreshToken: " + spotifyApi.getRefreshToken());

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn() + " seconds");
        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

        /*
            //Get name of Album
        final GetAlbumRequest getAlbumRequest = spotifyApi.getAlbum(id)
//          .market(CountryCode.SE)
                .build();

            try {
                final Album album = getAlbumRequest.execute();

                System.out.println("Name: " + album.getName());
            } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
                System.out.println("Error: " + e.getMessage());
            }

            //Number of tracks in album
        final GetAlbumsTracksRequest getAlbumsTracksRequest = spotifyApi.getAlbumsTracks(id)
//          .limit(10)
//          .offset(0)
//          .market(CountryCode.SE)
                .build();

            try {
                final Paging<TrackSimplified> trackSimplifiedPaging = getAlbumsTracksRequest.execute();

                System.out.println("Total: " + trackSimplifiedPaging.getTotal());
            } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
                System.out.println("Error: " + e.getMessage());
            }

            //User profile name
        final GetUsersProfileRequest getUsersProfileRequest = spotifyApi.getUsersProfile(userId)
                .build();

            try {
                final User user = getUsersProfileRequest.execute();

                System.out.println("Display name: " + user.getDisplayName());
            } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
                System.out.println("Error: " + e.getMessage());
            }
            */

            return "login";
        }

    }


