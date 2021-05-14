package me.antonjanto.mobilebutler.ui.orders.single;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import me.antonjanto.mobilebutler.R;

public class SingleOrderFragment extends Fragment
{
     private SingleOrderPagerAdapter singleOrderPagerAdapter;
     private SingleOrderViewModel singleOrderViewModel;
     private ViewPager2 viewPager;
     private TabLayout tabLayout;

     public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
          Bundle savedInstanceState)
     {
          super.onCreate(savedInstanceState);
          singleOrderViewModel = new ViewModelProvider(this).get(SingleOrderViewModel.class);
          return inflater.inflate(R.layout.fragment_single_order, container, false);
     }

     @Override
     public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
     {
          super.onViewCreated(view, savedInstanceState);

          long orderId = getArguments().getLong("orderId");
          singleOrderViewModel.fetchOrder(orderId);

          viewPager = view.findViewById(R.id.single_order_pager);
          tabLayout = view.findViewById(R.id.single_order_tab_layout);

          singleOrderPagerAdapter = new SingleOrderPagerAdapter(this);
          singleOrderPagerAdapter.setOrderId(orderId);
          viewPager.setAdapter(singleOrderPagerAdapter);

          new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
               String tabName;
               switch (position) {
                    case 0:
                         tabName = "Items";
                         break;
                    case 1:
                         tabName = "Details";
                         break;
                    default:
                         tabName = "";
               }
               tab.setText(tabName);
          }).attach();
     }

}

