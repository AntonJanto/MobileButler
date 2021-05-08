package me.antonjanto.mobilebutler.repository.sqlite.converters;


import androidx.room.TypeConverter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class DateConverter
{
     @TypeConverter
     public static LocalDateTime toDate(Long timestamp)
     {
          return timestamp == null || timestamp == 0 ? null : LocalDateTime.ofInstant(new Timestamp(timestamp).toInstant(),
               TimeZone.getDefault().toZoneId());
     }

     @TypeConverter
     public static long toTimestamp(LocalDateTime dateTime)
     {
          return dateTime == null ? 0 : Timestamp
               .valueOf(dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
               .getTime();
     }
}
