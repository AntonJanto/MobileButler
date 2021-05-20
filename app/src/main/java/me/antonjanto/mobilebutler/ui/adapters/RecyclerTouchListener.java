package me.antonjanto.mobilebutler.ui.adapters;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener
{
     private GestureDetector gestureDetector;
     private ClickListener clickListener;

     public RecyclerTouchListener(Context context, RecyclerView recyclerView, ClickListener clickListener)
     {
          this.clickListener = clickListener;
          gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener()
          {
               @Override
               public boolean onSingleTapUp(MotionEvent e)
               {
                    return true;
               }

               @Override
               public void onLongPress(MotionEvent e)
               {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());

                    if (child != null && clickListener != null)
                    {
                         clickListener.onLongClick(recyclerView.getChildAdapterPosition(child));
                    }
               }
          });
     }

     @Override
     public boolean onInterceptTouchEvent(@NonNull @NotNull RecyclerView rv,
          @NonNull @NotNull MotionEvent e)
     {
          View child = rv.findChildViewUnder(e.getX(), e.getY());

          if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {

               clickListener.onClick(rv.getChildAdapterPosition(child));

          }
          return false;
     }

     @Override
     public void onTouchEvent(@NonNull @NotNull RecyclerView rv, @NonNull @NotNull MotionEvent e)
     {

     }

     @Override
     public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept)
     {

     }

     public interface ClickListener {
          void onClick(int position);
          void onLongClick(int position);
     }
}
