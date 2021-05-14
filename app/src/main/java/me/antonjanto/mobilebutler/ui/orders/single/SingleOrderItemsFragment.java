package me.antonjanto.mobilebutler.ui.orders.single;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import ir.beigirad.zigzagview.ZigzagView;
import me.antonjanto.mobilebutler.R;
import me.antonjanto.mobilebutler.model.OrderItem;
import me.antonjanto.mobilebutler.ui.adapters.OrderItemAdapter;

public class SingleOrderItemsFragment extends Fragment
{
     private SingleOrderViewModel mViewModel;
     private RecyclerView recyclerView;
     private TextView noOrderItemsTextView;
     private ZigzagView zigzagView;
     private FloatingActionButton fab;

     public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
          Bundle savedInstanceState)
     {
          super.onCreate(savedInstanceState);
          mViewModel = new ViewModelProvider(this).get(SingleOrderViewModel.class);
          View root = inflater.inflate(R.layout.fragment_single_order_items, container, false);
          findViews(root);
          return root;
     }

     @Override
     public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
     {
          super.onViewCreated(view, savedInstanceState);

          fab.setOnClickListener(this::fabPressed);
          OrderItemAdapter orderItemAdapter = new OrderItemAdapter();
          recyclerView.setAdapter(orderItemAdapter);
          recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

          long orderId;
          if (getArguments() != null) {
               orderId = getArguments().getLong("orderId");
               mViewModel.fetchOrder(orderId);
               mViewModel.getOrder().observe(getViewLifecycleOwner(), (o) -> {
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
               });
          }
     }

     private void findViews(View view)
     {
          fab = view.findViewById(R.id.single_order_items_fab);
          recyclerView = view.findViewById(R.id.single_order_items_recycler_view);
          noOrderItemsTextView = view.findViewById(R.id.single_order_items_none);
          zigzagView = view.findViewById(R.id.single_order_item_zigzag);
     }

     private void fabPressed(View view)
     {
          Toast.makeText(getContext(), "Add Item", Toast.LENGTH_LONG).show();
          NavHostFragment.findNavController(this).navigate(R.id.action_nav_single_order_to_nav_products);
     }
}
