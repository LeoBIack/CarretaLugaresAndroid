package br.usjt.ucsist.cadaluno.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.usjt.ucsist.cadaluno.R;
import br.usjt.ucsist.cadaluno.model.Local;
import br.usjt.ucsist.cadaluno.util.ImageUtil;

public class LocalAdapter extends RecyclerView.Adapter<LocalAdapter.LocalHolder> {
    public LocalAdapter() {
        this.results = results;
    }

    private List<Local> results = new ArrayList<>();
    private static ItemClickListener itemClickListener;

    @NonNull
    @Override
    public LocalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_local, parent, false);

        return new LocalHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LocalHolder holder, int position) {
        Local local = results.get(position);
        holder.textViewNomeRef.setText(local.getNomeRef());
        holder.textViewDescricao.setText(local.getDescricao());
        holder.textViewLatitude.setText(local.getLat());
        holder.textViewLongitude.setText(local.getLongi());
        holder.textViewData.setText(local.getData());
        holder.imagemFotoCard.setImageBitmap(ImageUtil.decode(local.getImagem()));

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setResults(List<Local> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    class LocalHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewNomeRef;
        private TextView textViewDescricao;
        private TextView textViewLatitude;
        private TextView textViewLongitude;
        private TextView textViewData;
        private ImageView imagemFotoCard;


        public LocalHolder(@NonNull View itemView) {
            super(itemView);
            textViewNomeRef = itemView.findViewById(R.id.textViewNomeRefLocal);
            textViewDescricao = itemView.findViewById(R.id.textViewDescricaoLocal);
            textViewLatitude = itemView.findViewById(R.id.textViewLatitude);
            textViewLongitude = itemView.findViewById(R.id.textViewLongitude);
            textViewData = itemView.findViewById(R.id.textViewData);
            imagemFotoCard = itemView.findViewById(R.id.imagemFotoCard);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(getAdapterPosition(), results.get(getAdapterPosition()));
            }
        }
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(int position, Local local);
    }
}