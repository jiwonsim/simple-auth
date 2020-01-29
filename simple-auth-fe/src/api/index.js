import axios from 'axios'
import router from '../router'

const DOMAIN = 'http://localhost:8486'

const request = (method, url, data) => {
  return axios({
    method,
    url : DOMAIN + url,
    data
  }).then(result => result.data)
  .catch(result => {
    throw Error(result) 
  })
}

export const auth = {
  login(uid, password) {
    return request('post', '/login', {uid, password})
  }, 

  join(name, uid, password) {
    return request('post', '/join', {name, uid, password})
  }
}