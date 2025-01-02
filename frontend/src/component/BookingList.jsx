import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';
import { config } from '../services/config';
import { useNavigate } from 'react-router-dom';

const BookingList = () => {
  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();


  useEffect(() => {
    const fetchBookings = async () => {
      try {
        const token = sessionStorage['token'];
        const response = await axios.get(`${config.serverUrl}/bookings`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        setBookings(response.data);
      } catch (error) {
        toast.error('Error fetching bookings');
        console.error(error);
      } finally {
        setLoading(false);
      }
    };

    fetchBookings();
  }, []);

 
  const handleDelete = async (id) => {
    try {
      const token = sessionStorage['token'];
      const response = await axios.delete(`${config.serverUrl}/bookings/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      toast.success('Booking deleted successfully');
      setBookings(bookings.filter((booking) => booking.id !== id));
    } catch (error) {
      toast.error('Error deleting booking');
      console.error(error);
    }
  };

  
  const handleLogout = () => {
    sessionStorage.removeItem('user');
    sessionStorage.removeItem('token');
    toast.success('Logged out successfully');
    navigate('/login');
  };

  const getStatusColor = (status) => {
    if (status === 'CONFIRMED') {
      return 'green';
    } else if (status === 'CANCELED') {
      return 'red';
    }
    return 'orange'; 
  };

 
  const handleGetAllUsers = () => {
    navigate('/users');
  };

  if (loading) {
    return <div className="text-center">Loading bookings...</div>;
  }

  return (
    <div className="container mt-4">
      <h2>Bookings</h2>

      <div className="d-flex justify-content-between mb-3">
        <button className="btn btn-success" onClick={handleGetAllUsers}>
          Get All Users
        </button>
        <button className="btn btn-danger" onClick={handleLogout}>
          Logout
        </button>
      </div>

 
      <table className="table table-bordered mt-3">
        <thead>
          <tr>
            <th>ID</th>
            <th>Status</th>
            <th>Booking Date</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {bookings.map((booking) => (
            <tr key={booking.id}>
              <td>{booking.id}</td>
              <td style={{ color: getStatusColor(booking.status) }}>
                {booking.status}
              </td>
              <td>{booking.bookingDate}</td>
              <td>
                <button
                  className="btn btn-danger btn-sm"
                  onClick={() => handleDelete(booking.id)}
                >
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default BookingList;
