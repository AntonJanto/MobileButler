package me.antonjanto.mobilebutler.ui.orders.single;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.antonjanto.mobilebutler.R;

public class SingleOrderFragment extends Fragment
{
     private SingleOrderViewModel mViewModel;
     private TextView orderIdTextView;

     public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
     {
          super.onCreate(savedInstanceState);
          mViewModel = new ViewModelProvider(this).get(SingleOrderViewModel.class);
          View root = inflater.inflate(R.layout.fragment_single_order, container, false);

          orderIdTextView = root.findViewById(R.id.single_order_id);

          long orderId = getArguments().getLong("orderId");
          mViewModel.fetchOrder(orderId);

          mViewModel.getOrder().observe(getViewLifecycleOwner(), (o) ->
          {
               orderIdTextView.setText(String.valueOf(o.getOrderId()));
          });

          return root;
     }
}