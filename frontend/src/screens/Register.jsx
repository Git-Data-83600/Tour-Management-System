import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { toast } from 'react-toastify'
import { register } from '../services/user'


function Register() {
  const [name, setName] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [confirmPassword, setConfirmPassword] = useState('')
  const [role, setRole] = useState('')

  // get the navigation hook
  const navigate = useNavigate()

  const onRegister = async () => {
    if (name.length == 0) {
      toast.error('please enter name')
    } else if (email.length == 0) {
      toast.error('please enter email')
    } else if (password.length == 0) {
      toast.error('please enter password')
    } else if (confirmPassword.length == 0) {
      toast.error('please confirm the password')
    } else if (password != confirmPassword) {
      toast.error('password does not match')
    }else if(!role){
      toast.error('please select role')
    }else {
     
      const result = await register(name, email, password, role)
      if (result['status'] == true) {
        toast.success('Successfully registered a new user')
        navigate('/login')
      }else{
        toast.error(result['message'])
      }
    }
  }

  return (
    <div className="background-image d-flex align-items-center justify-content-center" style={{ minHeight: '100vh' }}>
    <div className="col-12 col-md-6 col-lg-5 p-4 bg-white rounded shadow">
      <h2 className='text-center mb-3'>Register</h2>
          <div className='form'>
            <div className='mb-3'>
              <label htmlFor=''>Name</label>
              <input
                onChange={(e) => setName(e.target.value)}
                type='text'
                className='form-control'
              />
            </div>
            <div className='mb-3'>
              <label htmlFor=''>Email</label>
              <input
                onChange={(e) => setEmail(e.target.value)}
                type='email'
                className='form-control'
              />
            </div>
            <div className='mb-3'>
              <label htmlFor=''>Password</label>
              <input
                onChange={(e) => setPassword(e.target.value)}
                type='password'
                className='form-control'
              />
            </div>
            <div className='mb-3'>
              <label htmlFor=''>Confirm Password</label>
              <input
                onChange={(e) => setConfirmPassword(e.target.value)}
                type='password'
                className='form-control'
              />
            </div>
            <div className="mb-3">
                <label htmlFor="">Role</label>
                <select
                  id="role"
                  className="form-control"
                  value={role}
                  onChange={(e) => setRole(e.target.value)}
                >
                  <option value="">Select Role</option>
                  <option value="ROLE_ADMIN">ADMIN</option>
                  <option value="ROLE_GUID">GUID</option>
                  <option value="ROLE_CUSTOMER">CUSTOMER</option>
                </select>
              </div>
            <div className="d-flex justify-content-between align-items-center">
              <div>
                Already have an account ? <Link to='/login'>Login here</Link>
              </div>
              <button onClick={onRegister} className='btn btn-success mt-2'>
                Register
              </button>
            </div>
          </div>
        </div>
      </div>
    
  )
}

export default Register
