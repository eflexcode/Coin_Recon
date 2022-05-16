package com.larrex.coinrecon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.larrex.coinrecon.databinding.ActivityMainBinding;
import com.larrex.coinrecon.fragment.ExchangeFragment;
import com.larrex.coinrecon.fragment.HomeMarketFragment;
import com.larrex.coinrecon.fragment.MarketInfoFragment;
import com.larrex.coinrecon.fragment.MoreFragment;
import com.larrex.coinrecon.fragment.NewsFragment;
import com.larrex.coinrecon.fragment.ProfileFragment;
import com.larrex.coinrecon.fragment.SearchFragment;
import com.larrex.coinrecon.fragment.TrendingFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    FragmentManager fragmentManager;

    Fragment homeMarket;
    Fragment exchange;
    Fragment news;
    Fragment search;
    Fragment profile;
    Fragment more;
    Fragment convert;

    Fragment activeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
//
//        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.mainFrame);
//
//        NavController navController = navHostFragment.getNavController();
//
//        NavigationUI.setupWithNavController(binding.bnv, navController);

        fragmentManager = getSupportFragmentManager();


//        exchange = new ExchangeFragment();
//        news = new NewsFragment();
//        search = new SearchFragment();

        if (savedInstanceState == null) {
            homeMarket = new HomeMarketFragment();
            fragmentManager.beginTransaction().add(R.id.mainFrame, homeMarket).commit();
            activeFragment = homeMarket;
        }

        PopupMenu popupMenu = new PopupMenu(this, binding.bnv, Gravity.END);
        popupMenu.inflate(R.menu.more_menu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.profile:

                        if (profile == null) {
                            profile = new ProfileFragment();
                            fragmentManager.beginTransaction().hide(activeFragment).add(R.id.mainFrame, profile).commit();
                        } else {
                            fragmentManager.beginTransaction().hide(activeFragment).show(profile).commit();
                        }
                        activeFragment = profile;
                        break;

//                    case R.id.trending:
//                        if (trending == null) {
//                            trending = new TrendingFragment();
//                            fragmentManager.beginTransaction().hide(activeFragment).add(R.id.mainFrame, trending).commit();
//                        } else {
//                            fragmentManager.beginTransaction().hide(activeFragment).show(trending).commit();
//                        }
//                        activeFragment = trending;
//                        break;

                    case R.id.convert:
                        if (convert == null) {
                            convert = new ProfileFragment();
                            fragmentManager.beginTransaction().hide(activeFragment).add(R.id.mainFrame, convert).commit();
                        } else {
                            fragmentManager.beginTransaction().hide(activeFragment).show(convert).commit();
                        }
                        activeFragment = convert;
                        break;
                }

                return true;
            }
        });

        binding.bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.market:

                        if (homeMarket == null) {
                            homeMarket = new HomeMarketFragment();
                            fragmentManager.beginTransaction().hide(activeFragment).add(R.id.mainFrame, homeMarket).commit();
                        } else {
                            fragmentManager.beginTransaction().hide(activeFragment).show(homeMarket).commit();
                        }
                        activeFragment = homeMarket;

                        break;

                    case R.id.exchange:

                        if (exchange == null) {
                            exchange = new ExchangeFragment();

                            fragmentManager.beginTransaction().hide(activeFragment).add(R.id.mainFrame, exchange).commit();
                        } else {
                            fragmentManager.beginTransaction().hide(activeFragment).show(exchange).commit();
                        }
                        activeFragment = exchange;

                        break;

                    case R.id.news:

                        if (news == null) {
                            news = new NewsFragment();

                            fragmentManager.beginTransaction().hide(activeFragment).add(R.id.mainFrame, news).commit();
                        } else {
                            fragmentManager.beginTransaction().hide(activeFragment).show(news).commit();
                        }
                        activeFragment = news;

                        break;

                    case R.id.search:

                        if (search == null) {
                            search = new SearchFragment();

                            fragmentManager.beginTransaction().hide(activeFragment).add(R.id.mainFrame, search).commit();
                        } else {
                            fragmentManager.beginTransaction().hide(activeFragment).show(search).commit();
                        }
                        activeFragment = search;

                        break;

                    case R.id.more:

                        if (more == null) {
                            more = new MoreFragment();

                            fragmentManager.beginTransaction().hide(activeFragment).add(R.id.mainFrame, more).commit();
                        } else {
                            fragmentManager.beginTransaction().hide(activeFragment).show(more).commit();
                        }
                        activeFragment = more;
                        break;

                }

                return true;
            }
        });


    }

    public void out(View view) {
        FirebaseAuth.getInstance().signOut();
    }
}