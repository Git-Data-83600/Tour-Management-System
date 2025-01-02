import React, { useEffect, useState } from 'react';
import axios from 'axios';
import TourPkgItem from './TourPkgItem';
import { toast } from 'react-toastify';
import { config } from '../services/config';
import { useNavigate } from 'react-router-dom';

const TourPkgList = () => {
  const [tourPackages, setTourPackages] = useState([]);
  const [loading, setLoading] = useState(true);
  const [user, setUser] = useState(null);

  const navigate = useNavigate();

  useEffect(() => {
    const fetchTourPackages = async () => {
      try {
        const response = await axios.get(`${config.serverUrl}/tour-packages`);
        setTourPackages(response.data);
      } catch (error) {
        toast.error('Something Went Wrong..!');
      } finally {
        setLoading(false);
      }
    };

   
    const loggedUser = sessionStorage.getItem('user');
    if (loggedUser) {
      try {
        setUser(JSON.parse(loggedUser));
      } catch (error) {
        setUser(null);
      }
    } else {
      setUser(null);
    }

    fetchTourPackages();
  }, []);

  if (loading) {
    return <div className="text-center">Loading...</div>;
  }

  const handleBooking = async (tourId) => {
    if (!user) {
      toast.error('Please First Login..!');
      navigate('/login');
    } else if (user.role !== 'ROLE_CUSTOMER') {
      toast.error('Only customers can book a tour!');
    } else {
      try {
        const response = await axios.post(
          `${config.serverUrl}/bookings`,
          { tourPackageId: tourId, customerId: user.id },
          {
            headers: { Authorization: `Bearer ${sessionStorage.getItem('token')}` },
          }
        );
        console.log(response);
        toast.success(response.data.message);
      } catch (error) {
        console.error('Error booking tour:');
      }
    }
  };

  const handleLogout = () => {
    sessionStorage.removeItem('user');
    sessionStorage.removeItem('token');
    setUser(null);
    toast.success('Logged out successfully');
    navigate('/'); 
  };

  const handleLogin = () => {
    navigate('/login');
  };

  return (
    <div className="tour-package-container">
      <header
        style={{
          backgroundColor: '#6c757d',
          color: 'white',
          padding: '30px 0',
          textAlign: 'center',
        }}
      >
        <h1 style={{ fontFamily: 'Roboto, sans-serif', fontSize: '2.5rem', fontWeight: 'bold' }}>
          Welcome to Our Tour Packages
        </h1>
        <p style={{ fontFamily: 'Arial, sans-serif', fontSize: '1.25rem' }}>
          Explore the world with our exclusive tour packages
        </p>
      </header>

      <div className="container mt-4">

        {!user ? (
          <div className="text-end mb-3">
            <button className="btn btn-primary" onClick={handleLogin}>
              Login
            </button>
          </div>
        ) : (
          
          <div className="text-end mb-3">
            
            <div
              className="d-flex align-items-center"
              style={{
                backgroundColor: '#87CEEB',
                padding: '8px 15px',
                borderRadius: '25px',
                color: 'white',
                fontWeight: 'bold',
                fontSize: '1.1rem',
              }}
            >
              <span className="me-3">Hello, {user.name}!</span>
              <button className="btn btn-danger btn-sm" onClick={handleLogout}>
                Logout
              </button>
            </div>
          </div>
        )}

        <div className="container mt-4">
          <div className="row">
            {tourPackages.map((tour) => (
              <div className="col-md-4" key={tour.id}>
                <TourPkgItem tour={tour} onBook={() => handleBooking(tour.id)} />
              </div>
            ))}
          </div>
        </div>
      </div>

      <footer className="bg-secondary text-white text-center p-3 mt-5">
        <p>© 2024 ExploreTours. All rights reserved.</p>
      </footer>
    </div>
  );
};

export default TourPkgList;
