import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    //토큰을 확인하여 로그인 상태인지 판별..
    isLoginUser : localStorage.getItem('isLoginUser'),
  },
  mutations: {
    setLoginUser(state) {
      state.isLoginUser = true;
      localStorage.setItem('isLoginUser',true)
    },
    setLogoutUser(state) {
      state.isLoginUser = false;
      localStorage.setItem('isLoginUser',false)
    }
  },
  actions: {
    setLoginUser : ({commit}) => { // === function({commit})
      commit('setLoginUser')
    },
    setLogoutUser : ({commit}) => {
      commit('setLogoutUser')
    },
  },
  modules: {
  }
})
