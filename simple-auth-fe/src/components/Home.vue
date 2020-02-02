<template>
  <div class="hello">
    <div v-if="is_successful_in_call">
    <h3>{{user_name}}({{user_id}})님, 환영합니다!</h3>
    </div>
    <div v-else>
      <h3>유효하지 않은 정보입니다.</h3>
    </div>
    
    <button v-on:click.prevent="logout">Logout</button>
    
    <div v-if="is_error_of_account">
      <ul><li>
        <h3>계정에 문제가 생겼습니다.</h3>
      </li></ul>
      <ul><li>
        <router-link to="/login">Back to LOGIN</router-link>
      </li></ul>
      <ul><li>
        <router-link to="/join">Back to JOIN</router-link>
      </li></ul>
    </div>
  </div>
</template>

<script>
import {auth, setAuthInHeader, social_auth} from '../api'

export default {
  data () {
    return {
      user_id: '',
      user_name: '', 
      logoff_message: '',
      is_error_of_account: false,
      is_successful_in_call: false,
      STATE: '', 
      CODE: '',
      SCOPE: '',
      AUTHUSER: '',
      PROMPT: '',
      SESSION_STATE: '',
      token: '', 
      is_social_account: false
    }
  }, 

  created() {
    console.log(this.$route.query)

    if (this.$route.query.state === undefined) { 
      // 계정 로그인
      this.is_social_account = false 
      console.log('login by account')
      this.fetch_data()
    }
    else { 
      // 소셜 로그인
      this.is_social_account = true
      console.log('login by social')
      this.STATE = this.$route.query.state
      this.CODE = this.$route.query.code
      this.SCOPE = this.$route.query.scope
      this.AUTHUSER = this.$route.query.authuser
      this.PROMPT = this.$route.query.prompt
      this.SESSION_STATE = this.$route.query.session_state

      this.issue_social_token();
    }
  }, 

  methods: {
    logout() {
        auth.logoff()
        .then(data => {
          if (data.statusCode === 200) {
            // token 해제 
            delete localStorage.token
            setAuthInHeader(null)

            console.log(this.$router)
            this.$router.push('/')
          }
          else {
            this.is_error_of_account = true
          }
        })
    },

    issue_social_token() {
      social_auth.issue_token(this.STATE, this.CODE, this.SCOPE, this.AUTHUSER, this.PROMPT, this.SESSION_STATE)
      .then(data => {
        if (data.statusCode === 200) {
          // token 발급 성공
          this.is_successful_in_call = true
          this.token = data.responseData

          this.fetch_social_user();
        }
      })
    }, 

    fetch_social_user() {
      // header에 token 장착
      localStorage.setItem('token', this.token)
      setAuthInHeader(this.token)

      social_auth.fetch()
      .then(data => {
        if (data.statusCode === 200) {
          this.user_name = data.responseData
          this.user_id = "구글계정"
        }
        else {
          is_error_of_account = true
        }
      })      
    },

    fetch_data() {
      auth.fetch()
      .then(data => {
        if (data.statusCode === 200) {
          this.is_successful_in_call = true
          this.user_id = data.responseData.uid
          this.user_name = data.responseData.name
        }
      })
    }
  }
}
</script>

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
