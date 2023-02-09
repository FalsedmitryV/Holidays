package com.example.p3.model;

public class ListHolidaysInfo
{
    public HolidayInfo[] ListHolidaysInfo;

    public ListHolidaysInfo(int size)
    {
        ListHolidaysInfo = new HolidayInfo[size];
    }

    public void addHoliday (String holiday_name, String holiday_date, int id)
    {
        HolidayInfo holiday = new HolidayInfo(holiday_name, holiday_date);
        ListHolidaysInfo[id] = holiday;
    }
}
