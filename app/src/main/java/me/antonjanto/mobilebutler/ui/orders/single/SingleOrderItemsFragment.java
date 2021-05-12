package me.antonjanto.mobilebutler.ui.orders.single;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import me.antonjanto.mobilebutler.R;

public class SingleOrderItemsFragment extends Fragment
{
     private SingleOrderViewModel mViewModel;
     private TextView orderIdTextView;
     private FloatingActionButton fab;

     public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
          Bundle savedInstanceState)
     {
          super.onCreate(savedInstanceState);
          mViewModel = new ViewModelProvider(this).get(SingleOrderViewModel.class);
          return inflater.inflate(R.layout.fragment_single_order_items, container, false);
     }

     @Override
     public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
     {
          super.onViewCreated(view, savedInstanceState);

          fab = view.findViewById(R.id.single_order_items_fab);
          fab.setOnClickListener(this::fabPressed);
          orderIdTextView = view.findViewById(R.id.single_order_id);

          long orderId;
          if (getArguments() != null) {
               orderId = getArguments().getLong("orderId");
               mViewModel.fetchOrder(orderId);
               mViewModel.getOrder().observe(getViewLifecycleOwner(), (o) -> {
                    orderIdTextView.setText(String.valueOf(o.getOrderId()));
               });
          }
     }

     private void fabPressed(View view)
     {
          Toast.makeText(getContext(), "Add Item", Toast.LENGTH_LONG).show();
     }
}
