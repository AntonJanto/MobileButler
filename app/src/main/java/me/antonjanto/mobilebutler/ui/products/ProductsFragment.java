package me.antonjanto.mobilebutler.ui.products;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import me.antonjanto.mobilebutler.R;
import me.antonjanto.mobilebutler.model.Order;
import me.antonjanto.mobilebutler.ui.adapters.OrdersAdapter;
import me.antonjanto.mobilebutler.ui.adapters.ProductsAdapter;
import me.antonjanto.mobilebutler.ui.adapters.RecyclerTouchListener;
import me.antonjanto.mobilebutler.ui.orders.single.SingleOrderFragmentDirections;

public class ProductsFragment extends Fragment
{

     private ProductsViewModel productsViewModel;
     private RecyclerView productsRecyclerView;

     @Override
     public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
          @Nullable Bundle savedInstanceState)
     {
          super.onCreate(savedInstanceState);
          productsViewModel = new ViewModelProvider(this).get(ProductsViewModel.class);
          Order order = ProductsFragmentArgs.fromBundle(getArguments()).getOrder();
          productsViewModel.init(order);
          View root = inflater.inflate(R.layout.fragment_products, container, false);
          findViews(root);
          return root;
     }

     private void findViews(View root)
     {
          productsRecyclerView = root.findViewById(R.id.products_recycler_view);
     }

     @Override
     public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
     {
          super.onViewCreated(view, savedInstanceState);
          ProductsAdapter productsAdapter = new ProductsAdapter();
          productsRecyclerView.setAdapter(productsAdapter);
          productsRecyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

          productsViewModel.getProducts().observe(getViewLifecycleOwner(), ps -> {
               productsAdapter.setProducts(ps);
               productsAdapter.notifyDataSetChanged();
          });

          productsRecyclerView.addOnItemTouchListener(
               new RecyclerTouchListener(getContext(), productsRecyclerView,
                    new ProductsRecyclertouchListener()));
     }

     private void navigateBack()
     {
          long orderId = productsViewModel.getOrder().getValue().getOrderId();

          ProductsFragmentDirections.ActionNavProductsToNavSingleOrder action = ProductsFragmentDirections
               .actionNavProductsToNavSingleOrder(orderId, String.valueOf(orderId));
          NavHostFragment.findNavController(this).navigate(action);
     }

     private class ProductsRecyclertouchListener implements RecyclerTouchListener.ClickListener
     {
          @Override
          public void onClick(View view, int position)
          {
               productsViewModel.addProductToOrder(position, 1);
               navigateBack();
          }

          @Override
          public void onLongClick(View view, int position)
          {
               QuantityDialogFragment dialog = new QuantityDialogFragment();
               dialog.setNoticeQuantityListener((quantity) -> {
                    productsViewModel.addProductToOrder(position, quantity);
                    navigateBack();
               });
               dialog.show(requireActivity().getSupportFragmentManager(), "QuantityDialogFragment");
          }
     }
}