import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import { config } from '../services/config';

const GuideTourPackage = () => {
  const [tourPackages, setTourPackages] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const user = JSON.parse(sessionStorage.getItem('user')); 
    const guideId = user.id;

    const fetchTourPackages = async () => {
      try {
        const token = sessionStorage['token'];
        const response = await axios.get(`${config.serverUrl}/tour-packages/${guideId}`, {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        });
        console.log(response.data);
        setTourPackages(response.data);
      } catch (error) {
        setError('Failed to load tour packages.');
        toast.error('Error fetching tour packages.');
        console.log(error);
      } finally {
        setLoading(false);
      }
    };

    fetchTourPackages();
  }, [navigate]);

  const handleEdit = (id) => {
    navigate(`/tour-package/edit/${id}`);
  };

  const handleDelete = async (id) => {
    try {
      const token = sessionStorage['token'];
      const response = await axios.delete(`${config.serverUrl}/tour-packages/${id}`, {
        headers: { 'Authorization': `Bearer ${token}` }
      });
      toast.success(response.data.message);
      setTourPackages(tourPackages.filter(tour => tour.id !== id)); 
    } catch (error) {
      toast.error('Failed to delete tour package.');
      console.log(error);
    }
  };

  const handleAddTourPackage = () => {
    navigate('/tour-packages');
  };

  const handleLogout = () => {
    sessionStorage.removeItem('user'); 
    sessionStorage.removeItem('token'); 
    toast.success('Logged out successfully');
    navigate('/login'); 
  };

  if (loading) {
    return <div className="text-center">Loading...</div>;
  }

  if (error) {
    return <div className="text-center text-danger">{error}</div>;
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
          Your Tour Packages
        </h1>
      </header>

      <div className="container mt-4">
        <div className="d-flex justify-content-between mb-3">
          <button className="btn btn-success" onClick={handleAddTourPackage}>
            Add Tour Package
          </button>
          <button className="btn btn-danger" onClick={handleLogout}>
            Logout
          </button>
        </div>

        <div className="table-responsive">
          <table className="table table-bordered">
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Price</th>
                <th>Duration</th>
                <th>Actions</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {tourPackages.map((tour) => (
                <tr key={tour.id}>
                  <td>{tour.id}</td>
                  <td>{tour.name}</td>
                  <td>${tour.price}</td>
                  <td>{tour.duration} days</td>
                  <td>
                    <button className="btn btn-warning btn-sm" onClick={() => handleEdit(tour.id)}>
                      Edit
                    </button>
                  </td>
                  <td>
                    <button className="btn btn-danger btn-sm ml-2" onClick={() => handleDelete(tour.id)}>
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>

      <footer className="bg-secondary text-white text-center p-3 mt-5">
        <p>© 2024 ExploreTours. All rights reserved.</p>
      </footer>
    </div>
  );
};

export default GuideTourPackage;
