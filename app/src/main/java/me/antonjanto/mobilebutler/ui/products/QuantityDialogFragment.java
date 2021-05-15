package me.antonjanto.mobilebutler.ui.products;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

import me.antonjanto.mobilebutler.R;

public class QuantityDialogFragment extends DialogFragment
{

     private NoticeQuantityDialogListener listener;
     private EditText quantityEditText;

     public void setNoticeQuantityListener(NoticeQuantityDialogListener listener)
     {
          this.listener = listener;
     }

     @NonNull
     @NotNull
     @Override
     public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
     {
          super.onCreateDialog(savedInstanceState);


          AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

          LayoutInflater inflater = requireActivity().getLayoutInflater();

          View view = inflater.inflate(R.layout.dialog_quantity, null);

          builder.setMessage(R.string.quantity)
               .setView(view)
               .setPositiveButton(R.string.ok, (dialog, which) -> listener
                    .onDialogPositiveClick(Double.valueOf(quantityEditText.getText().toString())))
               .setNegativeButton(R.string.cancel, (dialog, which) -> {
                    dialog.cancel();
               });

          findViews(view);
          return builder.create();
     }

     private void findViews(View view)
     {
          quantityEditText = view.findViewById(R.id.product_quantity);
     }

     public interface NoticeQuantityDialogListener
     {
          void onDialogPositiveClick(double quantity);
     }

}
