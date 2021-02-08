<template>
  <div class="side-bar"> 
      <div class="nav-toggle-switch" @click="sideBarToggle">
        <div style="padding-left : 15px;">
          <i class="fas fa-bars"></i>
        </div>
      </div>
      <div class="nav-logo">
        <i class="far fa-clock">카테고리이름</i>
      </div>
      <div class="nav-toggle-switch">
        <div style="padding-right : 15px;">
          <i class="fas fa-search"></i>
        </div>
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
            카테고리이름
          </div>
          <div @click="onClickSidebarPersonal">
            <i class="sidebar-fas fas fa-house-user">꽃보다 집</i>
          </div>
          <div @click="onClickSidebarSearch">
            <i class="sidebar-fas fas fa-search">검색</i>
          </div>
          <div @click="onClickSidebarProfile">
            <i class="sidebar-fas fas fa-user">프로필</i>
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
  methods: {
    sideBarToggle(e) {
      const toggleBar = document.querySelector('.nav-toggle-bar-before')
      const overlay = document.querySelector('.overlay')
      if(overlay.classList.contains('disappear')) { // 사이드바 사라지기
        if (e.target.classList.value.split(' ').includes('sidebar-fas') || e.target === overlay) {

          overlay.classList.remove('disappear')
          overlay.style.cssText = "display: block;background-color: rgba(0,0,0,0);"
          toggleBar.style= 'transform :translateX(0px);'
          setTimeout(() => {
            overlay.style = "display: none;"
          }, 500);
        }
      }
      else { // 사이드바 나타나기
        overlay.classList.add('disappear')
        overlay.style.cssText = "display: block;background-color: rgba(0,0,0,0.5);"
        setTimeout(() => {
          toggleBar.style = 'transform : translateX(250px);'
        }, 10);
      }
    },
    goToHome () {
      this.$router.push({name: 'Home'})
      .catch(err => {if (err.name === "NavigationDuplicated") {location.reload(); }})

    },
    goToSearch () {
      this.$router.push({name: 'Search'})
      .catch(err => {if (err.name === "NavigationDuplicated") {location.reload(); }})

    },
    goToPersonal () {
      this.$router.push({name: 'NewsfeedPersonal', query: { Category: '꽃보다 집'}})
      .catch(err => {if (err.name === "NavigationDuplicated") {location.reload(); }})

    },
    goToProfile () {
      this.$router.push({name: 'MyPage'})
      .catch(err => {if (err.name === "NavigationDuplicated") {location.reload(); }})

    },
    onClickSidebarPersonal(e) {
      this.sideBarToggle(e)
      setTimeout(() => {
      this.$router.push({name: 'NewsfeedPersonal', query: { Category: '꽃보다 집'}})
      .catch(err => {if (err.name === "NavigationDuplicated") {location.reload(); }})
      }, 10);
    },
    onClickSidebarHome(e) {
      this.sideBarToggle(e)
      setTimeout(() => {
        this.$router.push({name: 'Home'})
        .catch(err => {if (err.name === "NavigationDuplicated") {location.reload(); }})
      }, 10);
    },
    onClickSidebarSearch(e) {
      this.sideBarToggle(e)
      setTimeout(() => {
      this.$router.push({name: 'Search'})
      .catch(err => {if (err.name === "NavigationDuplicated") {location.reload(); }})
      }, 10);
    },
    onClickSidebarProfile(e) {
      this.sideBarToggle(e)
      setTimeout(() => {
      this.$router.push({name: 'MyPage'})
      .catch(err => {if (err.name === "NavigationDuplicated") {location.reload(); }})
      }, 10);
    }
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