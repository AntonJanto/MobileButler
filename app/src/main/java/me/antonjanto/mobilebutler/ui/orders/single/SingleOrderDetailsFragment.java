package me.antonjanto.mobilebutler.ui.orders.single;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.time.format.DateTimeFormatter;

import me.antonjanto.mobilebutler.R;

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
}
