package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.miscellaneous.CurrentlyPlaying;
import se.michaelthelin.spotify.model_objects.specification.Album;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;
import se.michaelthelin.spotify.model_objects.specification.User;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.data.albums.GetAlbumRequest;
import se.michaelthelin.spotify.requests.data.albums.GetAlbumsTracksRequest;
import se.michaelthelin.spotify.requests.data.player.*;
import se.michaelthelin.spotify.requests.data.users_profile.GetUsersProfileRequest;

import java.io.IOException;
import java.net.URI;
import java.util.*;

@org.springframework.stereotype.Controller
public class PageController {

    static final String clientId = "89a4bb620ad848989b787b700f508fe3";
    static final String clientSecret = "f37b655edd98488b9a7185fd0eab7036";
    static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8888/redir");
    static final String code = " ";
    static final String id = "6lQM0ttt55S8PtXkFHVURB";
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

    public static void authorizationCodeUri_Sync() {
        final URI uri = authorizationCodeUriRequest.execute();
        System.out.println("URI: " + uri.toString());
    }

    private static final GetUsersCurrentlyPlayingTrackRequest getUsersCurrentlyPlayingTrackRequest = spotifyApi
            .getUsersCurrentlyPlayingTrack()
//          .market(CountryCode.SE)
//          .additionalTypes("track,episode")
            .build();

    @RequestMapping("/user")
    public String user() {
        return "user";
    }

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

    @GetMapping("/currentsong")
    public void CurrentSong(){

        try {
            final CurrentlyPlaying currentlyPlaying = spotifyApi.getUsersCurrentlyPlayingTrack().build().execute();

            long minutes = (currentlyPlaying.getProgress_ms() / 1000) / 60;
            long seconds = (currentlyPlaying.getProgress_ms() / 1000) % 60;
            long DurationMinutes = (currentlyPlaying.getItem().getDurationMs() / 1000) / 60;
            long DurationSeconds = (currentlyPlaying.getItem().getDurationMs() / 1000) % 60;

            System.out.println(currentlyPlaying.getItem().getName());
            System.out.println(minutes + ":" + seconds + " / " + DurationMinutes + ":" + DurationSeconds);
        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
            System.out.println("ErrorSong: " + e.getMessage());
        }
    }

    @GetMapping("/redir")
    public String redir(@RequestParam(name = "code", required = false) String code, Model model) {
        model.addAttribute("code", code);
        //System.out.println("check");

        final AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code)
                .build();
        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
            System.out.println("Code: " + code);
            System.out.println("AccessToken: " + spotifyApi.getAccessToken());
            System.out.println("RefreshToken: " + spotifyApi.getRefreshToken());

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

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

            return "redir";
        }
}

