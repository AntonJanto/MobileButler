package me.antonjanto.mobilebutler.repository.sqlite;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import me.antonjanto.mobilebutler.model.Order;
import me.antonjanto.mobilebutler.model.OrderItem;
import me.antonjanto.mobilebutler.model.Table;

@Database(entities = {Order.class, OrderItem.class, Table.class}, version = 1)
public abstract class MobileButlerDatabase extends RoomDatabase
{
     private static MobileButlerDatabase instance;
     public abstract OrderDao orderDao();

     public static synchronized MobileButlerDatabase getInstance(Context context)
     {
          if (instance == null)
          {
               instance = Room.databaseBuilder(context.getApplicationContext(),
                    MobileButlerDatabase.class, "mobile_butler_database")
                    .fallbackToDestructiveMigration()
                    .build();
          }
          return instance;
     }
}
