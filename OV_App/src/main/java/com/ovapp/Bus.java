package com.ovapp;

import java.util.*;
public class Bus extends meansOfTransport{

    Bus(String name, List<Integer> schedule){
        super(name, schedule);
    }
    String getTransportName(){
        return name;
    }
    List<Integer> getTransportSchedule(){
        return schedule;
    }
    ArrayList<String> getDepartureTime(List<Integer> schedule, int departureHours, int departureMinutes){
        this.schedule = schedule;
        int scheduleListSpot = 0;
        int difference = 100;
        for(int i=0; i < this.schedule.size(); i++){
            int closestDepartureTime = Math.abs(this.schedule.get(i) - departureMinutes);
            if (closestDepartureTime < difference){
                if (this.schedule.get(i) >= departureMinutes) {
                    scheduleListSpot = i;
                    difference = closestDepartureTime;
                }
            }
        }
        int definitiveDepartureMinutes = this.schedule.get(scheduleListSpot);
        if(definitiveDepartureMinutes == 85){
            definitiveDepartureMinutes = 25;
        }
        //ScheduleListSpot is tested against schedule.size - 1 because the schedule list of the Bus class contains
        //25 and 85 to allow the code to determine the departure time of the journey to work.
        if(scheduleListSpot == this.schedule.size() - 1){
            departureHours += 1;
            if(departureHours == 24){
                departureHours = 0;
            }
        }
        ArrayList<String> departureTimes = new ArrayList<>();
        departureTimes.add(String.format("%02d:%02d", departureHours, definitiveDepartureMinutes));
        return departureTimes;
    }
}
