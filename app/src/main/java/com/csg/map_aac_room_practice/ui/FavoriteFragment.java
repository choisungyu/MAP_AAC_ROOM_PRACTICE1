package com.csg.map_aac_room_practice.ui;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csg.map_aac_room_practice.R;
import com.csg.map_aac_room_practice.databinding.ItemFavoriteBinding;
import com.csg.map_aac_room_practice.models.Favorite;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {


    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        FavoriteAdapter adapter = new FavoriteAdapter();

        recyclerView.setAdapter(adapter);

        List<Favorite> items = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            items.add(new Favorite());
        }

        adapter.setItems(items);


    }

    private static class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
        interface OnFavoriteClickListener {
            void setOnClickListener(Favorite model);
        }

        private OnFavoriteClickListener mListener;

        private List<Favorite> mItems = new ArrayList<>();

        public FavoriteAdapter() {
        }

        public FavoriteAdapter(OnFavoriteClickListener listener) {
            mListener = listener;
        }

        public void setItems(List<Favorite> items) {
            this.mItems = items;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_favorite, parent, false);
            final FavoriteViewHolder viewHolder = new FavoriteViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        final Favorite item = mItems.get(viewHolder.getAdapterPosition());
                        mListener.setOnClickListener(item);
                    }
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
            Favorite item = mItems.get(position);
            // TODO : 데이터를 뷰홀더에 표시하시오
            holder.binding.setFavorite(item);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
            // TODO : 뷰홀더 완성하시오
            ItemFavoriteBinding binding;


            public FavoriteViewHolder(@NonNull View itemView) {
                super(itemView);
                // TODO : 뷰홀더 완성하시오
                binding = ItemFavoriteBinding.bind(itemView);
            }
        }
    }

}
