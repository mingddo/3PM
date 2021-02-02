<template>
  <div class="side-bar"> 
      <div class="nav-toggle-switch" @click="sideBarToggle">
        <div>
          <i class="fas fa-bars"></i>
        </div>
      </div>
      <div class="nav-logo" @click="goToHome">
        <i class="far fa-clock"></i>
        오후 세시.
      </div>
      <div class="nav-content" v-if="userStatus">
        <div @click="goToPersonal">
          <i class="fas fa-house-user">꽃보다 집</i>
        </div>
        <div @click="goToSearch">
          <i class="fas fa-search">검색</i>
        </div>
        <div @click="goToProfile">
          <i class="fas fa-user">프로필</i>
        </div>
      </div>

      <div class='overlay' @click="sideBarToggle">
        <div class="nav-toggle-bar-before">
          <div class="nav-logo">
            <i class="far fa-clock"></i>
            오후 세시.
          </div>
          <div @click="goToPersonal">
            <i class="fas fa-house-user">꽃보다 집</i>
          </div>
          <div @click="goToSearch">
            <i class="fas fa-search">검색</i>
          </div>
          <div @click="goToProfile">
            <i class="fas fa-user">프로필</i>
          </div>
        </div>
      </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'Nav',
  data() {
    return {
      toggleStatus : false,
    };
  },
  created () {
    const body = document.querySelector('body')
    if (body.style.overflow !== 'auto') {
      body.style.overflow = 'auto'
    }
  },
  methods: {
    goToHome () {
      this.$router.push({name: 'Home'})
    },
    goToSearch () {
      this.$router.push({name: 'Search'})
    },
    goToPersonal () {
      this.$router.push({name: 'NewsfeedPersonal', query: { Category: '꽃보다 집'}})
    },
    goToProfile () {
      this.$router.push({name: 'MyPage'})
    },
    sideBarToggle(e) {
      const toggleBar = document.querySelector('.nav-toggle-bar-before')
      const overlay = document.querySelector('.overlay')
      const body = document.querySelector('body')
      if(overlay.classList.contains('disappear')) { // 사이드바 사라지기
        if (e.target !== overlay) return
        body.style.overflow = 'auto'
        overlay.classList.remove('disappear')
        overlay.style.cssText = "display: block;background-color: rgba(0,0,0,0);"
        toggleBar.style= 'transform :translateX(0px);'
        setTimeout(() => {
          overlay.style = "display: none;"
        }, 500);
      }
      else { // 사이드바 나타나기
        overlay.classList.add('disappear')
        body.style.overflow = 'hidden'
        overlay.style.cssText = "display: block;background-color: rgba(0,0,0,0.5);"
        setTimeout(() => {
          toggleBar.style = 'transform : translateX(250px);'
        }, 10);
      }
    },
  },
  computed : {
    ...mapState({
      userStatus : (state) => state.userStatus,
    })
  },
};
</script>

<style>

</style>