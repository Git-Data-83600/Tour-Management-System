package com.sunbeam.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User extends BaseEntity{
		private String name;
		private String email;
	    private String password;
	    @Enumerated(EnumType.STRING)
	    private Role role; 

	    @OneToMany(mappedBy = "guide", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	    private List<TourPackage> tourPackages = new ArrayList<>();

	    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	    private List<Booking> bookings = new ArrayList<>();


	    public void addTourPackage(TourPackage tourPackage) {
	        tourPackages.add(tourPackage);
	        tourPackage.setGuide(this);
	    }

	    public void removeTourPackage(TourPackage tourPackage) {
	        tourPackages.remove(tourPackage);
	        tourPackage.setGuide(null);
	    }

	    public void addBooking(Booking booking) {
	        bookings.add(booking);
	        booking.setCustomer(this);
	    }

	    public void removeBookings(List<Booking> bookings) {
	        for (Booking booking : bookings) {
	            bookings.remove(booking);
	            booking.setCustomer(null);  
	        }
	    }
	    
	    public void removeBooking(Booking booking) {
	            bookings.remove(booking);
	            booking.setCustomer(null);  
	    }

}
