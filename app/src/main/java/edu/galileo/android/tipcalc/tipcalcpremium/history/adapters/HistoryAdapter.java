package edu.galileo.android.tipcalc.tipcalcpremium.history.adapters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.SendButton;
import com.facebook.share.widget.ShareButton;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.galileo.android.tipcalc.R;
import edu.galileo.android.tipcalc.entities.TipRecordPremium;

/**
 * Created by carlos.gomez on 10/07/2016.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<TipRecordPremium> items;
    private OnItemClickListener clickListener;

    public HistoryAdapter(List<TipRecordPremium> items, OnItemClickListener clickListener) {
        this.items = items;
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TipRecordPremium tipRecordPremium = items.get(position);
        holder.setOnClickListener(tipRecordPremium, clickListener);
        holder.txtBill.setText(String.valueOf(tipRecordPremium.getBill()));
        holder.txtPercentage.setText(String.valueOf(tipRecordPremium.getTipPercentage()));
        holder.txtDate.setText(tipRecordPremium.getDateFormatted());
        holder.txtLatitude.setText(String.valueOf(tipRecordPremium.getLatitutde()));
        holder.txtLongitude.setText(String.valueOf(tipRecordPremium.getLongitude()));
        holder.txtBillFinalTotal.setText(String.valueOf(tipRecordPremium.getTip()));
    }

    public void setItems(List<TipRecordPremium> newItems) {
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    public void add(TipRecordPremium tipRecordPremium) {
        items.add(0, tipRecordPremium);
        notifyItemInserted(getItemCount() - 1);
        notifyDataSetChanged();
    }

    public void delete(TipRecordPremium tipRecordPremium) {
        int position = 0;
        for (TipRecordPremium item : items) {
            if (item.getTipId() == tipRecordPremium.getTipId()) {
                items.remove(position);
                notifyDataSetChanged();
                break;
            }
            position++;
        }
    }

    public void deleteAll(){
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txtBill)
        TextView txtBill;
        @Bind(R.id.txtPercentage)
        TextView txtPercentage;
        @Bind(R.id.txtBillFinalTotal)
        TextView txtBillFinalTotal;
        @Bind(R.id.txtDate)
        TextView txtDate;
        @Bind(R.id.txtLatitude)
        TextView txtLatitude;
        @Bind(R.id.txtLongitude)
        TextView txtLongitude;
        @Bind(R.id.fbShare)
        ShareButton fbShare;
        @Bind(R.id.fbSend)
        SendButton fbSend;
        @Bind(R.id.imgMyLocation)
        ImageView imgMyLocation;

        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.view = itemView;
        }

        public void setOnClickListener(final TipRecordPremium tipRecordPremium, final OnItemClickListener listener) {
            /*view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(tipRecordPremium);
                }
            });*/
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onItemLongClick(tipRecordPremium);
                    return false;
                }
            });
            imgMyLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onMyLocationClick(tipRecordPremium);
                }
            });
            ShareLinkContent content = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("http://www.last.fm/user/ancho85"))
                    .setContentTitle("He dejado una propina con TipCalcFinalApp")
                    .setContentDescription("Total: " +
                            String.valueOf(tipRecordPremium.getBill()) +
                            " aplicando un porcentaje de " + String.valueOf(tipRecordPremium.getTipPercentage()) +
                            " el día " + tipRecordPremium.getDateFormatted())
                    .build();
            fbShare.setShareContent(content);
            fbSend.setShareContent(content);
        }
    }
}
