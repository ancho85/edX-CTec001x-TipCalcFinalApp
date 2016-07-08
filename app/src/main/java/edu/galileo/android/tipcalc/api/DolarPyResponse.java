package edu.galileo.android.tipcalc.api;

import java.util.List;

import edu.galileo.android.tipcalc.entities.DolarPy;

/**
 * Created by carlos.gomez on 08/07/2016.
 */
public class DolarPyResponse {
    private int count;

    private List<DolarPy> dolarPyList;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DolarPy> getDolarPyList() {
        return dolarPyList;
    }

    public void setRecipes(List<DolarPy> dolarPyLists) {
        this.dolarPyList = dolarPyLists;
    }

    public DolarPy getFirstRecipe() {
        DolarPy first = null;
        if (!dolarPyList.isEmpty()) {
            first = dolarPyList.get(0);
        }
        return first;
    }
}
