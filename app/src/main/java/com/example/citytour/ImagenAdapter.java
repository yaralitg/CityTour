package com.example.citytour;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// Adaptador sencillo para mostrar las imagenes en el carrusel
public class ImagenAdapter extends RecyclerView.Adapter<ImagenAdapter.ImagenViewHolder> {

    private int[] imagenes;

    public ImagenAdapter(int[] imagenes) {
        this.imagenes = imagenes;
    }

    @NonNull
    @Override
    public ImagenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_imagen, parent, false);
        return new ImagenViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagenViewHolder holder, int position) {
        holder.imagen.setImageResource(imagenes[position]);
    }

    @Override
    public int getItemCount() {
        return imagenes.length;
    }

    public static class ImagenViewHolder extends RecyclerView.ViewHolder {
        ImageView imagen;

        public ImagenViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imagenCarrusel);
        }
    }
}
