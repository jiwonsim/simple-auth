import axios from 'axios'
import router from '../router'

const DOMAIN = 'http://15.164.141.101:8486'
// const DOMAIN = 'http://localhost:8486'

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
  },

  logoff() {
    return request('post', '/logoff')
  }, 

  fetch() {
    return request('get', '/service')
  }
}

export const social_auth = {
  // 소셜 계정 토큰 발급 
  issue_token(state, code, scope, authuser, prompt, session_state) {
    return request('post', '/google', {state, code, scope, authuser, prompt, session_state})
  }, 

  fetch() { // 소셜계정 정보 가져오기 
    return request('get', '/google')
  }, 

  logoff() {
    return request('post', '/google/logoff')
  }
}
export const setAuthInHeader = token => {
  axios.defaults.headers.common['Authorization'] = token ? `${token}` : null
}

const {token} = localStorage
if (token) setAuthInHeader(token)