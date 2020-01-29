<template>
  <div>
    <h2>회원가입</h2>
    
    <form action="">
      <ul>
        <li>
          <input v-model="user_name" placeholder="NAME">
        </li>
      </ul>
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
      <button v-on:click.prevent="submitted_join">Join</button>
        </li>
      </ul>
    </form>

      <h3>{{result_msg}}</h3>
      </div>
</template>

<script>
import {auth} from '../api'

export default {
  name: 'Join', 

  data () {
    return {
      user_name: "",
      user_id: "",
      user_pwd: "",
      result_msg : ""
    }
  }, 

  methods: {
    submitted_join() {
      // 서버 통신 성공시 
      console.log(this.user_name)
      auth.join(this.user_name, this.user_id, this.user_pwd)
      .then(data => {
        console.log(data.statusCode)
        if (data.statusCode === 201) this.$router.push("/")
        else this.result_msg = data.responseMessage
      })
      .catch(err => {
        console.log(err)
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