import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import { config } from '../services/config';

const UserList = () => {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  
  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const token = sessionStorage.getItem('token');
        const response = await axios.get(`${config.serverUrl}/users`, {
          headers: {
            'Authorization': `Bearer ${token}`,
          },
        });
        setUsers(response.data);
      } catch (error) {
        //console.log(error);
        toast.error('Failed to load users.');
      } finally {
        setLoading(false);
      }
    };

    fetchUsers();
  }, []);

  const handleEdit = (userId) => {
    navigate(`/user/edit/${userId}`);
  };

 
  const handleDelete = async (userId) => {
    try {
      const token = sessionStorage.getItem('token');
      const response = await axios.delete(`${config.serverUrl}/users/${userId}`, {
        headers: { 'Authorization': `Bearer ${token}` },
      });
      toast.success(response.data.message);
      setUsers(users.filter((user) => user.id !== userId));
    } catch (error) {
      toast.error('Failed to delete user.');
    //   console.log(error);
    }
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

  return (
    <div className="user-list-container">
      <header
        style={{
          backgroundColor: '#6c757d',
          color: 'white',
          padding: '30px 0',
          textAlign: 'center',
        }}
      >
        <h1 style={{ fontFamily: 'Roboto, sans-serif', fontSize: '2.5rem', fontWeight: 'bold' }}>
          User List
        </h1>
      </header>

      <div className="container mt-4">
        <div className="d-flex justify-content-between mb-3">
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
                <th>Email</th>
                <th>Role</th> 
                <th>Actions</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {users.map((user) => (
                <tr key={user.id}>
                  <td>{user.id}</td>
                  <td>{user.name}</td>
                  <td>{user.email}</td>
                  <td>{user.role}</td> 
                  <td>
                   
                      <button
                        className="btn btn-warning btn-sm"
                        onClick={() => handleEdit(user.id)}
                      >
                        Edit
                      </button>
                  </td>

                  <td>
                      <button
                        className="btn btn-danger btn-sm ml-2"
                        onClick={() => handleDelete(user.id)}
                      >
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

export default UserList;
