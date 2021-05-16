package me.antonjanto.mobilebutler.ui.orders.single;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import org.jetbrains.annotations.NotNull;

import me.antonjanto.mobilebutler.R;
import me.antonjanto.mobilebutler.ui.Converter;

public class SingleOrderClosedFragment extends Fragment
{
     private SingleOrderViewModel singleOrderViewModel;

     private TextView priceTextView;
     private TextView orderIdTextView;
     private Button confirmButton;
     private Button cancelButton;

     @Override
     public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
     {
          super.onCreateView(inflater, container, savedInstanceState);
          singleOrderViewModel = new ViewModelProvider(this).get(SingleOrderViewModel.class);
          long orderId = SingleOrderFragmentArgs.fromBundle(getArguments()).getOrderId();
          singleOrderViewModel.init(orderId);
          return inflater.inflate(R.layout.fragment_single_order_closed, container, false);
     }

     @Override
     public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
     {
          findViews(view);

          singleOrderViewModel.getOrder().observe(getViewLifecycleOwner(), o ->
          {
               priceTextView.setText(Converter.toDecimal(o.getTotalPrice()));
               orderIdTextView.setText(Converter.toInteger(o.getOrderId()));
          });

          confirmButton.setOnClickListener(this::onConfirmClickListener);

          cancelButton.setOnClickListener(this::onCancelClickListener);

     }

     private void onCancelClickListener(View view)
     {
          NavHostFragment.findNavController(this).navigateUp();
     }

     private void onConfirmClickListener(View view)
     {
          singleOrderViewModel.closeOrder();
          navigateToOrders();
     }

     private void navigateToOrders()
     {
          NavDirections navDirections = SingleOrderClosedFragmentDirections.actionNavSingleOrderClosedToNavOrders();
          NavHostFragment.findNavController(this).navigate(navDirections);
     }

     private void navigateBack()
     {

     }

     private void findViews(View view)
     {
          priceTextView = view.findViewById(R.id.order_price);
          orderIdTextView = view.findViewById(R.id.order_id);
          confirmButton = view.findViewById(R.id.order_close_confirm_button);
          cancelButton = view.findViewById(R.id.order_close_cancel_button);
     }
}
