package io.armcha.arch;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * 封装了 AAC 架构中, ViewModel 的获取,并将其和 Presenter 关联起来.
 * 所有 activity 都应该继承此 Activity
 *
 * @author Created by Chatikyan on 20.05.2017.
 */
public abstract class BaseMVPActivity<V extends BaseMVPContract.View, P extends BaseMVPContract.Presenter<V>>
    extends AppCompatActivity implements BaseMVPContract.View {

    protected P presenter;

    @SuppressWarnings("unchecked")
    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseViewModel<V, P> viewModel = ViewModelProviders.of(this).get(BaseViewModel.class);
        boolean isPresenterCreated = false;
        if (viewModel.getPresenter() == null) {
            viewModel.setPresenter(initPresenter());
            isPresenterCreated = true;
        }
        presenter = viewModel.getPresenter();
        //用于监听 Activity 的 Lifecycle
        presenter.attachLifecycle(getLifecycle());
        presenter.attachView((V) this);//完美的封装设计,第二重保障.
        if (isPresenterCreated)
            presenter.onPresenterCreate();
    }

    @CallSuper
    @Override
    protected void onDestroy() {
        presenter.detachLifecycle(getLifecycle());
        presenter.detachView();
        super.onDestroy();
    }

    protected abstract P initPresenter();
}
