import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate';

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userStatus : null,
    authToken : null,
    kakaoId : null,
  },
  mutations: {
    setUserStatus(state,payload) {
      state.userStatus = payload
    },
    setAuthToken(state,payload) {
      state.authToken = payload
    },
    setKakaoId(state,payload) {
      state.kakaoId = payload
    }
  },
  actions: {
    setUserStatus : function(context,payload) {
      context.commit('setUserStatus',payload)
    },
    setAuthToken : function(context,payload) {
      context.commit('setAuthToken',payload)
    },
    setKakaoId : function(context,payload) {
      context.commit('setKakaoId',payload)
    }
  },
  plugins: [
    createPersistedState()
  ]
})
