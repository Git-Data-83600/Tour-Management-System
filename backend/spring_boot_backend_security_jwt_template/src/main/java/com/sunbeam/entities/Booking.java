package com.sunbeam.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import java.time.LocalDate;



@Entity
@Table(name = "bookings")
@NoArgsConstructor
@AllArgsConstructor 
@Getter
@Setter
@ToString
public class Booking extends BaseEntity{
    
  
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_package_id")
    private TourPackage tourPackage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private User customer;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private LocalDate bookingDate;


}
