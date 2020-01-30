// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import VueRouter from 'vue-router'
// import GAuth from 'vue-google-oauth2'

// Vue.use(VueRouter)
// Vue.use(GAuth, {
//   client_id: '5626016189-14nstjk146gm5o3cabmv5duiceuq95qb.apps.googleusercontent.com',
//   scope: 'https://www.googleapis.com/auth/userinfo.email'
// })

Vue.config.productionTip = false

/*
Https://accounts.google.com/o/oauth2/v2/auth? 
scope=https://www.googleapis.com/auth/userinfo.email&
access_type=offline&
Include-granted_scopes=true&
state=state_parameter_passthrough_value&
redirect_uri=http://localhost:8627/callback&
response_type=code&
client_id=5626016189-14nstjk146gm5o3cabmv5duiceuq95qb.apps.googleusercontent.com
*/


/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})

