package com.example.demo;


import com.google.gson.JsonArray;
import com.neovisionaries.i18n.CountryCode;
import se.michaelthelin.spotify.IHttpManager;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.enums.ModelObjectType;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.pkce.AuthorizationCodePKCERefreshRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.pkce.AuthorizationCodePKCERequest;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import se.michaelthelin.spotify.requests.data.albums.GetAlbumRequest;
import se.michaelthelin.spotify.requests.data.albums.GetAlbumsTracksRequest;
import se.michaelthelin.spotify.requests.data.albums.GetSeveralAlbumsRequest;
import se.michaelthelin.spotify.requests.data.artists.*;
import se.michaelthelin.spotify.requests.data.browse.*;
import se.michaelthelin.spotify.requests.data.browse.miscellaneous.GetAvailableGenreSeedsRequest;
import se.michaelthelin.spotify.requests.data.episodes.GetEpisodeRequest;
import se.michaelthelin.spotify.requests.data.episodes.GetSeveralEpisodesRequest;
import se.michaelthelin.spotify.requests.data.follow.*;
import se.michaelthelin.spotify.requests.data.follow.legacy.FollowPlaylistRequest;
import se.michaelthelin.spotify.requests.data.follow.legacy.UnfollowPlaylistRequest;
import se.michaelthelin.spotify.requests.data.library.*;
import se.michaelthelin.spotify.requests.data.personalization.GetUsersTopArtistsAndTracksRequest;
import se.michaelthelin.spotify.requests.data.personalization.interfaces.IArtistTrackModelObject;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest;
import se.michaelthelin.spotify.requests.data.player.*;
import se.michaelthelin.spotify.requests.data.playlists.*;
import se.michaelthelin.spotify.requests.data.search.SearchItemRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.*;
import se.michaelthelin.spotify.requests.data.search.simplified.special.SearchAlbumsSpecialRequest;
import se.michaelthelin.spotify.requests.data.shows.GetSeveralShowsRequest;
import se.michaelthelin.spotify.requests.data.shows.GetShowRequest;
import se.michaelthelin.spotify.requests.data.shows.GetShowsEpisodesRequest;
import se.michaelthelin.spotify.requests.data.tracks.*;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetUsersProfileRequest;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Logger;

public class POJO {


    public static final String DEFAULT_AUTHENTICATION_HOST = "accounts.spotify.com";

    public static final int DEFAULT_AUTHENTICATION_PORT = 443;

    public static final String DEFAULT_AUTHENTICATION_SCHEME = "https";

    public static final String DEFAULT_HOST = "api.spotify.com";

    public static final IHttpManager DEFAULT_HTTP_MANAGER = new SpotifyHttpManager.Builder().build();

    public static final int DEFAULT_PORT = 443;

    public static final String DEFAULT_SCHEME = "https";

    public static final Logger LOGGER = Logger.getLogger(SpotifyApi.class.getName());


    private final IHttpManager httpManager;
    private final String scheme;
    private final String host;
    private final Integer port;
    private final String clientId;
    private final String clientSecret;
    private final URI redirectUri;
    private String accessToken;
    private String refreshToken;

    private POJO(Builder builder) {
        assert (builder.httpManager != null);

        this.httpManager = builder.httpManager;
        this.scheme = builder.scheme;
        this.host = builder.host;
        this.port = builder.port;
        //       this.proxyUrl = builder.proxyUrl;
        //       this.proxyPort = builder.proxyPort;
        //       this.proxyUsername = builder.proxyUsername;
        //       this.proxyPassword = builder.proxyPassword;
        this.clientId = builder.clientId;
        this.clientSecret = builder.clientSecret;
        this.redirectUri = builder.redirectUri;
        this.accessToken = builder.accessToken;
        this.refreshToken = builder.refreshToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    public String getRefreshToken() {
        return refreshToken;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public URI getRedirectUri() {
        return redirectUri;
    }
    public IHttpManager getHttpManager() {
        return httpManager;
    }

    public String getScheme() {
        return scheme;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }


    public AuthorizationCodeRefreshRequest.Builder authorizationCodeRefresh(String client_id, String client_secret, String refresh_token) {
        return new AuthorizationCodeRefreshRequest.Builder(client_id, client_secret)
                .setDefaults(httpManager, scheme, host, port)
                .grant_type("refresh_token")
                .refresh_token(refresh_token);
    }

    public AuthorizationCodeRefreshRequest.Builder authorizationCodeRefresh() {
        return new AuthorizationCodeRefreshRequest.Builder(clientId, clientSecret)
                .setDefaults(httpManager, scheme, host, port)
                .grant_type("refresh_token")
                .refresh_token(refreshToken);
    }

    public static class Builder {

        private IHttpManager httpManager = DEFAULT_HTTP_MANAGER;
        private String scheme = DEFAULT_SCHEME;
        private String host = DEFAULT_HOST;
        private Integer port = DEFAULT_PORT;
        private String proxyUrl;
        private Integer proxyPort;
        private Integer proxyUsername;
        private Integer proxyPassword;
        private String clientId;
        private String clientSecret;
        private URI redirectUri;
        private String accessToken;
        private String refreshToken;


        public Builder setHttpManager(IHttpManager httpManager) {
            this.httpManager = httpManager;
            return this;
        }

        public Builder setScheme(String scheme) {
            this.scheme = scheme;
            return this;
        }

        public Builder setHost(String host) {
            this.host = host;
            return this;
        }


        public Builder setPort(Integer port) {
            this.port = port;
            return this;
        }

        public Builder setProxyUrl(String proxyUrl) {
            this.proxyUrl = proxyUrl;
            return this;
        }

        public Builder setProxyPort(Integer proxyPort) {
            this.proxyPort = proxyPort;
            return this;
        }


        public Builder setProxyUsername(Integer proxyUsername) {
            this.proxyUsername = proxyUsername;
            return this;
        }


        public Builder setProxyPassword(Integer proxyPassword) {
            this.proxyPassword = proxyPassword;
            return this;
        }


        public Builder setClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }


        public Builder setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }


        public Builder setRedirectUri(URI redirectUri) {
            this.redirectUri = redirectUri;
            return this;
        }


        public Builder setAccessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }


        public Builder setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public POJO build() {
            return new POJO(this);
        }
    }

}

