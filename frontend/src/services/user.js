import axios from "axios"
import { config } from "./config"


export async function register(name, email, password, role) {
    try {
      // post body
      const body = {name, email, password, role}

      // send the post request
      const response = await axios.post(
        `${config.serverUrl}/users/signup`,
        body
      )
      // return the json body from response object
      return response.data
    } catch (ex) {
      console.log(`exception: `, ex)
    }
  
    return null
  }

  export async function login(email, password) {
    const body = { email, password }
    try {
      const response = await axios.post(`${config.serverUrl}/users/signin`, body)
      return response.data

    } catch (ex) {
       console.log(`exception: `, ex)
      return ex.response.data
    }

  }