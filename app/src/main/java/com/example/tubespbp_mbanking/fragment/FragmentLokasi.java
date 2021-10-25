package com.example.tubespbp_mbanking.fragment;

import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tubespbp_mbanking.R;
import com.example.tubespbp_mbanking.databinding.FragmentLokasiBinding;
import com.example.tubespbp_mbanking.model.User;
import com.example.tubespbp_mbanking.preferences.UserPreferences;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.OnSymbolDragListener;
import com.mapbox.mapboxsdk.plugins.annotation.OnSymbolLongClickListener;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;
import com.mapbox.mapboxsdk.plugins.annotation.Symbol;
import com.mapbox.mapboxsdk.plugins.annotation.OnSymbolClickListener;


import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentLokasi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLokasi extends Fragment implements OnMapReadyCallback, PermissionsListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private User userLogin;
    private FragmentLokasiBinding binding;
    private UserPreferences userPreferences;
    private List<User> userList;

    //Mapbox
    private PermissionsManager permissionsManager;
    private MapboxMap mapboxMap;
    private MapView mapView;
    private LocationEngine locationEngine;
    private Location originLocation;
    private Point originPosition;
    private Point destinationPosition;
    private Marker destinationMarker;
    private Button startButton;
    private static final String MAKI_ICON_CAFE = "cafe-15";
    private static final String MAKI_ICON_HARBOR = "harbor-15";
    private static final String MAKI_ICON_AIRPORT = "airport-15";
    private static final String MAKI_ICON_BANK = "bank-15";
    private SymbolManager symbolManager;
    private Symbol symbol1, symbol2, symbol3;

    private static final String TAG = "FragmentLokasi";

    public FragmentLokasi() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentLokasi.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentLokasi newInstance(String param1, String param2) {
        FragmentLokasi fragment = new FragmentLokasi();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Mapbox.getInstance(getActivity(), getString(R.string.mapbox_access_token));
        binding = FragmentLokasiBinding.inflate(inflater, container, false);
        binding.setFragment(this);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userPreferences = new UserPreferences(getActivity());

        userLogin = userPreferences.getUserLogin();

        mapView = binding.mapView;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        EditText etAlamat = binding.etAlamat.getEditText();
        etAlamat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if("JL. Rambutan No.111".toLowerCase().contains(etAlamat.getText().toString())) {
                    setInfoAtm("ATM Cabang Rambutan",
                            "JL. Rambutan No.111",
                            "10.0 KM");
                } else if("JL. Jambu No.456".toLowerCase().contains(etAlamat.getText().toString())) {
                    setInfoAtm("ATM Cabang Jambu",
                            "JL. Jambu No.456",
                            "5.0 KM");
                } else if("JL. Apel No.123".toLowerCase().contains(etAlamat.getText().toString())) {
                    setInfoAtm("ATM Cabang Apel",
                            "JL. Apel No.123",
                            "4.0 KM");
                }
            }
        });
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(getActivity(), R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(this.getContext(), R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;

        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                enableLocationComponent(style);

                // Set up a SymbolManager instance
                symbolManager = new SymbolManager(mapView, mapboxMap, style);

                symbolManager.setIconAllowOverlap(true);
                symbolManager.setTextAllowOverlap(true);

// Add symbol at specified lat/lon
                //Mountain View
                symbol1 = symbolManager.create(new SymbolOptions()
                        .withLatLng(new LatLng(37.352147, -122.041437))
                        .withIconImage(MAKI_ICON_BANK)
                        .withIconSize(2.0f)
                        .withDraggable(false));

                symbol2 = symbolManager.create(new SymbolOptions()
                        .withLatLng(new LatLng(37.382434, -122.064757))
                        .withIconImage(MAKI_ICON_BANK)
                        .withIconSize(2.0f)
                        .withDraggable(false));

                symbol3 = symbolManager.create(new SymbolOptions()
                        .withLatLng(new LatLng(37.409710, -122.040066))
                        .withIconImage(MAKI_ICON_BANK)
                        .withIconSize(2.0f)
                        .withDraggable(false));

                //Jogja
//                symbol1 = symbolManager.create(new SymbolOptions()
//                        .withLatLng(new LatLng(-7.766118, 110.378641))
//                        .withIconImage(MAKI_ICON_BANK)
//                        .withIconSize(2.0f)
//                        .withDraggable(false));
//
//                symbol2 = symbolManager.create(new SymbolOptions()
//                        .withLatLng(new LatLng(-7.782970, 110.404187))
//                        .withIconImage(MAKI_ICON_BANK)
//                        .withIconSize(2.0f)
//                        .withDraggable(false));
//
//                symbol3 = symbolManager.create(new SymbolOptions()
//                        .withLatLng(new LatLng(-7.777768, 110.415547))
//                        .withIconImage(MAKI_ICON_BANK)
//                        .withIconSize(2.0f)
//                        .withDraggable(false));

                //Bangka
//                symbol1 = symbolManager.create(new SymbolOptions()
//                        .withLatLng(new LatLng(-1.857493, 106.117985))
//                        .withIconImage(MAKI_ICON_BANK)
//                        .withIconSize(2.0f)
//                        .withDraggable(false));
//
//                symbol2 = symbolManager.create(new SymbolOptions()
//                        .withLatLng(new LatLng(-1.858797, 106.119752))
//                        .withIconImage(MAKI_ICON_BANK)
//                        .withIconSize(2.0f)
//                        .withDraggable(false));
//
//                symbol3 = symbolManager.create(new SymbolOptions()
//                        .withLatLng(new LatLng(-1.860094, 106.117399))
//                        .withIconImage(MAKI_ICON_BANK)
//                        .withIconSize(2.0f)
//                        .withDraggable(false));

// Add click listener and change the symbol to a cafe icon on click
                symbolManager.addClickListener(new OnSymbolClickListener() {
                    @Override
                    public boolean onAnnotationClick(Symbol symbol) {
                        if(symbol == symbol1) {
                            setInfoAtm("ATM Cabang Rambutan",
                                    "JL. Rambutan No.111",
                                    "10.0 KM");
                        } else if(symbol == symbol2) {
                            setInfoAtm("ATM Cabang Jambu",
                                    "JL. Jambu No.456",
                                    "5.0 KM");
                        } else if(symbol == symbol3) {
                            setInfoAtm("ATM Cabang Apel",
                                    "JL. Apel No.123",
                                    "4.0 KM");
                        }
//                        symbol.setIconImage(MAKI_ICON_CAFE);
//                        symbolManager.update(symbol);
                        return false;
                    }
                });

// Add long click listener and change the symbol to an airport icon on long click
                symbolManager.addLongClickListener((new OnSymbolLongClickListener() {
                    @Override
                    public boolean onAnnotationLongClick(Symbol symbol) {
//                        Toast.makeText(FragmentLokasi.this.getContext(),
//                                "annotation long click", Toast.LENGTH_SHORT).show();
//                        symbol.setIconImage(MAKI_ICON_AIRPORT);
//                        symbolManager.update(symbol);
                        return false;
                    }
                }));


//                Toast.makeText(FragmentLokasi.this.getContext(),
//                        "instruction", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    @SuppressWarnings( {"MissingPermission"})
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {

        if (PermissionsManager.areLocationPermissionsGranted(this.getContext())) {


            LocationComponentOptions customLocationComponentOptions = LocationComponentOptions.builder(this.getContext())
                    .pulseEnabled(true)
                    .build();


            LocationComponent locationComponent = mapboxMap.getLocationComponent();


            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(this.getContext(), loadedMapStyle)
                            .locationComponentOptions(customLocationComponentOptions)
                            .build());


            locationComponent.setLocationComponentEnabled(true);


            locationComponent.setCameraMode(CameraMode.TRACKING);


            locationComponent.setRenderMode(RenderMode.NORMAL);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(getActivity());
        }
    }

    private void setInfoAtm(String namaAtm, String alamat, String jarak) {
        binding.txtCabang.setText(namaAtm);
        binding.txtAlamat.setText(alamat);
        binding.txtJarak.setText(jarak);
    }


}