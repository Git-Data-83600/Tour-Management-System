import React, { useEffect, useState } from 'react';
import axios from 'axios';
import TourPkgItem from './TourPkgItem';
import { toast } from 'react-toastify';
import { config } from '../services/config';

const TourPkgList = () => {
  const [tourPackages, setTourPackages] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchTourPackages = async () => {
      try {
        const token = sessionStorage['token'];
        const response = await axios.get(`${config.serverUrl}/tour-packages`,{
          headers: {
            'Authorization': `Bearer ${token}`,
          },
        });
        setTourPackages(response.data);
      } catch (error) {
        toast.error('Something Went Wrong..!');
      } finally {
        setLoading(false);
      }
    };

    fetchTourPackages();
  }, []);

  if (loading) {
    return <div className="text-center">Loading...</div>;
  }

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
        <div className="row">
          {tourPackages.map((tour) => (
            <div className="col-md-4" key={tour.id}>
              <TourPkgItem tour={tour} />
            </div>
          ))}
        </div>
      </div>

     
      <footer className="bg-secondary text-white text-center p-3 mt-5">
        <p>© 2024 ExploreTours. All rights reserved.</p>
      </footer>
    </div>
  );
};

export default TourPkgList;
