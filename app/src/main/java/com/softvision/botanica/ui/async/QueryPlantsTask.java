package com.softvision.botanica.ui.async;

import com.softvision.botanica.bl.BusinessLogic;
import com.softvision.botanica.common.err.ApiException;
import com.softvision.botanica.common.pojo.out.QueryOutputPOJO;

/**
 * Created by lorand.krucz on 3/28/2015.
 */
public class QueryPlantsTask extends BaseAsyncTask<Void, Void, QueryOutputPOJO> {
    private String query;
    private Integer limit;

    public QueryPlantsTask(String query, Integer limit) {
        this.query = query;
        this.limit = limit;
    }

    @Override
    public QueryOutputPOJO executeAsyncOperation(Void... params) throws ApiException {
        return BusinessLogic.getFacade().getPlantsList(query, limit);
    }
}
