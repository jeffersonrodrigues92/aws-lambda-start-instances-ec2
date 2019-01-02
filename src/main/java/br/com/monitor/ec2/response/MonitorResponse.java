package br.com.monitor.ec2.response;

import com.amazonaws.services.ec2.model.Reservation;

public class MonitorResponse {

    private Reservation reservation;
    private Integer status;

    public MonitorResponse(Reservation reservation, Integer status){
        this.reservation = reservation;
        this.status = status;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
