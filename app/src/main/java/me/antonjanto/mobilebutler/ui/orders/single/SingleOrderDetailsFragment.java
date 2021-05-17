package me.antonjanto.mobilebutler.ui.orders.single;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import org.jetbrains.annotations.NotNull;

import java.time.format.DateTimeFormatter;

import me.antonjanto.mobilebutler.R;
import me.antonjanto.mobilebutler.ui.Converter;

public class SingleOrderDetailsFragment extends Fragment
{
     private SingleOrderViewModel mViewModel;
     private EditText orderIdEditText;
     private EditText tableNumberEditText;
     private TextView closedDateTextView;

     public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
          Bundle savedInstanceState)
     {
          super.onCreate(savedInstanceState);
          mViewModel = new ViewModelProvider(this).get(SingleOrderViewModel.class);
          setHasOptionsMenu(true);
          return inflater.inflate(R.layout.fragment_single_order_details, container, false);
     }

     @Override
     public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
     {
          super.onViewCreated(view, savedInstanceState);

          orderIdEditText = view.findViewById(R.id.single_order_detail_orderId);
          tableNumberEditText = view.findViewById(R.id.single_order_detail_table);
          closedDateTextView = view.findViewById(R.id.single_order_detail_closed);

          long orderId;
          if (getArguments() != null) {
               orderId = getArguments().getLong("orderId");
               mViewModel.init(orderId);
               mViewModel.getOrder().observe(getViewLifecycleOwner(), (o) -> {
                    orderIdEditText.setText(String.valueOf(o.getOrderId()));
                    tableNumberEditText.setText(String.valueOf(o.getTableId()));
                    if (o.getClosedTime() != null)
                         closedDateTextView.setText(o.getClosedTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    else
                         closedDateTextView.setText(R.string.not_closed);
               });
          }
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
                    = SingleOrderFragmentDirections.actionNavSingleOrderToSingleOrderClosedFragment(orderId, Converter
                    .toInteger(orderId));
               NavHostFragment.findNavController(this).navigate(action);
          }
          return super.onOptionsItemSelected(item);
     }
}
