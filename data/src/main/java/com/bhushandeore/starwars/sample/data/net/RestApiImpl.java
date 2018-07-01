package com.bhushandeore.starwars.sample.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.bhushandeore.starwars.sample.data.entity.PeopleDataResponse;
import com.bhushandeore.starwars.sample.data.entity.PeopleEntity;
import com.bhushandeore.starwars.sample.data.entity.mapper.PeopleEntityJsonMapper;
import com.bhushandeore.starwars.sample.data.exception.NetworkConnectionException;
import com.google.gson.GsonBuilder;

import java.net.MalformedURLException;
import java.util.List;

import io.reactivex.Observable;

/**
 * {@link RestApi} implementation for retrieving data from the network.
 */
public class RestApiImpl implements RestApi {

    private final Context context;
    private final PeopleEntityJsonMapper peopleEntityJsonMapper;

    /**
     * Constructor of the class
     *
     * @param context                {@link Context}.
     * @param peopleEntityJsonMapper {@link PeopleEntityJsonMapper}.
     */
    public RestApiImpl(Context context, PeopleEntityJsonMapper peopleEntityJsonMapper) {
        if (context == null || peopleEntityJsonMapper == null) {
            throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
        }
        this.context = context.getApplicationContext();
        this.peopleEntityJsonMapper = peopleEntityJsonMapper;
    }

    @Override
    public Observable<List<PeopleEntity>> peopleEntityList(final int page) {
        return Observable.create(emitter -> {
            if (isThereInternetConnection()) {
                try {
                    String responsePeopleEntities = getPeopleEntitiesFromApi(page);
                    if (responsePeopleEntities != null) {
                        System.out.println("People List API Response ==>> \n " + new GsonBuilder().setPrettyPrinting().create().toJson(responsePeopleEntities));

                        PeopleDataResponse peopleDataResponse = peopleEntityJsonMapper.transformPeopleDataResponse(
                                responsePeopleEntities);
                        emitter.onNext(peopleDataResponse.results);
                        emitter.onComplete();
                    } else {
                        emitter.onError(new NetworkConnectionException());
                    }
                } catch (Exception e) {
                    emitter.onError(new NetworkConnectionException(e.getCause()));
                }
            } else {
                emitter.onError(new NetworkConnectionException());
            }
        });
    }

    @Override
    public Observable<PeopleEntity> peopleEntityById(final int id) {
        return Observable.create(emitter -> {
            if (isThereInternetConnection()) {
                try {
                    String responsePeopleDetails = getPeopleDetailsFromApi(id);
                    if (responsePeopleDetails != null) {
                        System.out.println("People Details API Response ==>> \n" + new GsonBuilder().setPrettyPrinting().create().toJson(responsePeopleDetails));
                        emitter.onNext(peopleEntityJsonMapper.transformPeopleEntity(responsePeopleDetails));
                        emitter.onComplete();
                    } else {
                        emitter.onError(new NetworkConnectionException());
                    }
                } catch (Exception e) {
                    emitter.onError(new NetworkConnectionException(e.getCause()));
                }
            } else {
                emitter.onError(new NetworkConnectionException());
            }
        });
    }

    private String getPeopleEntitiesFromApi(int page) throws MalformedURLException {
        String apiUrl = API_URL_GET_PEOPLE_LIST + page;
        return ApiConnection.createGET(apiUrl).requestSyncCall();
    }

    private String getPeopleDetailsFromApi(int id) throws MalformedURLException {
        String apiUrl = API_URL_GET_PEOPLE_DETAILS + id;
        return ApiConnection.createGET(apiUrl).requestSyncCall();
    }

    /**
     * Checks if the device has any active internet connection.
     *
     * @return true device with internet connection, otherwise false.
     */
    private boolean isThereInternetConnection() {
        boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }
}
