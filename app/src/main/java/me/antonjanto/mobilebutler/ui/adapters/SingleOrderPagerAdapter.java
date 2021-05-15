package me.antonjanto.mobilebutler.ui.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jetbrains.annotations.NotNull;

import me.antonjanto.mobilebutler.ui.orders.single.SingleOrderDetailsFragment;
import me.antonjanto.mobilebutler.ui.orders.single.SingleOrderItemsFragment;

public class SingleOrderPagerAdapter extends FragmentStateAdapter
{
     private Bundle args;

     public SingleOrderPagerAdapter(Fragment fragment)
     {
          super(fragment);
          args = fragment.getArguments();
     }

     @NonNull
     @NotNull
     @Override
     public Fragment createFragment(int position)
     {
          Fragment fragment;
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

}
