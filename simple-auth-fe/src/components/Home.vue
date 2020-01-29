<template>
  <div class="hello">
    <h3>{{user_name}}({{user_id}})님, 환영합니다!</h3>
    
    <button v-on:click.prevent="logout">Logout</button>
  </div>
</template>

<script>
export default {
  data () {
    return {
      user_id: "",
      user_name: "", 
    }
  }, 

  created() {
    this.user_name = this.$route.query.name
    this.user_id = this.$route.query.uid
  }, 

  methods: {
    logout() {
      // 로그아웃 API 호출
      
      this.$router.go(-1)
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
