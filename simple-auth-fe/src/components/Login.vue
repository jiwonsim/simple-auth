<template>
  <div class="hello">
    <h2>로그인</h2>
    
    <form action="">
      <ul>
        <li>
          <input v-model.lazy="user_id" placeholder="ID">
        </li>
      </ul>
      <ul>
        <li>
      <input type="password" v-model.lazy="user_pwd" placeholder="PASSWORD"> 
        </li>
      </ul>
      <ul>
        <li>
      <button v-on:click.prevent="submitted">Login</button>
        </li>
      </ul>

      <router-link to="/join">
        <button>Join</button>
      </router-link>

      <ul>
        <li>
          <a :href=google_url>
            Google 로그인
          </a>
        </li>
      </ul>
      
    </form>

    <h3>{{response_msg}}</h3>


  </div>
</template>

<script>
import {auth, setAuthInHeader} from '../api'

export default {
  name: 'HelloWorld',
  data () {
    return {
      user_id: '',
      user_pwd: '',
      response_msg: '',
      token: '', 

      is_local_storage: false, 
      existing_local_storage: '', 

      CLIENT_ID: '5626016189-14nstjk146gm5o3cabmv5duiceuq95qb.apps.googleusercontent.com', 
      REDIRECT_URI: 'http://www.jiwonxdoori.tk/home',
      // REDIRECT_URI: 'http://localhost:8627/home',
      SCOPE: 'https://www.googleapis.com/auth/userinfo.email',
      ACCESS_TYPE: 'offline',
      INCLUD_GRANTED_SCOPES: 'true',
      STATE: 'state_parameter_passthrough_value',
      RESPONSE_TYPE: 'code',
      google_url: 'https://accounts.google.com/o/oauth2/v2/auth'
    }
  },

  created () {
    this.existing_local_storage = localStorage.getItem('token')
    console.log('token : ' + this.existing_local_storage);

    if (this.existing_local_storage !== null) { // localStorage에 token이 있는 경우
      this.keep_login()
    }

    this.google_url += '?scope=' + this.SCOPE
    this.google_url += '&Include-granted_scopes=' + this.INCLUD_GRANTED_SCOPES
    this.google_url += '&access_type=' + this.ACCESS_TYPE
    this.google_url += '&state=' + this.STATE
    this.google_url += '&redirect_uri=' + this.REDIRECT_URI
    this.google_url += '&response_type=' + this.RESPONSE_TYPE
    this.google_url += '&client_id=' + this.CLIENT_ID
  }, 

  methods : {
    submitted() {
      auth.login(this.user_id, this.user_pwd)
      .then(data => {
        if (data.responseData === null) {
          this.response_msg = data.responseMessage
        }
        else {
          localStorage.setItem('token', data.responseData.token)
          setAuthInHeader(data.responseData.token)
          
          this.$router.push({
            path: 'home'
          })
        }
      })
    }, 

    keep_login() {
      this.$router.push({
        path: 'home'
      })
    }


    // google_login() {
    //   console.log("냨")
    //   this.$gAuth.getAuthCode()
    //   .then(authCode => {
    //     console.log("authCode : " + authCode)
    //   })
    //   .then(response => {
    //     console.log("response : " + response)
    //   })
    //   .catch(error => {
    //     console.log("error : " + error)
    //   })
    // }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
