package edu.galileo.android.tipcalc.tipcalcpremium.extra.ui;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.galileo.android.tipcalc.R;
import edu.galileo.android.tipcalc.TipCalcApp;
import edu.galileo.android.tipcalc.entities.DolarPy;
import edu.galileo.android.tipcalc.tipcalcpremium.extra.adapters.ExtraAdapter;
import edu.galileo.android.tipcalc.tipcalcpremium.extra.di.ExtraComponent;
import edu.galileo.android.tipcalc.tipcalcpremium.extra.mvp.ExtraPresenter;
import edu.galileo.android.tipcalc.tipcalcpremium.extra.mvp.ExtraView;

/**
 * Created by carlos.gomez on 10/07/2016.
 */
public class ExtraFragment extends Fragment implements ExtraView {
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.framelayoutcontainer)
    FrameLayout container;

    @Inject
    ExtraAdapter adapter;
    @Inject
    ExtraPresenter presenter;

    public ExtraFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        ButterKnife.bind(this, view);
        setupInjection();
        setupRecyclerView();
        presenter.getDataFromWebService();
        return view;
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    private void setupInjection() {
        TipCalcApp app = (TipCalcApp) getActivity().getApplication();
        ExtraComponent extraComponent = app.getExtraComponent(this, this);
        extraComponent.inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }


    @Override
    public void showData() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideData() {
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
    public void onError(String error) {
        Snackbar.make(container, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setContent(List<DolarPy> items) {
        adapter.setItems(items);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
