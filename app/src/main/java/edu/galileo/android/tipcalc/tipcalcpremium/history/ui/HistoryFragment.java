package edu.galileo.android.tipcalc.tipcalcpremium.history.ui;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.galileo.android.tipcalc.R;
import edu.galileo.android.tipcalc.entities.TipRecordPremium;
import edu.galileo.android.tipcalc.tipcalcpremium.history.adapters.HistoryAdapter;
import edu.galileo.android.tipcalc.tipcalcpremium.history.adapters.OnItemClickListener;
import edu.galileo.android.tipcalc.tipcalcpremium.history.mvp.HistoryPresenter;
import edu.galileo.android.tipcalc.tipcalcpremium.history.mvp.HistoryView;

/**
 * Created by carlos.gomez on 10/07/2016.
 */
public class HistoryFragment extends Fragment implements HistoryView, OnItemClickListener {
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.framelayoutcontainer)
    FrameLayout framelayoutcontainer;

    HistoryAdapter adapter;
    HistoryPresenter presenter;

    public HistoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void showElements() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideElements() {
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void tipAdded() {
        showError("NO IMPLEMENTADO! agregar tip");
    }

    @Override
    public void tipDeleted() {
        showError("NO IMPLEMENTADO! borrar tip");
    }

    @Override
    public void onError(String error) {
        showError(error);
    }

    @Override
    public void setContent(List<TipRecordPremium> items) {
        adapter.setItems(items);
    }

    @Override
    public void onItemClick(TipRecordPremium tipRecordPremium) {
        showError("NO IMPLEMENTADO! click sobre id:" + tipRecordPremium.getTipId());
    }

    private void showError(String error) {
        Snackbar.make(framelayoutcontainer, error, Snackbar.LENGTH_SHORT).show();
    }
}
