import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { toast } from 'react-toastify'
import { login } from '../services/user'
import '../App.css';

function Login() {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [isEmailEmpty, setEmailEmpty] = useState(false)
  const [isPasswordEmpty, setPasswordEmpty] = useState(false)

  
  const navigate = useNavigate()

  const onLogin = async () => {
    if (email.length == 0) {
      toast.error('Please enter email')
    } else if (password.length == 0) {
      toast.error('Please enter password')
    } else {
      
      const result = await login(email, password)
    
      if (result['status']==false) {
        toast.error(result['message']);
        navigate('/register');
      } else {
        sessionStorage['token'] = result['jwt']
        sessionStorage['user'] = JSON.stringify(result['user']);
        toast.success(result['mesg'])

        const user = result['user'];
        if (user.role === 'ROLE_ADMIN') {
          navigate('/bookings');  
        } else if (user.role === 'ROLE_GUID') {
          navigate('/guide-tour-packages');  
        } else if (user.role === 'ROLE_CUSTOMER') {
          navigate('/tour-packages-all');  
        }
      }
    }
  }

  return (
    <div className="background-image d-flex align-items-center justify-content-center" style={{ minHeight: '100vh' }}>
    <div className="col-12 col-md-6 col-lg-5 p-4 bg-white rounded shadow">
      <h2 className='text-center mb-3' >Login</h2>
      <div className="form">
        <div className="mb-3">
          <label htmlFor="email">Email</label>
          <input
            id="email"
            onChange={(e) => {
              if (e.target.value.length === 0) {
                setEmailEmpty(true)
              } else {
                setEmailEmpty(false)
              }
              setEmail(e.target.value)
            }}
            type="email"
            className="form-control"
            value={email}
          />
          {isEmailEmpty && (
            <p style={{ color: 'red' }}>Email is mandatory</p>
          )}
        </div>

        <div className="mb-3">
          <label htmlFor="password">Password</label>
          <input
            id="password"
            onChange={(e) => {
              if (e.target.value.length === 0) {
                setPasswordEmpty(true)
              } else {
                setPasswordEmpty(false)
              }
              setPassword(e.target.value)
            }}
            type="password"
            className="form-control"
            value={password}
          />
          {isPasswordEmpty && (
            <p style={{ color: 'red' }}>Password is mandatory</p>
          )}
        </div>

        <div className="d-flex justify-content-between align-items-center">
          <div>
            Don't have an account? <Link to="/register">Register here</Link>
          </div>
          <button onClick={onLogin} className="btn btn-success">
            Login
          </button>
        </div>
      </div>
    </div>
  </div>
  )
}

export default Login
