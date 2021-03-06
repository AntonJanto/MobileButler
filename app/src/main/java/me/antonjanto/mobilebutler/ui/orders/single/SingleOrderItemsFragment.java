package me.antonjanto.mobilebutler.ui.orders.single;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import ir.beigirad.zigzagview.ZigzagView;
import me.antonjanto.mobilebutler.R;
import me.antonjanto.mobilebutler.model.Order;
import me.antonjanto.mobilebutler.model.OrderItem;
import me.antonjanto.mobilebutler.ui.Converter;
import me.antonjanto.mobilebutler.ui.adapters.OrderItemAdapter;

public class SingleOrderItemsFragment extends Fragment
{
     private SingleOrderViewModel mViewModel;

     private RecyclerView recyclerView;
     private TextView noOrderItemsTextView;
     private FloatingActionButton fab;
     private TextView totalPriceTextView;

     private NavDirections navDirections;
     private OrderItemAdapter orderItemAdapter;

     public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
          Bundle savedInstanceState)
     {
          super.onCreate(savedInstanceState);
          long orderId = SingleOrderFragmentArgs.fromBundle(getArguments()).getOrderId();
          mViewModel = new ViewModelProvider(this).get(SingleOrderViewModel.class);
          mViewModel.init(orderId);
          View root = inflater.inflate(R.layout.fragment_single_order_items, container, false);
          setHasOptionsMenu(true);
          findViews(root);
          return root;
     }

     @Override
     public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
     {
          super.onViewCreated(view, savedInstanceState);

          fab.setOnClickListener(this::fabPressed);
          orderItemAdapter = new OrderItemAdapter();
          recyclerView.setAdapter(orderItemAdapter);
          recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

          mViewModel.getOrder().observe(getViewLifecycleOwner(), new OrderObserverImpl());
     }

     @Override
     public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
     {
          inflater.inflate(R.menu.options_single_order, menu);
          super.onCreateOptionsMenu(menu, inflater);
     }

     @Override
     public void onPrepareOptionsMenu(@NonNull @NotNull Menu menu)
     {
          menu.removeItem(R.id.menu_settings);
          super.onPrepareOptionsMenu(menu);
     }

     @Override
     public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item)
     {
          if (item.getItemId() == R.id.menu_order_close) {
               long orderId = mViewModel.getOrder().getValue().getOrderId();
               SingleOrderFragmentDirections.ActionNavSingleOrderToSingleOrderClosedFragment action
                    = SingleOrderFragmentDirections.actionNavSingleOrderToSingleOrderClosedFragment(orderId, Converter.toInteger(orderId));
               NavHostFragment.findNavController(this).navigate(action);
          }
          else if (item.getItemId() == R.id.menu_order_cancel)
          {
               mViewModel.cancelOrder();
               NavHostFragment.findNavController(this).navigateUp();
          }
          return super.onOptionsItemSelected(item);
     }

     private void findViews(View view)
     {
          fab = view.findViewById(R.id.single_order_items_fab);
          recyclerView = view.findViewById(R.id.single_order_items_recycler_view);
          noOrderItemsTextView = view.findViewById(R.id.single_order_items_none);
          totalPriceTextView = view.findViewById(R.id.single_order_items_total_price);
     }

     private void fabPressed(View view)
     {
          NavHostFragment.findNavController(this).navigate(navDirections);
     }

     private class OrderObserverImpl implements Observer<Order>
     {
          @Override
          public void onChanged(Order o)
          {
               navDirections = SingleOrderFragmentDirections.actionNavSingleOrderToNavProducts(o);

               totalPriceTextView.setText(Converter.toDecimal(o.getTotalPrice()));

               List<OrderItem> orderItems = o.getItems();

               if (orderItems == null || orderItems.isEmpty()) {
                    recyclerView.setVisibility(View.INVISIBLE);
                    noOrderItemsTextView.setVisibility(View.VISIBLE);
               }
               else {
                    recyclerView.setVisibility(View.VISIBLE);
                    noOrderItemsTextView.setVisibility(View.INVISIBLE);
                    orderItemAdapter.setOrderItems(orderItems);
                    orderItemAdapter.notifyDataSetChanged();
               }
          }
     }

}
