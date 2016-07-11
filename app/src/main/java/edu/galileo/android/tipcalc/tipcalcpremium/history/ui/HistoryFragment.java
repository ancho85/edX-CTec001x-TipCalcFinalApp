package edu.galileo.android.tipcalc.tipcalcpremium.history.ui;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import edu.galileo.android.tipcalc.TipCalcApp;
import edu.galileo.android.tipcalc.entities.TipRecordPremium;
import edu.galileo.android.tipcalc.tipcalcpremium.history.adapters.HistoryAdapter;
import edu.galileo.android.tipcalc.tipcalcpremium.history.adapters.OnItemClickListener;
import edu.galileo.android.tipcalc.tipcalcpremium.history.di.HistoryComponent;
import edu.galileo.android.tipcalc.tipcalcpremium.history.mvp.HistoryPresenter;
import edu.galileo.android.tipcalc.tipcalcpremium.history.mvp.HistoryView;

/**
 * Created by carlos.gomez on 10/07/2016.
 */
public class HistoryFragment extends Fragment implements HistoryView, OnItemClickListener, HistoryFragmentListener {
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
        setupInjection();
        setupRecyclerView();
        return view;
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(adapter);
    }

    private void setupInjection() {
        TipCalcApp app = (TipCalcApp) getActivity().getApplication();
        HistoryComponent historyComponent = app.getHistoryComponent(this, this, this);
        presenter = historyComponent.getPresenter();
        adapter = historyComponent.getAdapter();
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
        presenter.getTipHistory("");
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
        showSnackbar("Propina agregada");
    }

    @Override
    public void tipDeleted() {
        showSnackbar("Propina borrada");
    }

    @Override
    public void onError(String error) {
        showSnackbar(error);
    }

    @Override
    public void setContent(List<TipRecordPremium> items) {
        adapter.setItems(items);
    }

    /*@Override
    public void onItemClick(TipRecordPremium tipRecordPremium) {
        presenter.addTip(tipRecordPremium);
    }*/

    @Override
    public void onItemLongClick(TipRecordPremium tipRecordPremium) {
        deleteFromHistory(tipRecordPremium);
    }

    private void showSnackbar(String msgText) {
        Snackbar.make(framelayoutcontainer, msgText, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void addToHistory(TipRecordPremium tipRecordPremium) {
        presenter.addTip(tipRecordPremium);
        adapter.add(tipRecordPremium);
    }

    @Override
    public void deleteFromHistory(TipRecordPremium tipRecordPremium) {
        presenter.deleteTip(tipRecordPremium);
        adapter.delete(tipRecordPremium);
    }

}
