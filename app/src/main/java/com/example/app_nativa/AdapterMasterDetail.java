package com.example.app_nativa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_nativa.placeholder.PlaceholderContent;

import java.util.ArrayList;

public class AdapterMasterDetail extends RecyclerView.Adapter<AdapterMasterDetail.ViewHolder>
 implements View.OnClickListener{

    ArrayList<PlaceholderContent.PlaceholderItem> listaNoticias;
    private View.OnClickListener listener;

    // Este es nuestro constructor (puede variar según lo que queremos mostrar)
    public AdapterMasterDetail(ArrayList<PlaceholderContent.PlaceholderItem> listaNoticias) {
        this.listaNoticias = listaNoticias;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title,description;
        ImageView image;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
            image = (ImageView) view.findViewById(R.id.image);
        }


    }



    // El layout manager invoca este método
    // para renderizar cada elemento del RecyclerView
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list_noticias, viewGroup, false);

        view.setOnClickListener(this);
        return new ViewHolder(view);
    }


    // Este método asigna valores para cada elemento de la lista
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        viewHolder.title.setText(listaNoticias.get(position).getTitle());
        viewHolder.description.setText(listaNoticias.get(position).getDescription());
        //viewHolder.image.setImage(listaNoticias.get(position).getImage());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listaNoticias.size();
    }

    @Override
    public void onClick(View v) {

        if (listener!= null){
            listener.onClick(v);
        }
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }
}
