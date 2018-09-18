package wanandroid.weifeng.com.wanandroid.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import wanandroid.weifeng.com.wanandroid.R;
import wanandroid.weifeng.com.wanandroid.repositiry.APIService;
import wanandroid.weifeng.com.wanandroid.repositiry.RetrofitClient;
import wanandroid.weifeng.com.wanandroid.repositiry.response.ListArticlesResponse;


public class FindFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RetrofitClient.getInstance().getService(APIService.class).getListArticles(0).enqueue(new Callback<ListArticlesResponse>() {
                @Override
                public void onResponse(Call<ListArticlesResponse> call, Response<ListArticlesResponse> response) {
                    int statusCode = response.code();
                    ListArticlesResponse listArticlesResponse = response.body();
                }

                @Override
                public void onFailure(Call<ListArticlesResponse> call, Throwable t) {
                    Log.e("","");
                }
            });


    }
}
