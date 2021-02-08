<template>
  <div>
    <!-- <Nav /> -->
    <router-view :key="$route.fullPath" />
  </div>
</template>

<script>
import {mapState} from 'vuex'
// import Nav from './components/Common/Nav.vue'
import "@/assets/css/main.css"
import "@/assets/css/newsfeed.css"

export default {
  components: { },
  methods: {
    goToPersonal () {
      this.$router.push({name: 'NewsfeedPersonal', query: { Category: '꽃보다 집'}})
    },
    goToProfile () {
      this.$router.push({name: 'MyPage'})
    },
  },
  computed : {
    ...mapState(['isLoginUser'])
  },
  created () {
    
  }
}

</script>
<style>
@media screen and (max-width: 1050px) {
  .side-bar {
    position: fixed;
    top : 0px;
    justify-content: space-between;
    width: 100%;
    height: 80px;
    flex-direction: row;
  }
  .nav-content {
    display: none;
  }
  .nav-logo {
    padding-top: 0px;
  }
  .newsfeed {
    width: 100%;
    margin: 0 auto;
  }
  .newsfeed-header{
    width : 100%;
    margin: 0 auto;
  }
  .nav-toggle-switch {
    display: block; 
  }
}
</style>
