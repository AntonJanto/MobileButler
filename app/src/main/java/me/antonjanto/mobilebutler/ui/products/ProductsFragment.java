package me.antonjanto.mobilebutler.ui.products;

import androidx.lifecycle.ViewModelProvider;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import me.antonjanto.mobilebutler.R;
import me.antonjanto.mobilebutler.model.Order;
import me.antonjanto.mobilebutler.ui.adapters.ProductsAdapter;
import me.antonjanto.mobilebutler.ui.adapters.RecyclerTouchListener;

public class ProductsFragment extends Fragment
{

     private ProductsViewModel productsViewModel;
     private RecyclerView productsRecyclerView;
     private ProductsAdapter productsAdapter;

     @Override
     public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
          @Nullable Bundle savedInstanceState)
     {
          super.onCreate(savedInstanceState);
          setHasOptionsMenu(true);
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
          productsAdapter = new ProductsAdapter();
          productsRecyclerView.setAdapter(productsAdapter);
          productsRecyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 3));

          productsViewModel.getProducts().observe(getViewLifecycleOwner(), ps -> {
               productsAdapter.setProducts(ps);
               productsAdapter.notifyDataSetChanged();
          });

          productsRecyclerView.addOnItemTouchListener(
               new RecyclerTouchListener(getContext(), productsRecyclerView,
                    new ProductsRecyclerTouchListener()));
     }

     @Override
     public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
     {
          inflater.inflate(R.menu.options_products, menu);

          SearchManager searchManager = (SearchManager) getContext()
               .getSystemService(Context.SEARCH_SERVICE);
          SearchView searchView = (SearchView) menu.findItem(R.id.menu_products_search)
               .getActionView();
          searchView.setSearchableInfo(
               searchManager.getSearchableInfo(getActivity().getComponentName()));
          searchView.setOnQueryTextListener(new ProductsSearchOnQueryListener());

          super.onCreateOptionsMenu(menu, inflater);
     }

     @Override
     public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item)
     {
          if (item.getItemId() == R.id.menu_products_search) {
               Toast.makeText(getContext(), R.string.search, Toast.LENGTH_SHORT).show();
          }
          return super.onOptionsItemSelected(item);
     }

     private void navigateBack()
     {
          long orderId = productsViewModel.getOrder().getValue().getOrderId();

          ProductsFragmentDirections.ActionNavProductsToNavSingleOrder action = ProductsFragmentDirections
               .actionNavProductsToNavSingleOrder(orderId, String.valueOf(orderId));
          NavHostFragment.findNavController(this).navigate(action);
     }

     private class ProductsRecyclerTouchListener implements RecyclerTouchListener.ClickListener
     {
          @Override
          public void onClick(View view, int position)
          {
               long productId = productsAdapter.getItemId(position);
               productsViewModel.addProductToOrder(productId, 1);
               navigateBack();
          }

          @Override
          public void onLongClick(View view, int position)
          {
               long productId = productsAdapter.getItemId(position);
               QuantityDialogFragment dialog = new QuantityDialogFragment();
               dialog.setNoticeQuantityListener((quantity) -> {
                    productsViewModel.addProductToOrder(productId, quantity);
                    navigateBack();
               });
               dialog.show(requireActivity().getSupportFragmentManager(), "QuantityDialogFragment");
          }
     }

     private class ProductsSearchOnQueryListener implements SearchView.OnQueryTextListener
     {
          @Override
          public boolean onQueryTextSubmit(String query)
          {
               return false;
          }

          @Override
          public boolean onQueryTextChange(String newText)
          {
               productsAdapter.getFilter().filter(newText);
               return true;
          }
     }
}