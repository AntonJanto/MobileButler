package me.antonjanto.mobilebutler.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import me.antonjanto.mobilebutler.R;
import me.antonjanto.mobilebutler.model.Order;
import me.antonjanto.mobilebutler.ui.adapters.OrderAdapter;

public class HomeFragment extends Fragment
{
     private HomeViewModel homeViewModel;
     private RecyclerView orderView;

     public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
     {
          homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
          View root = inflater.inflate(R.layout.fragment_home, container, false);
          orderView = root.findViewById(R.id.recyclerView_orders);
          orderView.setLayoutManager(new GridLayoutManager(root.getContext(), 2));

          OrderAdapter orderAdapter = new OrderAdapter();
          orderView.setAdapter(orderAdapter);
          homeViewModel.getOrders().observe(getViewLifecycleOwner(), new Observer<List<Order>>()
          {
               @Override
               public void onChanged(List<Order> orders)
               {
                    orderAdapter.setOrders(orders);
               }
          });
          return root;
     }
}