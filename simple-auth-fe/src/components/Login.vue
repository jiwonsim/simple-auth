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
    </form>

    <h3>{{response_msg}}</h3>


  </div>
</template>

<script>
import {auth} from '../api'

export default {
  name: 'HelloWorld',
  data () {
    return {
      user_id: "",
      user_pwd: "",
      response_msg: ""
    }
  }, 

  methods : {
    submitted() {
        auth.login(this.user_id, this.user_pwd)
        .then(data => {
          if (data.responseData === null) {
            console.log("??")
            this.response_msg = data.responseMessage
          }
          else {
            console.log("얍")
            this.$router.push({
              path: 'home',
              query: {
                name: data.responseData.name,
                uid: data.responseData.uid
              }
            })
          }
        })
    }
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
