package com.example.app_nativa;


import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public AdapterMasterDetail(ArrayList<PlaceholderContent.PlaceholderItem> listaNoticias) {
        this.listaNoticias = listaNoticias;
        this.listaAuxiliar= new ArrayList<>();
        this.listaAuxiliar.addAll(this.listaNoticias);

    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        TextView title,description,published_at, author;
        ImageView image;
        LinearLayout card;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.description);
            image = view.findViewById(R.id.image);
            published_at = view.findViewById(R.id.published_at);
            author = view.findViewById(R.id.author);
            card = view.findViewById(R.id.item_recycler);
            card.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
            ComponentName cn = am.getRunningTasks(1).get(0).topActivity;

            String className=cn.getClassName();
            if (!(className.equals("com.example.app_nativa.FavouritesActivity"))){
                menu.add(getBindingAdapterPosition(),101,0,R.string.favourite_context_menu);
                menu.add(getBindingAdapterPosition(),102,0,R.string.share_context_menu);
            }
            else{
                menu.add(getBindingAdapterPosition(),102,0,R.string.share_context_menu);
                menu.add(getBindingAdapterPosition(),103,0,R.string.remove_favourite_context_menu);
            }
        }
    }


    // El layout manager iwnvoca este método
    // para renderizar cada elemento del RecyclerView
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_list_noticias, viewGroup, false);

            view.setOnClickListener(this);
            return new ViewHolder(view);

    }

    // Este método asigna valores para cada elemento de la lista
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        if (viewHolder.title !=null){
        viewHolder.title.setText(listaNoticias.get(position).getTitle());
        viewHolder.description.setText(listaNoticias.get(position).getDescription());
        viewHolder.author.setText(listaNoticias.get(position).getAuthor());
        Glide.with(context).load(listaNoticias.get(position).getImage()).into(viewHolder.image);


            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = null;
            String result = null;

            try {
                date = inputFormat.parse(listaNoticias.get(position).getPublished_at());
                result = outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        viewHolder.published_at.setText(result);
        }

    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        context = recyclerView.getContext();
    }
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
