package com.thinkmobiles.easyerp.presentation.screens.details;

import com.thinkmobiles.easyerp.presentation.managers.CookieManager;
import com.thinkmobiles.easyerp.presentation.managers.ErrorManager;
import com.thinkmobiles.easyerp.presentation.utils.Constants;
import com.thinkmobiles.easyerp.presentation.utils.DynamicallyPreferences;

import rx.subscriptions.CompositeSubscription;

/**
 * @author Alex Michenko (Created on 28.02.17).
 *         Company: Thinkmobiles
 *         Email: alex.michenko@thinkmobiles.com
 */

public class DetailsPresenter implements DetailsContract.DetailsPresenter {

    private CompositeSubscription subscription;
    private DetailsContract.DetailsModel model;
    private DetailsContract.DetailsView view;
    private final CookieManager cookieManager;
    private final DynamicallyPreferences dynamicallyPreferences;


    public DetailsPresenter(DetailsContract.DetailsModel model, DetailsContract.DetailsView view, CookieManager cookieManager,
                            DynamicallyPreferences dynamicallyPreferences) {
        this.model = model;
        this.view = view;
        this.cookieManager = cookieManager;
        this.dynamicallyPreferences = dynamicallyPreferences;
        view.setPresenter(this);

        subscription = new CompositeSubscription();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        if (subscription.hasSubscriptions())
            subscription.clear();
    }

    @Override
    public void logOut() {
        view.showProgress();
        subscription.add(
                model.logout().subscribe(
                        result -> {
                            cookieManager.clearCookie();
                            view.dismissProgress();
                            view.restartApp();
                            dynamicallyPreferences.putBoolean(Constants.KEY_IS_SOCIAL, false);
                        },
                        t -> {
                            view.dismissProgress();
                            view.showToast(ErrorManager.getErrorMessage(t));
                        }
                )
        );
    }

}
