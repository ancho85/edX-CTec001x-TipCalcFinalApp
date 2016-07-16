package edu.galileo.android.tipcalc.tipcalcpremium.extra.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.galileo.android.tipcalc.R;
import edu.galileo.android.tipcalc.entities.DolarPy;

/**
 * Created by carlos.gomez on 16/07/2016.
 */
public class ExtraAdapter extends RecyclerView.Adapter<ExtraAdapter.ViewHolder> {
    private List<DolarPy> items;

    public ExtraAdapter(List<DolarPy> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_extra, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DolarPy dolarPy = items.get(position);
        holder.txtEntity.setText(dolarPy.getEntity());
        holder.txtPurchase.setText(String.valueOf(dolarPy.getPurchase()));
        holder.txtSale.setText(String.valueOf(dolarPy.getSale()));
        holder.txtDayReference.setText(String.valueOf(dolarPy.getDaily_ref()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd mm yyyy HH:mm:ss", Locale.getDefault());
        holder.txtLastUpdate.setText(simpleDateFormat.format(dolarPy.getLast_update()));
    }

    public void setItems(List<DolarPy> newItems){
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txtEntity)
        TextView txtEntity;
        @Bind(R.id.txtPurchase)
        TextView txtPurchase;
        @Bind(R.id.txtSale)
        TextView txtSale;
        @Bind(R.id.txtDayReference)
        TextView txtDayReference;
        @Bind(R.id.txtLastUpdate)
        TextView txtLastUpdate;
        @Bind(R.id.progressBar)
        ProgressBar progressBar;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.view = itemView;
        }
    }
}
