import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate';

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userStatus : null,
    authToken : null,
    refToken : null,
    kakaoId : null,
    userId : null,
  },
  mutations: {
    setUserStatus(state,payload) {
      state.userStatus = payload
    },
    setAuthToken(state,payload) {
      state.authToken = payload
    },
    setRefToken(state, payload) {
      state.refToken = payload
    },
    setKakaoId(state,payload) {
      state.kakaoId = payload
    },
    setUserId(state,payload) {
      state.userId = payload
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
    },
    setUserId : function(context,payload) {
      context.commit('setUserId',payload)
    }
  },
  plugins: [
    createPersistedState()
  ]
})
