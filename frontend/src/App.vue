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
    position: relative;
    justify-content: space-between;
    width: 100%;
    /* height: auto; */
    flex-direction: row;
  }
  .nav-content {
    display: none;
  }
  .newsfeed {
    width: 80%;
    padding: 15px;
    margin: 0 auto;
  }
  .nav-toggle-switch {
    display: block; 
  }
}
</style>
