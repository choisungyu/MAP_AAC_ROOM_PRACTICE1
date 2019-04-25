package com.csg.map_aac_room_practice;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.csg.map_aac_room_practice.models.Favorite;

public class MapInfoFragment extends DialogFragment {

    private static final String KEY_FAVORITE = "favorite";
    private Favorite mFavorite;

    public static MapInfoFragment newInstance(Favorite favorite) {


        MapInfoFragment fragment = new MapInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_FAVORITE, favorite);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFavorite = (Favorite) getArguments().getSerializable(KEY_FAVORITE);
        } else {
            throw new IllegalArgumentException("favorite값 필수");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.infowindow, (ViewGroup) getView(), false);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext()).setView(view);

        TextView nameTextView = view.findViewById(R.id.name_text);
        TextView addressTextView = view.findViewById(R.id.address_text);
        TextView latlngTextView = view.findViewById(R.id.latlng_text);
        EditText memoTextView = view.findViewById(R.id.memo_edit);

        nameTextView.setText(mFavorite.getName());
        addressTextView.setText(mFavorite.getAddress());
        latlngTextView.setText(mFavorite.getLatitude() + ", " + mFavorite.getLongitude());
        memoTextView.setText(mFavorite.getMemo());

        return builder.create();


    }
}
