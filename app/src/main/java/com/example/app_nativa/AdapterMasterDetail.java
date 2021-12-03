package com.example.app_nativa;

import android.content.Context;
import android.os.Build;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_nativa.placeholder.PlaceholderContent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdapterMasterDetail extends RecyclerView.Adapter<AdapterMasterDetail.ViewHolder>
 implements View.OnClickListener{

    Context context;

    ArrayList<PlaceholderContent.PlaceholderItem> listaNoticias;
    ArrayList<PlaceholderContent.PlaceholderItem> listaAuxiliar;
    private View.OnClickListener listener;

    // Este es nuestro constructor (puede variar según lo que queremos mostrar)
    public AdapterMasterDetail(ArrayList<PlaceholderContent.PlaceholderItem> listaNoticias) {
        this.listaNoticias = listaNoticias;
        this.listaAuxiliar= new ArrayList<>();
        this.listaAuxiliar.addAll(this.listaNoticias);
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        TextView title,description;
        ImageView image;
        LinearLayout card;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
            image = (ImageView) view.findViewById(R.id.image);
            card = view.findViewById(R.id.item_recycler);
            card.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(getBindingAdapterPosition(),101,0,"Add to favourites");
            menu.add(getBindingAdapterPosition(),102,0,"Share new");
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
        Glide.with(context).load(listaNoticias.get(position).getImage()).into(viewHolder.image);
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        context = recyclerView.getContext();
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

    public PlaceholderContent.PlaceholderItem getItemByPosition(int position)
    {
       return listaNoticias.get(position);

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void filter(String textSearch)
    {
        int length = textSearch.length();

        if(length == 0)
        {
            listaNoticias.clear();
            listaNoticias.addAll(listaAuxiliar);

        }
        else {
            List<PlaceholderContent.PlaceholderItem> collection = listaNoticias.stream().filter(i -> i.getTitle().toLowerCase().contains(textSearch.toLowerCase())).collect(Collectors.toList());
            listaNoticias.clear();
            listaNoticias.addAll(collection);

        }
        notifyDataSetChanged();
    }

}
