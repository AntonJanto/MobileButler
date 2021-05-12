package me.antonjanto.mobilebutler.ui.orders.single;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jetbrains.annotations.NotNull;

public class SingleOrderPagerAdapter extends FragmentStateAdapter
{
     private long orderId;

     public SingleOrderPagerAdapter(Fragment fragment)
     {
          super(fragment);
     }

     @NonNull
     @NotNull
     @Override
     public Fragment createFragment(int position)
     {
          Fragment fragment;
          Bundle args = new Bundle();
          args.putLong("orderId", orderId);
          switch (position) {
               case 1:
                    fragment = new SingleOrderDetailsFragment();
                    break;
               case 0:
               default:
                    fragment = new SingleOrderItemsFragment();
                    break;
          }
          fragment.setArguments(args);
          return fragment;
     }

     @Override
     public int getItemCount()
     {
          return 2;
     }

     public void setOrderId(long orderId)
     {
          this.orderId = orderId;
     }
}
