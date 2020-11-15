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
import br.usjt.ucsist.cadaluno.model.Contato;
import br.usjt.ucsist.cadaluno.util.ImageUtil;

public class ContatoAdapter extends RecyclerView.Adapter<ContatoAdapter.ContatoHolder> {
    private List<Contato> results = new ArrayList<>();
    private static ItemClickListener itemClickListener;

    @NonNull
    @Override
    public ContatoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_local, parent, false);

        return new ContatoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContatoHolder holder, int position) {
        Contato contato = results.get(position);
        holder.textViewNomeRef.setText(contato.getNomeRef());
        holder.textViewDescricao.setText(contato.getDescricao());
        holder.imagemFotoCard.setImageBitmap(ImageUtil.decode(contato.getImagem()));
    }
    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setResults(List<Contato> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    class ContatoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewNomeRef;
        private TextView textViewDescricao;
        private ImageView imagemFotoCard;


        public ContatoHolder(@NonNull View itemView) {
            super(itemView);
            textViewNomeRef = itemView.findViewById(R.id.textViewNomeRefLocal);
            textViewDescricao = itemView.findViewById(R.id.textViewDescricaoLocal);
            imagemFotoCard = itemView.findViewById(R.id.imagemFotoCard);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(itemClickListener != null) {
                itemClickListener.onItemClick(getAdapterPosition(), results.get(getAdapterPosition()));
            }
        }
    }
    public void setOnItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(int position, Contato contato);
    }
}