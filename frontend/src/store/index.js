import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    //토큰을 확인하여 로그인 상태인지 판별..
    isLoginUser : false,
  },
  mutations: {
    setloginUser(state) {
      state.isLoginUser = true;
    },
    setlogoutUser(state) {
      state.isLoginUser = false;
    }
  },
  actions: {
    setloginUser : ({commit}) => { // function({commit})
      commit('setloginUser')
    }
  },
  modules: {
  }
})
