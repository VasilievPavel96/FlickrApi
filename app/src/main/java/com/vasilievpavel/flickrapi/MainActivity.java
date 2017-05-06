package com.vasilievpavel.flickrapi;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    public static final String BASE_URL = "https://api.flickr.com/services/rest/";
    public static final String API_KEY = "0afd98b061dc66b200c3d877b4e2c4bb";
    private static final String TAG = MainActivity.class.getSimpleName();
    private ViewPager viewPager;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();
        FlickrService flickrService = retrofit.create(FlickrService.class);
        Call<ResponseBody> call = flickrService.getRecent(API_KEY);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String invalidJson = response.body().string();
                    int openedParenthesis = invalidJson.indexOf("(");
                    int closedParenthesisres = invalidJson.lastIndexOf(")");
                    String validJson = invalidJson.substring(openedParenthesis + 1, closedParenthesisres);
                    JSONObject jsonObject = new JSONObject(validJson);
                    JSONObject photos = jsonObject.getJSONObject("photos");
                    JSONArray photo = photos.getJSONArray("photo");
                    ArrayList<Photo> photoList = new ArrayList<>();
                    for (int i = 0; i < photo.length(); ++i) {
                        JSONObject photoItem = photo.getJSONObject(i);
                        Photo p = new Photo();
                        String id = photoItem.getString("id");
                        int farm = photoItem.getInt("farm");
                        String secret = photoItem.getString("secret");
                        String server = photoItem.getString("server");
                        String title = photoItem.getString("title");
                        p.downloadUrl = getResources()
                                .getString(R.string.download_url, farm, server, id, secret);
                        p.title = title;
                        photoList.add(p);
                    }
                    PhotoManager.getInstance().setPhotoList(photoList);
                    viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
                        @Override
                        public Fragment getItem(int position) {
                            return PhotoFragment.newInstance(position);
                        }

                        @Override
                        public CharSequence getPageTitle(int position) {
                            return PhotoManager.getInstance().getPhotoList().get(position).title;
                        }

                        @Override
                        public int getCount() {
                            return 2;
                        }
                    });
                    tabLayout.setupWithViewPager(viewPager);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }
}
