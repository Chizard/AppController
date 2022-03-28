package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.Album;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;
import se.michaelthelin.spotify.model_objects.specification.User;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.data.albums.GetAlbumRequest;
import se.michaelthelin.spotify.requests.data.albums.GetAlbumsTracksRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetUsersProfileRequest;

import java.io.IOException;
import java.net.URI;
import java.text.ParseException;

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
            .scope("user-read-email,user-read-private")
//          .show_dialog(true)
            .build();

    public static void authorizationCodeUri_Sync() {
        final URI uri = authorizationCodeUriRequest.execute();
        System.out.println("URI: " + uri.toString());
    }

    @RequestMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/redir")
    public String redir(@RequestParam(name = "code", required = false) String code, Model model) {
        model.addAttribute("code", code);

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

