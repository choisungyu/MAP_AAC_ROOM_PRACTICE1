package com.csg.map_aac_room_practice.ui;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.csg.map_aac_room_practice.MapInfoFragment;
import com.csg.map_aac_room_practice.R;
import com.csg.map_aac_room_practice.databinding.InfowindowBinding;
import com.csg.map_aac_room_practice.models.Favorite;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String TAG = MapFragment.class.getSimpleName();

    // Create a new Places client instance.
    private PlacesClient mPlacesClient;
    private Favorite mFavorite;


    public MapFragment() {
        // Required empty public constructor
    }

    // places api 초기화 할 때, 생성자에 넣을라고 했을 때 안되서 onAttach 만들고 여기다가 노놓은거 같음
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mPlacesClient = Places.createClient(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //----------autocompleteFragment
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.PHOTO_METADATAS,
                Place.Field.LAT_LNG,
                Place.Field.ADDRESS));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.d(TAG, "Place: " + place.toString());

                mFavorite = new Favorite();
                mFavorite.setAddress(place.getAddress());
                mFavorite.setName(place.getName());
                if (place.getLatLng() != null) {

                    mFavorite.setLatitude(place.getLatLng().latitude);
                    mFavorite.setLongitude(place.getLatLng().longitude);
                }


                LatLng selectedPlace = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
                MarkerOptions markerOptions = new MarkerOptions().position(selectedPlace);
//                        .snippet(place.getAddress())
//                        .title(place.getName());
//                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                mMap.addMarker(markerOptions);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(selectedPlace));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(selectedPlace, 15.0f));

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        //----------autocompleteFragment


    }

    // map 에서 customizing 하는 곳
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // DialogFragment
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                MapInfoFragment.newInstance(mFavorite).show(getChildFragmentManager(),"dialog");
                return false;
            }
        });
        
        // InfoWindow
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                // 이거 다시 보기
                View view = LayoutInflater.from(requireContext())
                        .inflate(R.layout.infowindow, (ViewGroup) getView(), false);

//                InfowindowBinding binding = InfowindowBinding.bind(view);
//                binding.setFavorite(mFavorite);
//                return binding.getRoot();
                TextView nameTextView = view.findViewById(R.id.name_text);
                TextView addressTextView = view.findViewById(R.id.address_text);
                TextView latlngTextView = view.findViewById(R.id.latlng_text);
                EditText memoTextView = view.findViewById(R.id.memo_edit);

                nameTextView.setText(mFavorite.getName());
                addressTextView.setText(mFavorite.getAddress());
                latlngTextView.setText(mFavorite.getLatitude() + ", " + mFavorite.getLongitude());
                memoTextView.setText(mFavorite.getMemo());

//                return view;
                view.findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(requireContext(), "asdasd", Toast.LENGTH_SHORT).show();

                    }
                });
                return null;
            }
        });


        // TODO : 현재위치로 이동
    }
}
